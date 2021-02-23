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

package com.seomse.stock.data.dev;

import com.seomse.jdbc.annotation.DateTime;
import com.seomse.jdbc.annotation.PrimaryKey;
import com.seomse.jdbc.annotation.Table;

/**
 * @author macle
 */
@Table(name="T_STOCK_FSMT")
public class FsmtNo {
    @PrimaryKey(seq = 1)
    private String ITEM_CD;
    @PrimaryKey(seq = 2)
    private String SETTLEMENT_YM;
    @PrimaryKey(seq = 3)
    private String SETTLEMENT_TP;

    private Double SALES_PRC;
    private Double OPF_PRC;
    private Double CBPF_PRC;
    private Double NPF_PRC;
    private Double NPF_DMN_PRC;
    private Double NPF_NDMN_PRC;
    private Double ASSET_TOTAL_PRC;
    private Double DEBT_TOTAL_PRC;
    private Double CAPITAL_TOTAL_PRC;
    private Double CAPITAL_TOTAL_DMN_PRC;
    private Double CAPITAL_TOTAL_NDMN_PRC;
    private Double CAPITAL_PRC;
    private Double OCF_PRC;
    private Double ICF_PRC;
    private Double FNCF_PRC;
    private Double CAPEX_PRC;
    private Double FCF_PRC;
    private Double INTEREST_DEBT_PRC;
    private Double OPF_RT;
    private Double NPF_RT;
    private Double ROE_RT;
    private Double ROA_RT;
    private Double DEBT_RT;
    private Double CRR_RT;
    private Double EPS_WON_PRC;
    private Double PER_RT;
    private Double BPS_WON_PRC;
    private Double PBR_RT;
    private Double DPS_WON_PRC;
    private Double DIVIDEND_RT;
    private Double CASH_DIVIDEND_RT;
    private Long STOCK_CNT;
    @DateTime
    private Long REG_DT ;
    @DateTime
    private Long UPT_DT ;
}
