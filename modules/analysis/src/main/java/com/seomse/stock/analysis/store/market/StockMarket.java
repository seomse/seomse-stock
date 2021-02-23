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

package com.seomse.stock.analysis.store.market;

import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

/**
 * 주식 증시
 * @author macle
 */
@Table(name="T_STOCK_MARKET")
public class StockMarket {
    @PrimaryKey(seq = 1)
    @Column(name = "MARKET_CD")
    
    protected String code;
    @Column(name = "MARKET_NM")
    protected String name;

    /**
     * 생성자
     * 리플렉션용
     */
    public StockMarket(){

    }

    /**
     * 생성자
     * @param code 증시코드
     * @param name 증시면
     */
    public StockMarket(String code, String name){
        this.code = code;
        this.name = name;
    }

    /**
     * 증시코드
     * @return 증시 코드
     */
    public String getCode() {
        return code;
    }

    /**
     * 증시명
     * @return 증시명
     */
    public String getName() {
        return name;
    }
}
