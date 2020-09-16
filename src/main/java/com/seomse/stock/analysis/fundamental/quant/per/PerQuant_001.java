///**
// * <pre>
// *  파 일 명 : PerQuant_001.java
// *  설    명 : PER퀀트 점수측정
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
//package com.seomse.stock.analysis.fundamental.quant.per;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import com.seomse.commons.config.Config;
//import com.seomse.jdbc.JdbcQuery;
//import com.seomse.jdbc.naming.JdbcNaming;
//import com.seomse.stock.analysis.fundamental.quant.QuantPoint;
//import com.seomse.stock.analysis.fundamental.quant.no.FinancialStatementsNo;
//import com.seomse.stock.util.AverageUtil;
//import com.seomse.stock.util.StockYmdUtil;
//
//public class PerQuant_001 implements QuantPoint{
//
////	private static final Logger logger = LoggerFactory.getLogger(PerQuint_001.class);
//
//	private String itemCode;
//
//	/**
//	 * 생성자
//	 * @param itemCode
//	 */
//	public PerQuant_001(String itemCode){
//		this.itemCode = itemCode;
//	}
//	/**
//	 * 생성자
//	 */
//	public PerQuant_001() {
//
//	}
//
//	/**
//	 * 종목코드얻기
//	 * @return
//	 */
//	@Override
//	public String getItemCode() {
//		return itemCode;
//	}
//	public void setItemCode(String itemCode) {
//		this.itemCode = itemCode;
//	}
//	/**
//	 * 점수얻기
//	 * @return
//	 */
//	public double getPoint(){
//
//		String nowYmd = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		return getPoint(nowYmd);
//	}
//
//	private Double savePoint = null;
//	private String saveYmd = null;
//
//	/**
//	 * 점수얻기
//	 * @param standardYmd 기준년월일
//	 * @return
//	 */
//	@Override
//	public double getPoint(String standardYmd){
//		if(savePoint != null && saveYmd != null && saveYmd.equals(standardYmd)) {
//			return savePoint;
//		}
//		saveYmd = standardYmd;
//		String minYm ;
//
//		String nowYmd = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		if(nowYmd.equals(standardYmd)){
//			//기준일시가 오늘이면
//			minYm = JdbcQuery.getResultOne("SELECT MAX(YMD_CLOSING) FROM TB_STOCK_FSMT WHERE CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Q'");
//		}else{
//			//기준일시가 오늘이아니면
//			minYm = StockYmdUtil.getMinYm(standardYmd.substring(0,6));
//			minYm = minYm.substring(0,4) + "/"  + minYm.substring(4,6);
//		}
//
//		//최근 연간정보얻기
//		FinancialStatementsNo financialStatementsYearNo = JdbcNaming.getObj(FinancialStatementsNo.class
//				, "CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Y' AND YMD_CLOSING<='"+ minYm +"' AND ROWNUM < 2 ORDER BY YMD_CLOSING DESC");
//
//		if(financialStatementsYearNo == null) {
//			return QuantPoint.NOT_VALID;
//		}
//
//
//		//발행주식수가 없으면 PER점수 계산불가
//		if(financialStatementsYearNo.getCNT_STOCK() == null){
//			return QuantPoint.NOT_VALID;
//		}
//
//		//최근 8분기 분기별재무를 가져온다.
//		List<FinancialStatementsNo> financialStatementsQuarterNoList = JdbcNaming.getObjList(FinancialStatementsNo.class
//						, "CD_ITEM='" + itemCode + "'  AND FG_ESTIMATION ='N'  AND TP_CLOSING ='Q' AND YMD_CLOSING<='"+ minYm +"' AND ROWNUM < 9 ORDER BY YMD_CLOSING DESC");
//
//		if(financialStatementsQuarterNoList.size() < 4) {
//			return QuantPoint.NOT_VALID;
//		}
//
//
//		//최근 거래일의 가격얻기
//		Long lastPirce = JdbcQuery.getResultLong("SELECT PRC_LAST FROM TB_STOCK_DAILY_ITEM WHERE CD_ITEM ='" +itemCode +"' "
//				+ "AND YMD_DAILY IN (SELECT MAX(YMD_DAILY) FROM TB_STOCK_DAILY_ITEM WHERE CD_ITEM ='" + itemCode  + "' AND YMD_DAILY <= " + standardYmd + " AND PRC_LAST IS NOT NULL)");
//
//		if(lastPirce == null){
//			return QuantPoint.NOT_VALID;
//		}
//
//
//		System.out.println(lastPirce);
//
//		//당기순이익이 영업이익보다 큰경우는 지속적인 당기순이익인지 체크 (1회성 당기순의익의경우 1회성 수익을뺀 비용 계산해보기)
//		//8분기 평균분포 순이익율을 활용
//
//
//
//
//		boolean isCheck =false;
//		//최근 4분기 가져오기
//
//		//연산에 사용해야해서 duble로 선언
//
//		double npfSum = 0.0;
//
//		for(int i =0 ; i<4 ; i++){
//
//			FinancialStatementsNo financialStatementsNo  = financialStatementsQuarterNoList.get(i);
//			//당기순이익
//			if (financialStatementsNo.getPRC_NPF()  == null) {
//				return QuantPoint.NOT_VALID;
//			}
//			//영업이익
//			if (financialStatementsNo.getPRC_OPF()  == null) {
//				return QuantPoint.NOT_VALID;
//			}
//
//			//순이익율
//			if (financialStatementsNo.getPCT_NPF()  == null) {
//				return QuantPoint.NOT_VALID;
//			}
//
//			if(financialStatementsNo.getPRC_NPF() > financialStatementsNo.getPRC_OPF()) {
//				isCheck = true;
//			}
//
//
//			npfSum += (double) financialStatementsNo.getPRC_NPF() ;
//		}
//
//		//당기순이익이 적자이면
//		if(npfSum <= 0.0) {
//			return  QuantPoint.NOT_VALID;
//		}
//
//		if(isCheck) {
//			//1회성 수익인지 지속가능 수익인지 체크
//			//1회성 수익인경우 수익을 1회성 수익분 제외한 per계산하기
//
//			double [] valueArray = new double[financialStatementsQuarterNoList.size()];
//			int index = 0;
//
//			//평균수익율 분포평균 구하기
//			for(FinancialStatementsNo financialStatementsNo : financialStatementsQuarterNoList) {
//				if(financialStatementsNo.getPCT_NPF() == null) {
//					return  QuantPoint.NOT_VALID;
//				}
//
//				valueArray[index] = financialStatementsNo.getPCT_NPF() ;
//				index++;
//			}
//
//
//			//분포평균 얻기
//			double avg = AverageUtil.getAverageDistribution(valueArray , 10.0);
//			double avg2 = avg*2.0;
//			npfSum = 0.0;
//			for(int i =0 ; i<4 ; i++){
//				FinancialStatementsNo financialStatementsNo  = financialStatementsQuarterNoList.get(i);
//				if(financialStatementsNo.getPRC_NPF() > financialStatementsNo.getPRC_OPF()) {
//					 if( avg2 < financialStatementsNo.getPCT_NPF() ) {
//						 //당기순우익이 분표평균 *2보다 높을경우
//						 //매출액대비 분포평균순이익율로 주기
//						 npfSum += (double) financialStatementsNo.getPRC_SALES() * avg / 100.0;
//					 }else {
//						 npfSum += (double) financialStatementsNo.getPRC_NPF() ;
//					 }
//
//
//				}else {
//					npfSum += (double) financialStatementsNo.getPRC_NPF() ;
//				}
//
//
//			}
//
//
//		}
//
//
//		double eps = (npfSum*100000000.0) / (double)financialStatementsYearNo.getCNT_STOCK();
//
//
//
//		double per = (double)lastPirce/eps;
//		if(per <= 0.0){
//
//			return QuantPoint.NOT_VALID;
//		}
//
//		System.out.println(per);
//
//		double logPerPoint = 12.0 - per;
//		double perPoint = 0;
//		if(logPerPoint < 0.0){
//			logPerPoint = logPerPoint*-1;
//			perPoint = Math.log(Math.E + logPerPoint)*-1;
//
//			perPoint += 2.0;
//		}else{
//			perPoint = Math.log(Math.E + logPerPoint);
//		}
//		saveYmd = standardYmd;
//		savePoint = perPoint;
//		return perPoint;
//
//
//	}
//	private int ranking = -1;
//
//	@Override
//	public int getRanking() {
//		return ranking;
//	}
//
//	@Override
//	public double getRankingPoint() {
//		if(savePoint != null) {
//			return savePoint;
//		}
//		return getPoint();
//	}
//
//	@Override
//	public String getBasisExplan() {
//		return "per 가산점:"  + getPoint();
//	}
//
//	@Override
//	public void setRanking(int ranking) {
//		this.ranking =ranking;
//	}
//	public static void main(String [] args){
//		Config.getConfig("");
//
//		PerQuant_001 perQuint = new PerQuant_001("095660");
//
//		System.out.println(perQuint.getPoint());
//
//
//
//	}
//
//
//}
