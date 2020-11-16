/*
 * Copyright (C) 2020 Seomse Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package seomse.stock.analysis.item.store;


import com.seomse.jdbc.annotation.*;

/**
 * 재무제표
 * @author macle
 */
@Table(name="T_STOCK_FSMT", orderBy = "SETTLEMENT_YM DESC")
public class FinancialStatements {

    public enum Type{
        YEAR //년간
        , QUARTER //분기

    }

    @PrimaryKey(seq = 1)
    @Column(name = "ITEM_CD")
    private String code;

    //결산 년월
    @PrimaryKey(seq = 2)
    @Column(name = "SETTLEMENT_YM")
    private String ym;

    //결산 유형
    @PrimaryKey(seq = 3)
    @Column(name = "SETTLEMENT_TP")
    private Type type = Type.QUARTER;

    //추정여부 (예상 재무 제표)
    @PrimaryKey(seq = 4)
    @FlagBoolean
    @Column(name = "ESTIMATE_FG")
    private boolean isEstimation = false;

    //매출액
    @Column(name = "SALES_PRC")
    private Double sales;

    //영업이익
    @Column(name = "OPF_PRC")
    private Double operatingProfit;

    //사전계속 사업이익
    //Continuing business profit before taxes
    @Column(name = "CBPF_PRC")
    private Double cbpf ;

    //당기 순이익
    //net profit during the term
    @Column(name = "NPF_PRC")
    private Double netProfit ;

    //당기 순이익 지배
    //net profit during the term domination
    @Column(name = "NPF_DMN_PRC")
    private Double netProfitDomination;

    //당기 순이익 비지배
    //net profit during the term not domination
    @Column(name = "NPF_NDMN_PRC")
    private Double netProfitNotDomination ;

    //자산 총계
    @Column(name = "ASSET_TOTAL_PRC")
    private Double assetTotal;

    //부채총계
    @Column(name = "DEBT_TOTAL_PRC")
    private Double deptTotal ;

    //자본총계
    @Column(name = "CAPITAL_TOTAL_PRC")
    private Double capitalTotal ;

    //자본총계 지배
    @Column(name = "CAPITAL_TOTAL_DMN_PRC")
    private Double capitalTotalDomination ;

    //자본총계 비지배
    @Column(name = "CAPITAL_TOTAL_NDMN_PRC")
    private Double capitalTotalNotDomination ;

    //자본금
    @Column(name = "CAPITAL_PRC")
    private Double capital;

    //영업활동 현금흐름
    @Column(name = "OCF_PRC")
    private Double operatingCashFlow;

    //투자활동 현금흐름
    @Column(name = "ICF_PRC")
    private Double investingCashFlow;

    //재무활동 현금흐름
    @Column(name = "FNCF_PRC")
    private Double financingCashFlow;

    //미래 현금흐름
    @Column(name = "FCF_PRC")
    private Double futureCashFlow;

    //CAPEX 미래의 이윤을 창출하기 위해 지출된 비용 Capital expenditures
    @Column(name = "CAPEX_PRC")
    private Double capex;

    //이자발생부채
    @Column(name = "INTEREST_DEBT_PRC")
    private Double interestDebt;

    //영업이익률
    @Column(name = "OPF_RT")
    private Double operatingProfitRatio;

    //순이익률
    @Column(name = "NPF_RT")
    private Double netProfitRatio;

    //roe 자기자본이익률 return on equity
    @Column(name = "ROE_RT")
    private Double roe;

    //roa 총 자산 순이익률 Return On Assets
    @Column(name = "ROA_RT")
    private Double roa;

    //부채비율
    @Column(name = "DEBT_RT")
    private Double debtRatio;

    //자본유보율
    @Column(name = "CRR_RT")
    private Double reserveRatio;

    //eps 주당순이익 earning per shara
    @Column(name = "EPS_WON_PRC")
    private Double eps;

    //per 주가수익 비율 price earning ratio
    @Column(name = "PER_RT")
    private Double per;

