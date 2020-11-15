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

package seomse.stock.analysis.fundamental.cashflow;

import seomse.stock.analysis.fundamental.QuantScore;
import seomse.stock.analysis.fundamental.QuantYmd;
import seomse.stock.analysis.store.FinancialStatements;
import seomse.stock.analysis.store.Item;
import seomse.stock.analysis.store.ItemStore;

/**
 * 영업활동 현금흐름 quant
 * @author macle
 */
public class OperatingCashFlowQuant_001 extends QuantScore {


    /**
     * 생성자
     * @param code      String (종목 코드)
     * @param itemStore ItemStore (종목 정보 저장소)
     */
    public OperatingCashFlowQuant_001(String code, ItemStore itemStore) {
        super(code, itemStore);
    }


    @Override
    protected double analysis() {
        
        //데이터 가 꼭 들어올 월을 계산하여 돌려준다
        String quantYm = QuantYmd.getMinYm(ymd);

        Item item = itemStore.getItem(code);

        FinancialStatements[] quarterFinancialStatementsArray = item.getQuarterFinancialStatementsArray();

        int lastIndex = -1;

        int intYm = Integer.parseInt(quantYm);

        for (int i = quarterFinancialStatementsArray.length-1 ; i > -1 ; i--) {
            FinancialStatements financialStatements = quarterFinancialStatementsArray[i];
            if(Integer.parseInt(financialStatements.getYm()) <= intYm ){
                lastIndex = i;
                break;
            }


        }


        if(lastIndex == -1){
            //재무정보가 없는경우
            return QuantScore.DEFAULT_SCORE;
        }


        return 0;
    }


}
