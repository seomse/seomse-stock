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


import com.seomse.stock.analysis.store.TradingTrend;
import com.seomse.trading.technical.analysis.candle.TradeCandle;

/**
 * 주식 일봉
 * @author macle
 */
public class ItemDailyCandle extends TradeCandle implements TradingTrend {

    //아래 항목은 없을수도 있으니 null을 허용하는 객체형으로 변수화 함
    String ymd;

    //기관 매매량
    Double institution;
    //외국계 매매량
    Double foreign;
    Double foreignBalanceVolume;
    Double foreignRate;

    //개인 매매량
    Double individual;

    //대차 체결량
    Double slb;
    //대차 상환
    Double slbRepay;
    //대차잔고
    Double slbBalance;
    //공매도
    Double shortSelling;
    //공매도 잔고
    Double shortSellingBalance;

    Double creditBalanceRate;
    Double creditTotalVolume;
    Double creditNewVolume;
    Double creditRepayVolume;
    Double creditBalanceVolume;
    Double creditPriceVolume;
    Double creditChangeVolume;
    Double creditExposureRate;

    /**
     *
     * @return yyyyMMdd
     */
    public String getYmd() {
        return ymd;
    }

    @Override
    public Double getInstitution() {
        return institution;
    }
    @Override
    public Double getForeign() {
        return foreign;
    }

    /**
     *
     * @return 외귝안 보유량
     */
    public Double getForeignBalanceVolume() {
        return foreignBalanceVolume;
    }

    /**
     *
     * @return 외귝안 보유율
     */
    public Double getForeignRate() {
        return foreignRate;
    }
    @Override
    public Double getIndividual() {
        return individual;
    }

    /**
     *
     * @return 대차체결량
     */
    public Double getSlb() {
        return slb;
    }

    /**
     *
     * @return 대차생환량
     */
    public Double getSlbRepay() {
        return slbRepay;
    }

    /**
     *
     * @return 대차잔고
     */
    public Double getSlbBalance() {
        return slbBalance;
    }

    /**
     *
     * @return 공매도량
     */
    public Double getShortSelling() {
        return shortSelling;
    }

    /**
     *
     * @return 공매도잔고
     */
    public Double getShortSellingBalance() {
        return shortSellingBalance;
    }


    /**
     *
     * @return 신용잔고율
     */
    public Double getCreditBalanceRate() {
        return creditBalanceRate;
    }

    /**
     *
     * @return 신용거래량
     */
    public Double getCreditTotalVolume() {
        return creditTotalVolume;
    }

    /**
     *
     * @return 신용 신규량
     */
    public Double getCreditNewVolume() {
        return creditNewVolume;
    }

    /**
     *
     * @return 신용 상환량
     */
    public Double getCreditRepayVolume() {
        return creditRepayVolume;
    }

    /**
     *
     * @return 신용 잔고량
     */
    public Double getCreditBalanceVolume() {
        return creditBalanceVolume;
    }

    /**
     *
     * @return 신용금액
     */
    public Double getCreditPriceVolume() {
        return creditPriceVolume;
    }

    /**
     *
     * @return 신용변동
     */
    public Double getCreditChangeVolume() {
        return creditChangeVolume;
    }

    /**
     *
     * @return 신용 공여
     */
    public Double getCreditExposureRate() {
        return creditExposureRate;
    }
}
