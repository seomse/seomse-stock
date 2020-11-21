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

package com.seomse.stock.analysis.store.market.domestic;

import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

/**
 * 국내증시
 * @author macle
 */
@Table(name="T_STOCK_MARKET")
public class DomesticMarket {

    @PrimaryKey(seq = 1)
    @Column(name = "MARKET_CD")
    String code;
    @Column(name = "MARKET_NM")
    String name;



}
