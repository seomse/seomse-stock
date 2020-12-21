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
import com.seomse.jdbc.JdbcQuery;
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.trade.AccountStatus;
import com.seomse.stock.trade.backtest.close.BacktestClosePrice;

import java.util.List;

/**
 * @author macle
 */
public class Backtest {

    public static void main(String[] args) {


        String beginYmd = "20200401";
        String endYmd ="20201203";

        AccountStatus accountStatus = new AccountStatus(YmdUtil.getTime(beginYmd));
        accountStatus.addCash(100000000.0);

        final StoreManager storeManager = new StoreManager();
        List<String> ymdList = JdbcQuery.getStringList("SELECT YMD FROM T_STOCK_MARKET_DAILY WHERE MARKET_CD ='KOSPI' AND YMD >= '" +beginYmd +"' AND YMD <= '" + endYmd +"' ORDER BY YMD ASC");

        new Thread(() -> {
            //분석속도 차이가 클떄만
            //거래일 얻기
            //거래일은 코스피 지수의 거래가 있는날짜로 한다
            ymdList.remove(0);
            //2번째 메모리 부터 미리 생성해놓기
            for(String ymd : ymdList){
                System.out.println("*************************************** ymd memory create: " + ymd);
                //메모리 정보 미리 생성하기 (사용하는 정보만)
                storeManager.getItemStore(ymd);
                storeManager.getPreferredStore(ymd);
                storeManager.getEtfStore(ymd);
                storeManager.getDomesticMarketStore(ymd);
//                storeManager.getOverseasMarketStore(ymd);
//                storeManager.getMarketIndexStore(ymd);
//                storeManager.getWicsStore(ymd);
            }
        }).start();
//

        TestBuyStrategy testBuyStrategy = new TestBuyStrategy(storeManager);
        TestSellStrategy testSellStrategy = new TestSellStrategy(storeManager);

        BacktestClosePrice backtestClosePrice = new BacktestClosePrice(testBuyStrategy, testSellStrategy, accountStatus, beginYmd, endYmd);
        backtestClosePrice.setStoreManager(storeManager);
        backtestClosePrice.run();



    }
}
