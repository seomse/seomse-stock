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
package com.seomse.stock.kiwoom.api;

public class KiwoomApiOrderCode {
    /**
     * 주문유형코드 nOrderType
     */
    public static class OrderCode{
        /* 신규매수 */ public static final String ORD_NEW_BUY = "1";
        /* 신규매도 */ public static final String ORD_NEW_SELL = "2";
        /* 매수취소 */ public static final String ORD_CANCEL_BUY = "3";
        /* 매도취소 */ public static final String ORD_CANCEL_SELL = "4";
        /* 매수정정 */ public static final String ORD_MODIFY_BUY = "5";
        /* 매도정정 */ public static final String ORD_MODIFY_SELL = "6";
    }
    /**
     * 호가구분 , 거래구분
     */
    public static class QuoteCode {
        /* 지정가 */ public static final String HG_LIMIT_PRICE = "00";
        /* 시장가 */ public static final String HG_MARKET_PRICE = "03";
        /* 조건부지정가 */ public static final String HG_COND_LIMIT_PRICE = "05";
        /* 최유리지정가 */ public static final String HG_TOP_ADV_LIMIT_PRICE = "06";
        /* 최우선지정가 */ public static final String HG_TOP_PRIOR_LIMIT_PRICE = "07";
        /* 지정가IOC */ public static final String HG_LIMIT_IOC = "10";
        /* 시장가IOC */ public static final String HG_MARKET_IOC = "13";
        /* 최유리IOC */ public static final String HG_TOP_ADV_IOC = "16";
        /* 지정가FOK */ public static final String HG_LIMIT_FOK = "20";
        /* 시장가FOK */ public static final String HG_MARKET_FOK = "23";
        /* 최유리FOK */ public static final String HG_TOP_ADV_FOK = "26";
        /* 장전시간외종가 */ public static final String HG_OVERTIME_CLOSE_PRICE = "61";
        /* 시간외단일가 */ public static final String HG_OVERTIME_SINGLE_PRICE = "62";
        /* 장후시간외종가 */ public static final String HG_AFTERTIME_CLOSE_PRICE = "81";
    }

}
