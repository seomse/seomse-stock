///**
// * <pre>
// *  파 일 명 : OpfIncreaseQuant_001.java
// *  설    명 : 영업이익 상승률 점수측정
// *
// *  작 성 자 : macle
// *  작 성 일 : 2017.11
// *  버    전 : 1.0
// *  수정이력 :
// *  기타사항 :
// * </pre>
// * @author Copyrights 2017 by ㈜섬세한사람들. All right reserved.
// */
//
//package com.seomse.stock.analysis.fundamental.quant.opf;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.seomse.commons.config.Config;
//import com.seomse.commons.utils.ExceptionUtil;
//import com.seomse.jdbc.JdbcQuery;
//import com.seomse.jdbc.naming.JdbcNaming;
//import com.seomse.stock.analysis.fundamental.quant.QuantPoint;
//import com.seomse.stock.analysis.fundamental.quant.no.FinancialStatementsNo;
//import com.seomse.stock.util.StockYmdUtil;
//
//public class OpfIncreaseQuant_001 implements QuantPoint{
//
//	private static final Logger logger = LoggerFactory.getLogger(OpfIncreaseQuant_001.class);
//
//	private String itemCode;
//
//	/**
//	 * 생성자
//	 * @param itemCode
//	 */
//	public OpfIncreaseQuant_001(String itemCode){
//		this.itemCode = itemCode;
//	}
//
//	public OpfIncreaseQuant_001() {
//
//	}
//
//	public void setItemCode(String itemCode) {
//		this.itemCode = itemCode;
//	}
//	/**
//	 * 종목코드얻기
//	 * @return
//	 */
//	@Override
//	public String getItemCode() {
//		return itemCode;
//	}
//
//	/**
//	 * 점수얻기
//	 * @return
//	 */
//	public double getPoint(){
//
//		String nowYmd = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		return getPoint(nowYmd);
//	}
//	private Double savePoint = null;
//	private String saveYmd = null;
//	/**
//	 * 점수얻기
//	 * @param standardYmd 기준년월일
//	 * @return
//	 */
//	@Override
//	public double getPoint(String standardYmd){
//		if(savePoint != null && saveYmd != null && saveYmd.equals(standardYmd)) {
//			return savePoint;
//		}
//		saveYmd = standardYmd;
//		String minYm ;
//
//		String nowYmd = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		if(nowYmd.equals(standardYmd)){
//			//기준일시가 오늘이면
//			minYm = JdbcQuery.getResultOne("SELECT MAX(YMD_CLOSING) FROM TB_STOCK_FSMT WHERE CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Q'");
//		}else{
//			//기준일시가 오늘이아니면
//			minYm = StockYmdUtil.getMinYm(standardYmd.substring(0,6));
//			minYm = minYm.substring(0,4) + "/"  + minYm.substring(4,6);
//		}
//
//		//최근 12분기 데이터 얻기
//		List<FinancialStatementsNo> dividendNoQuarterList = JdbcNaming.getObjList( FinancialStatementsNo.class
//				, "CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Q' AND YMD_CLOSING<='"+ minYm +"' AND ROWNUM < 13 ORDER BY YMD_CLOSING DESC");
//		//5분기보다 많은것만 유효하게 설정
//		if(dividendNoQuarterList.size()<5){
//			//점수 계산을 할필요가없어서 0점 리턴
//			//유효하지 않은 값은 아님
//			return 0.0;
//		}
//
//
//		List<FinancialStatementsNo> ascList = new ArrayList<>();
//		for(int i =dividendNoQuarterList.size()-1 ; i>-1 ; i--) {
//			ascList.add(dividendNoQuarterList.get(i));
//		}
//
//		try {
//		List<Double> pointList = new ArrayList<>();
//		for(int i=3 ;i<ascList.size() ; i++) {
//
//			int start = i-3;
//
//			double point = 0.0;
//
//			for(int j=start ; j<=i ; j++) {
//				point+= (double) ascList.get(j).getPRC_OPF();
//			}
//			pointList.add(point);
//		}
//
//
//		double point = 0.0;
//
//
//		for(int i=1 ; i<pointList.size() ; i++) {
//			double last = pointList.get(i-1);
//
//			double opf = pointList.get(i);
//
//			if(last < 0.0 && opf < 0.0) {
//				//둘다양수
//				double percent =opf/last;
//
//				double temp = 1.0 - percent;
//				if(temp > 0) {
//					if(temp > 0.1) {
//						point += 0.1;
//					}else {
//						point += temp;
//					}
//
//				}else if(temp < 0){
//					if(temp < -0.1) {
//						point -= 0.1;
//					}else {
//						point += temp;
//					}
//
//				}
//
//			}else if(last < 0.0 && opf >= 0.0){
//				point += 0.1;
//			}else if(last >= 0.0 && opf < 0.0){
//				point -= 0.1;
//			}else {
//				double percent =opf/last;
//
//				double temp = 1.0 - percent;
//				if(temp > 0) {
//					if(temp > 0.1) {
//						point -= 0.1;
//					}else {
//						point -= temp;
//					}
//
//				}else if(temp < 0){
//					if(temp < -0.1) {
//						point += 0.1;
//					}else {
//						point -= temp;
//					}
//
//				}
//			}
//
//
//		}
//		saveYmd = standardYmd;
//		savePoint = point;
//
//		return point;
//
//		}catch(Exception e) {
//			logger.error(ExceptionUtil.getStackTrace(e));
//			return NOT_VALID;
//		}
//
//	}
//	private int ranking = -1;
//
//	@Override
//	public int getRanking() {
//		return ranking;
//	}
//
//	@Override
//	public double getRankingPoint() {
//		if(savePoint != null) {
//			return savePoint;
//		}
//		return getPoint();
//	}
//
//	@Override
//	public void setRanking(int ranking) {
//		this.ranking =ranking;
//	}
//
//	@Override
//	public String getBasisExplan() {
//		return "영업이익 증가률 가산점:"  + getPoint();
//	}
//
//	public static void main(String [] args){
//		Config.getConfig("");
//
//		OpfIncreaseQuant_001 quint_001 = new OpfIncreaseQuant_001("002460");
//
//		System.out.println(quint_001.getPoint());
////		double point = 0.0;
////		double last = 40.0;
////
////		double opf = 50.0;
////		double percent =opf/last;
////
////		double temp = 1.0 - percent;
////		if(temp > 0) {
////			if(temp > 0.1) {
////				point -= 0.1;
////			}else {
////				point -= temp;
////			}
////
////		}else if(temp < 0){
////			if(temp < -0.1) {
////				point += 0.1;
////			}else {
////				point -= temp;
////			}
////
////		}
////
////
////		System.out.println(point);
//	}
//
//
//}
