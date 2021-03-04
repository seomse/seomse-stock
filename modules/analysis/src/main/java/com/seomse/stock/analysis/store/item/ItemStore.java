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

package com.seomse.stock.analysis.store.item;

import com.seomse.commons.config.Config;
import com.seomse.commons.utils.time.Times;
import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.PrepareStatements;
import com.seomse.jdbc.naming.JdbcNaming;
import com.seomse.jdbc.objects.JdbcObjects;
import com.seomse.stock.analysis.AnalysisConfig;
import com.seomse.stock.analysis.fundamental.QuantYmd;
import com.seomse.trading.technical.analysis.candle.candles.TradeCandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    private final int candleCount;

    private final Map<String, Item> itemMap = new HashMap<>();


    /**
     * 생성자
     * @param ymd String yyyyMMdd
     */
    public ItemStore(String ymd){

        this.ymd = ymd;
        this.candleCount = Config.getInteger(AnalysisConfig.CANDLE_COUNT.key(), (int) AnalysisConfig.CANDLE_COUNT.defaultValue());
        init();
    }


    /**
     * 생성자
     * @param ymd String yyyyMMdd
     * @param candleCount candle max count
     */
    public ItemStore(String ymd, int candleCount){

        this.ymd = ymd;
        this.candleCount = candleCount;
        init();
    }


    private void init(){
        long time = YmdUtil.getTime(ymd);
        List<Item> stockItemList = JdbcObjects.getObjList(Item.class, "(DELISTING_DT < ? OR DELISTING_DT IS NULL) AND LISTING_YMD <= '" + ymd +"'", PrepareStatements.newTimeMap(time));

        int maxYmdInt = -1;

        List<Item> addList = new ArrayList<>();

        for(Item item : stockItemList){


            //데이터가 주기적으로 수집되지 않았기때문에 minYm을 사용함/ 나중에는 데이터 등록일시를 활용할 예정
            String minYm = QuantYmd.getMinYm(ymd.substring(0,6));

            //지금에는 추정치까지 활용하지는 않음
            List<FinancialStatements> yearFinancialStatementsList = JdbcObjects.getObjList(FinancialStatements.class, "ITEM_CD ='"+item.getCode() +"'  AND SETTLEMENT_YM <= '" + minYm + "' AND SETTLEMENT_TP='YEAR'" );
            item.yearFinancialStatementsArray = yearFinancialStatementsList.toArray(new FinancialStatements[0]);

            List<FinancialStatements> quarterFinancialStatementsList = JdbcObjects.getObjList(FinancialStatements.class, "ITEM_CD ='"+item.getCode() +"'  AND SETTLEMENT_YM <= '" + minYm + "' AND SETTLEMENT_TP='QUARTER'" );
            item.quarterFinancialStatementsArray = quarterFinancialStatementsList.toArray(new FinancialStatements[0]);
            setCandles(item, ymd, candleCount);

            if(item.dailyCandles.length==0){
                logger.trace("skip item: " + item.getCode() +" " + item.getName());
                continue;
            }

            addList.add(item);

            int ymdInt = Integer.parseInt(item.dailyCandles[item.dailyCandles.length-1].getYmd());

            if( ymdInt > maxYmdInt){
                maxYmdInt = ymdInt;
            }
        }
        stockItemList.clear();

        String maxYmd = Integer.toString(maxYmdInt);

        for(Item item : addList){

            //데이터 적합성 체크
            if(!item.dailyCandles[item.dailyCandles.length-1].getYmd().equals(maxYmd)){
                logger.trace("skip item: " + item.getCode() +" " + item.getName() + " " + item.dailyCandles[item.dailyCandles.length-1].getYmd() + " " + maxYmd) ;
                continue;
            }

            itemMap.put(item.getCode(), item );
        }
        addList.clear();

        logger.info("itemMap size: " + itemMap.size());
    }

    /**
     * 년월일 얻기 yyyyMMdd
     * @return String (yyyyMMdd)
     */
    public String getYmd() {
        return ymd;
    }

    /**
     * 종목얻기
     * @param code String (종목코드)
     * @return Item (종목정보)
     */
    public Item getItem(String code){
        return itemMap.get(code);
    }

    /**
     * 모든 종목얻기
     * @return 모든 종목정보
     */
    public Item [] getItems(){
        return itemMap.values().toArray(new Item[0]);
    }

    /**
     * 
     * @return 분석에 사용하는 캔들 개수
     */
    public int getCandleCount() {
        return candleCount;
    }


    /**
     * 캔들설정
     * @param itemCandles 종목
     * @param ymd yyyyMMdd
     * @param candleCount 캔들 수
     */
    public static void setCandles(ItemCandles itemCandles, String ymd, int candleCount){
        TradeCandles tradeCandles = new TradeCandles(Times.DAY_1);
        //short 0.003(0.3%) // 0.001(0.1%)
        tradeCandles.setShortGapRatio(0.003);
        tradeCandles.setSteadyGapRatio(0.001);
        tradeCandles.setCount(candleCount);

        //jdbc 기능을 이용해서 size로 끊어서 가져오므로 역순정렬을 해야함 (최신것부터 count가 필요하므로)
        List<DailyNo> dailyNoList = JdbcNaming.getObjList(DailyNo.class, "ITEM_CD='" + itemCandles.getCode() +"' AND YMD <= '" + ymd + "' AND CLOSE_PRC IS NOT NULL AND INSTITUTION_TRADE_VOL IS NOT NULL",  "YMD DESC", candleCount);

        ItemDailyCandle[] dailyCandles = new ItemDailyCandle[dailyNoList.size()];

        int index = 0;

        for (int i = dailyNoList.size()-1; i > -1; i--) {
            DailyNo dailyNo = dailyNoList.get(i);
            ItemDailyCandle dailyCandle = new ItemDailyCandle();
            dailyCandle.setClose(dailyNo.CLOSE_PRC);
            dailyCandle.setOpen(dailyNo.OPEN_PRC);
            dailyCandle.setHigh(dailyNo.HIGH_PRC);
            dailyCandle.setLow(dailyNo.LOW_PRC);
            dailyCandle.setChange(dailyNo.CHANGE_PRC);
            dailyCandle.setPrevious(dailyNo.PREVIOUS_PRC);
            dailyCandle.setVolume(dailyNo.TRADE_VOL);
            dailyCandle.setStrength(dailyNo.STRENGTH_RT);
            dailyCandle.setChangeRate(dailyNo.CHANGE_RT);

            long ymdTime = YmdUtil.getTime(dailyNo.YMD);

            // 9부터 15시 30분이므로 시간값 맞추기
            //9시간 더하기
            dailyCandle.setOpenTime(ymdTime + Times.HOUR_3*3);
            //15시간 30분 더하기
            dailyCandle.setCloseTime(ymdTime + Times.HOUR_3*5 + Times.MINUTE_30);
            dailyCandle.setStandardTime(ymdTime);
            dailyCandle.setEndTrade();

            //주식 전용 정보 생성
            dailyCandle.ymd = dailyNo.YMD;
            dailyCandle.institution = dailyNo.INSTITUTION_TRADE_VOL;
            dailyCandle.foreign = dailyNo.FOREIGN_TRADE_VOL;
            dailyCandle.foreignRate = dailyNo.FOREIGN_RT;
            dailyCandle.foreignBalanceVolume = dailyNo.FOREIGN_BALANCE_VOL;
            dailyCandle.individual = dailyNo.INDIVIDUAL_TRADE_VOL;

            dailyCandle.slb = dailyNo.SLB_VOL;
            dailyCandle.slbRepay = dailyNo.SLB_REPAY_VOL;
            dailyCandle.slbBalance = dailyNo.SLB_BALANCE_VOL;

            dailyCandle.shortSelling = dailyNo.SHORT_SELLING_VOL;
            dailyCandle.shortSellingBalance = dailyNo.SHORT_SELLING_BALANCE_VOL;

            dailyCandle.creditBalanceRate = dailyNo.CREDIT_BALANCE_RT;
            dailyCandle.creditTotalVolume = dailyNo.CREDIT_TOTAL_VOL;
            dailyCandle.creditNewVolume = dailyNo.CREDIT_NEW_VOL;
            dailyCandle.creditRepayVolume = dailyNo.CREDIT_REPAY_VOL;
            dailyCandle.creditBalanceVolume = dailyNo.CREDIT_BALANCE_VOL;
            dailyCandle.creditPriceVolume = dailyNo.CREDIT_PRC_VOL;
            dailyCandle.creditChangeVolume = dailyNo.CREDIT_CHANGE_VOL;
            dailyCandle.creditExposureRate = dailyNo.CREDIT_EXPOSURE_RT;

            dailyCandles[index++] = dailyCandle;

        }

        itemCandles.minuteCandlesMap = new HashMap<>();
        itemCandles.dailyCandles = dailyCandles;
        tradeCandles.addCandle(dailyCandles);
    }

    public static void main(String[] args) {
        ItemStore itemStore = new ItemStore("20201118");
        ItemDailyCandle [] dailyCandles = itemStore.getItem("005930").getDailyCandles();
        for(ItemDailyCandle dailyCandle : dailyCandles){
            System.out.println(dailyCandle.getYmd() +" " + dailyCandle.getClose());
        }
    }
}
