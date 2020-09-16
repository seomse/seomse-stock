package com.seomse.stock;

import com.seomse.jdbc.annotation.*;
import com.seomse.stock.fundamental.analysis.FinancialStatements;

/**
 * <pre>
 *  파 일 명 : StockItem.java
 *  설    명 : 주식 종목
 *            우선주는 종목명 끝이 우가 붙어있고
 *            종목코드가 5로 끝나며
 *            종목코드가 마지막을 제외한 동일한 코드인 0으로 끝나는 값이 있음
 *  작 성 자 : macle
 *  작 성 일 : 2019.10.20
 *  버    전 : 1.1
 *  수정이력 : 2020.07.22
 *  기타사항 :
 * </pre>
 * @author Copyrights 2019 by ㈜섬세한사람들. All right reserved.
 */
@Table(name="T_STOCK_ITEM")
public class StockItem {
    @PrimaryKey(seq = 1)
    @Column(name = "ITEM_CD")
    private String code;
    @Column(name = "ITEM_NM")
    private String name;
    @Column(name = "ITEM_EN_NM")
    private String englishName;
    @Column(name = "MARKET_CD")
    private MarketType marketType = MarketType.KOSPI;
    @Column(name = "CATEGORY_NM")
    private String category;
    @Column(name = "WICS_NM")
    private String wics;
    @Column(name = "SUMMARY_DS")
    private String summary;

    @Column(name = "LISTING_YMD")
    private String listingYmd;
    @Column(name = "LISTING_CNT")
    private Long listingCount = 0L;

    @FlagBoolean
    @Column(name = "TRADE_FG")
    private boolean isTrade = true;

    @FlagBoolean
    @Column(name = "DELISTING_FG")
    private boolean isDelisting = false;


    FinancialStatements [] yearFinancialStatementsArray = null;
    FinancialStatements [] quarterFinancialStatementsArray = null;
    public FinancialStatements[] getYearFinancialStatementsArray() {
        return yearFinancialStatementsArray;
    }


    public FinancialStatements[] getQuarterFinancialStatementsArray() {
        return quarterFinancialStatementsArray;
    }



    @DateTime
    @Column(name = "LAST_UPT_DT")
    private long lastUpdateTime;

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public void setWics(String wics) {
        this.wics = wics;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getEnglishName() {
        return englishName;
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

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     *
     * @return 상장년월일
     */
    public String getListingYmd() {
        return listingYmd;
    }

    public void setListingYmd(String listingYmd) {
        this.listingYmd = listingYmd;
    }

    /**
     *
     * @return 상장주식수
     */
    public long getListingCount() {
        return listingCount;
    }

    public void setListingCount(long listingCount) {
        this.listingCount = listingCount;
    }

    /**
     * 시장유형 얻기
     * @return KOSPI, KOSDAQ
     */
    public MarketType getMarketType() {
        return marketType;
    }

    public void setMarketType(MarketType marketType) {
        this.marketType = marketType;
    }

    /**
     *
     * @return 거래가능여부
     */
    public boolean isTrade() {
        return isTrade;
    }

    public void setTrade(boolean trade) {
        isTrade = trade;
    }

    public boolean isDelisting() {
        return isDelisting;
    }

    public void setDelisting(boolean delisting) {
        isDelisting = delisting;
    }
}
