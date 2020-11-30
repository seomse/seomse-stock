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

import com.seomse.stock.analysis.store.market.StockMarket;

/**
 * 국내증시
 * @author macle
 */
public class DomesticMarket extends StockMarket{

    private DomesticMarketDailyCandle [] candles;

    /**
     * 생성자
     * @param code 증시코드
     * @param name 증시이름
     */
    public DomesticMarket(String code, String name){
        super(code,name);
    }

    /**
     * 증시 일별 캔들 배열 얻기
     * @return 증시 일별 캔들 (YMD DESC)
     */
    public DomesticMarketDailyCandle[] getCandles() {
        return candles;
    }

    /**
     * 증시 일별 캔들배열 설정 (YMD DESC)
     * @param candles 증시 일별 캔들
     */
    void setCandles(DomesticMarketDailyCandle[] candles) {
        this.candles = candles;
    }
}
