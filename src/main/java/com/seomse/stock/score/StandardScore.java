/** 
 * <pre>
 *  파 일 명 : PointUtil.java
 *  설    명 : 점수관련 유틸성 클래스
 *                    
 *  작 성 자 : macle
 *  작 성 일 : 2017.11
 *  버    전 : 1.0
 *  수정이력 : 
 *  기타사항 :
 * </pre>
 * @author Copyrights 2017 by ㈜섬세한사람들. All right reserved.
 */

package com.seomse.stock.score;

public class StandardScore {
	/**
	 * 기준점수 뒤에 점수는 점수가중치를 낮춤
	 * @param standard
	 * @param point
	 * @return
	 */
	public static double reduce(double standard, double point) {
		
		if(standard >=  point) {
			return point;
		}
		
		double remainder = point - standard;
		return Math.log(Math.E + remainder) -1.0 + standard;
		
	}
	
	
	
	
	public static void main(String [] args) {
		System.out.println(reduce(2.0, 4.5));
	}
}
