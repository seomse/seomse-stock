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

package com.seomse.stock.data.statistics.wics;

import com.seomse.jdbc.PrepareStatementData;
import com.seomse.jdbc.PrepareStatements;
import com.seomse.jdbc.objects.JdbcObjects;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author macle
 */
public class WicsDaily {

    /**
     * 업데이트
     * @param ymd yyyyMMdd 기준년원일
     *
     */
    public static void update(String ymd){

        try {
            long time = new SimpleDateFormat("yyyyMMdd").parse(ymd).getTime();
            Map<Integer, PrepareStatementData> timeMap = PrepareStatements.newTimeMap(time);

            List<WicsStockItem> itemList = JdbcObjects.getObjList(WicsStockItem.class, "DELISTING_DT IS NULL OR DELISTING_DT > ?", timeMap);

            System.out.println(itemList.size());

        }catch(Exception e){
            throw new RuntimeException(e);
        }


    }


    public static void main(String[] args) {
        update("20201118");
    }

}
