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

package com.seomse.stock.analysis.model.parallel;

import com.seomse.stock.analysis.StockScore;
import com.seomse.stock.analysis.store.StoreManager;
import com.seomse.stock.analysis.store.etf.Etf;
import com.seomse.stock.analysis.store.item.Item;
import com.seomse.stock.analysis.store.preferred.Preferred;

/**
 * 주식 분석자
 * @author macle
 */
public interface StockAnalyst {

    /**
     * 개별종목 분석
     * @param ymd yyyyMMdd
     * @param item 개별종목
     * @param storeManager 데이터 관리자
     * @return 종목과 점수 ( 유효하지 않으면 null )
     */
    StockScore analysisItem(String ymd, Item item, StoreManager storeManager);

    /**
     * 우선주 분석
     * @param ymd yyyyMMdd
     * @param preferred 우선주
     * @param storeManager 데이터 관리자
     * @return 종목과 점수 ( 유효하지 않으면 null )
     */
    StockScore analysisPreferred(String ymd, Preferred preferred, StoreManager storeManager);


    /**
     * etf 분석
     * @param ymd yyyyMMdd
     * @param etf etf
     * @param storeManager 데이터 관리자
     * @return 종목과 점수 ( 유효하지 않으면 null )
     */
    StockScore analysisEtf(String ymd, Etf etf, StoreManager storeManager);

}
