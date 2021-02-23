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

package com.seomse.stock.analysis.fundamental;

/**
 * 점수 측정 관련 유틸
 * @author macle
 */
public class ScoreMeasurement {

	public static final double MAX_SCORE = 100.0;


	/**
	 * 기준점수 뒤에 점수는 점수가중치를 낮춤
	 * 기준좀서보다 높은점수는 가산점이 줄어든다
	 * 기준점수의 10%가 최대점수
	 * @param standard 기준점수
	 * @param score 점수
	 * @return 감소 된 점수
	 */
	public static double reduce(double standard, double score) {
		
		if(standard >=  score) {
			return score;
		}
		
		double weight = (score - standard)/score * standard/10.0;
		return weight + standard;
		
	}

}
