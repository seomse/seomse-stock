///**
// * <pre>
// *  파 일 명 : FinancialStatementsNo.java
// *  설    명 : 제무제표 배당정보
// *
// *  작 성 자 : macle
// *  작 성 일 : 2017.10
// *  버    전 : 1.0
// *  수정이력 :
// *  기타사항 :
// * </pre>
// * @author Copyrights 2017 by ㈜섬세한사람들. All right reserved.
// */
//
//package com.seomse.stock.analysis.fundamental.quant.no;
//
//import com.seomse.jdbc.annotation.PrimaryKey;
//import com.seomse.jdbc.annotation.Table;
//
//@Table(name="TB_STOCK_FSMT")
//public class FinancialStatementsDividendNo {
//	@PrimaryKey(seq = 1)
//	private String CD_ITEM;
//	@PrimaryKey(seq = 2)
//	private String YMD_CLOSING;
//	@PrimaryKey(seq = 3)
//	private String TP_CLOSING;
//
//	private Double PCT_DIVIDEND;
//
//	private Double PT_DPS_WON;
//
//	public String getCD_ITEM() {
//		return CD_ITEM;
//	}
//
//	public void setCD_ITEM(String cD_ITEM) {
//		CD_ITEM = cD_ITEM;
//	}
//
//	public String getYMD_CLOSING() {
//		return YMD_CLOSING;
//	}
//
//	public void setYMD_CLOSING(String yMD_CLOSING) {
//		YMD_CLOSING = yMD_CLOSING;
//	}
//
//	public String getTP_CLOSING() {
//		return TP_CLOSING;
//	}
//
//	public void setTP_CLOSING(String tP_CLOSING) {
//		TP_CLOSING = tP_CLOSING;
//	}
//
//	public Double getPCT_DIVIDEND() {
//		return PCT_DIVIDEND;
//	}
//
//	public void setPCT_DIVIDEND(Double pCT_DIVIDEND) {
//		PCT_DIVIDEND = pCT_DIVIDEND;
//	}
//
//	public Double getPT_DPS_WON() {
//		return PT_DPS_WON;
//	}
//
//	public void setPT_DPS_WON(Double pT_DPS_WON) {
//		PT_DPS_WON = pT_DPS_WON;
//	}
//
//
//}
