package com.seomse.stock.data.verificator;

import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.objects.JdbcObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * 종목 데이터 검증
 * @author wjrmffldrhrl
 */
public class Verificator {
    private static final Logger logger = LoggerFactory.getLogger(Verificator.class);

    public static void updateAll() {
        List<Item> items = getAllItems();

        for (Item item : items) {
            update(item.getCode());
        }
    }

    public static void update(String itemCode) {
        List<Daily> dailies = JdbcObjects.getObjList(Daily.class, "ITEM_CD='" + itemCode + "'");
        String targetDate = "20201120";

        boolean dateFlag = false;
        for (Daily daily : dailies) {
            if (daily.getYmd().equals(targetDate) && daily.getClosePrice() != -1) {
                dateFlag = true;
            }
        }

        if (!dateFlag) {
            System.out.println(itemCode);
        }
    }

    private static List<Item> getAllItems() {
        return JdbcObjects.getObjList(Item.class);
    }

    public static void main(String[] args) {
        updateAll();
    }
}
