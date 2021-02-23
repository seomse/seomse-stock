package com.seomse.stock.data.verificator;

import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

/**
 * 종목
 * @author wjrmffldrhrl
 */
@Table(name="T_STOCK_ITEM")
public class Item {

    @PrimaryKey(seq = 1)
    @Column(name = "ITEM_CD")
    private String code;

    @Column(name = "ITEM_NM")
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


}