    //bps 주당순 자산가치 bookvalue per share
    @Column(name = "BPS_WON_PRC")
    private Double bps;

    //주가와 1주당 순자산을 비교하여 나타낸 비율(PBR = 주가 / 주당 순자산가치). 즉 주가가 순자산(자본금과 자본잉여금, 이익잉여금의 합계)에 비해 1주당 몇 배로 거래되고 있는지를 측정하는 지표
    @Column(name = "PBR_RT")
    private Double pbr;

    //주당 배당액
    @Column(name = "DPS_WON_PRC")
    private Double dps;

    //배당수익률
    @Column(name = "DIVIDEND_RT")
    private Double dividendYieldRatio;

    //배당성향
    @Column(name = "CASH_DIVIDEND_RT")
    private Double propensityToDividend;

    //발행주식수
    //기업이 발행하여 일반 투자자가 소유하고 있는 주식수, 회사의 정관에 의하여 규정된 발행가능한 총 주식 중에서 이미 발행된 주식과 설립후 발행된 주식수는 합하고 발행기업이 금고주의 재매입하여 보유하거나 소각한 주식은 차감하여 계산한다
    @Column(name = "STOCK_CNT")
    private Long issueStockCount;

    @DateTime(isNullable = false)
    @Column(name = "UPT_DT")
    private long updateTime;

    /**
     * 결산년월 얻기
     * @return String ym 결산년월 ex->201912
     */
    public String getYm() {
        return ym;
    }

    /**
     * 결산년월 설정
     * @param ym String ym 결산년월 ex->201912
     */
    public void setYm(String ym) {
        this.ym = ym;
    }

    /**
     * 결산유형 얻기
     * @return Type 결산유형 년,분기 YEAR, QUARTER
     */
    public Type getType() {
        return type;
    }

    /**
     * 결산유형 설정
     * @param type Type 결산유형 년,분기 YEAR, QUARTER
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * 추정여부
     * 예상 실적인지 아닌지
     * @return boolean flag isEstimation
     */
    public boolean isEstimation() {
        return isEstimation;
    }

    /**
     * 추정여부 설정
     * 예상 실적인지 아닌지
     * @param estimation boolean flag isEstimation
     */
    public void setEstimation(boolean estimation) {
        isEstimation = estimation;
    }


    /**
     * 매출액 얻기
     * @return Double sales price 매출액 설정
     */
    public Double getSales() {
        return sales;
    }

    /**
     * 매출액 설정
     * @param sales Double price sales 매출액
     */
    public void setSales(Double sales) {
        this.sales = sales;
    }

    /**
     * 영업어익 얻기
     * @return price Double operatingProfit 영업이익
     */
    public Double getOperatingProfit() {
        return operatingProfit;
    }

    /**
     * 영업이익 설정
     * @param operatingProfit Double price operatingProfit 영업이익
     */
    public void setOperatingProfit(Double operatingProfit) {
        this.operatingProfit = operatingProfit;
    }

    /**
     * 세전계속 사업이익 얻기
     * Continuing business profit before taxes
     * @return Double price Continuing business profit before taxes
     */
    public Double getCbpf() {
        return cbpf;
    }

    /**
     * 세전계속 사업이익 설정
     * Continuing business profit before taxes
     * @param cbpf Double price Continuing business profit before taxes
     */
    public void setCbpf(Double cbpf) {
        this.cbpf = cbpf;
    }

    /**
     * 당기순이익 얻기
     * net profit during the term
     * @return Double price net profit during the term
     */
    public Double getNetProfit() {
        return netProfit;
    }

    /**
     * 당기순이익 설정
     * @param netProfit Double price net profit during the term
     */
    public void setNetProfit(Double netProfit) {
        this.netProfit = netProfit;
    }

    /**
     * 지배 당기순이익 얻기
     * net profit during the term domination
     * @return Double price net profit during the term domination
     */
    public Double getNetProfitDomination() {
        return netProfitDomination;
    }

