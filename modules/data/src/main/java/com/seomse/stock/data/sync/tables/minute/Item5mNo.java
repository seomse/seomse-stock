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

package com.seomse.stock.data.sync.tables.minute;

import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

/**
 * 개별종목 5분봉
 * @author macle
 */
@Table(name="T_STOCK_ITEM_5M")
public class Item5mNo {
    @PrimaryKey(seq = 1)
    private String ITEM_CD;
    @PrimaryKey(seq = 2)
    private String YMDHM;
    private Double CLOSE_PRC;
    private Double PREVIOUS_PRC;
    private Double DAILY_PREVIOUS_PRC;
    private Double OPEN_PRC;
    private Double HIGH_PRC;
    private Double LOW_PRC;
    private Double TRADE_VOL;
    private Double TRADE_PRC_VOL;

}
