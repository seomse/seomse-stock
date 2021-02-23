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

package com.seomse.stock.analysis.store.wics;

import com.seomse.commons.config.Config;
import com.seomse.jdbc.JdbcQuery;
import com.seomse.jdbc.objects.JdbcObjects;
import com.seomse.stock.analysis.AnalysisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * wics별 통계 정보
 * @author macle
 */
public class WicsStore {

    private static final Logger logger = LoggerFactory.getLogger(WicsStore.class);

    private final String ymd;

    private final int candleCount;

    private final Map<String, WicsDaily [] > wicsDailiesMap = new HashMap<>();

    /**
     * 생성자
     * @param ymd String yyyyMMdd
     */
    public WicsStore(String ymd){

        this.ymd = ymd;
        this.candleCount = Config.getInteger(AnalysisConfig.CANDLE_COUNT.key(), (int) AnalysisConfig.CANDLE_COUNT.defaultValue());
        init();
    }


    /**
     * 생성자
     * @param ymd String yyyyMMdd
     * @param candleCount candle max count
     */
    public WicsStore(String ymd, int candleCount){

        this.ymd = ymd;
        this.candleCount = candleCount;
        init();
    }

    private void init(){
        //wics 종류 구하기

        String maxYmd = JdbcQuery.getResultOne("SELECT MAX(YMD) FROM T_STOCK_WICS_DAILY WHERE YMD <='" +ymd +"'");

        if(maxYmd == null){
            logger.error("ymd null.." );
            return ;
        }

        List<String> wicsList = JdbcQuery.getStringList("SELECT WICS_NM FROM T_STOCK_WICS_DAILY WHERE YMD='" + maxYmd +"'");

        for(String wics : wicsList){
            List<WicsDaily>  dailyList = JdbcObjects.getObjList(WicsDaily.class, "WICS_NM ='" + wics +"' AND YMD <='" +ymd +"'", "YMD DESC", candleCount);
            WicsDaily [] dailies = new WicsDaily[dailyList.size()];
            int index = 0;
            for (int i = dailyList.size()-1; i > -1; i--) {
                dailies[index++] = dailyList.get(i);
            }
            wicsDailiesMap.put(wics, dailies);
        }

    }

    /**
     * 
     * @param wics wics aud
     * @return WicsDaily [] 일별 정보
     */
    public WicsDaily [] getDailies(String wics){
        return wicsDailiesMap.get(wics);
    }

    /**
     * wics names 얻기
     * @return wics names
     */
    public String [] getWicsNames(){
        return wicsDailiesMap.keySet().toArray(new String[0]);
    }

    public static void main(String[] args) {
        WicsStore wicsStore = new WicsStore("20201118");
        WicsDaily [] dailies = wicsStore.getDailies("도로와철도운송");

        for (WicsDaily wicsDaily : dailies){
            System.out.println( wicsDaily.ymd +" " + wicsDaily.averageRate +" " + wicsDaily.middle80AverageRate);
        }
    }

}
