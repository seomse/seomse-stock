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

package com.seomse.stock.trade.strategy;

import com.seomse.stock.analysis.store.StoreManager;

/**
 * 저장소와 일별정보
 * @author macle
 */
public class StoreYmd {


    protected StoreManager storeManager;

    //현재날짜
    protected String ymd;


    /**
     * 생성자
     * @param storeManager in memory store
     * @param ymd yyyyMMdd
     */
    public StoreYmd(StoreManager storeManager, String ymd){
        this.storeManager = storeManager;
        this.ymd = ymd;
    }

    /**
     * ymd 설정
     * @param ymd yyyyMMdd
     */
    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    /**
     * ymd 얻기
     * @return yyyyMMdd
     */
    public String getYmd() {
        return ymd;
    }

}
