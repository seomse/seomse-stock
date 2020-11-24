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

package com.seomse.stock.analysis.store.market.overseas;

import com.seomse.commons.config.Config;
import com.seomse.commons.utils.time.Times;
import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.naming.JdbcNaming;
import com.seomse.jdbc.objects.JdbcObjects;
import com.seomse.stock.analysis.AnalysisConfig;
import com.seomse.trading.technical.analysis.candle.candles.TradeCandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 해외증시
 * @author macle
 */
public class OverseasMarketStore {

    private static final Logger logger = LoggerFactory.getLogger(OverseasMarketStore.class);

    private final String ymd;

    private final int candleCount;

    private final Map<String, OverseasMarket> marketMap = new HashMap<>();


    /**
     * 생성자
     * @param ymd String yyyyMMdd
     */
    public OverseasMarketStore(String ymd){

        this.ymd = ymd;
        this.candleCount = Config.getInteger(AnalysisConfig.CANDLE_COUNT.key(), (int) AnalysisConfig.CANDLE_COUNT.defaultValue());
        init();
    }


    /**
     * 생성자
     * @param ymd String yyyyMMdd
     * @param candleCount candle max count
     */
    public OverseasMarketStore(String ymd, int candleCount){

        this.ymd = ymd;
        this.candleCount = candleCount;
        init();
    }

    private void init(){
        List<OverseasMarket> marketList = JdbcObjects.getObjList(OverseasMarket.class, "COUNTRY_NM !='한국'");

        int maxYmdInt = -1;

        List<OverseasMarket> addList = new ArrayList<>();

        for(OverseasMarket market : marketList){
            TradeCandles tradeCandles = new TradeCandles(Times.DAY_1);
            //short 0.003(0.3%) // 0.001(0.1%)
            tradeCandles.setShortGapRatio(0.003);
            tradeCandles.setSteadyGapRatio(0.001);
            tradeCandles.setCount(this.candleCount);

            List<OverseasMarketDailyNo> dailyNoList = JdbcNaming.getObjList(OverseasMarketDailyNo.class, "MARKET_CD='" + market.getCode() +"' AND YMD <= '" + ymd + "' AND CLOSE_PRC IS NOT NULL ",  "YMD DESC", candleCount);
            if(dailyNoList.size() == 0){
                logger.debug("daily 0 skip: " + market.getCode() +" " + market.getName());
                continue;
            }


            OverseasMarketDailyCandle[] dailyCandles = new OverseasMarketDailyCandle[dailyNoList.size()];



            int index = 0;
            for (int i = dailyNoList.size()-1; i > -1; i--) {
                OverseasMarketDailyNo dailyNo = dailyNoList.get(i);
                OverseasMarketDailyCandle dailyCandle = new OverseasMarketDailyCandle();
                dailyCandle.setClose(dailyNo.CLOSE_PRC);
                dailyCandle.setOpen(dailyNo.OPEN_PRC);
                dailyCandle.setHigh(dailyNo.HIGH_PRC);
                dailyCandle.setLow(dailyNo.LOW_PRC);
                dailyCandle.setChange(dailyNo.CHANGE_PRC);
                dailyCandle.setPrevious(dailyNo.PREVIOUS_PRC);
                dailyCandle.setVolume(dailyNo.TRADE_VOL);
                dailyCandle.setChangeRate(dailyNo.CHANGE_RT);

                long ymdTime = YmdUtil.getTime(dailyNo.YMD);


                String openHm = market.getOpenHm();
                String closeHm = market.getCloseHm();
                long openTime = ymdTime + Times.HOUR_1 * Long.parseLong(openHm.substring(0,2)) + Times.MINUTE_1 * Long.parseLong(openHm.substring(2,4));
                long closeTime = ymdTime + Times.HOUR_1 * Long.parseLong(closeHm.substring(0,2)) + Times.MINUTE_1 * Long.parseLong(closeHm.substring(2,4));
                if(openTime > closeTime){
                    closeTime += Times.DAY_1;
                }


                dailyCandle.setOpenTime(openTime);
                //15시간 30분 더하기
                dailyCandle.setCloseTime(closeTime);
                dailyCandle.setStandardTime(ymdTime);
                dailyCandle.setEndTrade();

                //주식 전용 정보 생성
                dailyCandle.ymd = dailyNo.YMD;
                dailyCandles[index++] = dailyCandle;

                addList.add(market);

            }

            market.dailyCandles = dailyCandles;

            tradeCandles.addCandle(dailyCandles);
            market.tradeCandles =tradeCandles;

            int ymdInt =  Integer.parseInt(market.dailyCandles[market.dailyCandles.length -1].getYmd());
            if( ymdInt > maxYmdInt){
                maxYmdInt = ymdInt;
            }

            addList.add(market);

        }
        marketList.clear();


        //해외증시는 오픈시간이 있으므로 1일정도 차이가 있을 수 있다고 가정함

        String maxYmd = Integer.toString(maxYmdInt);
        String minYmd = YmdUtil.getYmd(maxYmd, -1);

        int minInt = Integer.parseInt(minYmd);

        for(OverseasMarket market :addList){
            int ymdInt =  Integer.parseInt(market.dailyCandles[market.dailyCandles.length -1].getYmd());
            if( ymdInt < minInt){

                logger.debug(" skip: " + market.getCode() +" " + market.getName() + " " + market.dailyCandles[market.dailyCandles.length -1].getYmd() + " " + maxYmd);
                continue;
            }

            marketMap.put(market.getCode() , market);

        }

    }

    /**
     * 해외 증시 얻기
     * @param code 해외증시코드
     * @return 해외증시
     */
    public OverseasMarket getMarket(String code){
        return marketMap.get(code);
    }

    /**
     * 해외증시 전체 얻기
     * @return 해외증시 전체
     */
    public OverseasMarket [] getMarkets(){
        return marketMap.values().toArray(new OverseasMarket[0]);
    }


    public static void main(String[] args) {
        OverseasMarketStore store = new OverseasMarketStore("20201118");
        OverseasMarketDailyCandle[] dailyCandles = store.getMarket("NAS@NDX").getDailyCandles();
        for(OverseasMarketDailyCandle dailyCandle : dailyCandles){
            System.out.println(dailyCandle.getYmd() +" " + dailyCandle.getClose());
        }
    }
}
