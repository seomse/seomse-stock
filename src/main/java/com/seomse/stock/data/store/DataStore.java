//
//package com.seomse.stock.data.store;
//
//import com.seomse.commons.config.Config;
//import com.seomse.jdbc.JdbcQuery;
//import com.seomse.jdbc.naming.JdbcNaming;
//import com.seomse.stock.data.store.element.StockItem;
//import com.seomse.stock.data.store.no.StockDailyNo;
//import com.seomse.stock.data.store.no.StockFsmtNo;
//import com.seomse.stock.util.StockYmdUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
///**
// * <pre>
// *  파 일 명 : DataStore.java
// *  설    명 : 주식 datastore (메모리저장소)
// 			 재무, 일별가격정보 (전체리뉴얼)
// *  작 성 자 : macle
// *  작 성 일 : 2018.05 ~ 2018.09
// *  버    전 : 1.0
// *  수정이력 :
// *  기타사항 :
// * </pre>
// * @author Copyrights 2018 by ㈜섬세한사람들. All right reserved.
// */
//public class DataStore {
//	private static final Logger logger = LoggerFactory.getLogger(DataStore.class);
////
////	private static class Singleton {
////		private static final DataStore instance = new DataStore();
////	}
////
////	/**
////	 * 인스턴스 얻기
////	 * @return
////	 */
////	public static DataStore getInstance () {
////		return Singleton.instance;
////	}
//
//	/**
//	 * 주식 종목 쿼리 얻기
//	 * @return
//	 */
//	public  static String getTracdeItemCodeQuery(){
//		String sql = "SELECT CD_ITEM FROM TB_STOCK_ITEM   WHERE FG_TRADE='Y' AND FG_PREFERRED='N'  AND VAL_WICS !='미분류' AND VAL_WICS !='복합기업' AND NM_ITEM NOT LIKE '%홀딩스' "
//		;
//
//		return sql;
//	}
//
//
//	private Map<String, StockItem> itemMap;
//	private StockItem [] itemArray;
//
//	private Object lock = new Object();
//
//	/**
//	 * 생성자
//	 * @param ymd
//	 */
//	DataStore(String ymd){
//		setDataStore(ymd);
//	}
//
//
////	/**
////	 * 생성자
////	 */
////	DataStore() {
////		updateRealTime();
////	}
////
////	/**
////	 * 실시간 데이터 업데이트
////	 */
////	public void updateRealTime() {
////		synchronized (lock) {
////			logger.debug("data store update");
////
////			Map<String, StockItem> itemMap = new HashMap<>();
////			int count = Config.getInteger("stock.daily.count" , 1000);
////
////			List<String> itemCodeList = JdbcQuery.getStringList(getTracdeItemCodeQuery());
////			for(String itemCode : itemCodeList) {
////
////				logger.debug("meta load item: " + itemCode);
////
////				StockItem stockItem = new StockItem(this, itemCode);
////				itemMap.put(itemCode, stockItem);
////
////				//ArrayList LinkedList로 변환
////				List<StockDailyNo> stockDailyNoList = JdbcNaming.getObjList(StockDailyNo.class, "CD_ITEM='"+ itemCode +"'","YMD_DAILY DESC", count);
////
////				StockDailyNo [] dailyArray = new StockDailyNo[stockDailyNoList.size()];
////
////				int index = 0;
////				for(int i=stockDailyNoList.size()-1 ; i > -1 ; i--) {
////					dailyArray[index] = stockDailyNoList.get(i);
////					index++;
////				}
////
////				stockItem.setDailyArray(dailyArray);
////
////				stockDailyNoList.clear();
////
////				List<StockFsmtNo> fsmtNoList = JdbcNaming.getObjList(StockFsmtNo.class, "CD_ITEM='"+ itemCode +"'","YMD_CLOSING ASC");
////
////				for(StockFsmtNo stockFsmtNo :fsmtNoList ) {
////					stockItem.addFsmt(stockFsmtNo);
////				}
////				fsmtNoList.clear();
////			}
////			itemArray = itemMap.values().toArray(new StockItem[itemMap.size()]);
////			Map<String, StockItem> tempMap = this.itemMap;
////			this.itemMap = itemMap;
////			if(tempMap != null) {
////				tempMap.clear();
////			}
////			logger.debug("data store update complete");
////		}
////	}
//
//
//
//	/**
//	 * 데이터 저장소 세팅
//	 * 날짜기준
//	 * @param ymd
//	 */
//	void setDataStore(String ymd) {
//		synchronized (lock) {
//			logger.debug("data store set: " + ymd);
//
//			Map<String, StockItem> itemMap = new HashMap<>();
//			int count = Config.getInteger("stock.daily.count" , 1000);
//
//
//			String minYm = StockYmdUtil.getMinYm(ymd.substring(0,6));
//			minYm = minYm.substring(0,4) + "/"  + minYm.substring(4,6);
//
//			String ym = ymd.substring(0,4) + "/"  + ymd.substring(4,6);
//
//			List<String> itemCodeList = JdbcQuery.getStringList(getTracdeItemCodeQuery());
//			for(String itemCode : itemCodeList) {
//
//				logger.debug("meta load item: " + itemCode);
//
//				StockItem stockItem = new StockItem(this, itemCode);
//				itemMap.put(itemCode, stockItem);
//
//				//ArrayList LinkedList로 변환
//				List<StockDailyNo> stockDailyNoList = JdbcNaming.getObjList(StockDailyNo.class, "CD_ITEM='"+ itemCode +"' AND YMD_DAILY<='" + ymd + "'", "YMD_DAILY DESC", count);
////				StockDailyNo [] dailyArray = stockDailyNoList.toArray(new StockDailyNo[stockDailyNoList.size()]);
//				StockDailyNo [] dailyArray = new StockDailyNo[stockDailyNoList.size()];
//
//				int index = 0;
//				for(int i=stockDailyNoList.size()-1 ; i > -1 ; i--) {
//					dailyArray[index] = stockDailyNoList.get(i);
//					index++;
//				}
//
////				stockItem.setDailyArray(dailyArray);
//
//				stockDailyNoList.clear();
//
//				//추정치 제외
//				List<StockFsmtNo> fsmtNoList = JdbcNaming.getObjList(StockFsmtNo.class, "CD_ITEM='"+ itemCode +"' AND FG_ESTIMATION ='N'"
//						+ " AND YMD_CLOSING<='"+ minYm +"'","YMD_CLOSING ASC");
//
////				for(StockFsmtNo stockFsmtNo :fsmtNoList ) {
////					stockItem.addFsmt(stockFsmtNo);
////				}
//				fsmtNoList.clear();
//
//				//추정치
//				fsmtNoList = JdbcNaming.getObjList(StockFsmtNo.class, "CD_ITEM='"+ itemCode +"' AND FG_ESTIMATION ='Y'"
//						+ " AND YMD_CLOSING<='"+ ym +"'","YMD_CLOSING ASC");
//
////				for(StockFsmtNo stockFsmtNo :fsmtNoList ) {
////					stockItem.addFsmt(stockFsmtNo);
////				}
//				fsmtNoList.clear();
//			}
//			itemArray = itemMap.values().toArray(new StockItem[itemMap.size()]);
//			Map<String, StockItem> tempMap = this.itemMap;
//			this.itemMap = itemMap;
//			if(tempMap != null) {
//				tempMap.clear();
//			}
//
//
//			logger.debug("data store set complete: " + ymd);
//		}
//	}
//
//
//	/**
//	 * 종목객체 배열 얻기
//	 * @return
//	 */
//	public StockItem [] getItemArray() {
//		return itemArray;
//	}
//
//	/**
//	 * 종목 객체 얻기
//	 * @param itemCode
//	 * @return
//	 */
//	public StockItem getItem(String itemCode) {
//		return itemMap.get(itemCode);
//	}
//
//
//}
