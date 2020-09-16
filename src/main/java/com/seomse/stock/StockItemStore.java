package com.seomse.stock;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  파 일 명 : StockItemStore.java
 *  설    명 : 주식 데이터 저장소
 *             기준시점을 다르게 하여 생성하여 그때 당시의 퀀트 및 다른 정보를 확인할 수 있음 ( 리뮬레이터용)
 *  작 성 자 : macle
 *  작 성 일 : 2020.04.18
 *  버    전 : 1.0
 *  수정이력 :
 *  기타사항 :
 * </pre>
 * @author Copyrights 2020 by ㈜섬세한사람들. All right reserved.
 */
public class StockItemStore {

    private String standardYmd;

    private Map<String, StockItem> itemMap = new HashMap<>();

    /**
     * 생성자
     * @param standardYmd string yyyyMMdd 기준 년월일
     */
    public StockItemStore(String standardYmd){
        this.standardYmd = standardYmd;
    }


    public void loadData(){


    }


}
