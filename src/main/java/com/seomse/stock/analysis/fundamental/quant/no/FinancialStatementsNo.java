///**
// * <pre>
// *  파 일 명 : FinancialStatementsNo.java
// *  설    명 : 제무제표 전체정보
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
//package com.seomse.stock.analysis.fundamental.quant.no;
//
//import com.seomse.jdbc.annotation.PrimaryKey;
//import com.seomse.jdbc.annotation.Table;
//
//@Table(name="TB_STOCK_FSMT")
//public class FinancialStatementsNo {
//	@PrimaryKey(seq = 1)
//	private String CD_ITEM;
//	@PrimaryKey(seq = 2)
//	private String YMD_CLOSING;
//	@PrimaryKey(seq = 3)
//	private String TP_CLOSING;
//	private String FG_ESTIMATION = "N"  ;
//	private Long PRC_SALES;
//	private Long PRC_OPF;
//	private Long PRC_CBPF;
//	private Long PRC_NPF;
//	private Long PRC_NPF_DMN;
//	private Long PRC_NPF_NDMN;
//	private Long PRC_ASSET_TOTAL;
//	private Long PRC_DEBT_TOTAL;
//	private Long PRC_CAPITAL_TOTAL;
//	private Long PRC_CAPITAL_TOTAL_DMN;
//	private Long PRC_CAPITAL_TOTAL_NDMN;
//	private Long PRC_CAPITAL;
//	private Long PRC_OCF;
//	private Long PRC_ICF;
//	private Long PRC_FNCF;
//	private Long PRC_CAPEX;
//	private Long PRC_FCF;
//	private Long PRC_INTEREST_DEBT;
//	private Double PCT_OPF;
//	private Double PCT_NPF;
//	private Double PCT_ROE;
//	private Double PCT_ROA;
//	private Double PCT_DEBT;
//	private Double PCT_CRR;
//	private Double PT_EPS_WON;
//	private Double PT_PER;
//	private Double PT_BPS_WON;
//	private Double PT_PBR;
//	private Double PT_DPS_WON;
//	private Double PCT_DIVIDEND;
//	private Double PCT_CASH_DIVIDEND;
//	private Long CNT_STOCK;
//	public String getCD_ITEM() {
//		return CD_ITEM;
//	}
//	public void setCD_ITEM(String cD_ITEM) {
//		CD_ITEM = cD_ITEM;
//	}
//	public String getYMD_CLOSING() {
//		return YMD_CLOSING;
//	}
//	public void setYMD_CLOSING(String yMD_CLOSING) {
//		YMD_CLOSING = yMD_CLOSING;
//	}
//	public String getTP_CLOSING() {
//		return TP_CLOSING;
//	}
//	public void setTP_CLOSING(String tP_CLOSING) {
//		TP_CLOSING = tP_CLOSING;
//	}
//	public String getFG_ESTIMATION() {
//		return FG_ESTIMATION;
//	}
//	public void setFG_ESTIMATION(String fG_ESTIMATION) {
//		FG_ESTIMATION = fG_ESTIMATION;
//	}
//	public Long getPRC_SALES() {
//		return PRC_SALES;
//	}
//	public void setPRC_SALES(Long pRC_SALES) {
//		PRC_SALES = pRC_SALES;
//	}
//	public Long getPRC_OPF() {
//		return PRC_OPF;
//	}
//	public void setPRC_OPF(Long pRC_OPF) {
//		PRC_OPF = pRC_OPF;
//	}
//	public Long getPRC_CBPF() {
//		return PRC_CBPF;
//	}
//	public void setPRC_CBPF(Long pRC_CBPF) {
//		PRC_CBPF = pRC_CBPF;
//	}
//	public Long getPRC_NPF() {
//		return PRC_NPF;
//	}
//	public void setPRC_NPF(Long pRC_NPF) {
//		PRC_NPF = pRC_NPF;
//	}
//	public Long getPRC_NPF_DMN() {
//		return PRC_NPF_DMN;
//	}
//	public void setPRC_NPF_DMN(Long pRC_NPF_DMN) {
//		PRC_NPF_DMN = pRC_NPF_DMN;
//	}
//	public Long getPRC_NPF_NDMN() {
//		return PRC_NPF_NDMN;
//	}
//	public void setPRC_NPF_NDMN(Long pRC_NPF_NDMN) {
//		PRC_NPF_NDMN = pRC_NPF_NDMN;
//	}
//	public Long getPRC_ASSET_TOTAL() {
//		return PRC_ASSET_TOTAL;
//	}
//	public void setPRC_ASSET_TOTAL(Long pRC_ASSET_TOTAL) {
//		PRC_ASSET_TOTAL = pRC_ASSET_TOTAL;
//	}
//	public Long getPRC_DEBT_TOTAL() {
//		return PRC_DEBT_TOTAL;
//	}
//	public void setPRC_DEBT_TOTAL(Long pRC_DEBT_TOTAL) {
//		PRC_DEBT_TOTAL = pRC_DEBT_TOTAL;
//	}
//	public Long getPRC_CAPITAL_TOTAL() {
//		return PRC_CAPITAL_TOTAL;
//	}
//	public void setPRC_CAPITAL_TOTAL(Long pRC_CAPITAL_TOTAL) {
//		PRC_CAPITAL_TOTAL = pRC_CAPITAL_TOTAL;
//	}
//	public Long getPRC_CAPITAL_TOTAL_DMN() {
//		return PRC_CAPITAL_TOTAL_DMN;
//	}
//	public void setPRC_CAPITAL_TOTAL_DMN(Long pRC_CAPITAL_TOTAL_DMN) {
//		PRC_CAPITAL_TOTAL_DMN = pRC_CAPITAL_TOTAL_DMN;
//	}
//	public Long getPRC_CAPITAL_TOTAL_NDMN() {
//		return PRC_CAPITAL_TOTAL_NDMN;
//	}
//	public void setPRC_CAPITAL_TOTAL_NDMN(Long pRC_CAPITAL_TOTAL_NDMN) {
//		PRC_CAPITAL_TOTAL_NDMN = pRC_CAPITAL_TOTAL_NDMN;
//	}
//	public Long getPRC_CAPITAL() {
//		return PRC_CAPITAL;
//	}
//	public void setPRC_CAPITAL(Long pRC_CAPITAL) {
//		PRC_CAPITAL = pRC_CAPITAL;
//	}
//	public Long getPRC_OCF() {
//		return PRC_OCF;
//	}
//	public void setPRC_OCF(Long pRC_OCF) {
//		PRC_OCF = pRC_OCF;
//	}
//	public Long getPRC_ICF() {
//		return PRC_ICF;
//	}
//	public void setPRC_ICF(Long pRC_ICF) {
//		PRC_ICF = pRC_ICF;
//	}
//	public Long getPRC_FNCF() {
//		return PRC_FNCF;
//	}
//	public void setPRC_FNCF(Long pRC_FNCF) {
//		PRC_FNCF = pRC_FNCF;
//	}
//	public Long getPRC_CAPEX() {
//		return PRC_CAPEX;
//	}
//	public void setPRC_CAPEX(Long pRC_CAPEX) {
//		PRC_CAPEX = pRC_CAPEX;
//	}
//	public Long getPRC_FCF() {
//		return PRC_FCF;
//	}
//	public void setPRC_FCF(Long pRC_FCF) {
//		PRC_FCF = pRC_FCF;
//	}
//	public Long getPRC_INTEREST_DEBT() {
//		return PRC_INTEREST_DEBT;
//	}
//	public void setPRC_INTEREST_DEBT(Long pRC_INTEREST_DEBT) {
//		PRC_INTEREST_DEBT = pRC_INTEREST_DEBT;
//	}
//	public Double getPCT_OPF() {
//		return PCT_OPF;
//	}
//	public void setPCT_OPF(Double pCT_OPF) {
//		PCT_OPF = pCT_OPF;
//	}
//	public Double getPCT_NPF() {
//		return PCT_NPF;
//	}
//	public void setPCT_NPF(Double pCT_NPF) {
//		PCT_NPF = pCT_NPF;
//	}
//	public Double getPCT_ROE() {
//		return PCT_ROE;
//	}
//	public void setPCT_ROE(Double pCT_ROE) {
//		PCT_ROE = pCT_ROE;
//	}
//	public Double getPCT_ROA() {
//		return PCT_ROA;
//	}
//	public void setPCT_ROA(Double pCT_ROA) {
//		PCT_ROA = pCT_ROA;
//	}
//	public Double getPCT_DEBT() {
//		return PCT_DEBT;
//	}
//	public void setPCT_DEBT(Double pCT_DEBT) {
//		PCT_DEBT = pCT_DEBT;
//	}
//	public Double getPCT_CRR() {
//		return PCT_CRR;
//	}
//	public void setPCT_CRR(Double pCT_CRR) {
//		PCT_CRR = pCT_CRR;
//	}
//	public Double getPT_EPS_WON() {
//		return PT_EPS_WON;
//	}
//	public void setPT_EPS_WON(Double pT_EPS_WON) {
//		PT_EPS_WON = pT_EPS_WON;
//	}
//	public Double getPT_PER() {
//		return PT_PER;
//	}
//	public void setPT_PER(Double pT_PER) {
//		PT_PER = pT_PER;
//	}
//	public Double getPT_BPS_WON() {
//		return PT_BPS_WON;
//	}
//	public void setPT_BPS_WON(Double pT_BPS_WON) {
//		PT_BPS_WON = pT_BPS_WON;
//	}
//	public Double getPT_PBR() {
//		return PT_PBR;
//	}
//	public void setPT_PBR(Double pT_PBR) {
//		PT_PBR = pT_PBR;
//	}
//	public Double getPT_DPS_WON() {
//		return PT_DPS_WON;
//	}
//	public void setPT_DPS_WON(Double pT_DPS_WON) {
//		PT_DPS_WON = pT_DPS_WON;
//	}
//	public Double getPCT_DIVIDEND() {
//		return PCT_DIVIDEND;
//	}
//	public void setPCT_DIVIDEND(Double pCT_DIVIDEND) {
//		PCT_DIVIDEND = pCT_DIVIDEND;
//	}
//	public Double getPCT_CASH_DIVIDEND() {
//		return PCT_CASH_DIVIDEND;
//	}
//	public void setPCT_CASH_DIVIDEND(Double pCT_CASH_DIVIDEND) {
//		PCT_CASH_DIVIDEND = pCT_CASH_DIVIDEND;
//	}
//	public Long getCNT_STOCK() {
//		return CNT_STOCK;
//	}
//	public void setCNT_STOCK(Long cNT_STOCK) {
//		CNT_STOCK = cNT_STOCK;
//	}
//
//
//
//}
