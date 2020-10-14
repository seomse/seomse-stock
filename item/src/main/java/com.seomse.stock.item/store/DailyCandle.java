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

package com.seomse.stock.item.store;

import com.seomse.trading.technical.analysis.candle.TradeCandle;

/**
 * 주식 일봉
 * @author macle
 */
public class DailyCandle extends TradeCandle {

    //아래 항목은 없을수도 있으니 null을 허용하는 객체형으로 변수화 함
    String ymd;

    //기관 매매량
    Double institution;
    //외국계 매매량
    Double foreign;
    //외국인 매매량
    Double foreigner;
    //개인 매매량
    Double individual;
    //프로그램 매매량
    Double program;
    //대차 체결량
    Double slb;
    //대차 상환
    Double slbRepay;
    //대차잔고
    Double slbBalance;
    //공매도
    Double shortSelling;
    //공매도 잔고
    Double shortSellingBalance;
    //신용 비율
    Double creditRatio;

    public String getYmd() {
        return ymd;
    }

    public Double getInstitution() {
        return institution;
    }

    public Double getForeign() {
        return foreign;
    }

    public Double getIndividual() {
        return individual;
    }

    public Double getProgram() {
        return program;
    }

    public Double getSlb() {
        return slb;
    }

    public Double getSlbRepay() {
        return slbRepay;
    }

    public Double getSlbBalance() {
        return slbBalance;
    }

    public Double getShortSelling() {
        return shortSelling;
    }

    public Double getShortSellingBalance() {
        return shortSellingBalance;
    }

    public Double getCreditRatio() {
        return creditRatio;
    }
}
