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

package com.seomse.stock.analysis.store.market.domestic;

import com.seomse.commons.config.Config;
import com.seomse.commons.utils.time.Times;
import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.naming.JdbcNaming;
import com.seomse.stock.analysis.AnalysisConfig;
import com.seomse.stock.analysis.store.TradingTrendDaily;
import com.seomse.stock.analysis.store.market.MarketDailyNo;

import java.util.List;

/**
 * 국내증시
 * @author macle
 */
public class DomesticMarketStore {



    private final String ymd;

    private final int candleCount;


    //코스피 코스닥은 전체정보가 있음 캔들정보 가격정보 매매동향정보
    private DomesticMarket kospiMarket, kosdaqMarket;
    
    //코스피 200은 가격정보와 거래량만 존재함
    private Kpi200Market kpi200Market;

    //선물 (선물은 종가가 너무적어서 한동안은 매매동향만 사용 함
    private FutMarket futMarket;

    //코스닥100
    //데이터가 잘 안만들어진 문제로 나중에 사용함




    /**
     * 생성자
     * @param ymd String yyyyMMdd
     */
    public DomesticMarketStore(String ymd){
        this.ymd = ymd;
        this.candleCount = Config.getInteger(AnalysisConfig.CANDLE_COUNT.key(), (int) AnalysisConfig.CANDLE_COUNT.defaultValue());
        init();
    }

    /**
     * 생성자
     * @param ymd String yyyyMMdd
     * @param candleCount candle max count
     */
    public DomesticMarketStore(String ymd, int candleCount){
        this.ymd = ymd;
        this.candleCount = candleCount;
        init();
    }

    
    private void init(){
        //코스피, 코스닥
        kospiMarket = new DomesticMarket("KOSPI","코스피");
        setDailyCandle(kospiMarket);

        kosdaqMarket = new DomesticMarket("KOSDAQ","코스닥");
        setDailyCandle(kosdaqMarket);

        // KPI200
        kpi200Market = new Kpi200Market();
        List<MarketDailyNo> dailyNoList = JdbcNaming.getObjList(MarketDailyNo.class, "ITEM_CD='" + kpi200Market.getCode() +"' AND YMD <= '" + ymd + "'  AND CLOSE_PRC IS NOT NULL",  "YMD DESC", candleCount);
        Kpi200Daily [] dailies = new Kpi200Daily[dailyNoList.size()];
        int index = 0;
        for (int i = dailyNoList.size()-1; i > -1; i--) {
            MarketDailyNo dailyNo = dailyNoList.get(i);
            Kpi200Daily daily = new Kpi200Daily();
            daily.close = dailyNo.getCLOSE_PRC();
            daily.change = dailyNo.getCHANGE_PRC();
            daily.changeRate = dailyNo.getCHANGE_RT();
            daily.previous = dailyNo.getPREVIOUS_PRC();
            daily.tradeVolume = dailyNo.getTRADE_VOL();
            daily.tradePriceVolume = dailyNo.getTRADE_PRC_VOL();

            dailies[index++] = daily;
        }
        kpi200Market.dailies = dailies;
        dailyNoList.clear();

        // 선물 (FUT)
        futMarket = new FutMarket();
        dailyNoList = JdbcNaming.getObjList(MarketDailyNo.class, "ITEM_CD='" + futMarket.getCode() +"' AND YMD <= '" + ymd + "'  AND CLOSE_PRC IS NOT NULL",  "YMD DESC", candleCount);

        TradingTrendDaily [] trendDailies = new TradingTrendDaily[dailyNoList.size() ];
        for (int i = dailyNoList.size()-1; i > -1; i--) {
            MarketDailyNo dailyNo = dailyNoList.get(i);
            TradingTrendDaily tradingTrendDaily = new TradingTrendDaily(dailyNo.getYMD(), dailyNo.getINSTITUTION_TRADE_VOL(), dailyNo.getFOREIGN_TRADE_VOL(), dailyNo.getINDIVIDUAL_TRADE_VOL() );

            trendDailies[i] = tradingTrendDaily;
        }
        futMarket.dailies = trendDailies;
        dailyNoList.clear();
    }
    
    
    private void setDailyCandle(DomesticMarket market){

        //거래정보가 다 있는것만 활용한다.
        //AND OPEN_PRC IS NOT NULL AND CLOSE_PRC IS NOT NULL AND INSTITUTION_TRADE_VOL IS NOT NULL 이정도 정보를 추가로 활용하면 있는데이터만 뽑아올 수 있음
        List<MarketDailyNo> dailyNoList = JdbcNaming.getObjList(MarketDailyNo.class, "ITEM_CD='" + market.getCode() +"' AND YMD <= '" + ymd + "' AND OPEN_PRC IS NOT NULL AND CLOSE_PRC IS NOT NULL AND INSTITUTION_TRADE_VOL IS NOT NULL",  "YMD DESC", candleCount);
        DomesticMarketDailyCandle [] candles = new DomesticMarketDailyCandle[dailyNoList.size()];

        int index = 0;
        for (int i = dailyNoList.size()-1; i > -1; i--) {
            MarketDailyNo dailyNo = dailyNoList.get(i);
            DomesticMarketDailyCandle dailyCandle = new DomesticMarketDailyCandle();
            dailyCandle.setClose(dailyNo.getCLOSE_PRC());
            dailyCandle.setOpen(dailyNo.getOPEN_PRC());
            dailyCandle.setHigh(dailyNo.getHIGH_PRC());
            dailyCandle.setLow(dailyNo.getLOW_PRC());
            dailyCandle.setChange(dailyNo.getCHANGE_PRC());
            dailyCandle.setPrevious(dailyNo.getPREVIOUS_PRC());
            //증시는 거래량을 거래대금으로 사용한다.
            dailyCandle.setVolume(dailyNo.getTRADE_PRC_VOL());
            dailyCandle.setChangeRate(dailyNo.getCHANGE_RT());



            long ymdTime = YmdUtil.getTime(dailyNo.getYMD());

            // 9부터 15시 30분이므로 시간값 맞추기
            //9시간 더하기
            dailyCandle.setStartTime(ymdTime + Times.HOUR_3*3);
            //15시간 30분 더하기
            dailyCandle.setEndTime(ymdTime + Times.HOUR_3*5 + Times.MINUTE_30);
            dailyCandle.setStandardTime(ymdTime);
            dailyCandle.setEndTrade();

            dailyCandle.tradeVolume = dailyNo.getTRADE_VOL();
            dailyCandle.tradePriceVolume = dailyNo.getTRADE_PRC_VOL();

            dailyCandle.institution = dailyNo.getINSTITUTION_TRADE_VOL();
            dailyCandle.foreign = dailyNo.getFOREIGN_TRADE_VOL();
            dailyCandle.individual = dailyNo.getINDIVIDUAL_TRADE_VOL();
            candles[index++] = dailyCandle;
        }
        market.setCandles(candles);
        dailyNoList.clear();
    }



}
