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

import com.seomse.stock.trade.HoldStock;
import com.seomse.stock.trade.StockCount;

/**
 * 매도 전략
 * @author macle
 */
public interface SellStrategy {

    /**
     * 매도 종목 얻기
     * @param holdStocks 보유종목 배열
     * @return 종목 및 수량 배열
     */
    StockCount[] getSellStocks(HoldStock[] holdStocks);
    
}
