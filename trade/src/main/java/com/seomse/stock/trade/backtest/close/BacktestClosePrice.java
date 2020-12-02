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

package com.seomse.stock.trade.backtest.close;

import com.seomse.jdbc.JdbcQuery;
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.trade.backtest.AccountStatus;
import com.seomse.stock.trade.strategy.BuyStrategyClosePrice;
import com.seomse.stock.trade.strategy.SellStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * 개별종목 수수료
 *  매수 수수료 0.015%
 *  매도 수수료 0.015%
 *  매도 세금 0.25%
 *
 *  ETF 수수료
 *  헷지로 사용할 kodex 200선물인버스2x 매수 0.01, 매도 0.01
 *  모든 ETF 매수 0.01, 매도 0.01로 동작하게함
 *
 *  종가 매매 벡테스팅이지만 특정 ETF는 리얼타임이 도입될 수 있음
 *
 * 종가 거래관련 백테스팅
 * 매수 대기 종목은
 * @author macle
 */
public class BacktestClosePrice {

    private static final Logger logger = LoggerFactory.getLogger(BacktestClosePrice.class);

    private final BuyStrategyClosePrice buyStrategy;
    private SellStrategy sellStrategy;
    private AccountStatus accountStatus;
    private String beginYmd;
    private String endYmd;

    //한종목 최대 구매 금액
    private double itemPrice = 1000000.0;

    private StoreManager storeManager = null;

    /**
     * 증시정보 메모리 저장소 설정
     * @param storeManager 증서정보 메모리 저장소
     */
    public void setStoreManager(StoreManager storeManager) {
        this.storeManager = storeManager;
    }

    /**
     * 생성자
     * @param buyStrategy 매수전략
     * @param sellStrategy 매도전략
     * @param accountStatus 계좌현황
     * @param beginYmd 시작 년월일 yyyyMMdd
     * @param endYmd 끝 년월일 yyyyMMdd
     */
    public BacktestClosePrice(BuyStrategyClosePrice buyStrategy
            , SellStrategy sellStrategy
            , AccountStatus accountStatus
            , String beginYmd
            , String endYmd
    ){
        this.buyStrategy = buyStrategy;
        this.sellStrategy = sellStrategy;
        this.accountStatus =  accountStatus;
        this.beginYmd = beginYmd;
        this.endYmd = endYmd;
    }


    public void test(){

        //증시 상승률 대비



        //거래일 얻기
        //거래일은 코스피 지수의 거래가 있는날짜로 한다
        List<String> ymdList = JdbcQuery.getStringList("SELECT YMD FROM T_STOCK_MARKET_DAILY WHERE MARKET_CD ='KOSPI' AND YMD >= '" +beginYmd +"' AND YMD <= '" + endYmd +"' ORDER BY YMD ASC");








    }




}
