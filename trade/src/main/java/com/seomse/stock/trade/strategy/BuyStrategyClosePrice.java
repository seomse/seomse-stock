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

package com.seomse.stock.trade.strategy;

import com.seomse.stock.trade.StockCount;

/**
 * 종가 거래 관련 매수전략
 * @author macle
 */
public interface BuyStrategyClosePrice {

    /**
     * 감시 대상 종목 매수종목 과 수량
     * @return 매수 종목과 수량 배열
     */ 
   StockCount[] getBuyObserverStocks();

    /**
     * 전일 데이터로 감시를 한후에 구매할지에 대한 결정을 한다.
     * 매수여부
     * @param stockCount 종목과 수량
     * @return 구매여부
     */
   boolean isBuy(StockCount stockCount);

}