    /**
     * 지배 당기순이익 설정
     * @param netProfitDomination Double price net profit during the term domination
     */
    public void setNetProfitDomination(Double netProfitDomination) {
        this.netProfitDomination = netProfitDomination;
    }

    /**
     * 비지배 당기 순이익 얻기
     * @return Double price net profit during the term not domination
     */
    public Double getNetProfitNotDomination() {
        return netProfitNotDomination;
    }

    /**
     * 비지배 당기 순이익 설정
     * @param netProfitNotDomination Double price net profit during the term not domination
     */
    public void setNetProfitNotDomination(Double netProfitNotDomination) {
        this.netProfitNotDomination = netProfitNotDomination;
    }

    /**
     * 자산 총계 얻기
      * @return Double price asset total
     */
    public Double getAssetTotal() {
        return assetTotal;
    }

    /**
     * 자산총계 설정
     * @param assetTotal Double price asset total
     */
    public void setAssetTotal(Double assetTotal) {
        this.assetTotal = assetTotal;
    }

    /**
     * 부채총계 얻기
     * @return Double price dept total
     */
    public Double getDeptTotal() {
        return deptTotal;
    }

    /**
     * 부채총계 설정
     * @param deptTotal Double price dept total
     */
    public void setDeptTotal(Double deptTotal) {
        this.deptTotal = deptTotal;
    }

    /**
     * 자본총계 얻기
     * @return Double price capital total
     */
    public Double getCapitalTotal() {
        return capitalTotal;
    }

    /**
     * 자본총계 설정
     * @param capitalTotal Double price capital total
     */
    public void setCapitalTotal(Double capitalTotal) {
        this.capitalTotal = capitalTotal;
    }

    /**
     * 지배자본총계 얻기
     * @return Double price capital total domination
     */
    public Double getCapitalTotalDomination() {
        return capitalTotalDomination;
    }

    /**
     * 지배자본총계 설정
     * @param capitalTotalDomination Double price capital total domination
     */
    public void setCapitalTotalDomination(Double capitalTotalDomination) {
        this.capitalTotalDomination = capitalTotalDomination;
    }

    /**
     * 비지배 자본총계 얻기
     * @return Double price capital total not domination
     */
    public Double getCapitalTotalNotDomination() {
        return capitalTotalNotDomination;
    }

    /**
     * 비지배 자본총게 설정
     * @param capitalTotalNotDomination Double price capital total not domination
     */
    public void setCapitalTotalNotDomination(Double capitalTotalNotDomination) {
        this.capitalTotalNotDomination = capitalTotalNotDomination;
    }

    /**
     * 자본금 얻기
     * @return Double price capital
     */
    public Double getCapital() {
        return capital;
    }

    /**
     * 자본금 설정
     * @param capital Double price capital
     */
    public void setCapital(Double capital) {
        this.capital = capital;
    }

    /**
     * 영업활동 현금흐름 얻기
     * @return Double price operating cash flow
     */
    public Double getOperatingCashFlow() {
        return operatingCashFlow;
    }

    /**
     * 영업활도 현금흐름 설정
     * @param operatingCashFlow Double price operating cash flow
     */
    public void setOperatingCashFlow(Double operatingCashFlow) {
        this.operatingCashFlow = operatingCashFlow;
    }

    /**
     * 투자활동 현금흐름 얻기
     * @return Double investing cash flow
     */
    public Double getInvestingCashFlow() {
        return investingCashFlow;
    }

    /**
     * 투자활동 현금흐름 설정
     * @param investingCashFlow Double price investing cash flow
     */
    public void setInvestingCashFlow(Double investingCashFlow) {
        this.investingCashFlow = investingCashFlow;
    }

    /**
     * 재무활동 현금흐름 얻기
     * @return Double price financing cash flow
     */
    public Double getFinancingCashFlow() {
        return financingCashFlow;
    }

