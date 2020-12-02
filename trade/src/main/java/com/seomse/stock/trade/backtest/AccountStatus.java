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

package com.seomse.stock.trade.backtest;

import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.stock.analysis.Stock;
import com.seomse.stock.analysis.StockType;
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.analysis.store.preferred.Preferred;
import com.seomse.stock.trade.StockCount;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 계좌보유현황 잔고현황등
 * @author macle
 */
public class AccountStatus {



    private long time;
    private final Map<String, StockCount> holdStockMap = new HashMap<>();
    //한국돈은 소수점이 없음 //해외주식까지 연계할때 double 형으로 변경
    private double cash = 0.0;

    /**
     * 생성자
     * @param time unix time
     */
    public AccountStatus(long time){
        this.time = time;
    }

    /**
     * 시간설정 
     * @param time unix time
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * 금액설정
     * @param cash 금액
     */
    public void setCash(double cash) {
        this.cash = cash;
    }

    private final Object lock = new Object();

    /**
     * 현금 변화량 추가
     * @param cash 현금 변화량
     */
    public void addCash(long cash){
        synchronized (lock) {
            this.cash += cash;
        }
    }


    /**
     * 보유종목 추가 / 변경
     * @param stockCount 신규 보유종목 또는 변화된 보유종목
     */
    public void addStock(StockCount stockCount){
        
    }

    /**
     * 보유종목 제거
     * @param stockCode 보유종목 코드
     */
    public StockCount removeStock(String stockCode){
        synchronized (lock){
            return holdStockMap.remove(stockCode);
        }
    }
    
    
    /**
     * 자산얻기
     * @param storeManager 데이터 메모리 저장소
     * @return 자산총액 현금 + 보유주식
     */
    public double getAssets(StoreManager storeManager){
        final String ymd = YmdUtil.getYmd(time);

        synchronized (lock){

            double stockPriceSum = 0L;
            Collection<StockCount> stockCounts = holdStockMap.values();

            for(StockCount stockCount : stockCounts){
                double price;
                Stock stock = stockCount.getStock();

                if(stockCount.getStock().getType() == StockType.ITEM){
                    if(stock instanceof Preferred){
                        //우선주이면
                        price = storeManager.getPreferredStore(ymd).getPreferred(stock.getCode()).getLastCandle().getClose();


                    }else{
                        //일반주이면
                        price = storeManager.getItemStore(ymd).getItem(stock.getCode()).getLastCandle().getClose();}

                }else{
                    //etf
                    price = storeManager.getEtfStore(ymd).getEtf(stock.getCode()).getLastCandle().getClose();
                }
                stockPriceSum += price * stockCount.getCount();
            }

            return cash + stockPriceSum;
        }
        
    }

    /**
     * 보유종목 얻기
     * @return 보유종목 전체
     */
    public StockCount [] getHoldStocks (){
        synchronized (lock){
            return holdStockMap.values().toArray(new StockCount[0]);
        }
    }

    /**
     * 시간얻기
     * @return unix time
     */
    public long getTime(){
        return time;
    }

    /**
     * 년월일 얻기
     * @return yyyyMMdd
     */
    public String getYmd(){
        return YmdUtil.getYmd(time);
    }

}
