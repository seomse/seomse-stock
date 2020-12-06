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

package com.seomse.stock.trade.backtest.close;

import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.JdbcQuery;
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarketStore;
import com.seomse.stock.trade.AccountStatus;
import com.seomse.stock.trade.HoldStock;
import com.seomse.stock.trade.StockCount;
import com.seomse.stock.trade.strategy.StoreBuyStrategy;
import com.seomse.stock.trade.strategy.StoreSellStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**

 *
 *  종가 매매 벡테스팅이지만 특정 ETF는 리얼타임이 도입될 수 있음
 *
 * 종가 거래관련 백테스팅
 * 매수 대기 종목은
 * @author macle
 */
public class BacktestClosePrice {

    private static final Logger logger = LoggerFactory.getLogger(BacktestClosePrice.class);

    private final StoreBuyStrategy buyStrategy;
    private final StoreSellStrategy sellStrategy;
    private final AccountStatus accountStatus;
    private final String beginYmd;
    private final String endYmd;
    private StoreManager storeManager;

    /**
     * 생성자
     * @param buyStrategy 매수전략
     * @param sellStrategy 매도전략
     * @param accountStatus 계좌현황
     * @param beginYmd 시작 년월일 yyyyMMdd
     * @param endYmd 끝 년월일 yyyyMMdd
     */
    public BacktestClosePrice(StoreBuyStrategy buyStrategy
            , StoreSellStrategy sellStrategy
            , AccountStatus accountStatus
            , String beginYmd
            , String endYmd
    ){
        this.buyStrategy = buyStrategy;
        this.sellStrategy = sellStrategy;
        this.accountStatus =  accountStatus;
        this.beginYmd = beginYmd;
        this.endYmd = endYmd;
    }

    /**
     * 메모리 데이터 저장소 설정
     * @param storeManager 메모리 데이터 저장소
     */
    public void setStoreManager(StoreManager storeManager) {
        this.storeManager = storeManager;
    }

    /**
     * 실행
     */
    public void run(){

        if(storeManager == null){
            storeManager = new StoreManager();
        }

        double startAssets = accountStatus.getAssets(storeManager);

        //거래일 얻기
        //거래일은 코스피 지수의 거래가 있는날짜로 한다
        List<String> ymdList = JdbcQuery.getStringList("SELECT YMD FROM T_STOCK_MARKET_DAILY WHERE MARKET_CD ='KOSPI' AND YMD >= '" +beginYmd +"' AND YMD <= '" + endYmd +"' ORDER BY YMD ASC");
        //시작자산

        DomesticMarketStore domesticMarketStore = storeManager.getDomesticMarketStore(ymdList.get(0));
        double startKospi = domesticMarketStore.getKospiMarket().getClose();
        double startKosdaq = domesticMarketStore.getKosdaqMarket().getClose();

        logger.info("시작 자산: " + (long)startAssets + " 코스피: " + startKospi + " 코스닥: " + startKosdaq );
        logger.info("begin: " + ymdList.get(0) +", end: " + ymdList.get(ymdList.size()-1));

        double lastAssets =startAssets;
        double lastKospi =startKospi;
        double lastKosdaq =startKosdaq;

        for(String ymd: ymdList){

            logger.info("====================== daily change ====================\n\n\n");
            domesticMarketStore = storeManager.getDomesticMarketStore(ymd);
            double kospi = domesticMarketStore.getKospiMarket().getClose();
            double kosdaq = domesticMarketStore.getKosdaqMarket().getClose();

            accountStatus.setTime(YmdUtil.getTime(ymd));

            //매수전 보유정목 전송
            HoldStock[] holdStocks = accountStatus.getHoldStocks();
            
            //매수
            buyStrategy.setYmd(ymd);
            StockCount[] buyStocks = buyStrategy.getBuyStocks(accountStatus);
            for(StockCount stockCount : buyStocks){
                accountStatus.buyStock(stockCount);
                logger.info("buy: " + stockCount.getStock().getName());
            }

            //매도
            sellStrategy.setYmd(ymd);
            StockCount[] sellStocks = sellStrategy.getSellStocks(holdStocks);
            for(StockCount stockCount : sellStocks){
                accountStatus.sellStock(stockCount);
                logger.info("sell: " + stockCount.getStock().getName());
            }



            double assets =  accountStatus.getAssets(storeManager);

            if(assets < 0){
                logger.error("strategy fail assets 0");
                break;
            }

            logger.info(ymd + " 자산: " + (long)accountStatus.getAssets(storeManager)
                    + "\n현금: " + (long)accountStatus.getCash()
                    + "\n총 자산 변화율: " + Math.round((assets - startAssets)/startAssets* 10000.0)/100.0
                    + "\n자산 변화율: " + Math.round((assets - lastAssets)/lastAssets* 10000.0)/100.0
                    + "\n총 코스피 변화율: " + Math.round((kospi - startKospi)/startKospi* 10000.0)/100.0
                    + "\n코스피 변화율: " + Math.round((kospi - lastKospi)/lastKospi* 10000.0)/100.0
                    + "\n총 코스닥 변화율: " + Math.round((kosdaq - startKosdaq)/startKosdaq* 10000.0)/100.0
                    + "\n코스닥 변화율: " + Math.round((kosdaq - lastKosdaq)/lastKosdaq* 10000.0)/100.0
            );
            lastAssets = assets;
            lastKospi = kospi;
            lastKosdaq = kosdaq;

            storeManager.remove(ymd);
        }
    }
}
