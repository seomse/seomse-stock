/*
 * Copyright (C) 2021 Seomse Inc.
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

package com.seomse.stock.data.dev;

import com.seomse.stock.data.sync.CenterDatabaseSync;

/**
 * 특정 테이블 복사
 * @author macle
 */
public class TableSync {

    public static void main(String[] args) {

        String [] tables = """
           T_STOCK_ETF_1M
           T_STOCK_ITEM_5M
            """.split("\n");


        CenterDatabaseSync centerDatabaseSync = new CenterDatabaseSync();
        centerDatabaseSync.sync(tables);
    }

}
