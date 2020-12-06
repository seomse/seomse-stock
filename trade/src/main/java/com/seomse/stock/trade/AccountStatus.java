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

import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.stock.analysis.StockType;
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.analysis.store.item.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * 개별종목 수수료
 *  매수 수수료 0.015%
 *  매도 수수료 0.015%
 *  매도 세금 0.25%
 *
 *  ETF 수수료
 *  헷지로 사용할 kodex 200선물인버스2x 매수 0.01, 매도 0.01
 *  모든 ETF 매수 0.01, 매도 0.01로 동작하게함
 * 계좌보유현황 잔고현황등
 * @author macle
 */
public class AccountStatus {


    private long time;
    private final Map<String, HoldStock> holdStockMap = new HashMap<>();
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
    public void addCash(double cash){
        synchronized (lock) {
            this.cash += cash;
        }
    }

    /**
     * 보유종목 추가
     * 관련기능은 있던 종목을 추가할 경우 정보가 교체됨
     * @param holdStock 보유종목
     */
    public void addStock(HoldStock holdStock){
        synchronized (lock){
            holdStockMap.put(holdStock.getCode(), holdStock);
        }
    }

    /**
     * 보유종목 제거
     * @param stockCode 보유종목 코드
     */
    public HoldStock removeStock(String stockCode){
        synchronized (lock){
            return holdStockMap.remove(stockCode);
        }
    }

    /**
     * 종목구매
     * @param stockCount 신규 보유종목 또는 변화된 보유종목
     */
    public void buyStock(StockCount stockCount){
        synchronized (lock){

            HoldStock holdStock = holdStockMap.get(stockCount.getCode());

            if(holdStock == null){
                holdStock = new HoldStock(stockCount.getStock());
                holdStockMap.put(stockCount.getCode(), holdStock);
            }

            // 구매 수수료
            double fee;

            if(holdStock.getType() == StockType.ITEM){
                //개별종목
                fee = 0.00015;
            }else{
                //etf
                fee = 0.0001;
            }

            holdStock.buy(stockCount.getCount(), stockCount.getStock().getClose());

            double price = stockCount.getStock().getClose() * stockCount.getCount();

            cash -= (price + price*fee);
        }
    }


    /**
     * 종목 매도
     * @param stockCount 매도 종목과 수량
     */
    public void sellStock(StockCount stockCount){
        synchronized (lock){

            HoldStock holdStock = holdStockMap.get(stockCount.getCode());

            // 팜매 수수료
            double fee;

            if(holdStock.getType() == StockType.ITEM){
                //개별종목
                fee = 0.00015 + 0.0025;
            }else{
                //etf
                fee = 0.0001;
            }

            double price = stockCount.getStock().getClose() * stockCount.getCount();
            cash += (price - price*fee);

            holdStock.sell(stockCount.getCount(),stockCount.getStock().getClose());
            if(holdStock.getCount() == 0){
                holdStockMap.remove(holdStock.getCode());
            }
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
            Collection<HoldStock> stockCounts = holdStockMap.values();

            double fee;

            for(HoldStock holdStock : stockCounts){
                double price;

                if(holdStock.getType() == StockType.ITEM){
                    fee = 0.00015 + 0.0025;
                    Item item =storeManager.getItemStore(ymd).getItem(holdStock.getCode());

                    if(item == null){
                        //우선주이면
                        price = storeManager.getPreferredStore(ymd).getPreferred(holdStock.getCode()).getClose();
                    }else{
                        //일반주이면
                        price = item.getClose();
                    }

                }else{
                    //etf
                    fee = 0.0001;
                    price = storeManager.getEtfStore(ymd).getEtf(holdStock.getCode()).getLastCandle().getClose();
                }


                stockPriceSum += holdStock.getEvaluationAmount(price, fee);

            }

            return Math.round(cash + stockPriceSum);
        }
        
    }

    /**
     * 보유종목 수량 얻기
     * @param code 종목코드
     * @return 보유종목 수량
     */
    public long getStockCount(String code){
        synchronized (lock){
            HoldStock holdStock = holdStockMap.get(code);
            if(holdStock == null){
                return 0L;
            }

            return holdStock.getCount();
        }
    }
    
    /**
     * 보유종목 얻기
     * @return 보유종목 전체
     */
    public HoldStock [] getHoldStocks (){
        synchronized (lock){
            return holdStockMap.values().toArray(new HoldStock[0]);
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

    /**
     * 보유 현금 얻기
     * @return 현금
     */
    public double getCash() {
        return cash;
    }
}
