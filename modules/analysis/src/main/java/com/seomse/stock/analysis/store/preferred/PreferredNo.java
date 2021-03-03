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

package com.seomse.stock.analysis.store.preferred;

import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

/**
 * 우선주 정보 DB 연동객체
 * @author macle
 */
@Table(name="T_STOCK_ITEM_PREFERRED")
public class PreferredNo {
    @PrimaryKey(seq = 1)
     String ITEM_CD;
     String ITEM_NM;
     String PARENTS_CD;

     Long LISTING_CNT;
}