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

import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarket;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarketDailyCandle;
import com.seomse.stock.trade.AccountStatus;
import com.seomse.stock.trade.backtest.close.BacktestClosePrice;

/**
 * @author macle
 */
public class Backtest {

    public static void main(String[] args) {


        String beginYmd = "20200401";
        String endYmd ="20201203";

        AccountStatus accountStatus = new AccountStatus(YmdUtil.getTime(beginYmd));
        accountStatus.addCash(100000000.0);

        StoreManager storeManager = new StoreManager();


//        DomesticMarket domesticMarket = storeManager.getDomesticMarketStore("20200401").getKospiMarket();
//        DomesticMarketDailyCandle[] candles = domesticMarket.getCandles();
//        for(DomesticMarketDailyCandle candle :candles){
//            System.out.println(candle.getYmd());
//        }


        TestBuyStrategy testBuyStrategy = new TestBuyStrategy(storeManager);
        TestSellStrategy testSellStrategy = new TestSellStrategy(storeManager);

        BacktestClosePrice backtestClosePrice = new BacktestClosePrice(testBuyStrategy, testSellStrategy, accountStatus, beginYmd, endYmd);
        backtestClosePrice.setStoreManager(storeManager);
        backtestClosePrice.run();



    }
}
