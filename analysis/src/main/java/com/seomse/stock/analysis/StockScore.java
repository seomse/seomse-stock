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

package com.seomse.stock.analysis;

/**
 * 종목과 점수
 * @author macle
 */
public class StockScore implements AnalysisScore{
    protected final Stock stock;
    protected final double score;

    /**
     * 생성자
     * @param stock 종목
     * @param score 점수
     */
    public StockScore(Stock stock, double score){
        this.stock = stock;
        this.score = score;
    }

    /**
     * 주식 종목 얻기
     * @return 거래 가능한 주식 종목
     */
    public Stock getStock() {
        return stock;
    }

    @Override
    public double getScore() {
        return score;
    }
}
