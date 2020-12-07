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

import com.seomse.stock.analysis.*;
import com.seomse.stock.analysis.model.parallel.StockAnalyst;
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.analysis.store.etf.Etf;
import com.seomse.stock.analysis.store.item.Item;
import com.seomse.stock.analysis.store.item.ItemDailyCandle;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarket;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarketDailyCandle;
import com.seomse.stock.analysis.store.preferred.Preferred;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 증시대바 강한종목 찾기 분석
 * 유효성 결과로 점수는 의미가 없음
 * 우선주와 개별주만 분석함
 * @author macle
 */
public class StrongerThenMarketAnalyst implements StockAnalyst {


    private static final Logger logger = LoggerFactory.getLogger(StrongerThenMarketAnalyst.class);
    
    //최근 40 거래일 분석
    private int candleCount = 40;

    // 시장대비 높은 캔들 수 오를 떄는 많이 떨어질때는 적게
    private int marketUpCount = 21;

    //증시대비 합산 상승률
    private double marketUpPer = 5.0;
    
    //상승 캔들 수 
    private int minUpCount =  21;

    /**
     * 최근 거래일 분석 건수
     * @param candleCount 최근 거래일 분석 건수
     */
    public void setCandleCount(int candleCount) {
        this.candleCount = candleCount;
    }


    /**
     * 증시대비 합산 상승률
     * @param marketUpPer 증시대바 합산 상승률
     */
    public void setMarketUpPer(double marketUpPer) {
        this.marketUpPer = marketUpPer;
    }

    /**
     * 시장대비 높은 캔들 수 오를 떄는 많이 떨어질때는 적게
     * @param marketUpCount  시장대비 높은 캔들 수
     */
    public void setMarketUpCount(int marketUpCount) {
        this.marketUpCount = marketUpCount;
    }

    /**
     * 상승 캔들 수
     * @param minUpCount 상승 캔들 수
     */
    public void setMinUpCount(int minUpCount) {
        this.minUpCount = minUpCount;
    }

    @Override
    public StockScore analysisItem(String ymd, Item item, StoreManager storeManager) {
        MarketType marketType = item.getMarketType();
        DomesticMarket domesticMarket;
        if(marketType == MarketType.KOSPI){
            domesticMarket = storeManager.getDomesticMarketStore(ymd).getKospiMarket();
        }else{
            domesticMarket = storeManager.getDomesticMarketStore(ymd).getKosdaqMarket();
        }
        Double score = analysis(item, domesticMarket.getCandles(), item.getDailyCandles());

        if(score == null){
            return null;
        }
        return new StockScore(item, score);
    }

    @Override
    public StockScore analysisPreferred(String ymd, Preferred preferred, StoreManager storeManager) {

        MarketType marketType = preferred.getItem().getMarketType();
        DomesticMarket domesticMarket;
        if(marketType == MarketType.KOSPI){
            domesticMarket = storeManager.getDomesticMarketStore(ymd).getKospiMarket();
        }else{
            domesticMarket = storeManager.getDomesticMarketStore(ymd).getKosdaqMarket();
        }
        Double score = analysis(preferred, domesticMarket.getCandles(),  preferred.getDailyCandles());

        if(score == null){
            return null;
        }

        return new StockScore(preferred, score);
    }

    @Override
    public StockScore analysisEtf(String ymd, Etf etf, StoreManager storeManager) {
        //etf 는 분석하지 않는다
        return null;
    }


    private Double analysis(Stock stock, DomesticMarketDailyCandle[] marketDailyCandles, ItemDailyCandle[] dailyCandles){
        if(dailyCandles.length < candleCount){
            return null;
        }


        int upCount = 0;
        int marketUpCount = 0;
        double marketUpPer = 0.0;



        for (int i = candleCount ; i > 0; i--) {

            //기준일시가 다르면
            if(!marketDailyCandles[marketDailyCandles.length-i].getYmd().equals(dailyCandles[dailyCandles.length-i].getYmd())){
                logger.debug("ymd mismatch: " + stock.getCode() + ", " + stock.getName() +"," + marketDailyCandles[marketDailyCandles.length-i].getYmd() + "," +dailyCandles[dailyCandles.length-i].getYmd() );
                return null;
            }


            if(dailyCandles[dailyCandles.length-1].getChangeRate() > 0.0){
                upCount++;
            }

            if(dailyCandles[dailyCandles.length-1].getChangeRate() > marketDailyCandles[marketDailyCandles.length-i].getChangeRate()){
                marketUpCount ++;
            }


            marketUpPer+= dailyCandles[dailyCandles.length-1].getChangeRate() - marketDailyCandles[marketDailyCandles.length-i].getChangeRate();
        }


        if(upCount < this.minUpCount
                || marketUpCount < this.marketUpCount
                || marketUpPer < this.marketUpPer
        ){
            return null;
        }
        
        //관련 분석은 유효성 과정으로 점수는 계산하지 않음
        return AnalysisScore.DEFAULT_SCORE;
    }
}
