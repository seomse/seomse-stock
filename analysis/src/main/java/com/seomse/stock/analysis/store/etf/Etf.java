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

import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;
import com.seomse.stock.analysis.Stock;
import com.seomse.stock.analysis.StockType;
import com.seomse.trading.technical.analysis.candle.candles.TradeCandles;

import java.util.Map;

/**
 * etf
 * @author macle
 */
@Table(name="T_STOCK_ETF")
public class Etf implements Stock {
    @PrimaryKey(seq = 1)
    @Column(name = "ETF_CD")
    String code;
    @Column(name = "ETF_NM")
    String name;

    Map<Long, TradeCandles> candlesMap;
    EtfDailyCandle[] dailyCandles;

    @Override
    public StockType getType() {
        return StockType.ETF;
    }

    /**
     *
     * @return etf 코드
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @return etf 명
     */
    public String getName() {
        return name;
    }
    /**
     *
     * @return 일봉 얻기
     */
    public EtfDailyCandle[] getDailyCandles() {
        return dailyCandles;
    }

    /**
     * @param standardTime 기준시간 5분 15분 읿 등
     * @return TradeCandles
     */
    public TradeCandles getCandles(long standardTime){
        return candlesMap.get(standardTime);
    }
}
