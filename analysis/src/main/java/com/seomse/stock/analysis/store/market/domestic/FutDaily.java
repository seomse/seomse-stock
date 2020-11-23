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
import com.seomse.stock.analysis.store.TradingTrendDaily;

/**
 * 국내 선물 일별정보
 * KOSPI 200 을 따르는것으로 판단됨
 * 한동은 매매동향만 사용하지만 가격정보가 있을때는 사용
 * 9월10부터 있음
 * @author macle
 */
public class FutDaily extends TradingTrendDaily implements PriceChange {


    /**
     * 생성자
     *
     * @param ymd         yyyyMMdd
     * @param institution 기관 매매동향
     * @param foreign     외국계 매매동향
     * @param individual  개인 매매동향
     */
    public FutDaily(String ymd, double institution, double foreign, double individual) {
        super(ymd, institution, foreign, individual);
    }

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
