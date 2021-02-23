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

package com.seomse.stock.data.sync.tables.daily;

import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

/**
 * 개별종목 일별
 * @author macle
 */
@Table(name="T_STOCK_ITEM_DAILY")
public class ItemDailyNo {
    @PrimaryKey(seq = 1)
    private String ITEM_CD;
    @PrimaryKey(seq = 2)
    private String YMD;
    private Double CLOSE_PRC;
    private Double CHANGE_RT;
    private Double CHANGE_PRC;
    private Double PREVIOUS_PRC;
    private Double OPEN_PRC;
    private Double HIGH_PRC;
    private Double LOW_PRC;
    private Double TRADE_VOL;
    private Double STRENGTH_RT;
    private Double INSTITUTION_TRADE_VOL;
    private Double FOREIGN_TRADE_VOL;
    private Double INDIVIDUAL_TRADE_VOL;
    private Double FOREIGN_BALANCE_VOL;
    private Double FOREIGN_RT;
    private Double SLB_VOL;
    private Double SLB_REPAY_VOL;
    private Double SLB_BALANCE_VOL;
    private Double CREDIT_BALANCE_RT;
    private Double CREDIT_TOTAL_VOL;
    private Double CREDIT_NEW_VOL;
    private Double CREDIT_REPAY_VOL;
    private Double CREDIT_BALANCE_VOL;
    private Double CREDIT_PRC_VOL;
    private Double CREDIT_CHANGE_VOL;
    private Double CREDIT_EXPOSURE_RT;
}
