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

package com.seomse.stock.analysis.store.etf;

import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

/**
 * etf 일별정보
 * @author macle
 */
@Table(name="T_STOCK_ETF_DAILY")
public class EtfDailyNo {
    @PrimaryKey(seq = 1)
    String ETF_CD;
    @PrimaryKey(seq = 2)
    String YMD;
    Double CLOSE_PRC;
    Double CHANGE_RT;
    Double CHANGE_PRC;
    Double PREVIOUS_PRC;
    Double OPEN_PRC;
    Double HIGH_PRC;
    Double LOW_PRC;
    Double TRADE_VOL;
    Double STRENGTH_RT;
    Double INSTITUTION_TRADE_VOL;
    Double FOREIGN_TRADE_VOL;
    Double INDIVIDUAL_TRADE_VOL;
    Double FOREIGN_BALANCE_VOL;
    Double FOREIGN_RT;
}
