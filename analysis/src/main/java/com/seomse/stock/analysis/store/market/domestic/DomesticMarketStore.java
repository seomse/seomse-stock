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

import com.seomse.commons.config.Config;
import com.seomse.stock.analysis.AnalysisConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 국내증시
 * @author macle
 */
public class DomesticMarketStore {


    private final String ymd;

    private final int candleCount;

    private final Map<String, DomesticMarket> marketMap = new HashMap<>();

    /**
     * 생성자
     * @param ymd String yyyyMMdd
     */
    public DomesticMarketStore(String ymd){
        this.ymd = ymd;
        this.candleCount = Config.getInteger(AnalysisConfig.CANDLE_COUNT.key(), (int) AnalysisConfig.CANDLE_COUNT.defaultValue());
        init();
    }

    /**
     * 생성자
     * @param ymd String yyyyMMdd
     * @param candleCount candle max count
     */
    public DomesticMarketStore(String ymd, int candleCount){
        this.ymd = ymd;
        this.candleCount = candleCount;
        init();
    }

    
    private void init(){

    }




}
