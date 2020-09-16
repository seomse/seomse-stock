///**
// * <pre>
// *  파 일 명 : DebtReduceQuant_001.java
// *  설    명 : 부채비율 감소율 점수측정
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
//package com.seomse.stock.analysis.fundamental.quant.debt;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import com.seomse.commons.config.Config;
//import com.seomse.jdbc.JdbcQuery;
//import com.seomse.jdbc.naming.JdbcNaming;
//import com.seomse.stock.analysis.fundamental.quant.QuantPoint;
//import com.seomse.stock.analysis.fundamental.quant.no.FinancialStatementsNo;
//import com.seomse.stock.util.StockYmdUtil;
//
//public class DebtReduceQuant_001 implements QuantPoint{
//
////	private static final Logger logger = LoggerFactory.getLogger(PerQuint_001.class);
//
//	private String itemCode;
//
//	/**
//	 * 생성자
//	 * @param itemCode
//	 */
//	public DebtReduceQuant_001(String itemCode){
//		this.itemCode = itemCode;
//	}
//	/**
//	 * 생성자
//	 */
//	public DebtReduceQuant_001() {
//
//	}
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
//		//최근4년치 연간재무정보 얻기
//		List<FinancialStatementsNo> dividendNoYearList = JdbcNaming.getObjList( FinancialStatementsNo.class
//				, "CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Y' AND YMD_CLOSING<='"+ minYm +"' AND ROWNUM < 5 ORDER BY YMD_CLOSING DESC");
//		//년간데이터가 3년보다 많은것만 유요하게 설정한다.
//
//		if(dividendNoYearList.size()<2){
//			//점수 계산을 할필요가없어서 0점 리턴
//			//유효하지 않은 값은 아님
//			return QuantPoint.NOT_VALID;
//		}
//
//		double point = 0.0;
//
//		List<FinancialStatementsNo> pointList = new ArrayList<>();
//		for(int i =dividendNoYearList.size()-1 ; i>-1 ; i--) {
//			pointList.add(dividendNoYearList.get(i));
//		}
//		FinancialStatementsNo yearLast = pointList.get(pointList.size()-1);
//
//		if(!minYm.equals(yearLast.getYMD_CLOSING())) {
//			//최근 분기 제무재표 정보
//			FinancialStatementsNo financialStatementsQuarterNo = JdbcNaming.getObj(FinancialStatementsNo.class
//					, "CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Q' AND YMD_CLOSING<='"+ minYm +"' AND ROWNUM < 2 ORDER BY YMD_CLOSING DESC");
//
//
//			if(!yearLast.getYMD_CLOSING().equals(financialStatementsQuarterNo.getYMD_CLOSING())) {
//				pointList.add(financialStatementsQuarterNo);
//			}
//
//
//
//		}
//
//
//		for(int i =1 ; i<pointList.size() ; i++) {
//			FinancialStatementsNo lastNo = pointList.get(i-1);
//			if( lastNo.getPRC_DEBT_TOTAL() == null) {
//				return QuantPoint.NOT_VALID;
//			}
//			double lastDept = (double) lastNo.getPRC_DEBT_TOTAL();
//
//			FinancialStatementsNo statementsNo = pointList.get(i);
//			if(statementsNo.getPRC_DEBT_TOTAL() == null) {
//				return QuantPoint.NOT_VALID;
//			}
//			double dept = (double)statementsNo.getPRC_DEBT_TOTAL();
//			double percent =dept/lastDept;
//
//			double temp = 1.0 - percent;
//			if(temp > 0) {
//				if(temp > 0.1) {
//					point += 0.1;
//				}else {
//					point += temp;
//				}
//
//			}else if(temp < 0){
//				if(temp < -0.1) {
//					point -= 0.1;
//				}else {
//					point += temp;
//				}
//
//			}
//
//		}
//
//
//
//		saveYmd = standardYmd;
//		savePoint = point;
//
//
//		return point;
//
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
//	public static void main(String [] args){
//		Config.getConfig("");
//
//		DebtReduceQuant_001 debtReduceQuint_001 = new DebtReduceQuant_001("002460");
////
//		System.out.println(debtReduceQuint_001.getPoint());
//
//	}
//	@Override
//	public String getBasisExplan() {
//		return "부채비율,배체감소율 가산점:"  + getPoint();
//	}
//
//
//}
