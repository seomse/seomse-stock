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

import com.seomse.stock.analysis.store.TradingTrend;

/**
 * 국내 선물 일별정보
 * KOSPI 200 을 따르는것으로 판단됨
 * 한동향은 매매동향ㅁ
 * @author macle
 */
public class DomesticFutDaily implements TradingTrend {

    String ymd;



    //기관 매매량
    double institution;
    //외국계 매매량
    double foreign;
    //개인 매매량
    double individual;

    @Override
    public Double getInstitution() {
        return institution;
    }

    @Override
    public Double getForeign() {
        return foreign;
    }

    @Override
    public Double getIndividual() {
        return individual;
    }

}
