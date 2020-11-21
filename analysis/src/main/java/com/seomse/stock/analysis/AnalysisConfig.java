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

package com.seomse.stock.analysis;

/**
 * 주식에서 사용하는 설정정보
 * @author macle
 */
public enum AnalysisConfig {

    
    //주식 분석에 사용하는 기본 캔들 건수
    CANDLE_COUNT("stock.analysis.candle.count", 1000)
    ;

    private final String key;
    private final Object defaultValue;

    /**
     * 생성자
     * @param key String
     * @param defaultValue Object, null enable
     */
    AnalysisConfig(String key, Object defaultValue){
        this.key = key;
        this.defaultValue = defaultValue;
    }
    /**
     * @return String
     */
    public String key(){return key;}

    /**
     * @return Object
     */
    public Object defaultValue(){return defaultValue;}

}
