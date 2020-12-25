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

package com.seomse.stock.analysis.store.index;

import com.seomse.commons.config.Config;
import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.naming.JdbcNaming;
import com.seomse.jdbc.objects.JdbcObjects;
import com.seomse.stock.analysis.AnalysisConfig;
import com.seomse.stock.analysis.store.DailyPriceChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 마켓 지수
 * 인덱스지수는 늦게 통계되는게 있으므로 -3일까지는 유효한 값으로 판단한다.
 * @author macle
 */
public class MarketIndexStore {

    private static final Logger logger = LoggerFactory.getLogger(MarketIndexStore.class);

    private final String ymd;

    private final int candleCount;

    private final Map<String, MarketIndex> marketIndexMap = new HashMap<>();

    /**
     * 생성자
     * @param ymd String yyyyMMdd
     */
    public MarketIndexStore(String ymd){

        this.ymd = ymd;
        this.candleCount = Config.getInteger(AnalysisConfig.CANDLE_COUNT.key(), (int) AnalysisConfig.CANDLE_COUNT.defaultValue());
        init();
    }


    /**
     * 생성자
     * @param ymd String yyyyMMdd
     * @param candleCount candle max count
     */
    public MarketIndexStore(String ymd, int candleCount){

        this.ymd = ymd;
        this.candleCount = candleCount;
        init();
    }

    private void init(){
        List<MarketIndex> indexList = JdbcObjects.getObjList(MarketIndex.class);


        // -3일 까지만 유효한 규칙으로 한다
        int maxYmd = -1;


        List<MarketIndex> addList = new ArrayList<>();


        for(MarketIndex marketIndex : indexList){
            List<IndexDailyNo> dailyNoList = JdbcNaming.getObjList(IndexDailyNo.class, "INDEX_CD='" + marketIndex.getCode() +"' AND YMD <= '" + ymd + "' AND CLOSE_PRC IS NOT NULL",  "YMD DESC", candleCount);

            if(dailyNoList.size() == 0){
                continue;
            }

            int index = 0;

            DailyPriceChange[] dailies = new DailyPriceChange[dailyNoList.size()];
            for (int i = dailyNoList.size()-1; i > -1; i--) {
                IndexDailyNo indexDailyNo = dailyNoList.get(i);
                DailyPriceChange dailyPriceChange = new DailyPriceChange(indexDailyNo.YMD, indexDailyNo.CLOSE_PRC, indexDailyNo.CHANGE_PRC, indexDailyNo.CHANGE_RT, indexDailyNo.PREVIOUS_PRC);
                dailies[index++] = dailyPriceChange;
            }

            marketIndex.dailies = dailies;
            addList.add(marketIndex);

            int ymdInt = Integer.parseInt(dailies[dailies.length-1].getYmd());

            if( ymdInt > maxYmd){
                maxYmd = ymdInt;
            }

        }

        indexList.clear();
        int minYmd = Integer.parseInt(YmdUtil.getYmd(Integer.toString(maxYmd), -3));

        for(MarketIndex marketIndex : addList) {
            //데이터 적합성 체크

            if(Integer.parseInt(marketIndex.dailies[marketIndex.dailies.length-1].getYmd()) < minYmd){
                logger.trace("skip market: " + marketIndex.getCode() + ", " + marketIndex.getName() +", " + marketIndex.dailies[marketIndex.dailies.length-1].getYmd() + " " + maxYmd);
                continue;
            }


            marketIndexMap.put(marketIndex.code , marketIndex);

        }

        addList.clear();

    }


    /**
     *
     * @param code market index code
     * @return MarketIndex
     */
    public MarketIndex getMarketIndex(String code){
        return marketIndexMap.get(code);
    }


    /**
     *
     * @return 전체 market index
     */
    public MarketIndex[] getMarketIndexArray(){
        return marketIndexMap.values().toArray(new MarketIndex[0]);
    }

    public static void main(String[] args) {
        MarketIndexStore marketIndexStore = new MarketIndexStore("20201118");
        DailyPriceChange [] dailies = marketIndexStore.getMarketIndex("FUND_CREDIT").getDailies();
        for (DailyPriceChange dailyPriceChange : dailies){
            System.out.println(dailyPriceChange.getYmd() +" " + dailyPriceChange.getClose());
        }

    }

}
