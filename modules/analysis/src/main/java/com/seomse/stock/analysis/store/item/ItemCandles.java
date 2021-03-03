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

import com.seomse.trading.technical.analysis.candle.candles.TradeCandles;

import java.util.Map;

/**
 * @author macle
 */
public abstract class ItemCandles {


    protected ItemDailyCandle [] dailyCandles;

    protected Map<Integer, TradeCandles> minuteCandlesMap;
    /**
     *
     * @return 일봉 얻기
     */
    public ItemDailyCandle[] getDailyCandles() {
        return dailyCandles;
    }


    /**
     * @param minute 분봉
     * @return TradeCandles
     */
    public TradeCandles getMinuteCandles(int minute){
        return minuteCandlesMap.get(minute);
    }


    /**
     * 최종 일별 캔들얻기 
     * @return 최근 일별 캔들
     */
    public ItemDailyCandle getLastCandle(){
        return dailyCandles[dailyCandles.length -1];
    }
    
    
    /**
     * 코드 얻기
     * @return 종목 코드
     */
    public abstract String getCode();

}
