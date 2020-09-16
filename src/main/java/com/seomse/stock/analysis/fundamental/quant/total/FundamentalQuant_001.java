///**
// * <pre>
// *  파 일 명 : FundamentalQuint_001.java
// *  설    명 : 종합퀀트점수
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
//package com.seomse.stock.analysis.fundamental.quant.total;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.seomse.stock.analysis.fundamental.quant.QuantPoint;
//import com.seomse.stock.analysis.fundamental.quant.cashflow.OperatingCashFlowQuant_001;
//import com.seomse.stock.analysis.fundamental.quant.debt.DebtReduceQuant_001;
//import com.seomse.stock.analysis.fundamental.quant.divend.DividendQuant_001;
//import com.seomse.stock.analysis.fundamental.quant.opf.OpfIncreaseQuant_001;
//import com.seomse.stock.analysis.fundamental.quant.pbr.PbrQuant_001;
//import com.seomse.stock.analysis.fundamental.quant.per.PerQuant_001;
//
//public class FundamentalQuant_001 implements QuantPoint{
//	private static final Logger logger = LoggerFactory.getLogger(FundamentalQuant_001.class);
//
//	private String itemCode;
//
//	/**
//	 * 생성자
//	 */
//	public FundamentalQuant_001() {
//
//	}
//
//	/**
//	 * 생성자
//	 * @param itemCode
//	 */
//	public FundamentalQuant_001(String itemCode){
//		this.itemCode = itemCode;
//	}
//
//
//	public void setItemCode(String itemCode) {
//		this.itemCode = itemCode;
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
//
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
//
//	private Double savePoint = null;
//	private String saveYmd = null;
//	private List<QuantPoint> quintList = new ArrayList<QuantPoint>();
//	@Override
//	public double getPoint(String standardYmd){
//
//		if(savePoint != null && saveYmd != null && saveYmd.equals(standardYmd)) {
//			return savePoint;
//		}
//		saveYmd = standardYmd;
//
//		quintList.clear();
//		quintList.add(new OperatingCashFlowQuant_001(itemCode));
//		quintList.add(new DebtReduceQuant_001(itemCode));
//		quintList.add(new OpfIncreaseQuant_001(itemCode));
//		quintList.add(new PbrQuant_001(itemCode));
//		quintList.add(new PerQuant_001(itemCode));
//
//		quintList.add(new DividendQuant_001(itemCode));
////		QuantPoint  dividendQuint = new DividendQuant_001(itemCode);
//
////		double dividendPoint = PointUtil.reduce(2.0, dividendQuint.getPoint(standardYmd));
////		logger.trace(dividendQuint.getClass().getSimpleName() + ": " + dividendPoint);
////		if(dividendPoint == QuantPoint.NOT_VALID) {
////			return QuantPoint.NOT_VALID;
////		}
//		double sumPoint = 0.0;
////		sumPoint+=dividendPoint;
//
//
//		for(QuantPoint quintPoint : quintList) {
//			double point = quintPoint.getPoint(standardYmd);
//
//			logger.trace(quintPoint.getClass().getSimpleName() + ": " + point);
//			if(point == QuantPoint.NOT_VALID) {
//				return QuantPoint.NOT_VALID;
//			}
//
//			sumPoint += point;
//		}
//
//		saveYmd = standardYmd;
//		savePoint = sumPoint;
//		return sumPoint;
//	}
//
//
//
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
//
//
//		return getPoint();
//	}
//
//	@Override
//	public void setRanking(int ranking) {
//		this.ranking =ranking;
//	}
//	@Override
//	public String getBasisExplan() {
//		StringBuilder sb = new StringBuilder();
//		sb.append( "종합점수:"  + getPoint());
//
//		for(QuantPoint quantPoint: quintList) {
//			sb.append( ", "  + quantPoint.getBasisExplan());
//		}
//
//		return sb.toString();
//	}
//
//	public static void main(String [] args) {
//		FundamentalQuant_001 fundamentalQuint_001 = new FundamentalQuant_001("095660");
//
//		System.out.println(fundamentalQuint_001.getPoint());
//
//	}
//}
