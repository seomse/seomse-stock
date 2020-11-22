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

import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

/**
 * 증시 일별정보
 * @author macle
 */
@Table(name="T_STOCK_MARKET_DAILY")
public class MarketDailyNo {

    @PrimaryKey(seq = 1)
    private String MARKET_CD;
    @PrimaryKey(seq = 2)
    private String YMD;
    private Double CLOSE_PRC;
    private Double CHANGE_RT;
    private Double CHANGE_PRC;
    private Double PREVIOUS_PRC;
    private Double OPEN_PRC;
    private Double HIGH_PRC;
    private Double LOW_PRC;
    private Double TRADE_VOL;
    private Double TRADE_PRC_VOL;
    private Double INSTITUTION_TRADE_VOL;
    private Double FOREIGN_TRADE_VOL;
    private Double INDIVIDUAL_TRADE_VOL;
    private Double DAILY_PREVIOUS_PRC;


    public String getMARKET_CD() {
        return MARKET_CD;
    }

    public String getYMD() {
        return YMD;
    }

    public Double getCLOSE_PRC() {
        return CLOSE_PRC;
    }

    public Double getCHANGE_RT() {
        return CHANGE_RT;
    }

    public Double getCHANGE_PRC() {
        return CHANGE_PRC;
    }

    public Double getPREVIOUS_PRC() {
        return PREVIOUS_PRC;
    }

    public Double getOPEN_PRC() {
        return OPEN_PRC;
    }

    public Double getHIGH_PRC() {
        return HIGH_PRC;
    }

    public Double getLOW_PRC() {
        return LOW_PRC;
    }

    public Double getTRADE_VOL() {
        return TRADE_VOL;
    }

    public Double getTRADE_PRC_VOL() {
        return TRADE_PRC_VOL;
    }

    public Double getINSTITUTION_TRADE_VOL() {
        return INSTITUTION_TRADE_VOL;
    }

    public Double getFOREIGN_TRADE_VOL() {
        return FOREIGN_TRADE_VOL;
    }

    public Double getINDIVIDUAL_TRADE_VOL() {
        return INDIVIDUAL_TRADE_VOL;
    }

    public Double getDAILY_PREVIOUS_PRC() {
        return DAILY_PREVIOUS_PRC;
    }
}
