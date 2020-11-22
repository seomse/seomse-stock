package com.seomse.stock.data.verificator;

import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

/**
 * 종목당 일별 정보
 * @author wjrmffldrhrl
 */
@Table(name="T_STOCK_ITEM_DAILY")
public class Daily {
    @PrimaryKey(seq = 1)
    @Column(name = "ITEM_CD")
    private String code;

    @Column(name = "YMD")
    private String ymd;

    @Column(name = "CLOSE_PRC")
    private Double closePrice;

    public String getCode() {
        return code;
    }

    public String getYmd() {
        return ymd;
    }

    public double getClosePrice() {
        if (closePrice == null) {
            return -1;
        }

        return closePrice;
    }
}
