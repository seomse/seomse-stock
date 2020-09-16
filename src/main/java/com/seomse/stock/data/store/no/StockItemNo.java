

package com.seomse.stock.data.store.no;

import com.seomse.jdbc.annotation.DateTime;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;


/**
 * <pre>
 *  파 일 명 : StockItemNo.java
 *  설    명 : 주식종목정보
 *			T_STOCK_ITEM 테이블 내용 (전체정보는 아님)
 *			주식 정보성 객체
 *  작 성 자 : macle
 *  작 성 일 : 2020.04
 *  버    전 : 1.0
 *  수정이력 :
 *  기타사항 :
 * </pre>
 * @author Copyrights 2020 by ㈜섬세한사람들. All right reserved.
 */
@Table(name="T_STOCK_ITEM")
public class StockItemNo {
	@PrimaryKey(seq = 1)
	private String ITEM_CD;
	private String ITEM_NM;
	private String ITEM_EN_NM;
	private String MARKET_CD = "KOSPI"  ;
	private String CATEGORY_NM;
	private String WICS_NM;
	private String SUMMARY_DS;
	private String DELISTING_FG = "N"  ;
	private String TRADE_FG = "Y"  ;
	private String LISTING_YMD;
	private Long LISTING_CNT;

	@DateTime
	private Long LAST_UPT_DT;

	public String getITEM_CD() {
		return ITEM_CD;
	}

	public String getITEM_NM() {
		return ITEM_NM;
	}

	public String getITEM_EN_NM() {
		return ITEM_EN_NM;
	}

	public String getMARKET_CD() {
		return MARKET_CD;
	}

	public String getCATEGORY_NM() {
		return CATEGORY_NM;
	}

	public String getWICS_NM() {
		return WICS_NM;
	}

	public String getSUMMARY_DS() {
		return SUMMARY_DS;
	}

	public String getDELISTING_FG() {
		return DELISTING_FG;
	}

	public String getTRADE_FG() {
		return TRADE_FG;
	}

	public String getLISTING_YMD() {
		return LISTING_YMD;
	}

	public Long getLISTING_CNT() {
		return LISTING_CNT;
	}

	public Long getLAST_UPT_DT() {
		return LAST_UPT_DT;
	}
}
