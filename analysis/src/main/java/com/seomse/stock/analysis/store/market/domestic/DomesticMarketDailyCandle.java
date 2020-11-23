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

import com.seomse.stock.analysis.store.PriceChange;
import com.seomse.stock.analysis.store.TradingTrend;
import com.seomse.trading.technical.analysis.candle.TradeCandle;

/**
 * KOSPI, KOSDAQ 에서 사용할 수 있는 속성
 * 2014년부터 분석한다 2013년 7월 부터 모든 데이터가 있음
 *
 * @author macle
 */
public class DomesticMarketDailyCandle extends TradeCandle implements  TradingTrend, PriceChange {

    String ymd;

    double tradeVolume;
    double tradePriceVolume;

    //기관 매매량
    double institution;
    //외국계 매매량
    double foreign;
    //개인 매매량
    double individual;

    /**
     * 
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

    @Override
    public Double getInstitution() {
        return institution;
    }

    @Override
    public Double getForeign() {
        return foreign;
    }

    @Override
    public Double getIndividual() {
        return individual;
    }


}
