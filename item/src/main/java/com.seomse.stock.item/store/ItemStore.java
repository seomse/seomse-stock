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

package com.seomse.stock.item.store;

import com.seomse.commons.config.Config;
import com.seomse.commons.utils.time.Times;
import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.PrepareStatements;
import com.seomse.jdbc.naming.JdbcNaming;
import com.seomse.jdbc.objects.JdbcObjects;
import com.seomse.stock.item.ItemConfig;
import com.seomse.stock.item.fundamental.analysis.QuantYmd;
import com.seomse.trading.technical.analysis.candle.TradeCandle;
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
        this.candleCount = Config.getInteger(ItemConfig.CANDLE_COUNT.key(), (int) ItemConfig.CANDLE_COUNT.defaultValue());
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
            //short 0.003(0.3%) // 0.001(0.1%)
            tradeCandles.setShortGapRatio(0.003);
            tradeCandles.setSteadyGapRatio(0.001);
            tradeCandles.setCount(this.candleCount);

            //jdbc 기능을 이용해서 size로 끊어서 가져오므로 역순정렬을 해야함 (최신것부터 count가 필요하므로)
            List<DailyNo> dailyNoList = JdbcNaming.getObjList(DailyNo.class, "ITEM_CD='" + item.getCode() +"' AND YMD <= '" + ymd + "'",  "YMD DESC", candleCount);

            TradeCandle[] tradeCandleArray = new TradeCandle[dailyNoList.size()];

            int index = 0;

            for (int i = dailyNoList.size()-1; i > -1; i--) {
                DailyNo dailyNo = dailyNoList.get(i);
                DailyCandle dailyCandle = new DailyCandle();
                dailyCandle.setClose(dailyNo.CLOSE_PRC);
                dailyCandle.setOpen(dailyNo.OPEN_PRC);
                dailyCandle.setHigh(dailyNo.HIGH_PRC);
                dailyCandle.setLow(dailyNo.LOW_PRC);
                dailyCandle.setVolume(dailyNo.TRADE_VOL);
                long ymdTime = YmdUtil.getTime(dailyNo.YMD);
                //9시간 더하기
                dailyCandle.setStartTime(ymdTime + Times.HOUR_3*3);
                //15시간 30분 더하기
                dailyCandle.setEndTime(ymdTime + Times.HOUR_3*5 + Times.MINUTE_30);
                dailyCandle.setStandardTime(ymdTime);
                dailyCandle.setEndTrade();

                //주식 전용 정보 생성
                dailyCandle.ymd = dailyNo.YMD;
                dailyCandle.institution = dailyNo.INSTITUTION_TRADE_VOL;
                dailyCandle.foreign = dailyNo.FOREIGN_TRADE_VOL;
                dailyCandle.foreigner = dailyNo.FOREIGNER_TRADE_VOL;
                dailyCandle.individual = dailyNo.INDIVIDUAL_TRADE_VOL;
                dailyCandle.program = dailyNo.PROGRAM_TRADE_VOL;
                dailyCandle.slb = dailyNo.SLB_VOL;
                dailyCandle.slbRepay = dailyNo.SLB_REPAY_VOL;
                dailyCandle.slbBalance = dailyNo.SLB_BALANCE_VOL;
                dailyCandle.shortSelling = dailyNo.SHORT_SELLING_BALANCE_VOL;
                dailyCandle.shortSellingBalance = dailyNo.SHORT_SELLING_BALANCE_VOL;
                dailyCandle.creditRatio = dailyNo.CREDIT_RT;
                tradeCandleArray[index++] = dailyCandle;
            }

            item.timeCandlesMap = new HashMap<>();
            item.timeCandlesMap.put(Times.DAY_1, tradeCandles);
            tradeCandles.addCandle(tradeCandleArray);
        }

        System.out.println(itemMap.size());

    }

    /**
     * 년월일 얻기 yyyyMMdd
     * @return String
     */
    public String getYmd() {
        return ymd;
    }

    public static void main(String[] args) {
        new ItemStore("20201015");
    }
}
