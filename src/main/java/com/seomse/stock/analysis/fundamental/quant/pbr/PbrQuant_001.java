///**
// * <pre>
// *  파 일 명 : PbrQuant_001.java
// *  설    명 : PBR퀀트 점수측정
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
//package com.seomse.stock.analysis.fundamental.quant.pbr;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.seomse.commons.config.Config;
//import com.seomse.jdbc.JdbcQuery;
//import com.seomse.jdbc.naming.JdbcNaming;
//import com.seomse.stock.analysis.fundamental.quant.QuantPoint;
//import com.seomse.stock.analysis.fundamental.quant.no.FinancialStatementsNo;
//import com.seomse.stock.util.StockYmdUtil;
//
//public class PbrQuant_001 implements QuantPoint{
//
//	private static final Logger logger = LoggerFactory.getLogger(PbrQuant_001.class);
//
//	private String itemCode;
//
//	/**
//	 * 생성자
//	 * @param itemCode
//	 */
//	public PbrQuant_001(String itemCode){
//		this.itemCode = itemCode;
//	}
//	/**
//	 * 생성자
//	 */
//	public PbrQuant_001() {
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
//		//최근 분기 재무재표 정보
//		FinancialStatementsNo financialStatementsQuarterNo = JdbcNaming.getObj(FinancialStatementsNo.class
//				, "CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Q' AND YMD_CLOSING<='"+ minYm +"' AND ROWNUM < 2 ORDER BY YMD_CLOSING DESC");
//
//		//최근 연간 재무재표 정보
//		FinancialStatementsNo financialStatementsYearNo = JdbcNaming.getObj(FinancialStatementsNo.class
//				, "CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Y' AND YMD_CLOSING<='"+ minYm +"' AND ROWNUM < 2 ORDER BY YMD_CLOSING DESC");
//
//
//		//최근 거래일의 가격얻기
//		Long lastPirce = JdbcQuery.getResultLong("SELECT PRC_LAST FROM TB_STOCK_DAILY_ITEM WHERE CD_ITEM ='" +itemCode +"' "
//				+ "AND YMD_DAILY IN (SELECT MAX(YMD_DAILY) FROM TB_STOCK_DAILY_ITEM WHERE CD_ITEM ='" + itemCode  + "' AND YMD_DAILY <= " + standardYmd + " AND PRC_LAST IS NOT NULL)");
//
//		if(lastPirce == null){
//			return QuantPoint.NOT_VALID;
//		}
//
//		if(financialStatementsQuarterNo == null) {
//			logger.error("financial statements quarter null code: "  + itemCode);
//			return QuantPoint.NOT_VALID;
//		}
//
//		if(financialStatementsQuarterNo.getPRC_ASSET_TOTAL() == null) {
//
//			return QuantPoint.NOT_VALID;
//
//		}
//
//		double total80 = (double)financialStatementsQuarterNo.getPRC_ASSET_TOTAL()*80.0/100.0;
//		double capitalTotal = total80 - financialStatementsQuarterNo.getPRC_DEBT_TOTAL();
//
//		if(financialStatementsYearNo == null) {
//			return QuantPoint.NOT_VALID;
//		}
//
//		if(financialStatementsYearNo.getCNT_STOCK() == null) {
//			logger.error("CNT_STOCK null code: "  + itemCode);
//
//			return QuantPoint.NOT_VALID;
//		}
//
//		double assetTotal = (double)financialStatementsYearNo.getCNT_STOCK() * (double)lastPirce;
//
////		System.out.println(lastPirce);
////		System.out.println(financialStatementsYearNo.getCNT_STOCK());
////		System.out.println((long)assetTotal);
//
//		double pbr= assetTotal/(capitalTotal*100000000.0);
//
//
//		if(pbr <= 0.0){
//
//			return QuantPoint.NOT_VALID;
//		}
//
//
////		System.out.println(pbr);
//
//		double logPbrPoint = (3.0 - pbr)*10.0;
//		double pbrPoint = 0;
//		if(logPbrPoint < 0.0){
//			logPbrPoint = logPbrPoint*-1;
//			pbrPoint = Math.log(Math.E + logPbrPoint)*-1;
//			pbrPoint += 2.0;
//
//		}else{
//			pbrPoint = Math.log(Math.E + logPbrPoint);
//			pbrPoint = pbrPoint -1.0;
//		}
//
//		saveYmd = standardYmd;
//		savePoint = pbrPoint;
//		return pbrPoint;
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
//
//	@Override
//	public String getBasisExplan() {
//		return "pbr 가산점:"  + getPoint();
//	}
//
//
//	public static void main(String [] args){
//		Config.getConfig("");
////		065530
//		PbrQuant_001 dividendQuint = new PbrQuant_001("065530");
//
//		System.out.println(dividendQuint.getPoint());
//
////		double pbr = 0.5;
////
////		double logPbrPoint = (3.0 - pbr)*10.0;
////		double pbrPoint = 0;
////		if(logPbrPoint < 0.0){
////			logPbrPoint = logPbrPoint*-1;
////			pbrPoint = Math.log(Math.E + logPbrPoint)*-1;
////			pbrPoint += 2.0;
////
////		}else{
////			pbrPoint = Math.log(Math.E + logPbrPoint);
////			pbrPoint = pbrPoint -1.0;
////		}
////
////
////		System.out.println(pbrPoint);
//	}
//
//
//}
