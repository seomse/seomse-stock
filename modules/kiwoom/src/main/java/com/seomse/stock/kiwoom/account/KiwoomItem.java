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
package com.seomse.stock.kiwoom.account;

public class KiwoomItem {
    private String itemCode;
    private String itemName;
    private int itemCount;
    private double avgPrice;
    private int nowPrice;
    private int valuePriceTotal;
    private int profitLossPriceTotal;

    /**
     * 종목 코드
     * @return
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 종목코드
     * @param itemCode String
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 종목명
     * @return
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 종목명
     * @param itemName String
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 종목수량
     * @return
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * 종목수량
     * @param itemCount
     */
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    /**
     * 평단가
     * @return
     */
    public double getAvgPrice() {
        return avgPrice;
    }

    /**
     * 평단가
     * @param avgPrice double
     */
    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    /**
     * 현재가
     * @return
     */
    public int getNowPrice() {
        return nowPrice;
    }

    /**
     * 현재가
     * @param nowPrice int
     */
    public void setNowPrice(int nowPrice) {
        this.nowPrice = nowPrice;
    }

    /**
     * 평가금액
     * @return
     */
    public int getValuePriceTotal() {
        return valuePriceTotal;
    }

    /**
     * 평가금액
     * @param valuePriceTotal int
     */
    public void setValuePriceTotal(int valuePriceTotal) {
        this.valuePriceTotal = valuePriceTotal;
    }

    /**
     * 손익금액
     * @return
     */
    public int getProfitLossPriceTotal() {
        return profitLossPriceTotal;
    }

    /**
     * 손익금액
     * @param profitLossPriceTotal int
     */
    public void setProfitLossPriceTotal(int profitLossPriceTotal) {
        this.profitLossPriceTotal = profitLossPriceTotal;
    }
}
