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

import com.seomse.commons.config.Config;
import com.seomse.system.engine.EngineInitializer;

/**
 * @author macle
 */
public class WicsDailyStatisticsInitializer implements EngineInitializer {
    @Override
    public void init() {
        if(Config.getBoolean("wics.daily.service.flag", false)){
            WicsDailyStatisticsService wicsDailyStatisticsService = new WicsDailyStatisticsService();
            wicsDailyStatisticsService.start();
        }
    }
}
