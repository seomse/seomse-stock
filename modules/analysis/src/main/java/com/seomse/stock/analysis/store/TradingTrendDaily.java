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

package com.seomse.stock.analysis.store;

/**
 * 일별 매매동향
 * @author macle
 */
public class TradingTrendDaily implements TradingTrend {


    protected final String ymd;


    //기관 매매량
    final double institution;
    //외국계 매매량
    final double foreign;
    //개인 매매량
    final double individual;

    /**
     * 생성자
     * @param ymd yyyyMMdd
     * @param institution 기관 매매동향
     * @param foreign 외국계 매매동향
     * @param individual 개인 매매동향
     */
    public TradingTrendDaily(String ymd, double institution, double foreign, double individual
    ){
        this.ymd = ymd;
        this.institution = institution;
        this.foreign = foreign;
        this.individual = individual;

    }

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

    /**
     * 년월일
     * @return yyyyMMdd
     */
    public String getYmd() {
        return ymd;
    }
}
