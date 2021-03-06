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

import com.seomse.stock.trade.AccountStatus;
import com.seomse.stock.trade.StockCount;

/**
 * 매수전략
 * @author macle
 */
public interface BuyStrategy {



    /**
     * 매수 종목 과 수량
     * @param accountStatus 계좌현황
     * @return 종목과 수량 배열
     */
    StockCount[] getBuyStocks(AccountStatus accountStatus);
}
