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

package com.seomse.stock.analysis.model.relative.strength;

import com.seomse.commons.callback.ObjCallback;
import com.seomse.commons.utils.time.Times;
import com.seomse.stock.analysis.AnalysisScore;
import com.seomse.stock.analysis.StockScore;
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.analysis.store.item.Item;
import com.seomse.stock.analysis.store.item.ItemStore;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarketStore;
import com.seomse.stock.analysis.store.preferred.Preferred;
import com.seomse.stock.analysis.store.preferred.PreferredStore;
import com.seomse.stock.analysis.store.wics.WicsStore;
import com.seomse.trading.PriceChangeAnalysis;
import com.seomse.trading.PriceChangeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 증시대비 상승이 강한종목
 * 매수 종목 판단시 활용을 위해 제작함
 * 병렬 Thread 지원
 * @author macle
 */
public class StrongerThenMarket implements PriceChangeAnalysis {

    private static final Logger logger = LoggerFactory.getLogger(StrongerThenMarket.class);

    private final String ymd;
    
    //강해지기 시작한 지점 찾기
    int candleCount = 14;

    // 시장대비 높은 가격 1.0이 최대 전체가 10이면 1
    double marketUpCountPer = 0.5;

    //증시대비 합산 상승률
    double marketUpPer = 1.0;


    //상승 캔들 비율 상승날짜 
    double minUpPer =  0.5;

    /**
     * 캔글 개수 설정
     * @param candleCount 캔들개수 최소 3이상 기본값 10 (2주))
     */
    public void setCandleCount(int candleCount) {
        this.candleCount = candleCount;
    }

    /**
     * 시장 대비 높은 가격의 캔들 비율 설정
     * @param marketUpCountPer 시장 대비 높은 가격의 캔들비율 기본값 0.7
     */
    public void setMarketUpCountPer(double marketUpCountPer) {
        this.marketUpCountPer = marketUpCountPer;
    }

    /**
     * 캔들의 전체합이 증시 대비 최소 상승
     * @param marketUpPer 캔들의 상승 합이 증시 대비 상승 최소값
     */
    public void setMarketUpPer(double marketUpPer) {
        this.marketUpPer = marketUpPer;
    }

    /**
     * 가격이 상승한 날 비율
     * @param minUpPer 가격이 상슨한날 비율 설정
     */
    public void setMinUpPer(double minUpPer) {
        this.minUpPer = minUpPer;
    }

    /**
     * 생성자
     * @param ymd String yyyyMMdd
     */
    public StrongerThenMarket(String ymd){
        this.ymd = ymd;
    }

    private int threadCount = -1;
    private ObjCallback endCallback = null;

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


    private StrongerThenMarketModuleScore[] stockScores;
    /**
     * 분석 동기실행
     * @param storeManager 저장소 관리자
     * @return 코드와 점수 배열 score 기준 desc 정렬
     */
    public StockScore [] analysisSync(StoreManager storeManager){

        final Thread t = Thread.currentThread();
        AtomicBoolean isAnalysis = new AtomicBoolean(false);
        endCallback = obj -> {
            isAnalysis.set(true);
            t.interrupt();
        };
        analysis(storeManager);
        //분석이 끝날때까지 대기
        for(;;){
            if(isAnalysis.get()){
                break;
            }
            try {
                //noinspection BusyWait
                Thread.sleep(Times.DAY_1);
            } catch (InterruptedException e) {
                break;
            }
        }

        return stockScores;
    }

    private int analysisThreadCount;

    // 가격 이 증시보다 더 오르거나 덜 떨어지고
    // 장기 매도 중이지 않고
    // 지속 흐름. 최근에 전혼된 종목을 찾찾아본다
    int upCount, marketUpCount;

    /**
     * 분석 비동기 실행
     * @param storeManager 저장소 관리자
     */
    public void analysis(StoreManager storeManager){

        upCount = (int)((double)candleCount*minUpPer);
        marketUpCount = (int)((double)candleCount*marketUpCountPer);
        logger.debug("up count: " + upCount +", market up count: " + marketUpCount);

        itemStore = storeManager.getItemStore(ymd);
        preferredStore = storeManager.getPreferredStore(ymd);
        wicsStore = storeManager.getWicsStore(ymd);
        domesticMarketStore = storeManager.getDomesticMarketStore(ymd);

        items = itemStore.getItems();
        preferredArray = preferredStore.getPreferredArray();

        if(threadCount < 1){
            analysisThreadCount = Math.max(Runtime.getRuntime().availableProcessors() - 1, 1);
        }else{
            analysisThreadCount = threadCount;
        }

        for (int i = 0; i < analysisThreadCount ; i++) {
            StrongerThenMarketAnalysis strongerThenMarketAnalysis =new StrongerThenMarketAnalysis(this);
            new Thread(strongerThenMarketAnalysis).start();
        }

    }

    ItemStore itemStore;
    PreferredStore preferredStore;
    WicsStore wicsStore;
    DomesticMarketStore domesticMarketStore;

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

    private final Object addLock = new Object();
    private final List<StrongerThenMarketModuleScore> addList = new ArrayList<>();

    void add(StrongerThenMarketModuleScore moduleScore ){
        synchronized (addLock){
            addList.add(moduleScore);
        }
    }




    private final Object completeLock = new Object();
    private int completeCount = 0;

    void complete(){
        synchronized (completeLock){
            completeCount++;
            if(completeCount == analysisThreadCount){
                System.out.println("complete");
                //분석 완료

                stockScores = addList.toArray(new StrongerThenMarketModuleScore[0]);
                addList.clear();

                for(StrongerThenMarketModuleScore moduleScore :stockScores ){
                    System.out.println("search: " + "," +moduleScore.getStock().getCode() + ", "+  moduleScore.getStock().getName() +", " + moduleScore.marketUpPer +", " + moduleScore.marketUpCount + ", " + moduleScore.upCount + ", " + moduleScore.rsi);
                }



                Arrays.sort(stockScores, AnalysisScore.SORT_DESC);
                if(endCallback != null){
                    endCallback.callback(stockScores);
                }
            }

        }
    }



    @Override
    public PriceChangeType getPriceChangeType() {
        return PriceChangeType.RISE;
    }


    public static void main(String[] args) {
        StrongerThenMarket strongerThenMarket = new StrongerThenMarket("20201020");
        strongerThenMarket.analysisSync(new StoreManager());
    }
}
