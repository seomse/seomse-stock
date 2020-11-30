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

import com.seomse.stock.analysis.store.TradingTrendDaily;
import com.seomse.stock.analysis.store.market.StockMarket;

/**
 * 선물 마켓
 * KPI200 지수와 연동된걸로 판단됨
 * @author macle
 */
public class FutMarket extends StockMarket {

    TradingTrendDaily[] trendDailies;
    
    FutDaily [] futDailies;
    
    /**
     * 생성자
     */
    public FutMarket(){
        super("FUT", "선물");
    }

    /**
     * 
     * @return 일별 매매동향
     */
    public TradingTrendDaily[] getTrendDailies() {
        return trendDailies;
    }

    /**
     * 한동안은 매매동향만 사용
     * 거래정보가 많지않음
     * 9월 10일부터 있음
     * @return 선물 일별 매매정보
     */
    public FutDaily[] getFutDailies() {
        return futDailies;
    }
}
