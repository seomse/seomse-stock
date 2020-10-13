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

package com.seomse.stock.store.item;

import com.seomse.commons.config.Config;
import com.seomse.commons.utils.time.Times;
import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.PrepareStatements;
import com.seomse.jdbc.objects.JdbcObjects;
import com.seomse.stock.StockConfig;
import com.seomse.stock.fundamental.analysis.FinancialStatements;
import com.seomse.stock.fundamental.analysis.QuantYmd;
import com.seomse.trading.technical.analysis.candle.candles.TradeCandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 개별 종목들 데이터 저장소
 * @author macle
 */
public class ItemStore {

    private static final Logger logger = LoggerFactory.getLogger(ItemStore.class);

    private final String ymd;

    private final Map<String, Item> itemMap = new HashMap<>();

    private final int candleCount;

    /**
     * 생성자
     * @param ymd String yyyyMMdd
     */
    public ItemStore(String ymd){

        this.ymd = ymd;
        this.candleCount = Config.getInteger(StockConfig.CANDLE_COUNT.key(), (int) StockConfig.CANDLE_COUNT.defaultValue());
        init();
    }


    /**
     * 생성자
     * @param ymd String yyyyMMdd
     * @param candleCount int
     */
    public ItemStore(String ymd, int candleCount){

        this.ymd = ymd;
        this.candleCount = candleCount;
        init();
    }


    void init(){
        long time = YmdUtil.getTime(ymd);
        List<Item> stockItemList = JdbcObjects.getObjList(Item.class, "(DELISTING_DT < ? OR DELISTING_DT IS NULL) AND LISTING_YMD <= '" + ymd +"'", PrepareStatements.newTimeMap(time));


        for(Item item : stockItemList){
            itemMap.put(item.getCode(), item );

            //데이터가 주기적으로 수집되지 않았기때문에 minYm을 사용함/ 나중에는 데이터 등록일시를 활용할 예정
            String minYm = QuantYmd.getMinYm(ymd.substring(0,6));

            //지금에는 추정치까지 활용하지는 않음
            List<FinancialStatements> yearFinancialStatementsList = JdbcObjects.getObjList(FinancialStatements.class, "ITEM_CD ='"+item.getCode() +"'  AND SETTLEMENT_YM <= '" + minYm + "' AND SETTLEMENT_TP='YEAR' AND ESTIMATE_FG='N'" );
            item.yearFinancialStatementsArray = yearFinancialStatementsList.toArray(new FinancialStatements[0]);

            List<FinancialStatements> quarterFinancialStatementsList = JdbcObjects.getObjList(FinancialStatements.class, "ITEM_CD ='"+item.getCode() +"'  AND SETTLEMENT_YM <= '" + minYm + "' AND SETTLEMENT_TP='QUARTER' AND ESTIMATE_FG='N'" );
            item.quarterFinancialStatementsArray = yearFinancialStatementsList.toArray(new FinancialStatements[0]);

            TradeCandles tradeCandles = new TradeCandles(Times.DAY_1);
            tradeCandles.setSaveCount(this.candleCount);

//            List<>

            //일봉 캔들정보 추가
            
//            QuantYmd.getMinYm(minYm);


        }
    }


    /**
     * 년월일 얻기 yyyyMMdd
     * @return String
     */
    public String getYmd() {
        return ymd;
    }

    public static void main(String[] args) {
        new ItemStore("20201013");




    }

}
