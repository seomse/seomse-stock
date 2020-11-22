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
import com.seomse.jdbc.naming.JdbcNaming;
import com.seomse.stock.analysis.AnalysisConfig;
import com.seomse.stock.analysis.store.market.MarketDailyNo;

import java.util.List;

/**
 * 국내증시
 * @author macle
 */
public class DomesticMarketStore {



    private final String ymd;

    private final int candleCount;


    //코스피 코스닥
    private DomesticMarket kospiMarket, kosdaqMarket;
    //코스피 200


    //선물 (선물은 종가가 너무적어서 한동안은 매매동향만 사용 함

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
       


        // KPI200

        // 선물 (FUT)

    }
    
    
    private void setDailyCandle(DomesticMarket market){
        List<MarketDailyNo> dailyNoList = JdbcNaming.getObjList(MarketDailyNo.class, "ITEM_CD='" + market.getCode() +"' AND YMD <= '" + ymd + "'",  "YMD DESC", candleCount);
        DomesticMarketDailyCandle [] candles = new DomesticMarketDailyCandle[dailyNoList.size()];
    }



}
