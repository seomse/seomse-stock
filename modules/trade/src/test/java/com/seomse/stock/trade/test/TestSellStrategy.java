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
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.analysis.store.item.Item;
import com.seomse.stock.analysis.store.item.ItemCandles;
import com.seomse.stock.analysis.store.item.ItemDailyCandle;
import com.seomse.stock.analysis.store.item.ItemStore;
import com.seomse.stock.analysis.store.preferred.PreferredStore;
import com.seomse.stock.trade.AccountStatus;
import com.seomse.stock.trade.HoldStock;
import com.seomse.stock.trade.StockCount;
import com.seomse.stock.trade.strategy.StoreSellStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 샘플용 판매전략
 * @author macle
 */
public class TestSellStrategy extends StoreSellStrategy {

    /**
     * 생성자
     *
     * @param storeManager in memory store
     */
    public TestSellStrategy(StoreManager storeManager) {
        super(storeManager);
    }

    @Override
    public StockCount[] getSellStocks(AccountStatus accountStatus) {

        HoldStock [] holdStocks = accountStatus.getHoldStocks();

        List<StockCount> sellList = new ArrayList<>();
        String etfCode = "252670";
        final String upCode = "122630";

        ItemStore itemStore = storeManager.getItemStore(ymd);
        PreferredStore preferredStore = storeManager.getPreferredStore( ymd);
        //인버스를 있으면
        outer:
        for(HoldStock holdStock : holdStocks){
            if(holdStock.getCode().equals(etfCode)){
                //etf 일경우

                if( storeManager.getEtfStore(ymd).getEtf(etfCode).getLastCandle().getChangeRate() < - 0.2){
                    sellList.add(new StockCount(storeManager.getEtfStore(ymd).getEtf(etfCode),holdStock.getCount()));
                }
                continue;
            }

//            if(holdStock.getCode().equals(upCode)){
//                //etf 일경우
//                if( storeManager.getEtfStore(ymd).getEtf(upCode).getLastCandle().getChangeRate() < - 0.2){
//                    sellList.add(new StockCount(storeManager.getEtfStore(ymd).getEtf(upCode),holdStock.getCount()));
//                }
//                continue;
//            }



            ItemCandles itemCandles ;

            Item item = storeManager.getItemStore(ymd).getItem(holdStock.getCode());
            if(item != null){
                itemCandles = item;
            }else{
                itemCandles = storeManager.getPreferredStore(ymd).getPreferred(holdStock.getCode());
            }


            ItemDailyCandle[] dailyCandle = itemCandles.getDailyCandles();
            //2일 연속 하락했으면 매도
            for (int i = dailyCandle.length-2; i <dailyCandle.length ; i++) {
                if( dailyCandle[i].getChange() >= 0){
                    continue outer;
                }
            }
            sellList.add(new StockCount((StockPrice) itemCandles, holdStock.getCount()));

        }

        return sellList.toArray(new StockCount[0]);
    }
}
