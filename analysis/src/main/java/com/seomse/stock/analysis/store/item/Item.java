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
import com.seomse.stock.analysis.Stock;
import com.seomse.stock.analysis.StockType;

/**
 * 주식 종목 (개별 종목)
 * @author macle
 */
@Table(name="T_STOCK_ITEM")
public class Item extends ItemCandles implements Stock {
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

    @Override
    public StockType getType() {
        return StockType.ITEM;
    }

    /**
     *
     * @return 종목코드
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     *
     * @return 종목명
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return 종목명 (영어))
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * 
     * @return 증시유형 (KOSPI, KOSDAQ)
     */
    public MarketType getMarketType() {
        return marketType;
    }

    /**
     * 
     * @return 업종분류
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @return wics
     */
    public String getWics() {
        return wics;
    }

    /**
     * 
     * @return 종목 요약 설명
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * @return 상장년원일
     */
    public String getListingYmd() {
        return listingYmd;
    }

    /**
     * 
     * @return 상장주식수 (보통주))
     */
    public Long getListingCount() {
        return listingCount;
    }

    /**
     * 
     * @return 정보갱신 시간
     */
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }


}
