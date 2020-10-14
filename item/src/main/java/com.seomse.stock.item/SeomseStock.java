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

package com.seomse.stock.item;

import com.seomse.api.server.ApiServer;
import com.seomse.commons.config.Config;
import com.seomse.commons.utils.time.RunningTime;
import com.seomse.commons.utils.time.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 섬세 스톡
 * @author macle
 */
public class SeomseStock {
	
	private static final Logger logger = LoggerFactory.getLogger(SeomseStock.class);
	
	public static void main(String [] args) {
		
		RunningTime time = new RunningTime();
		//설정 올리기
		Config.getConfig("");
//		Config.setLogbackConfigPath("config/logback.xml");
		//api server 실행
		new ApiServer(Config.getInteger("api.port", 31001), "com.seomse.stock.api").start();
		
		logger.info("seomse stock start : " + TimeUtil.getTimeValue(time.getRunningTime()));
	}
}
