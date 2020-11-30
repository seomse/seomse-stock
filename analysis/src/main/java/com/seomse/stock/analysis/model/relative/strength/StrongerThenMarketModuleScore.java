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

package com.seomse.stock.analysis.model.relative.strength;

import com.seomse.stock.analysis.Stock;
import com.seomse.stock.analysis.StockScore;

/**
 * 검색될 종목 정보
 * @author macle
 */
public class StrongerThenMarketModuleScore extends StockScore {
    int upCount ;
    int marketUpCount;
    double marketUpPer;
    double rsi ;

    /**
     * 생성자
     *
     * @param stock 종목
     * @param score 점수
     */
    public StrongerThenMarketModuleScore(Stock stock, double score) {
        super(stock, score);

    }
}
