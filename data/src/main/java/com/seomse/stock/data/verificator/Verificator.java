package com.seomse.stock.data.verificator;

import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.objects.JdbcObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 종목 데이터 검증
 * @author wjrmffldrhrl
 */
public class Verificator {
    private static final Logger logger = LoggerFactory.getLogger(Verificator.class);

    public static void updateAll() {
        List<Item> items = getAllItems();
        Set<String> invalidItems = new HashSet<>();

        for (Item item : items) {
            if (update(item.getCode())) {
                invalidItems.add(item.getCode());
            }
        }

        for (String code : invalidItems) {
            logger.info("Invalid item : " + code);
        }
    }

    public static boolean update(String itemCode) {
        List<Daily> dailies = JdbcObjects.getObjList(Daily.class, "ITEM_CD='" + itemCode + "'");


        boolean invalidFlag = false;
        for (Daily daily : dailies) {
            if (daily.getYmd().equals("20131231")) {
                continue;
            }
            if (daily.getClosePrice() <= 0) {
                invalidFlag = true;
            } else if (daily.getChangeRt() < -30 || daily.getChangeRt() > 30) {
                invalidFlag = true;
            }

            if (invalidFlag) {
                logger.info(daily.toString());

                break;
            }
        }

        return invalidFlag;
    }

    private static List<Item> getAllItems() {
        return JdbcObjects.getObjList(Item.class);
    }

    public static void main(String[] args) {
        updateAll();
    }
}
