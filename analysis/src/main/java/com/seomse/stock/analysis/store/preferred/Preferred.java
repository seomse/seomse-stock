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

import com.seomse.stock.analysis.StockPrice;
import com.seomse.stock.analysis.StockType;
import com.seomse.stock.analysis.store.item.Item;
import com.seomse.stock.analysis.store.item.ItemCandles;

/**
 * 우선주
 * @author macle
 */
public class Preferred extends ItemCandles implements StockPrice {

    String code;
    String name;
    //일반주 중목정보
    Item item;
    /**
     * 상장주식수 null 일 수 있음
     */
    Long listingCount;

    @Override
    public StockType getType() {
        return StockType.ITEM;
    }

    /**
     *
     * @return 종목코드
     */
    @Override
    public String getCode() {
        return code;
    }
    /**
     *
     * @return 종목명
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return 일반 주식 종목 정보
     */
    public Item getItem() {
        return item;
    }
    /**
     *
     * @return 상장주식수 (보통주))
     */
    public Long getListingCount() {
        return listingCount;
    }

    @Override
    public double getClose() {
        return getLastCandle().getClose();
    }
}
