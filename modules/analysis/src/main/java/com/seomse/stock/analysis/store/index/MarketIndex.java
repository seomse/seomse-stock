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

package com.seomse.stock.analysis.store.index;

import com.seomse.jdbc.annotation.Column;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;
import com.seomse.stock.analysis.store.DailyPriceChange;

/**
 * @author macle
 */
@Table(name="T_STOCK_MARKET_INDEX")
public class MarketIndex {
    @PrimaryKey(seq = 1)
    @Column(name = "INDEX_CD")
    String code;
    @Column(name = "INDEX_NM")
    String name;
    @Column(name = "UNIT_NM")
    String unitName;
    @Column(name = "CATEGORY_NM")
    String categoryName;

    DailyPriceChange[] dailies;
    /**
     *
     * @return 코드
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @return 이름
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return 금액 단위
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * 금리, 원자재, 펀드 등
     * @return 카테고리
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * @return 일별 가격 정보
     */
    public DailyPriceChange[] getDailies() {
        return dailies;
    }
}
