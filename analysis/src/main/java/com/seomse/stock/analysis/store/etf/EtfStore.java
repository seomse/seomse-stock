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

package com.seomse.stock.analysis.store.etf;

import com.seomse.commons.config.Config;
import com.seomse.commons.utils.time.Times;
import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.PrepareStatements;
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
 * @author macle
 */
public class EtfStore {

    private static final Logger logger = LoggerFactory.getLogger(EtfStore.class);

    private final String ymd;

    private final int candleCount;

    private final Map<String, Etf> etfMap = new HashMap<>();
    /**
     * 생성자
     * @param ymd String yyyyMMdd
     */
    public EtfStore(String ymd){

        this.ymd = ymd;
        this.candleCount = Config.getInteger(AnalysisConfig.CANDLE_COUNT.key(), (int) AnalysisConfig.CANDLE_COUNT.defaultValue());
        init();
    }


    /**
     * 생성자
     * @param ymd String yyyyMMdd
     * @param candleCount candle max count
     */
    public EtfStore(String ymd, int candleCount){

        this.ymd = ymd;
        this.candleCount = candleCount;
        init();
    }


    private void init(){
        long time = YmdUtil.getTime(ymd);
        List<Etf> etfList = JdbcObjects.getObjList(Etf.class, "DELISTING_DT < ? OR DELISTING_DT IS NULL", PrepareStatements.newTimeMap(time));


        List<Etf> addList = new ArrayList<>();
        int maxYmdInt = -1;
        for(Etf etf : etfList){

            TradeCandles tradeCandles = new TradeCandles(Times.DAY_1);
            //short 0.003(0.3%) // 0.001(0.1%)
            tradeCandles.setShortGapRatio(0.003);
            tradeCandles.setSteadyGapRatio(0.001);
            tradeCandles.setCount(this.candleCount);


            int index = 0;
            //jdbc 기능을 이용해서 size로 끊어서 가져오므로 역순정렬을 해야함 (최신것부터 count가 필요하므로)
            List<EtfDailyNo> dailyNoList = JdbcNaming.getObjList(EtfDailyNo.class, "ETF_CD='" + etf.code +"' AND YMD <= '" + ymd + "' AND CLOSE_PRC IS NOT NULL AND INSTITUTION_TRADE_VOL IS NOT NULL",  "YMD DESC", candleCount);

            if(dailyNoList.size() == 0){
                logger.debug("daily 0 skip: " + etf.getCode() +" " + etf.getName());
                continue;
            }

            EtfDailyCandle[] dailyCandles = new EtfDailyCandle[dailyNoList.size()];
            for (int i = dailyNoList.size()-1; i > -1; i--) {
                EtfDailyNo dailyNo = dailyNoList.get(i);

                EtfDailyCandle dailyCandle = new EtfDailyCandle();
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
                dailyCandles[index++] = dailyCandle;
            }
            int ymdInt = Integer.parseInt(dailyCandles[dailyCandles.length-1].getYmd());

            if( ymdInt > maxYmdInt){
                maxYmdInt = ymdInt;
            }

            etf.candlesMap = new HashMap<>();
            etf.candlesMap.put(Times.DAY_1, tradeCandles);
            etf.dailyCandles = dailyCandles;

            addList.add(etf);

        }

        String maxYmd = Integer.toString(maxYmdInt);


        for(Etf etf : addList){
            //데이터 적합성 체크
            if(!etf.dailyCandles[etf.dailyCandles.length-1].getYmd().equals(maxYmd)){
                logger.debug("skip: " + etf.getCode() +" " + etf.getName()+ " " + etf.dailyCandles[etf.dailyCandles.length-1].getYmd() +" " + maxYmd);
                continue;
            }

            etfMap.put(etf.code, etf);
        }


        etfList.clear();

    }

    /**
     * etf 얻기
     * @param etfCode code
     * @return Etf
     */
    public Etf getEtf(String etfCode){
        return etfMap.get(etfCode);
    }

    /**
     * 모든 etf 얻기
     * @return 모든 etf
     */
    public Etf [] getEtfs(){
        return etfMap.values().toArray(new Etf[0]);
    }
    public static void main(String[] args) {
        EtfStore store = new EtfStore("20201118");
        EtfDailyCandle[] dailyCandles = store.getEtf("252670").getDailyCandles();
        for(EtfDailyCandle dailyCandle : dailyCandles){
            System.out.println(dailyCandle.getYmd() +" " + dailyCandle.getClose());
        }
    }
}
