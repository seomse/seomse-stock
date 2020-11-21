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
package com.seomse.stock.analysis.store.item;

import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.DateTime;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;
import com.seomse.stock.analysis.MarketType;
import com.seomse.trading.technical.analysis.candle.candles.TradeCandles;

import java.util.Map;

/**
 * 주식 종목 (개별 종목)
 * @author macle
 */
@Table(name="T_STOCK_ITEM")
public class Item {
    @PrimaryKey(seq = 1)
    @Column(name = "ITEM_CD")
    private String code;
    @Column(name = "ITEM_NM")
    private String name;
    @Column(name = "ITEM_EN_NM")
    private String englishName;
    @Column(name = "MARKET_CD")
    private MarketType marketType;
    @Column(name = "CATEGORY_NM")
    private String category;
    @Column(name = "WICS_NM")
    private String wics;
    @Column(name = "SUMMARY_DS")
    private String summary;

    @Column(name = "LISTING_YMD")
    private String listingYmd;
    @Column(name = "LISTING_CNT")
    private Long listingCount;

    //캔들정보
    Map<Long, TradeCandles> timeCandlesMap;
    
    FinancialStatements[] yearFinancialStatementsArray = null;
    FinancialStatements[] quarterFinancialStatementsArray = null;

    public FinancialStatements[] getYearFinancialStatementsArray() {
        return yearFinancialStatementsArray;
    }


    public FinancialStatements[] getQuarterFinancialStatementsArray() {
        return quarterFinancialStatementsArray;
    }

    @DateTime
    @Column(name = "UPT_DT")
    private long lastUpdateTime;


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public MarketType getMarketType() {
        return marketType;
    }

    public String getCategory() {
        return category;
    }

    public String getWics() {
        return wics;
    }

    public String getSummary() {
        return summary;
    }

    public String getListingYmd() {
        return listingYmd;
    }

    public Long getListingCount() {
        return listingCount;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }
}
