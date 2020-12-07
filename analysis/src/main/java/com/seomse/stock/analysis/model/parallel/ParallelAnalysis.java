/*
 * Copyright (C) 2020 Seomse Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seomse.stock.analysis.model.parallel;

import com.seomse.commons.callback.ObjCallback;
import com.seomse.commons.utils.time.Times;
import com.seomse.jdbc.JdbcQuery;
import com.seomse.stock.analysis.AnalysisScore;
import com.seomse.stock.analysis.StockScore;
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.analysis.store.etf.Etf;
import com.seomse.stock.analysis.store.etf.EtfStore;
import com.seomse.stock.analysis.store.item.Item;
import com.seomse.stock.analysis.store.item.ItemStore;
import com.seomse.stock.analysis.store.preferred.Preferred;
import com.seomse.stock.analysis.store.preferred.PreferredStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 국내시장 에서의 병렬처리
 * 병렬 Thread 지원
 * @author macle
 */
public class ParallelAnalysis  {

    private static final Logger logger = LoggerFactory.getLogger(ParallelAnalysis.class);

    protected final String ymd;
    protected final StockAnalyst stockAnalyst;

    protected final StoreManager storeManager;
    /**
     * 생성자
     * @param ymd String yyyyMMdd
     * @param storeManager memory 데이터 저장소
     * @param stockAnalyst 분석체 구현 클래스
     */
    public ParallelAnalysis(String ymd
            , StoreManager storeManager
            ,  StockAnalyst stockAnalyst

    ){
        //최근 거래일로 설정한다
        //최근 거래일은 kospi 거래일과 비교하여 세팅
        this.ymd = JdbcQuery.getResultOne("SELECT MAX(YMD) FROM T_STOCK_MARKET_DAILY WHERE MARKET_CD ='KOSPI' AND YMD <= '" + ymd +"'");
        logger.debug("set ymd: " + this.ymd);
        this.storeManager = storeManager;
        this.stockAnalyst = stockAnalyst;
    }

    protected int threadCount = -1;
    protected ObjCallback endCallback = null;

    /**
     * 분석 thread count 설정
     * 설정하지 않으면 availableProcessors -1
     * @param threadCount analysis thread count
     */
    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * 종료 이벤트 받기
     * @param endCallback end callback
     */
    public void setEndCallback(ObjCallback endCallback) {
        this.endCallback = endCallback;
    }


    private StockScore[] stockScores;

    /**
     * 메모리 데이터 저장소 얻기
     * @return memory 데이터 저장소
     */
    public StoreManager getStoreManager() {
        return storeManager;
    }

    /**
     * 분석 동기실행
     * @return 코드와 점수 배열 score 기준 desc 정렬
     */
    public StockScore [] analysisSync(){

        final Thread t = Thread.currentThread();
        AtomicBoolean isAnalysis = new AtomicBoolean(false);
        endCallback = obj -> {
            isAnalysis.set(true);
            t.interrupt();
        };
        analysis();
        //분석이 끝날때까지 대기
        for(;;){
            if(isAnalysis.get()){
                break;
            }
            try {
                //noinspection BusyWait
                Thread.sleep(Times.DAY_1);
            } catch (InterruptedException ignore) {
                break;
            }
        }

        return stockScores;
    }

    private int analysisThreadCount;

    /**
     * 분석 비동기 실행
     */
    public void analysis(){

        ItemStore itemStore = storeManager.getItemStore(ymd);
        items = itemStore.getItems();

        PreferredStore preferredStore = storeManager.getPreferredStore(ymd);
        preferredArray = preferredStore.getPreferredArray();

        EtfStore etfStore = storeManager.getEtfStore(ymd);
        etfs = etfStore.getEtfs();


        if(threadCount < 1){
            analysisThreadCount = Math.max(Runtime.getRuntime().availableProcessors() - 1, 1);
        }else{
            analysisThreadCount = threadCount;
        }

        for (int i = 0; i < analysisThreadCount ; i++) {
            ParallelAnalyst parallelAnalyst = new ParallelAnalyst(this);
            new Thread(parallelAnalyst).start();
        }

    }


    private Item [] items;
    private int itemIndex = 0;
    private final Object itemLock = new Object();
    Item getItem(){
        synchronized (itemLock){
            if(itemIndex >= items.length){
                return null;
            }
            return items[itemIndex++];
        }
    }

    private Preferred [] preferredArray;
    private int preferredIndex = 0;
    private final Object preferredLock = new Object();
    Preferred getPreferred(){
        synchronized (preferredLock){
            if(preferredIndex >= preferredArray.length){
                return null;
            }
            return preferredArray[preferredIndex++];
        }
    }

    private Etf[] etfs;
    private int etfIndex = 0;
    private final Object etfLock = new Object();
    Etf getEtf(){
        synchronized (etfLock){
            if(etfIndex >= etfs.length){
                return null;
            }
            return etfs[etfIndex++];
        }
    }

    private final Object addLock = new Object();
    private final List<StockScore> addList = new ArrayList<>();

    void add(StockScore stockScore ){
        synchronized (addLock){
            addList.add(stockScore);
        }
    }

    private final Object completeLock = new Object();
    private int completeCount = 0;

    void complete(){
        synchronized (completeLock){
            completeCount++;
            if(completeCount == analysisThreadCount){
                //분석 완료
                stockScores = addList.toArray(new StockScore[0]);
                addList.clear();

                Arrays.sort(stockScores, AnalysisScore.SORT_DESC);
                if(endCallback != null){
                    endCallback.callback(stockScores);
                }
            }
        }
    }
}