///**
// * <pre>
// *  파 일 명 : OperatingCashFlowQuant_002.java
// *  설    명 : 영업활동 현금흐름 퀀트 점수
// *             재무안전 체크용 그대로 사용하기보다는 점수에 따라 다르게 사용하는게 좋음.
// *             0.0 이상이었을때 믿을만한 재무이고
// *             -0.4이면 현금흐름상으로 재무를 믿을수 없다고 판단하는게 좋음
// *             4분기가 전부 단기순이익보다 높지않고 낮을경우 -0.1점
// *  작 성 자 : macle
// *  작 성 일 : 2017.10
// *  버    전 : 1.0
// *  수정이력 :
// *  기타사항 :
// * </pre>
// * @author Copyrights 2017 by ㈜섬세한사람들. All right reserved.
// */
//package com.seomse.stock.analysis.fundamental.quant.cashflow;
//
//import com.seomse.commons.config.Config;
//import com.seomse.jdbc.JdbcQuery;
//import com.seomse.jdbc.naming.JdbcNaming;
//import com.seomse.stock.analysis.fundamental.quant.QuantPoint;
//import com.seomse.stock.analysis.fundamental.quant.no.FinancialStatementsOcfNo;
//import com.seomse.stock.data.store.DataStore;
//import com.seomse.stock.data.store.DataStoreManager;
//import com.seomse.stock.util.StockYmdUtil;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//public class OperatingCashFlowQuantI_002 implements QuantPoint{
//
//
//
//	private String itemCode;
//
//	/**
//	 * 생성자
//	 * @param itemCode
//	 */
//	public OperatingCashFlowQuantI_002(String itemCode) {
//		this.itemCode = itemCode;
//	}
//	/**
//	 * 생성자
//	 */
//	public OperatingCashFlowQuantI_002() {
//
//	}
//
//
//	public void setItemCode(String itemCode) {
//		this.itemCode = itemCode;
//	}
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
//
//
//		String nowYmd = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		if(savePoint != null && saveYmd.equals(nowYmd)) {
//			return savePoint;
//		}
//		DataStore dataStore = DataStoreManager.getInstance().getDataStore();
//		return getPoint(dataStore);
//	}
//
//
//	public double getPoint(DataStore dataStore) {
//
//		return 0.0;
//	}
//
//	private Double savePoint = null;
//	private String saveYmd = null;
//	@Override
//	public double getPoint(String standardYmd) {
//		if(savePoint != null && saveYmd != null && saveYmd.equals(standardYmd)) {
//			return savePoint;
//		}
//		saveYmd = standardYmd;
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
//		//최근 4분기 분기별재무를 가져온다.
//		List<FinancialStatementsOcfNo> financialStatementsQuarterNoList = JdbcNaming.getObjList(FinancialStatementsOcfNo.class
//				, "CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Q' AND YMD_CLOSING<='"+ minYm +"' AND ROWNUM < 5 ORDER BY YMD_CLOSING DESC");
//
//		double flowPoint = 0;
//
//		for(int i=financialStatementsQuarterNoList.size()-1 ; i >-1 ; i--){
//			FinancialStatementsOcfNo financialStatementsOcfNo = financialStatementsQuarterNoList.get(i);
//			if(financialStatementsOcfNo.getPRC_NPF()  < 0){
//				continue;
//			}
//			if(financialStatementsOcfNo.getPRC_OCF() < 0l){
//				flowPoint -= 0.1;
//			}else{
//				long flowPrice =financialStatementsOcfNo.getPRC_OCF() - financialStatementsOcfNo.getPRC_NPF()  ;
//				if(flowPrice > 0){
//					flowPoint += 0.1;
//				}
//			}
//		}
//		saveYmd = standardYmd;
//		savePoint = flowPoint;
//		return flowPoint;
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
//	@Override
//	public String getBasisExplan() {
//		return "영엽활동 현금흐름 가산점:"  + getPoint();
//	}
//
//	public static void main(String [] args){
//		Config.getConfig("");
//
//		OperatingCashFlowQuantI_002 operatingCashFlowQuint = new OperatingCashFlowQuantI_002("011500");
//
//		System.out.println(operatingCashFlowQuint.getPoint());
//	}
//}
