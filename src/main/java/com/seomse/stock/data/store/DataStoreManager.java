///**
// * <pre>
// *  파 일 명 : DataStoreManager.java
// *  설    명 : 데이터 저장소 관리자
// *
// *  작 성 자 : macle
// *  작 성 일 : 2018.08
// *  버    전 : 1.0
// *  수정이력 :
// *  기타사항 :
// * </pre>
// * @author Copyrights 2018 by ㈜섬세한사람들. All right reserved.
// */
//
//package com.seomse.stock.data.store;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class DataStoreManager {
//	private static final Logger logger = LoggerFactory.getLogger(DataStoreManager.class);
//
//	private static class Singleton {
//		private static final DataStoreManager instance = new DataStoreManager();
//	}
//
//	/**
//	 * 인스턴스 얻기
//	 * @return
//	 */
//	public static DataStoreManager getInstance () {
//		return Singleton.instance;
//	}
//
//
//	private Object lock ;
//
//	private Map<String, Object> lockObjectMap;
//
//	private Map<String, DataStore> ymdStoreMap ;
//
//
//	private DataStore dataStore;
//
//	/**
//	 * 생성자
//	 */
//	private DataStoreManager() {
//		dataStore = new DataStore("");
//		lockObjectMap = new HashMap<>();
//		ymdStoreMap = new HashMap<>();
//		lock = new Object();
//	}
//
//
//	/**
//	 * 일자에 해당하는 데이터 저장소 삭제
//	 * @param ymd
//	 */
//	public void removeStore(String ymd) {
//		Object lockObj = ymdStoreMap.get(ymd);
//		if(lockObj == null) {
//			logger.debug("lock object null remove fail: " + ymd);
//		}
//
//
//		synchronized (lockObj) {
//			DataStore dataStore =  ymdStoreMap.remove(ymd);
//			if(dataStore == null) {
//				logger.debug("data store null remove fail: " + ymd);
//			}
//		}
//	}
//
//	/**
//	 * 일자에 해당하는 데이터 저장소 얻기
//	 * @param ymd
//	 * @return
//	 */
//	public DataStore getDataStore(String ymd) {
//
//		Object lockObj;
//		synchronized (lock) {
//			lockObj = lockObjectMap.get(ymd);
//			if(lockObj == null) {
//				lockObj = new Object();
//				lockObjectMap.put(ymd, lockObj);
//			}
//		}
//		DataStore dataStore;
//		synchronized (lockObj) {
//			dataStore =  ymdStoreMap.get(ymd);
//			if(dataStore == null) {
//				dataStore = new DataStore(ymd);
//				ymdStoreMap.put(ymd, dataStore);
//			}
//		}
//
//		return dataStore;
//	}
//
//
//	/**
//	 * 저장소 업데이트
//	 */
//	public DataStore update() {
////		dataStore.updateRealTime();
//		return dataStore;
//	}
//
//	/**
//	 * 저장소 업데이트
//	 * @param ymd
//	 * @return
//	 */
//	public DataStore update(String ymd) {
//		Object lockObj;
//		synchronized (lock) {
//			lockObj = lockObjectMap.get(ymd);
//			if(lockObj == null) {
//				lockObj = new Object();
//				lockObjectMap.put(ymd, lockObj);
//			}
//		}
//		DataStore dataStore;
//		synchronized (lockObj) {
//			dataStore =  ymdStoreMap.get(ymd);
//			if(dataStore == null) {
//				dataStore = new DataStore(ymd);
//				ymdStoreMap.put(ymd, dataStore);
//			}else {
//				dataStore.setDataStore(ymd);
//			}
//		}
//
//		return dataStore;
//	}
//
//
//	/**
//	 * 데이터 저장소얻기 (리얼타임)
//	 * @return
//	 */
//	public DataStore getDataStore() {
//		return dataStore;
//	}
//
//
//}