    /**
     * 재무활동 현금흐름 설정
     * @param financingCashFlow Double price financing cash flow
     */
    public void setFinancingCashFlow(Double financingCashFlow) {
        this.financingCashFlow = financingCashFlow;
    }

    /**
     * 미래 현금흐름 얻기
     * @return Double price future cash flow
     */
    public Double getFutureCashFlow() {
        return futureCashFlow;
    }

    /**
     * 미래 현금흐름 설정
     * @param futureCashFlow Double price future cash flow
     */
    public void setFutureCashFlow(Double futureCashFlow) {
        this.futureCashFlow = futureCashFlow;
    }

    /**
     * capex 얻기
     * 미래의 이윤을 창출하기 위해 지출된 비용
     *  capital expenditures
     * @return Double price capital expenditures
     */
    public Double getCapex() {
        return capex;
    }

    /**
     * capex 설정
     * 미래의 이윤을 창출하기 위해 지출된 비용
     *  capital expenditures
     * @param capex Double price capital expenditures
     */
    public void setCapex(Double capex) {
        this.capex = capex;
    }

    /**
     * 이자발생부채 얻기
     * @return Double price interest debt
     */
    public Double getInterestDebt() {
        return interestDebt;
    }

    /**
     * 이자발생부채 설정
     * @param interestDebt Double price interest debt
     */
    public void setInterestDebt(Double interestDebt) {
        this.interestDebt = interestDebt;
    }

    /**
     * 영업이익률 얻기
     * @return Double operating profit ratio
     */
    public Double getOperatingProfitRatio() {
        return operatingProfitRatio;
    }

    /**
     * 영업이익률 설정
     * @param operatingProfitRatio Double operating profit ratio
     */
    public void setOperatingProfitRatio(Double operatingProfitRatio) {
        this.operatingProfitRatio = operatingProfitRatio;
    }

    /**
     * 순이익률 얻기
     * @return Double net profit ratio
     */
    public Double getNetProfitRatio() {
        return netProfitRatio;
    }

    /**
     * 순이익률 설정
     * @param netProfitRatio Double net profit ratio
     */
    public void setNetProfitRatio(Double netProfitRatio) {
        this.netProfitRatio = netProfitRatio;
    }

    /**
     * roe 얻기
     * 자기자본이익률
     * @return Double return on equity
     */
    public Double getRoe() {
        return roe;
    }

    /**
     * roe 설정
     * 자기자본이익률
     * @param roe Double return on equity
     */
    public void setRoe(Double roe) {
        this.roe = roe;
    }

    /**
     * roa 얻기
     * 총 자산 순이익률
     * @return Double return on assets
     */
    public Double getRoa() {
        return roa;
    }

    /**
     * roa 설정
     * 총 자산 순이익률
     * @param roa Double return on assets
     */
    public void setRoa(Double roa) {
        this.roa = roa;
    }

    /**
     * 부채비율 얻기
     * @return Double debt ratio
     */
    public Double getDebtRatio() {
        return debtRatio;
    }

    /**
     * 부채비율 설정
     * @param debtRatio Double debt ratio
     */
    public void setDebtRatio(Double debtRatio) {
        this.debtRatio = debtRatio;
    }

    /**
     * 자본유보율 얻기
     * @return Double reserve ratio
     */
    public Double getReserveRatio() {
        return reserveRatio;
    }

    /**
     * 자분유보율 설정
     * @param reserveRatio Double reserve ratio
     */
    public void setReserveRatio(Double reserveRatio) {
        this.reserveRatio = reserveRatio;
    }

    /**
     * eps 얻기
     * 주당순이익
     * earning per shara
     * @return Double (price) earning per shara
     */
    public Double getEps() {
        return eps;
    }

    /**
     * eps 설정
     * 주당 순이익
     * @param eps Double (price) earning per shara
     */
    public void setEps(Double eps) {
        this.eps = eps;
    }

    /**
     * per 얻기
     * 주가수익 비율
     * price earning ratio
     * @return Double price earning ratio
     */
    public Double getPer() {
        return per;
    }

