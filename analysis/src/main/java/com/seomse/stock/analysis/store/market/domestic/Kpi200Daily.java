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


import com.seomse.trading.PriceChange;

/**
 * 코스피 200 일별정보
 * @author macle
 */
public class Kpi200Daily implements PriceChange {

    String ymd;

    double close;
    double change;

    double changeRate;
    double previous;

    double tradeVolume;
    double tradePriceVolume;

    @Override
    public double getClose() {
        return close;
    }

    @Override
    public double getChange() {
        return change;
    }

    @Override
    public double getChangeRate() {
        return changeRate;
    }

    @Override
    public double getPrevious() {
        return previous;
    }

    /**
     * 년월일 얻기
     * @return yyyyMMdd
     */
    public String getYmd() {
        return ymd;
    }

    /**
     * 
     * @return 거래량
     */
    public double getTradeVolume() {
        return tradeVolume;
    }

    /**
     * 
     * @return 거래대금
     */
    public double getTradePriceVolume() {
        return tradePriceVolume;
    }
}
