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

import com.seomse.commons.utils.ExceptionUtil;
import com.seomse.stock.analysis.MarketType;
import com.seomse.stock.analysis.store.item.Item;
import com.seomse.stock.analysis.store.item.ItemDailyCandle;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarket;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarketDailyCandle;
import com.seomse.stock.analysis.store.preferred.Preferred;
import com.seomse.trading.technical.analysis.subindex.rsi.RSI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 분석 작업자
 * @author macle
 */
public class StrongerThenMarketAnalysis implements Runnable{

    private final Logger logger = LoggerFactory.getLogger(StrongerThenMarketAnalysis.class);

    private final StrongerThenMarket strongerThenMarket;

    private final DomesticMarket kospi, kosdaq;


    /**
     * 생성자
     * @param strongerThenMarket strongerThenMarket
     */
    StrongerThenMarketAnalysis(StrongerThenMarket strongerThenMarket){
        this.strongerThenMarket = strongerThenMarket;
        this.kospi = strongerThenMarket.domesticMarketStore.getKospiMarket();
        this.kosdaq = strongerThenMarket.domesticMarketStore.getKospiMarket();

    }

    @Override
    public void run() {
        try{


            boolean isItem = true;
            for(;;){

                if(isItem){

                    Item item = strongerThenMarket.getItem();
                    if(item == null){
                        isItem = false;
                        continue;
                    }
                    item(item);
                }else{
                    Preferred preferred = strongerThenMarket.getPreferred();
                    if(preferred == null){
                        break;
                    }
                    preferred(preferred);
                }
            }

        }catch(Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }

        strongerThenMarket.complete();

    }



    private void item(Item item){
        //개별종목분석
        MarketType marketType = item.getMarketType();
        DomesticMarket domesticMarket;
        if(marketType == MarketType.KOSPI){
            domesticMarket = kospi;
        }else{
            domesticMarket = kosdaq;
        }

        ItemDailyCandle[] dailyCandles= item.getDailyCandles();
        analysis(item.getCode(), domesticMarket, dailyCandles);

    }


    private void preferred(Preferred preferred){
        //우선주분석

        MarketType marketType = preferred.getItem().getMarketType();
        DomesticMarket domesticMarket;
        if(marketType == MarketType.KOSPI){
            domesticMarket = kospi;
        }else{
            domesticMarket = kosdaq;
        }
        ItemDailyCandle[] dailyCandles= preferred.getDailyCandles();
        if(dailyCandles.length < strongerThenMarket.candleCount){
            return;
        }

        analysis(preferred.getCode(), domesticMarket, dailyCandles);
    }

    private void analysis(String code, DomesticMarket domesticMarket, ItemDailyCandle[] dailyCandles){
        if(dailyCandles.length < strongerThenMarket.candleCount){
            return;
        }

        int upCount = 0;
        int marketUpCount = 0;
        double marketUpPer = 0.0;


        DomesticMarketDailyCandle[] marketDailyCandles = domesticMarket.getCandles();

        //증시대기 강해지기 시작하는 구간에 진입한 종목을 찾게 변경..

        for (int i = dailyCandles.length - strongerThenMarket.candleCount; i < dailyCandles.length; i++) {

            if(dailyCandles[i].getVolume() < 5000.0){
                //거래량이 없느 종목이면 분석하지 않음
                logger.debug("trade volume is low " + code + " " + dailyCandles[i].getVolume() );
                return;
            }

            //기준일시가 다르면
            if(!marketDailyCandles[i].getYmd().equals(dailyCandles[i].getYmd())){
                logger.info("ymd mismatch: " + code);
                return;
            }


            if(dailyCandles[i].getChangeRate() > 0.0){
                upCount++;
            }

            if(dailyCandles[i].getChangeRate() > marketDailyCandles[i].getChangeRate()){
                marketUpCount ++;
            }


            //저가 고가 비율이 10%이를 초과하면 제외/
            //변동성이 크기전에만 사야 됨

            double gap = dailyCandles[i].getChangeRate() - marketDailyCandles[i].getChangeRate();

            //최근 2주간 변동성이 너무 크면 제외
            if(gap >= 10.0 || gap <= -10.0){
                return ;
            }

            marketUpPer+= dailyCandles[i].getChangeRate() - marketDailyCandles[i].getChangeRate();
        }
        
        // 최근 3일거래일중 20%이상 상승하거나
        // 최근 3거래일이 연속 하락이면 제외
        int downCount = 0;
        for (int i = dailyCandles.length -3 ; i <dailyCandles.length ; i++) {
            if(dailyCandles[i].getChangeRate() < 0.0){
                downCount ++;
            }

            if(dailyCandles[i].getChangeRate() > 20.0){
                return ;
            }

        }

        if(downCount == 3){
            return;
        }

        // rsi 를 구할 수 없거나 가 80 이상이면 제외
        double rsi =  RSI.getScore(dailyCandles);
        if(rsi == RSI.NOT_VALID ||  rsi >= 70.0){
            return ;
        }


        if(marketUpPer >= strongerThenMarket.marketUpPer
            && upCount >= strongerThenMarket.upCount
            && marketUpCount >= strongerThenMarket.marketUpCount) {

            StrongerThenMarketItem item = new StrongerThenMarketItem();
            item.code = code;
            item.upCount = upCount;
            item.marketUpCount = marketUpCount;
            item.marketUpPer = marketUpPer;
            item.rsi = rsi;

            strongerThenMarket.add(item);
        }
    }
}
