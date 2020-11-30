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

package com.seomse.stock.trade;

import com.seomse.stock.analysis.Stock;

/**
 * 종목과 수량
 * @author macle
 */
public class StockCount {
    private final Stock stock;
    private final long count;

    /**
     * 생성자
     * @param stock 종목
     * @param count 수량
     */
    public StockCount(Stock stock, long count){
        this.stock = stock;
        this.count = count;
    }

    /**
     * 주식 종목 얻기
     * @return 거래 가능한 주식 종목
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * 수량 얻기
     * @return 거래 수량 (미래를 생각해서 long으로 잡음)
     */
    public long getCount() {
        return count;
    }

    /**
     * 수량 double 형으로 얻기
     * 가격 계산용
     * @return 거래수량 (double)
     */
    public double getCountDouble(){
        return (double) count;
    }

}
