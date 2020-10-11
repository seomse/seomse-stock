/*
 * Copyright (C) 2020 Wigo Inc.
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

package com.seomse.stock.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 종목 데이터 저장소
 * @author macle
 */
public class ItemDataStore {

    private static final Logger logger = LoggerFactory.getLogger(ItemDataStore.class);
    
    private String ymd;

    /**
     * 생성자
     * @param ymd String 기준 년월일
     */
    public ItemDataStore(String ymd){
        this.ymd = ymd;
    }


    public void setYmd(String ymd) {
        this.ymd = ymd;
    }
}
