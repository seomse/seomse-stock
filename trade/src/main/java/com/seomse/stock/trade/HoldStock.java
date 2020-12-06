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
import com.seomse.stock.analysis.StockType;

/**
 * 보유종목 정보
 * @author macle
 */
public class HoldStock implements Stock {


    StockType type;
    String code;
    String name;

    long count = 0;

    double buySum = 0;
    
    /**
     * 보유종목 설정
     * @param stock 주식정보
     */
    public HoldStock(Stock stock){
        this.type = stock.getType();
        this.code = stock.getCode();
        this.name = stock.getName();
    }

    /**
     * 매수
     * @param count 매수 수량
     * @param price 1개당 가격
     */
    public void buy(long count, double price){
        this.count += count;
        buySum += price * count;
    }

    /**
     * 매도
     * @param count 매도 수량
     * @param price 1개당 가격
     */
    public void sell(long count, double price){
        this.count -= count;
        buySum -= price * count;
    }

    /**
     * 평가 금액 얻기
     * @param price 금액
     * @param fee 수수료
     * @return 평가금액 총액 + 수수료
     */
    public double getEvaluationAmount(double price ,double fee){

        double total  = price * count;
        total -= total*fee;
        return total;
    }

    /**
     * 보유수량 얻기
     * @return 보유수량
     */
    public long getCount() {
        return count;
    }

    /**
     * 총 구매대금 얻기
     * @return 구매대금
     */
    public double getBuySum() {
        return buySum;
    }

    /**
     * 평단가
     * @return 평균 구매가
     */
    public double avgBuy(){
        return buySum/count;
    }

    @Override
    public StockType getType() {
        return type;
    }
    
    

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
