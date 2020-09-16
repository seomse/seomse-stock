/** 
 * <pre>
 *  파 일 명 : StockDailyNo.java
 *  설    명 : 주식일별정보
               TB_STOCK_DAILY_ITEM 테이블 내용 (전체 정보는 아님)
 *  작 성 자 : macle
 *  작 성 일 : 2018.07
 *  버    전 : 1.0
 *  수정이력 : 
 *  기타사항 :
 * </pre>
 * @author Copyrights 2018 by ㈜섬세한사람들. All right reserved.
 */

package com.seomse.stock.data.store.no;

import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

@Table(name="TB_STOCK_DAILY_ITEM")
public class StockDailyNo {
	@PrimaryKey(seq = 1)
	private String CD_ITEM;
	@PrimaryKey(seq = 2)
	private String YMD_DAILY;
	private Long PRC_LAST;
	private Long PRC_YESTER;
	private Long PRC_MAX;
	private Long PRC_START;
	private Long PRC_MIN;
	private Integer CNT_TRADE;
	private Long PRC_TOTAL;
	private Long PRC_CHANGE;
	private Double PCT_CHANGE;
	public String getCD_ITEM() {
		return CD_ITEM;
	}
	public void setCD_ITEM(String cD_ITEM) {
		CD_ITEM = cD_ITEM;
	}
	public String getYMD_DAILY() {
		return YMD_DAILY;
	}
	public void setYMD_DAILY(String yMD_DAILY) {
		YMD_DAILY = yMD_DAILY;
	}
	public Long getPRC_LAST() {
		return PRC_LAST;
	}
	public void setPRC_LAST(Long pRC_LAST) {
		PRC_LAST = pRC_LAST;
	}
	public Long getPRC_YESTER() {
		return PRC_YESTER;
	}
	public void setPRC_YESTER(Long pRC_YESTER) {
		PRC_YESTER = pRC_YESTER;
	}
	public Long getPRC_MAX() {
		return PRC_MAX;
	}
	public void setPRC_MAX(Long pRC_MAX) {
		PRC_MAX = pRC_MAX;
	}
	public Long getPRC_START() {
		return PRC_START;
	}
	public void setPRC_START(Long pRC_START) {
		PRC_START = pRC_START;
	}
	public Long getPRC_MIN() {
		return PRC_MIN;
	}
	public void setPRC_MIN(Long pRC_MIN) {
		PRC_MIN = pRC_MIN;
	}
	public Integer getCNT_TRADE() {
		return CNT_TRADE;
	}
	public void setCNT_TRADE(Integer cNT_TRADE) {
		CNT_TRADE = cNT_TRADE;
	}
	public Long getPRC_TOTAL() {
		return PRC_TOTAL;
	}
	public void setPRC_TOTAL(Long pRC_TOTAL) {
		PRC_TOTAL = pRC_TOTAL;
	}
	public Long getPRC_CHANGE() {
		return PRC_CHANGE;
	}
	public void setPRC_CHANGE(Long pRC_CHANGE) {
		PRC_CHANGE = pRC_CHANGE;
	}
	public Double getPCT_CHANGE() {
		return PCT_CHANGE;
	}
	public void setPCT_CHANGE(Double pCT_CHANGE) {
		PCT_CHANGE = pCT_CHANGE;
	}
	
	
}
