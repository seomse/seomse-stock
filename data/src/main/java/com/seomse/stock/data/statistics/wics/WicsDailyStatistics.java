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

package com.seomse.stock.data.statistics.wics;

import com.seomse.commons.utils.AverageUtil;
import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.objects.JdbcObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Wics 별 일별통계
 * @author macle
 */
public class WicsDailyStatistics {

    private static final Logger logger = LoggerFactory.getLogger(WicsDailyStatistics.class);

    /**
     * 업데이트
     * @param startYmd yyyyMMdd 시작 년원일
     * @param endYmd yyyyMMdd 종료 년원일
     */
    public static void update(String startYmd, String endYmd){

        try {

            List<String> ymdList = YmdUtil.getYmdList(startYmd, endYmd);

            List<WicsStockItem> wicsItemList = JdbcObjects.getObjList(WicsStockItem.class, "WICS_NM IS NOT NULL");

            Map<String, String> codeWicsMap = new HashMap<>();

            for(WicsStockItem wicsStockItem : wicsItemList){
                codeWicsMap.put(wicsStockItem.code, wicsStockItem.wics);
            }

            for(String ymd : ymdList){

                Map<String, List<Double>> wicsPriceMap = new HashMap<>();

                List<DailyChangeRate> closeList = JdbcObjects.getObjList(DailyChangeRate.class, "YMD='" + ymd +"' AND CHANGE_RT IS NOT NULL");

                for(DailyChangeRate dailyClose : closeList){
                    String wics = codeWicsMap.get(dailyClose.code);
                    if(wics == null){
//                        logger.error("wics null: " + dailyClose.code);
                        continue;
                    }

                    List<Double> doubles = wicsPriceMap.computeIfAbsent(wics, k -> new ArrayList<>());
                    doubles.add(dailyClose.rate);
                }

                if(wicsPriceMap.size() == 0){
                    logger.info("wics null: " + ymd);
                    continue;
                }


                Set<String> wicsSet = wicsPriceMap.keySet();

                WicsDaily wicsDaily = new WicsDaily();
                wicsDaily.ymd = ymd;

                for(String wics : wicsSet){
                    wicsDaily.wics = wics;
                    List<Double> doubleList = wicsPriceMap.get(wics);

                    double [] doubles = new double [doubleList.size()];
                    for (int i = 0; i < doubles.length; i++) {
                        doubles[i] = doubleList.get(i);
                    }

                    wicsDaily.avg = (double)Math.round(AverageUtil.getAverage(doubles)*100.0)/100.0;

                    if(doubles.length < 5){
                        wicsDaily.avg80 = wicsDaily.avg;
                    }else{
                        wicsDaily.avg80 = (double)Math.round(AverageUtil.getAverageDistribution(doubles, 0.1)*100.0)/100.0;
                    }

                    JdbcObjects.insertOrUpdate(wicsDaily, false);

                }

                logger.info("wics complete: " + ymd);
            }


        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) {

        update("20201117", "20201119");
    }

}
