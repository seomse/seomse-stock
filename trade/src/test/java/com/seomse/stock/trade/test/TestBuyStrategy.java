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

package com.seomse.stock.trade.test;

import com.seomse.stock.analysis.StockPrice;
import com.seomse.stock.analysis.StockScore;
import com.seomse.stock.analysis.model.relative.strength.StrongerThenMarket;
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarket;
import com.seomse.stock.trade.AccountStatus;
import com.seomse.stock.trade.StockCount;
import com.seomse.stock.trade.strategy.StoreBuyStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author macle
 */
public class TestBuyStrategy extends StoreBuyStrategy {

    /**
     * 종목당 매수금액
     */
    private final double buyPrice = 1000000.0;

    /**
     * 생성자
     * @param storeManager in memory store
     */
    public TestBuyStrategy(StoreManager storeManager) {
        super(storeManager);
    }

    @Override
    public StockCount[] getBuyStocks(AccountStatus accountStatus) {
        StrongerThenMarket strongerThenMarket = new StrongerThenMarket(ymd);
        StockScore[] stocks = strongerThenMarket.analysisSync(storeManager);
//
        double hedging = accountStatus.getAssets(storeManager) * 0.25;

        double cash = accountStatus.getCash();
        if(cash < hedging){
            hedging = cash;
        }
//
//
        List<StockCount> buyList = new ArrayList<>();
//
        for(StockScore stockScore : stocks){

            if(cash <= 30000000.0){
                break;
            }

            if(accountStatus.getStockCount(stockScore.getStock().getCode()) > 0L ){
                continue;
            }

            StockPrice stock = (StockPrice)stockScore.getStock();

            long count = (long)(buyPrice/stock.getClose());

            buyList.add(new StockCount(stock, count));
        }

        final String etfCode = "252670";

        //인버스를 돌고있지 않으면
        long count = accountStatus.getStockCount(etfCode);

        DomesticMarket kospi = storeManager.getDomesticMarketStore(ymd).getKospiMarket();



        if(
                //기능 테스트용 간단 소스
                count == 0L
              ){

            if(storeManager.getEtfStore(ymd).getEtf(etfCode).getLastCandle().getChangeRate() > 0.0){
                long etfCount = (long) (hedging / storeManager.getEtfStore(ymd).getEtf(etfCode).getLastCandle().getClose());

                buyList.add(new StockCount(storeManager.getEtfStore(ymd).getEtf(etfCode), etfCount));
            }

        }
//
//        final String upCode = "122630";
//        count = accountStatus.getStockCount(upCode);
//        //증시가 상승하면
//        if(count == 0L) {
//
//            if(storeManager.getEtfStore(ymd).getEtf(upCode).getLastCandle().getChangeRate() > 0.0){
//                long etfCount = (long) (hedging / storeManager.getEtfStore(ymd).getEtf(upCode).getLastCandle().getClose());
//
//                buyList.add(new StockCount(storeManager.getEtfStore(ymd).getEtf(upCode), etfCount));
//            }
//
//
//
//        }

        return buyList.toArray(new StockCount[0]);
    }
}
