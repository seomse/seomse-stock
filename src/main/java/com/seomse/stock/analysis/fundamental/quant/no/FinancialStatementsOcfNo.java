/** 
 * <pre>
 *  파 일 명 : FinancialStatementsNo.java
 *  설    명 : 제무제표 정보 영업활동 현금흐름 관련 정보객체
 *                    
 *  작 성 자 : macle
 *  작 성 일 : 2017.10
 *  버    전 : 1.0
 *  수정이력 : 
 *  기타사항 :
 * </pre>
 * @author Copyrights 2017 by ㈜섬세한사람들. All right reserved.
 */
package com.seomse.stock.analysis.fundamental.quant.no;

import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

@Table(name="TB_STOCK_FSMT")
public class FinancialStatementsOcfNo {
	@PrimaryKey(seq = 1)
	private String CD_ITEM;
	@PrimaryKey(seq = 2)
	private String YMD_CLOSING;
	@PrimaryKey(seq = 3)
	private String TP_CLOSING;
	private Long PRC_NPF;
	private Long PRC_OCF;
	public String getCD_ITEM() {
		return CD_ITEM;
	}
	public void setCD_ITEM(String cD_ITEM) {
		CD_ITEM = cD_ITEM;
	}
	public String getYMD_CLOSING() {
		return YMD_CLOSING;
	}
	public void setYMD_CLOSING(String yMD_CLOSING) {
		YMD_CLOSING = yMD_CLOSING;
	}
	public String getTP_CLOSING() {
		return TP_CLOSING;
	}
	public void setTP_CLOSING(String tP_CLOSING) {
		TP_CLOSING = tP_CLOSING;
	}
	public Long getPRC_NPF() {
		return PRC_NPF;
	}
	public void setPRC_NPF(Long pRC_NPF) {
		PRC_NPF = pRC_NPF;
	}
	public Long getPRC_OCF() {
		return PRC_OCF;
	}
	public void setPRC_OCF(Long pRC_OCF) {
		PRC_OCF = pRC_OCF;
	}
	
	
	
}
