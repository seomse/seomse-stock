///*
// * Copyright (C) 2020 Seomse Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.seomse.stock.data.store.no;
//
//import com.seomse.jdbc.annotation.DateTime;
//import com.seomse.jdbc.annotation.PrimaryKey;
//import com.seomse.jdbc.annotation.Table;
//
///**
// * <pre>
// *  파 일 명 : StockFsmtNo.java
// *  설    명 : 주식 재무제표 관련 정보
// 				T_STOCK_FSMT 테이블 내용
// 				주식 서비스화 작업
// *  작 성 일 : 2020.04.16
// *  버    전 : 1.0
// *  수정이력 :
// *  기타사항 :
// * </pre>
// * @author Copyrights 2020 by ㈜섬세한사람들. All right reserved.
// */
//@Table(name="T_STOCK_FSMT")
//public class StockFsmtNo {
//	@PrimaryKey(seq = 1)
//	private String ITEM_CD;
//	@PrimaryKey(seq = 2)
//	private String SETTLEMENT_YM;
//	@PrimaryKey(seq = 3)
//	private String SETTLEMENT_TP;
//	private String ESTIMATE_FG = "N"  ;
//	private Double SALES_PRC;
//	private Double OPF_PRC;
//	private Double CBPF_PRC;
//	private Double NPF_PRC;
//	private Double NPF_DMN_PRC;
//	private Double NPF_NDMN_PRC;
//	private Double ASSET_TOTAL_PRC;
//	private Double DEBT_TOTAL_PRC;
//	private Double CAPITAL_TOTAL_PRC;
//	private Double CAPITAL_TOTAL_DMN_PRC;
//	private Double CAPITAL_TOTAL_NDMN_PRC;
//	private Double CAPITAL_PRC;
//	private Double OCF_PRC;
//	private Double ICF_PRC;
//	private Double FNCF_PRC;
//	private Double CAPEX_PRC;
//	private Double FCF_PRC;
//	private Double INTEREST_DEBT_PRC;
//	private Double OPF_RT;
//	private Double NPF_RT;
//	private Double ROE_RT;
//	private Double ROA_RT;
//	private Double DEBT_RT;
//	private Double CRR_RT;
//	private Double EPS_WON_PRC;
//	private Double PER_RT;
//	private Double BPS_WON_PRC;
//	private Double PBR_RT;
//	private Double DPS_WON_PRC;
//	private Double DIVIDEND_RT;
//	private Double CASH_DIVIDEND_RT;
//	private Long STOCK_CNT;
//	@DateTime
//	private Long UPT_LAST_DT = System.currentTimeMillis();
//
//
//	public String getITEM_CD() {
//		return ITEM_CD;
//	}
//
//	public void setITEM_CD(String ITEM_CD) {
//		this.ITEM_CD = ITEM_CD;
//	}
//
//	public String getSETTLEMENT_YM() {
//		return SETTLEMENT_YM;
//	}
//
//	public void setSETTLEMENT_YM(String SETTLEMENT_YM) {
//		this.SETTLEMENT_YM = SETTLEMENT_YM;
//	}
//
//	public String getSETTLEMENT_TP() {
//		return SETTLEMENT_TP;
//	}
//
//	public void setSETTLEMENT_TP(String SETTLEMENT_TP) {
//		this.SETTLEMENT_TP = SETTLEMENT_TP;
//	}
//
//	public String getESTIMATE_FG() {
//		return ESTIMATE_FG;
//	}
//
//	public void setESTIMATE_FG(String ESTIMATE_FG) {
//		this.ESTIMATE_FG = ESTIMATE_FG;
//	}
//
//	public Double getSALES_PRC() {
//		return SALES_PRC;
//	}
//
//	public void setSALES_PRC(Double SALES_PRC) {
//		this.SALES_PRC = SALES_PRC;
//	}
//
//	public Double getOPF_PRC() {
//		return OPF_PRC;
//	}
//
//	public void setOPF_PRC(Double OPF_PRC) {
//		this.OPF_PRC = OPF_PRC;
//	}
//
//	public Double getCBPF_PRC() {
//		return CBPF_PRC;
//	}
//
//	public void setCBPF_PRC(Double CBPF_PRC) {
//		this.CBPF_PRC = CBPF_PRC;
//	}
//
//	public Double getNPF_PRC() {
//		return NPF_PRC;
//	}
//
//	public void setNPF_PRC(Double NPF_PRC) {
//		this.NPF_PRC = NPF_PRC;
//	}
//
//	public Double getNPF_DMN_PRC() {
//		return NPF_DMN_PRC;
//	}
//
//	public void setNPF_DMN_PRC(Double NPF_DMN_PRC) {
//		this.NPF_DMN_PRC = NPF_DMN_PRC;
//	}
//
//	public Double getNPF_NDMN_PRC() {
//		return NPF_NDMN_PRC;
//	}
//
//	public void setNPF_NDMN_PRC(Double NPF_NDMN_PRC) {
//		this.NPF_NDMN_PRC = NPF_NDMN_PRC;
//	}
//
//	public Double getASSET_TOTAL_PRC() {
//		return ASSET_TOTAL_PRC;
//	}
//
//	public void setASSET_TOTAL_PRC(Double ASSET_TOTAL_PRC) {
//		this.ASSET_TOTAL_PRC = ASSET_TOTAL_PRC;
//	}
//
//	public Double getDEBT_TOTAL_PRC() {
//		return DEBT_TOTAL_PRC;
//	}
//
//	public void setDEBT_TOTAL_PRC(Double DEBT_TOTAL_PRC) {
//		this.DEBT_TOTAL_PRC = DEBT_TOTAL_PRC;
//	}
//
//	public Double getCAPITAL_TOTAL_PRC() {
//		return CAPITAL_TOTAL_PRC;
//	}
//
//	public void setCAPITAL_TOTAL_PRC(Double CAPITAL_TOTAL_PRC) {
//		this.CAPITAL_TOTAL_PRC = CAPITAL_TOTAL_PRC;
//	}
//
//	public Double getCAPITAL_TOTAL_DMN_PRC() {
//		return CAPITAL_TOTAL_DMN_PRC;
//	}
//
//	public void setCAPITAL_TOTAL_DMN_PRC(Double CAPITAL_TOTAL_DMN_PRC) {
//		this.CAPITAL_TOTAL_DMN_PRC = CAPITAL_TOTAL_DMN_PRC;
//	}
//
//	public Double getCAPITAL_TOTAL_NDMN_PRC() {
//		return CAPITAL_TOTAL_NDMN_PRC;
//	}
//
//	public void setCAPITAL_TOTAL_NDMN_PRC(Double CAPITAL_TOTAL_NDMN_PRC) {
//		this.CAPITAL_TOTAL_NDMN_PRC = CAPITAL_TOTAL_NDMN_PRC;
//	}
//
//	public Double getCAPITAL_PRC() {
//		return CAPITAL_PRC;
//	}
//
//	public void setCAPITAL_PRC(Double CAPITAL_PRC) {
//		this.CAPITAL_PRC = CAPITAL_PRC;
//	}
//
//	public Double getOCF_PRC() {
//		return OCF_PRC;
//	}
//
//	public void setOCF_PRC(Double OCF_PRC) {
//		this.OCF_PRC = OCF_PRC;
//	}
//
//	public Double getICF_PRC() {
//		return ICF_PRC;
//	}
//
//	public void setICF_PRC(Double ICF_PRC) {
//		this.ICF_PRC = ICF_PRC;
//	}
//
//	public Double getFNCF_PRC() {
//		return FNCF_PRC;
//	}
//
//	public void setFNCF_PRC(Double FNCF_PRC) {
//		this.FNCF_PRC = FNCF_PRC;
//	}
//
//	public Double getCAPEX_PRC() {
//		return CAPEX_PRC;
//	}
//
//	public void setCAPEX_PRC(Double CAPEX_PRC) {
//		this.CAPEX_PRC = CAPEX_PRC;
//	}
//
//	public Double getFCF_PRC() {
//		return FCF_PRC;
//	}
//
//	public void setFCF_PRC(Double FCF_PRC) {
//		this.FCF_PRC = FCF_PRC;
//	}
//
//	public Double getINTEREST_DEBT_PRC() {
//		return INTEREST_DEBT_PRC;
//	}
//
//	public void setINTEREST_DEBT_PRC(Double INTEREST_DEBT_PRC) {
//		this.INTEREST_DEBT_PRC = INTEREST_DEBT_PRC;
//	}
//
//	public Double getOPF_RT() {
//		return OPF_RT;
//	}
//
//	public void setOPF_RT(Double OPF_RT) {
//		this.OPF_RT = OPF_RT;
//	}
//
//	public Double getNPF_RT() {
//		return NPF_RT;
//	}
//
//	public void setNPF_RT(Double NPF_RT) {
//		this.NPF_RT = NPF_RT;
//	}
//
//	public Double getROE_RT() {
//		return ROE_RT;
//	}
//
//	public void setROE_RT(Double ROE_RT) {
//		this.ROE_RT = ROE_RT;
//	}
//
//	public Double getROA_RT() {
//		return ROA_RT;
//	}
//
//	public void setROA_RT(Double ROA_RT) {
//		this.ROA_RT = ROA_RT;
//	}
//
//	public Double getDEBT_RT() {
//		return DEBT_RT;
//	}
//
//	public void setDEBT_RT(Double DEBT_RT) {
//		this.DEBT_RT = DEBT_RT;
//	}
//
//	public Double getCRR_RT() {
//		return CRR_RT;
//	}
//
//	public void setCRR_RT(Double CRR_RT) {
//		this.CRR_RT = CRR_RT;
//	}
//
//	public Double getEPS_WON_PRC() {
//		return EPS_WON_PRC;
//	}
//
//	public void setEPS_WON_PRC(Double EPS_WON_PRC) {
//		this.EPS_WON_PRC = EPS_WON_PRC;
//	}
//
//	public Double getPER_RT() {
//		return PER_RT;
//	}
//
//	public void setPER_RT(Double PER_RT) {
//		this.PER_RT = PER_RT;
//	}
//
//	public Double getBPS_WON_PRC() {
//		return BPS_WON_PRC;
//	}
//
//	public void setBPS_WON_PRC(Double BPS_WON_PRC) {
//		this.BPS_WON_PRC = BPS_WON_PRC;
//	}
//
//	public Double getPBR_RT() {
//		return PBR_RT;
//	}
//
//	public void setPBR_RT(Double PBR_RT) {
//		this.PBR_RT = PBR_RT;
//	}
//
//	public Double getDPS_WON_PRC() {
//		return DPS_WON_PRC;
//	}
//
//	public void setDPS_WON_PRC(Double DPS_WON_PRC) {
//		this.DPS_WON_PRC = DPS_WON_PRC;
//	}
//
//	public Double getDIVIDEND_RT() {
//		return DIVIDEND_RT;
//	}
//
//	public void setDIVIDEND_RT(Double DIVIDEND_RT) {
//		this.DIVIDEND_RT = DIVIDEND_RT;
//	}
//
//	public Double getCASH_DIVIDEND_RT() {
//		return CASH_DIVIDEND_RT;
//	}
//
//	public void setCASH_DIVIDEND_RT(Double CASH_DIVIDEND_RT) {
//		this.CASH_DIVIDEND_RT = CASH_DIVIDEND_RT;
//	}
//
//	public Long getSTOCK_CNT() {
//		return STOCK_CNT;
//	}
//
//	public void setSTOCK_CNT(Long STOCK_CNT) {
//		this.STOCK_CNT = STOCK_CNT;
//	}
//
//	public Long getUPT_LAST_DT() {
//		return UPT_LAST_DT;
//	}
//
//	public void setUPT_LAST_DT(Long UPT_LAST_DT) {
//		this.UPT_LAST_DT = UPT_LAST_DT;
//	}
//}
