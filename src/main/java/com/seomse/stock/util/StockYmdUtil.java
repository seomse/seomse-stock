/** 
 * <pre>
 *  파 일 명 : StockYmdUtil.java
 *  설    명 : 주식 년월일 관련 유틸성 클래스
 *                    
 *  작 성 자 : macle
 *  작 성 일 : 2017.10
 *  버    전 : 1.0
 *  수정이력 : 
 *  기타사항 :
 * </pre>
 * @author Copyrights 2017 by ㈜섬세한사람들. All right reserved.
 */
package com.seomse.stock.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StockYmdUtil {
		
	
	/**
	 * 년원일을 입력했을때 제무가 등록되어있다고 생각되는 년월을 얻기
	 * 
	 * @param ym
	 * @return
	 */
	public static String getMinYm(String ym){
		
		String yy = ym.substring(0,4);
		
		String mm  = ym.substring(ym.length()-2);
		
		int month = Integer.parseInt(mm);
		
		if(month ==12 || month ==1 || month==2){
			if(month ==12){
				return yy+"09";
			}else{
				int year = Integer.parseInt(yy);
				year = year-1;
				return year + "09";
			}
			
		}else if(month>=9 && month<12){
			return yy+"06";
		}else if(month>=6 && month<9){
			return yy+"03";
		}
//		else if(month>=3 && month<6){
		else{
			int year = Integer.parseInt(yy);
			year = year-1;
			return year + "12";
		}
	}
	
	/**
	 * 오늘기준의 YMD 얻기
	 * @return
	 */
	public String getNowYmd() {
		return  new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	
}
