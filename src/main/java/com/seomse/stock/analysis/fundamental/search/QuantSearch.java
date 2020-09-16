///**
// * <pre>
// *  설    명 : 기본적 분석 quant 종목 검색
//
// *  작 성 자 : macle
// *  작 성 일 : 2017.11
// *  버    전 : 1.1
// *  수정이력 : 2018.06
// *  기타사항 :
// * </pre>
// * @author Copyrights 2017, 2018 by ㈜섬세한사람들. All right reserved.
// */
//package com.seomse.stock.analysis.fundamental.search;
//
//import java.lang.reflect.InvocationTargetException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.seomse.commons.utils.ExceptionUtil;
//import com.seomse.commons.utils.ranking.Ranking;
//import com.seomse.jdbc.JdbcQuery;
//import com.seomse.jdbc.naming.JdbcNaming;
//import com.seomse.stock.analysis.fundamental.quant.QuantPoint;
//import com.seomse.stock.analysis.fundamental.quant.total.FundamentalQuant_001;
//import com.seomse.stock.data.store.no.StockItemNo;
//
//public class  QuantSearch <T extends QuantPoint>{
//
//	private static final Logger logger = LoggerFactory.getLogger(QuantSearch.class);
//
//	private Class<T> tClass;
//	private int rankLimit = 10;
//	/**
//	 * 생성자
//	 */
//	public QuantSearch(Class<T> tClass){
//		this.tClass = tClass;
//	}
//
//	/**
//	 * 랭크 limt 설정
//	 * @param rankLimit
//	 */
//	public void setRankLimit(int rankLimit) {
//		this.rankLimit = rankLimit;
//	}
//
//
//
//
//	public List<QuantPoint> search() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//		String nowYmd = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		return search(nowYmd, tClass, rankLimit);
//	}
//
//	public static <T extends QuantPoint> List<QuantPoint> search(Class<T> tClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//		String nowYmd = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		return search(nowYmd, tClass, 10);
//	}
//
//	public static <T extends QuantPoint> List<QuantPoint> search(String standardYmd, Class<T> tClass, int rankLimit) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//
////		String nowYmd = new SimpleDateFormat("yyyyMMdd").format(new Date());
////
////		boolean isNow = false;
////		if(standardYmd.equals(nowYmd)) {
////			isNow = true;
////		}
//
//		List<String> codeList = JdbcQuery.getStringList(getTracdeItemCodeQuery());
//
//		List<QuantPoint> pointList = new ArrayList<>();
//
//		for(String code : codeList) {
//
//			try {
//				T t = tClass.getDeclaredConstructor().newInstance();
//				t.setItemCode(code);
//				double point = t.getPoint(standardYmd);
//				if(point == QuantPoint.NOT_VALID) {
//					continue;
//				}
//				if(Double.isNaN(point)) {
//					continue;
//				}
//				pointList.add(t);
//			}catch(Exception e) {
//				logger.error("error code: " + code + "\n"  + ExceptionUtil.getStackTrace(e));
//			}
//
//
//
//
//		}
//
//
//		if(pointList.size() == 0) {
//			return pointList;
//		}
//
//		List<QuantPoint> rankList= Ranking.getRankingList(pointList.toArray(new QuantPoint[pointList.size()]), rankLimit);
//		return rankList;
//	}
//
//
//	public static <T extends QuantPoint> void wicsRankView(String standardYmd, Class<T> tClass, int rankLimit) {
//
//		try {
//			Map<String, StockItemNo> itemMap = new HashMap<>();
////			SELECT CD_ITEM FROM TB_STOCK_ITEM   WHERE FG_TRADE='Y' AND FG_PREFERRED='N'  AND VAL_WICS !='미분류' AND VAL_WICS !='복합기업' AND NM_ITEM NOT LIKE '%홀딩스'
//			List<StockItemNo> itemList = JdbcNaming.getObjList(StockItemNo.class, "FG_TRADE='Y' AND FG_PREFERRED='N'  AND VAL_WICS !='미분류' AND VAL_WICS !='복합기업' AND NM_ITEM NOT LIKE '%홀딩스' ");
//			Map<String , List<QuantPoint>> wicsListMap = new HashMap<>();
//
//			List<QuantPoint> allList = new ArrayList<>();
//
//			for(StockItemNo item : itemList) {
//
//				String code = item.getCD_ITEM();
//
//				itemMap.put(item.getCD_ITEM(), item);
//
//				List<QuantPoint> pointList = wicsListMap.get(item.getVAL_WICS());
//				if(pointList == null) {
//					pointList = new ArrayList<>();
//					wicsListMap.put(item.getVAL_WICS(), pointList);
//				}
//
//
//				try {
//					T t = tClass.getDeclaredConstructor().newInstance();
//					t.setItemCode(code);
//					double point = t.getPoint(standardYmd);
//					if(point == QuantPoint.NOT_VALID) {
//						continue;
//					}
//					if(Double.isNaN(point)) {
//						continue;
//					}
//					pointList.add(t);
//					allList.add(t);
//				}catch(Exception e) {
//					logger.error("error code: " + code + "\n"  + ExceptionUtil.getStackTrace(e));
//				}
//
//
//			}
//
//			List<QuantPoint> allRankList= Ranking.getRankingList(allList.toArray(new QuantPoint[allList.size()]), rankLimit);
//
//			System.out.println("======== 전체기준  ===========================");
//			for(QuantPoint quantPoint : allRankList) {
//				StockItemNo item = itemMap.get(quantPoint.getItemCode());
//				System.out.println(item.getNM_ITEM() +", " + item.getCD_ITEM() + ", " + quantPoint.getBasisExplan());
//			}
//
//			System.out.println();
//			System.out.println();
//
//			Set<String> keySet = wicsListMap.keySet();
//			for(String wics : keySet) {
//				System.out.println("======== WICS: " + wics + " ===========================");
//
//
//				List <QuantPoint> wicsList = wicsListMap.get(wics);
//				List<QuantPoint> rankList= Ranking.getRankingList(wicsList.toArray(new QuantPoint[wicsList.size()]), rankLimit);
//				for(QuantPoint quantPoint : rankList) {
//					StockItemNo item = itemMap.get(quantPoint.getItemCode());
//					System.out.println(item.getNM_ITEM() +", " + item.getCD_ITEM() + ", " + quantPoint.getBasisExplan());
//				}
//				System.out.println();
//				System.out.println();
//
//			}
//
//
//
//		}catch(Exception e) {
//			logger.error(ExceptionUtil.getStackTrace(e));
//		}
//	}
//
//
//
//	public static String getTracdeItemCodeQuery(){
//		String sql = "SELECT CD_ITEM FROM TB_STOCK_ITEM   WHERE FG_TRADE='Y' AND FG_PREFERRED='N'  AND VAL_WICS !='미분류' AND VAL_WICS !='복합기업' AND NM_ITEM NOT LIKE '%홀딩스' "
//		;
//
//		return sql;
//	}
//
//	public static void main(String [] args) {
////		try {
////			 List<QuantPoint> quintList = search(FundamentalQuant_001.class);
////
//////			 DataStoreManager dataStoreManager = DataStoreManager.getInstance();
////
//////			 DataStore dataStore = dataStoreManager.getDataStore();
////			 for(QuantPoint quantPoint : quintList) {
////				 String name = JdbcQuery.getResultOne("SELECT NM_ITEM FROM TB_STOCK_ITEM WHERE CD_ITEM='" + quantPoint.getItemCode() +"'");
////
////				 System.out.println(name + " " +quantPoint.getItemCode() + " " + quantPoint.getRankingPoint());
////			 }
////
////		}catch(Exception e) {
////			e.printStackTrace();
////		}
//		String nowYmd = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		wicsRankView(nowYmd, FundamentalQuant_001.class, 10);
//	}
//
//}
