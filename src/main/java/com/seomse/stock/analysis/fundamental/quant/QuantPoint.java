///**
// * <pre>
// *  파 일 명 : QuantPoint.java
// *  설    명 : 퀀트 점수
// *
// *  작 성 자 : malce
// *  작 성 일 : 2017.10
// *  버    전 : 1.0
// *  수정이력 :
// *  기타사항 :
// * </pre>
// * @author Copyrights 2017 by ㈜섬세한사람들. All right reserved.
// */
//
//package com.seomse.stock.analysis.fundamental.quant;
//
//import com.seomse.commons.utils.ranking.RankingInfo;
//
//public interface QuantPoint extends RankingInfo{
//	/**
//	 * 유효하지않은 점수
//	 */
//	double NOT_VALID = Double.NaN;
//
//
//
//	/**
//	 * 종목코드얻기
//	 * @return
//	 */
//	String getItemCode();
//
//	/**
//	 * 종목코드설정
//	 * @param itemCode
//	 */
//	void setItemCode(String itemCode);
//
//	/**
//	 * 퀀트점수 얻기
//	 * @param standardYmd 기준년월일
//	 * @return
//	 */
//	double getPoint(String standardYmd);
//
//	/**
//	 * 근거 설명얻기
//	 */
//	String getBasisExplan();
//
//}
