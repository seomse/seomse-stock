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

package com.seomse.stock.analysis.store.etf;

import com.seomse.stock.analysis.store.PriceChange;
import com.seomse.stock.analysis.store.TradingTrend;
import com.seomse.trading.technical.analysis.candle.TradeCandle;

/**
 * @author macle
 */
public class EtfDailyCandle  extends TradeCandle implements TradingTrend, PriceChange {


    String ymd;

    //기관 매매량
    Double institution;
    //외국계 매매량
    Double foreign;
    Double foreignBalanceVolume;
    Double foreignRate;

    //개인 매매량
    Double individual;


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

}
