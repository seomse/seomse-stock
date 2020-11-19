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

package com.seomse.stock.data.statistics.wics;

import com.seomse.commons.service.Service;
import com.seomse.commons.utils.time.Times;
import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.JdbcQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author macle
 */
public class WicsDailyStatisticsService extends Service {

    private final static Logger logger = LoggerFactory.getLogger(WicsDailyStatisticsService.class);

    /**
     * 생성자
     */
    WicsDailyStatisticsService(){
        setServiceId(this.getClass().getName());
        setState(State.START);
        setSleepTime(Times.HOUR_3);
    }

    @Override
    public void work() {

        String ymd = JdbcQuery.getResultOne("SELECT MAX(YMD) FROM T_STOCK_ITEM_DAILY");

        logger.info("wics daily ymd: " +  ymd);

        if(ymd == null){
            return;
        }

        String startYmd = YmdUtil.getYmd(ymd, -7);
        WicsDailyStatistics.update(startYmd, ymd);

    }
}
