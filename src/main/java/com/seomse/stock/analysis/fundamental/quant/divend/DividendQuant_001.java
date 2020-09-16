///**
// * <pre>
// *  파 일 명 : DividendQuant_001.java
// *  설    명 : 배당퀀트 점수측정
// *             배당율을 계산해서 배당율자체가 점수화
// *  작 성 자 : macle
// *  작 성 일 : 2017.10
// *  버    전 : 1.0
// *  수정이력 :
// *  기타사항 :
// * </pre>
// * @author Copyrights 2017 by ㈜섬세한사람들. All right reserved.
// */
//
//package com.seomse.stock.analysis.fundamental.quant.divend;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import com.seomse.commons.config.Config;
//import com.seomse.jdbc.JdbcQuery;
//import com.seomse.jdbc.naming.JdbcNaming;
//import com.seomse.stock.analysis.fundamental.quant.QuantPoint;
//import com.seomse.stock.analysis.fundamental.quant.no.FinancialStatementsDividendNo;
//import com.seomse.stock.util.PointUtil;
//import com.seomse.stock.util.StockYmdUtil;
//
//public class DividendQuant_001 implements QuantPoint{
//
//
//
//	private String itemCode;
//
//	/**
//	 * 생성자
//	 * @param itemCode
//	 */
//	public DividendQuant_001(String itemCode){
//		this.itemCode = itemCode;
//	}
//
//	/**
//	 * 생성자
//	 */
//	public DividendQuant_001() {
//
//	}
//
//	public void setItemCode(String itemCode) {
//		this.itemCode = itemCode;
//	}
//
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
//
//		//DB에서 데이터가져오는부분 공통 객체화 (재무정보관리객체 - 싱글턴)
//		//재무정봐관리객체 -싱글턴
//		//너무바빠서 다음에해야하는작업 ㅠㅠ
//		//DB와 메모리의 데이터동기화작업
//
//		String minYm ;
//
//		String nowYmd = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		if(nowYmd.equals(standardYmd)){
//			//기준일시가 오늘이면
//			minYm = JdbcQuery.getResultOne("SELECT MAX(YMD_CLOSING) FROM TB_STOCK_FSMT WHERE CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Y'");
//		}else{
//			//기준일시가 오늘이아니면
//			minYm = StockYmdUtil.getMinYm(standardYmd.substring(0,6));
//			minYm = minYm.substring(0,4) + "/"  + minYm.substring(4,6);
//		}
//		//최근4년치 배당관련 정보얻기
//		List<FinancialStatementsDividendNo> dividendNoYearList = JdbcNaming.getObjList( FinancialStatementsDividendNo.class
//				, "CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Y' AND YMD_CLOSING<='"+ minYm +"' AND ROWNUM < 5 ORDER BY YMD_CLOSING DESC");
//		//년간데이터가 3년보다 많은것만 유요하게 설정한다.
//		if(dividendNoYearList.size()<3){
//			return QuantPoint.NOT_VALID;
//		}
//
//
//		//배당횟수
//		int dividendCount =0;
//		//배당율합계 평균배당 계산할때 활용
//		double dividendSum = 0.0;
//
//		for(FinancialStatementsDividendNo dividendNo : dividendNoYearList){
//			dividendCount++;
//			if(dividendNo.getPCT_DIVIDEND() == null) {
//				return QuantPoint.NOT_VALID;
//			}
//
//			dividendSum += dividendNo.getPCT_DIVIDEND();
//		}
//
//		//배당을 2년이상 하지않은경우 유효하지않음
//		if(dividendCount < dividendNoYearList.size()-1 ){
//			return QuantPoint.NOT_VALID;
//		}
//		//예상배당금이 있을경우
//		FinancialStatementsDividendNo  estimationFinancialStatementsNo = JdbcNaming.getObj(FinancialStatementsDividendNo.class, "CD_ITEM='" + itemCode +"'  AND FG_ESTIMATION ='Y' AND TP_CLOSING ='Y' AND "
//				+ "YMD_CLOSING LIKE '" + standardYmd.substring(0,4) + "%'");
//
//		Long lastPirce = JdbcQuery.getResultLong("SELECT PRC_LAST FROM TB_STOCK_DAILY_ITEM WHERE CD_ITEM ='" +itemCode +"' "
//				+ "AND YMD_DAILY IN (SELECT MAX(YMD_DAILY) FROM TB_STOCK_DAILY_ITEM WHERE CD_ITEM ='" + itemCode  + "' AND YMD_DAILY <= " + standardYmd + " AND PRC_LAST IS NOT NULL)");
//
//		if(lastPirce == null){
//			return QuantPoint.NOT_VALID;
//		}
//
//		if(estimationFinancialStatementsNo != null && lastPirce !=null){
//			//예상배당금
//			Double dividendPrice = estimationFinancialStatementsNo.getPT_DPS_WON();
//
//			if(dividendPrice != null && dividendPrice > 0.1){
//				//배당율계산
//
//				savePoint = (double)dividendPrice/(double)lastPirce*100.0;
//				savePoint = PointUtil.reduce(2.0, savePoint);
//				return savePoint;
//			}
//		}
//
//
//		saveYmd = standardYmd;
//
//
//		double dividendAvg = dividendSum/(double)dividendNoYearList.size();
//		//최종배당률
//		FinancialStatementsDividendNo lastYearFinancialStatementsNo = dividendNoYearList.get(0);
//		if(lastYearFinancialStatementsNo.getPT_DPS_WON() != null){
//			double dividendPoint = (double)lastYearFinancialStatementsNo.getPT_DPS_WON()/(double)lastPirce*100.0;
//			//4년치 배당평균과 최종배당율중 작은점수로 측정
//			if(dividendAvg < dividendPoint){
//				dividendPoint = dividendAvg;
//			}
//			savePoint = dividendPoint;
//			savePoint = PointUtil.reduce(2.0, savePoint);
//			return savePoint;
//		}
//
//
//		savePoint = dividendAvg;
//		savePoint = PointUtil.reduce(2.0, savePoint);
//		return savePoint;
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
//		return "배당 가산점:"  + getPoint();
//	}
//
//
//	public static void main(String [] args){
//		Config.getConfig("");
//
//		DividendQuant_001 dividendQuint = new DividendQuant_001("095660");
//
//		System.out.println(dividendQuint.getPoint());
//	}
//
//}
