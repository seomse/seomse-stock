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

package com.seomse.stock.analysis.store.market.domestic;

import com.seomse.stock.analysis.store.market.StockMarket;

/**
 * KPI200 정보
 * 코스피 200
 * 선물은 관련지수를 보고 옴직이는것으로 판단됨
 * @author macle
 */
public class Kpi200Market extends StockMarket {

    Kpi200Daily [] dailies;

    /**
     * 생성자
     */
    public Kpi200Market(){
        super("KPI200", "코스피200");
    }

    /**
     * 일병정보는 가격정보, 거래량정보
     * Kpi200Daily 클래스의 내부변수가 사용할 수 있는 정보임
     * @return 일별 정보 배열 얻기
     */
    public Kpi200Daily[] getDailies() {
        return dailies;
    }
}
