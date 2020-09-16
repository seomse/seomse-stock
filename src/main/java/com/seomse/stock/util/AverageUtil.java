/** 
 * <pre>
 *  파 일 명 : AverageUtil.java
 *  설    명 : 주식 년월일 관련 유틸성 클래스
 *                    
 *  작 성 자 : macle
 *  작 성 일 : 2017.11
 *  버    전 : 1.0
 *  수정이력 : 
 *  기타사항 :
 * </pre>
 * @author Copyrights 2017 by ㈜섬세한사람들. All right reserved.
 */

package com.seomse.stock.util;

import java.util.Arrays;

public class AverageUtil {
	/**
	 * 분포평균 얻기 (10%를지정하면 상위10% 하위10%를 제거한 중간값들의 평균)
	 * @param valueArray
	 * @param per 10%를지정하면 상위10% 하위10%를 제거한 중간값들의 평균
	 * @return
	 */
	public static double getAverageDistribution(double [] valueArray,  double per){
		Arrays.sort(valueArray);
		int removeSize = (int)(((double) valueArray.length) * (per/100.0));
		int lengthPoint = valueArray.length - removeSize;
		double total = 0;
		for(int i=removeSize ; i<lengthPoint ; i++){
			total += valueArray[i];
		}
		return (double)total/(double)(lengthPoint - removeSize);
	}
}
