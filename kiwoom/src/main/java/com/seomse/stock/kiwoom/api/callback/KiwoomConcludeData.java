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
package com.seomse.stock.kiwoom.api.callback;

public class KiwoomConcludeData {
    /**
     *
     [OnReceiveChejan()이벤트로 전달되는 FID목록정리]
     "9201" : "계좌번호"
     "9203" : "주문번호"
     "9001" : "종목코드"
     "302" : "종목명"
     "900" : "주문수량"
     "901" : "주문가격"
     "906" : "매매구분"
     "908" : "주문/체결시간"
     "909" : "체결번호"
     "910" : "체결가"
     "911" : "체결량"
     "913" : "주문상태"
     */
    String depositNumber;
    String orderNumber;
    String itemCode;
    String itemName;
    int orderCount;
    int orderPrice;
    String tradeType;
    String concludeTime;
    String concludeNumber;
    int concludePrice;
    int concludeCount;
    int depositVolume;

    /**
     * 계좌번호
     * @return String
     */
    public String getDepositNumber() {
        return depositNumber;
    }

    /**
     * 계좌번호
     * @param depositNumber
     */
    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }

    /**
     * 주문번호
     * @return String
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * 주문번호
     * @param orderNumber
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 종목코드
     * @return String
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 종목코드
     * @param itemCode
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 종목명
     * @return String
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 종목명
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 주문수량
     * @return String
     */
    public int getOrderCount() {
        return orderCount;
    }

    /**
     * 주문수량
     * @param orderCount
     */
    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    /**
     * 주문가격
     * @return String
     */
    public int getOrderPrice() {
        return orderPrice;
    }

    /**
     * 주문가격
     * @param orderPrice
     */
    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * 매매구분
     * @return String
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * 매매구분
     * @param tradeType
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 체결시간
     * @return String
     */
    public String getConcludeTime() {
        return concludeTime;
    }

    /**
     * 체결시간
     * @param concludeTime
     */
    public void setConcludeTime(String concludeTime) {
        this.concludeTime = concludeTime;
    }

    /**
     * 체결번호
     * @return String
     */
    public String getConcludeNumber() {
        return concludeNumber;
    }

    /**
     * 체결번호
     * @param concludeNumber
     */
    public void setConcludeNumber(String concludeNumber) {
        this.concludeNumber = concludeNumber;
    }

    /**
     * 체결가
     * @return String
     */
    public int getConcludePrice() {
        return concludePrice;
    }

    /**
     * 체결가
     * @param concludePrice
     */
    public void setConcludePrice(int concludePrice) {
        this.concludePrice = concludePrice;
    }

    /**
     * 체결량
     * @return String
     */
    public int getConcludeCount() {
        return concludeCount;
    }

    /**
     * 체결량
     * @param concludeCount
     */
    public void setConcludeCount(int concludeCount) {
        this.concludeCount = concludeCount;
    }

    /**
     * 예수금
     * @return int
     */
    public int getDepositVolume() {
        return depositVolume;
    }

    /**
     * 예수금
     * @param depositVolume
     */
    public void setDepositVolume(int depositVolume) {
        this.depositVolume = depositVolume;
    }
}
