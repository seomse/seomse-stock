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

package com.seomse.stock.analysis.store.market.overseas;

import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.Table;
import com.seomse.stock.analysis.store.market.StockMarket;
import com.seomse.trading.technical.analysis.candle.TradeCandle;
import com.seomse.trading.technical.analysis.candle.candles.TradeCandles;

/**
 * 해외증시
 * @author macle
 */
@Table(name="T_STOCK_MARKET")
public class OverseasMarket  extends StockMarket {


    @Column(name = "COUNTRY_NM")
    private String country;
    @Column(name = "OPEN_HM")
    private String openHm;

    @Column(name = "CLOSE_HM")
    private String closeHm;

    @Column(name = "EXCHANGE_NM")
    private String exchange;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "TRADE_TIME_DS")
    private String tradeTimeDescription;
    @Column(name = "INDEX_DS")
    private String indexDescription;


    OverseasMarketDailyCandle [] dailyCandles;
    //해외증시는 일봉정보만 있으므로(아직은)
    TradeCandles tradeCandles;

    /**
     * 생성자
     *
     * @param code 증시코드
     * @param name 증시이름
     */
    public OverseasMarket(String code, String name) {
        super(code, name);
    }

    /**
     * jdbc에서 자동생성용
     * 생성자
     */
    public OverseasMarket() {

    }

    /**
     * 국가 이름
     * @return 국가 (name)
     */
    public String getCountry() {
        return country;
    }

    /**
     * 개장시간 (시분)
     * @return HHmm
     */
    public String getOpenHm() {
        return openHm;
    }

    /**
     * 장 종료시간 (시분)
     * @return HHmm
     */
    public String getCloseHm() {
        return closeHm;
    }

    /**
     * 소래소 이름
     * @return 거래소 (name)
     */
    public String getExchange() {
        return exchange;
    }

    /**
     *
     * @return 기준통화
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 거래시간 설명
     * 점심시간이 에 거래를 못하는 국가들이 많음
     * @return 거래시간 서술
     */
    public String getTradeTimeDescription() {
        return tradeTimeDescription;
    }

    /**
     * 증시에 대한 설명 서술
     * @return 지수 서술
     */
    public String getIndexDescription() {
        return indexDescription;
    }

    /**
     *
     * @return 일봉 얻기
     */
    public OverseasMarketDailyCandle [] getDailyCandles() {
        return dailyCandles;
    }

    /**
     *
     * @return TradeCandles 얻기 (봉배열정보)
     */
    public TradeCandles getTradeCandles() {
        return tradeCandles;
    }
}