    /**
     * per 설정
     * 주가시욱 비율
     * price earning ratio
     * @param per Double price earning ratio
     */
    public void setPer(Double per) {
        this.per = per;
    }

    /**
     * bps 얻기
     * 주당순 자산가치
     * bookvalue per share
     *
     * @return Double bookvalue per share
     */
    public Double getBps() {
        return bps;
    }

    /**
     * bps 설정
     * 주당순 자산가치
     * bookvalue per share
     * @param bps Double bookvalue per share
     */
    public void setBps(Double bps) {
        this.bps = bps;
    }

    /**
     * pbr 얻기
     * 주가와 1주당 순자산을 비교하여 나타낸 비율(PBR = 주가 / 주당 순자산가치). 즉 주가가 순자산(자본금과 자본잉여금, 이익잉여금의 합계)에 비해 1주당 몇 배로 거래되고 있는지를 측정하는 지표
     * price bookvalue ratio
     * @return Double price bookvalue ratio
     */
    public Double getPbr() {
        return pbr;
    }

    /**
     * pbr 설정
     * 주가와 1주당 순자산을 비교하여 나타낸 비율(PBR = 주가 / 주당 순자산가치). 즉 주가가 순자산(자본금과 자본잉여금, 이익잉여금의 합계)에 비해 1주당 몇 배로 거래되고 있는지를 측정하는 지표
     * price bookvalue ratio
     * @param pbr Double price bookvalue ratio
     */
    public void setPbr(Double pbr) {
        this.pbr = pbr;
    }

    /**
     * dps 얻기
     * 주당배당액
     * @return Double dps
     */
    public Double getDps() {
        return dps;
    }

    /**
     * dps 설정
     * 주당바댕액
     * @param dps Double dps
     */
    public void setDps(Double dps) {
        this.dps = dps;
    }

    /**
     * 배당 수익률 얻기
     * @return Double dividend yield ratio
     */
    public Double getDividendYieldRatio() {
        return dividendYieldRatio;
    }

    /**
     * 배당 수익률 설정
     * @param dividendYieldRatio Double dividend yield ratio
     */
    public void setDividendYieldRatio(Double dividendYieldRatio) {
        this.dividendYieldRatio = dividendYieldRatio;
    }

    /**
     * 배당 성향 얻기
     * @return Double (ratio) propensity to dividend
     */
    public Double getPropensityToDividend() {
        return propensityToDividend;
    }

    /**
     * 배당성항 설정
     * @param propensityToDividend Double (ratio) propensity to dividend
     */
    public void setPropensityToDividend(Double propensityToDividend) {
        this.propensityToDividend = propensityToDividend;
    }

    /**
     * 발행주식 건수 얻기
     * 기업이 발행하여 일반 투자자가 소유하고 있는 주식수, 회사의 정관에 의하여 규정된 발행가능한 총 주식 중에서 이미 발행된 주식과 설립후 발행된 주식수는 합하고 발행기업이 금고주의 재매입하여 보유하거나 소각한 주식은 차감하여 계산한다
     * @return Long issued stock count
     */
    public Long getIssueStockCount() {
        return issueStockCount;
    }

    /**
     * 발행주식 건수 설정
     * 기업이 발행하여 일반 투자자가 소유하고 있는 주식수, 회사의 정관에 의하여 규정된 발행가능한 총 주식 중에서 이미 발행된 주식과 설립후 발행된 주식수는 합하고 발행기업이 금고주의 재매입하여 보유하거나 소각한 주식은 차감하여 계산한다
     * @param issueStockCount Long issue stock count
     */
    public void setIssueStockCount(Long issueStockCount) {
        this.issueStockCount = issueStockCount;
    }

    /**
     * 종목 코드 얻기
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * 종목 코드설정
     * @param code String
     */
    public void setCode(String code) {
        this.code = code;
    }


    /**
     * 업데이트 시간 얻기
     * @return long
     */
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * 업데이트 시간 설정
     * @param updateTime long
     */
    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}