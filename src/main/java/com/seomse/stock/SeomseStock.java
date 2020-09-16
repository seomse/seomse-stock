

package com.seomse.stock;

import com.seomse.api.server.ApiServer;
import com.seomse.commons.config.Config;
import com.seomse.commons.utils.date.RunningTime;
import com.seomse.commons.utils.date.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * <pre>
 *  파 일 명 : SeomseStock.java
 *  설    명 : 섬세한 사람들 주식 시스템트레이딩
 *  작 성 자 : macle
 *  작 성 일 : 2018.05
 *  버    전 : 1.0
 *  수정이력 :
 *  기타사항 :
 * </pre>
 * @author Copyrights 2018 by ㈜섬세한사람들. All right reserved.
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
