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
import com.seomse.stock.analysis.Stock;
import com.seomse.stock.analysis.store.item.Item;
import com.seomse.stock.analysis.store.item.ItemDailyCandle;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarket;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarketDailyCandle;
import com.seomse.stock.analysis.store.preferred.Preferred;
import com.seomse.trading.technical.analysis.subindex.rsi.RSI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

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
        analysis(item, domesticMarket, dailyCandles);

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

        analysis(preferred, domesticMarket, dailyCandles);
    }

    private void analysis(Stock stock, DomesticMarket domesticMarket, ItemDailyCandle[] dailyCandles){
        if(dailyCandles.length < strongerThenMarket.candleCount){
            return;
        }

        DomesticMarketDailyCandle[] marketDailyCandles = domesticMarket.getCandles();

        // 최근 3일거래일중 9%이상 상승하거나
        // 최근 3거래일이 연속 하락이면 제외
        // 변동성이 너무 크면 제외
        int downCount = 0;
        for (int i = dailyCandles.length -3 ; i <dailyCandles.length ; i++) {
            if(dailyCandles[i].getChangeRate() < 0.0){
                downCount ++;
            }

            if(dailyCandles[i].getChangeRate() > 9.0){
                return ;
            }

        }

        if(downCount == 3){
            return;
        }

        for (int i = 7; i > 0; i--) {
            //최근 7일이 유효하지 않고 최근에 유효해 졌을때만
            // 최근 7일 유효하면 제외
            if(getModuleScore(stock, Arrays.copyOfRange(marketDailyCandles,0,marketDailyCandles.length - i), Arrays.copyOfRange(dailyCandles,0,dailyCandles.length - i)) != null){
                return ;
            }
        }


        // 기준일이 유효해진 날자이면 추가
        StrongerThenMarketModuleScore moduleScore = getModuleScore(stock, marketDailyCandles, dailyCandles);
        if(moduleScore == null){
            return;
        }

        strongerThenMarket.add(moduleScore);
    }

    /**
     * 유효성 여부
     * @param marketDailyCandles 증시 일별캔들
     * @param dailyCandles 종목 일별캔들
     * @return 유효성 여부 (증시대비 강한 종목인지)
     */
    private StrongerThenMarketModuleScore getModuleScore(Stock stock, DomesticMarketDailyCandle[] marketDailyCandles,  ItemDailyCandle[] dailyCandles){
        if(dailyCandles.length < strongerThenMarket.candleCount){
            return null;
        }


        int upCount = 0;
        int marketUpCount = 0;
        double marketUpPer = 0.0;

        for (int i = dailyCandles.length - strongerThenMarket.candleCount; i < dailyCandles.length; i++) {


            if(dailyCandles[i].getVolume() < 5000.0){
                //거래량이 없느 종목이면 분석하지 않음
                logger.debug("trade volume is low " + stock.getCode() + ", " + stock.getName() +", " + dailyCandles[i].getVolume() );
                return null;
            }

            //기준일시가 다르면
            if(!marketDailyCandles[i].getYmd().equals(dailyCandles[i].getYmd())){
                logger.info("ymd mismatch: " + stock.getCode() + ", " + stock.getName());
                return null;
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
                return null;
            }

            marketUpPer+= dailyCandles[i].getChangeRate() - marketDailyCandles[i].getChangeRate();
        }
    
        
        //rsi 가 70이상이면 제외
        double rsi =  RSI.getScore(dailyCandles);
        if(rsi == RSI.NOT_VALID ||  rsi >= 70.0){
            return null;
        }

        if(upCount < strongerThenMarket.upCount
                || marketUpCount < strongerThenMarket.marketUpCount
                || marketUpPer < strongerThenMarket.marketUpPer
        ){
            return null;
        }


        StrongerThenMarketModuleScore moduleScore = new StrongerThenMarketModuleScore(stock, 0);
        moduleScore.upCount = upCount;
        moduleScore.marketUpCount = marketUpCount;
        moduleScore.marketUpPer = marketUpPer;
        moduleScore.rsi = rsi;

        return moduleScore;

    }


}
