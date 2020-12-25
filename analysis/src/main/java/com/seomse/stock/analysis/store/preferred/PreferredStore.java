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

package com.seomse.stock.analysis.store.preferred;

import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.PrepareStatements;
import com.seomse.jdbc.naming.JdbcNaming;
import com.seomse.stock.analysis.store.item.Item;
import com.seomse.stock.analysis.store.item.ItemDailyCandle;
import com.seomse.stock.analysis.store.item.ItemStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 우선주 저장소
 * 우선주 분석은 개별종목 분석과 같이 이루어지므로 ItemStore 에 의존성이 있음
 * @author macle
 */
public class PreferredStore {
    
    private static final Logger logger = LoggerFactory.getLogger(PreferredStore.class);
    
    private final String ymd;

    private final Map<String, Preferred> preferredMap = new HashMap<>();
    
    /**
     * 우선주 분석은 개별종목 분석과 같이 이루어지므로 ItemStore 에 의존성이 있음
     * @param itemStore 개별정목 정보
     */
    public PreferredStore(ItemStore itemStore){
        ymd = itemStore.getYmd();
        int candleCount = itemStore.getCandleCount();


        List<Preferred> addList = new ArrayList<>();
        int maxYmdInt = -1;
        //기본데이터 세팅
        long time = YmdUtil.getTime(ymd);
        List<PreferredNo> preferredNoList = JdbcNaming.getObjList(PreferredNo.class, "DELISTING_DT < ? OR DELISTING_DT IS NULL", PrepareStatements.newTimeMap(time));



        for(PreferredNo preferredNo : preferredNoList){
            Item item = itemStore.getItem(preferredNo.PARENTS_CD);
            if(item == null){
                logger.trace("item null skip: " + preferredNo.ITEM_CD +", " + preferredNo.PARENTS_CD);
                continue;
            }
            
            Preferred preferred = new Preferred();
            preferred.code = preferredNo.ITEM_CD;
            preferred.name = preferredNo.ITEM_CD;
            preferred.listingCount = preferredNo.LISTING_CNT;
            preferred.item = item;

            //일봉세팅
            ItemStore.setCandles(preferred, ymd, candleCount);

            if(preferred.getDailyCandles().length == 0){
                logger.trace("daily 0 skip: " + preferred.getCode() +" " + preferred.getName());
                continue;
            }

            int ymdInt = Integer.parseInt(preferred.getDailyCandles()[preferred.getDailyCandles().length-1].getYmd());

            if( ymdInt > maxYmdInt) {
                maxYmdInt = ymdInt;
            }

            addList.add(preferred);
        }

        preferredNoList.clear();

        String maxYmd = Integer.toString(maxYmdInt);

        for(Preferred preferred : addList){
            //데이터 적합성 체크
            if(!preferred.getDailyCandles()[preferred.getDailyCandles().length-1].getYmd().equals(maxYmd)){
                logger.debug("skip " + preferred.getCode() + " " + preferred.getName() +" " + preferred.getDailyCandles()[preferred.getDailyCandles().length-1].getYmd() + " " + maxYmd);
                continue;
            }


            preferredMap.put(preferred.code, preferred);

        }


        addList.clear();

    }


    /**
     * 년월일 얻기 yyyyMMdd
     * @return String (yyyyMMdd)
     */
    public String getYmd() {
        return ymd;
    }

    /**
     * 우선주 얻기
     * @param code 종목코드
     * @return 우선주
     */
    public Preferred getPreferred(String code){
        return preferredMap.get(code);
    }

    /**
     * 우선주 전체 얻기
     * @return 우선주 전체
     */
    public Preferred [] getPreferredArray(){
        return preferredMap.values().toArray(new Preferred[0]);
    }


    public static void main(String[] args) {
        String ymd = "20201118";
        ItemStore itemStore = new ItemStore(ymd);
        PreferredStore preferredStore = new PreferredStore(itemStore);

        ItemDailyCandle[] dailyCandles = preferredStore.getPreferred("005935").getDailyCandles();
        for(ItemDailyCandle dailyCandle : dailyCandles){
            System.out.println(dailyCandle.getYmd() +" " + dailyCandle.getClose());
        }
    }
}
