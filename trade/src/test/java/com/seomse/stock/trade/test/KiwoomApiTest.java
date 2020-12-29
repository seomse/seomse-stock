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
package com.seomse.stock.trade.test;

import com.seomse.stock.kiwoom.KiwoomApiStart;
import com.seomse.stock.kiwoom.account.KiwoomAccount;
import com.seomse.stock.kiwoom.account.KiwoomItem;
import com.seomse.stock.kiwoom.api.KiwoomApiCallbackData;
import com.seomse.stock.kiwoom.api.KiwoomApiSender;
import com.seomse.stock.kiwoom.config.KiwoomConfig;

import java.util.Map;

public class KiwoomApiTest {
    public static void main(String [] args){

        KiwoomConfig.setConfigPath("C:\\workspace\\git\\seomse-kiwoom-java-api\\config\\kiwoom_config");
        KiwoomConfig.reLoad();

        KiwoomApiStart kiwoomApiStart = new KiwoomApiStart(33333,33334);
        kiwoomApiStart.runAndWait();




        // 시장가 주문, 장 마감 동시호가도 동일
        //KiwoomApiSender.getInstance().sendMarketPriceOrder("코드 숫자 6자리","계좌번호10자리","1 or 2 ==> 1신규매도 2신규매수", 0 /* 주문수량 */ );

        // 계좌정보 동기화 ==> KiwoomAccount
        //KiwoomApiSender.getInstance().getAccountDetail("계좌번호10자리");

        // 보유 종목 개수
        //int itemCount = KiwoomAccount.getInstance().getCodeReserve("종목코드");


        // 계좌 상세 정보
        System.out.println("계좌번호:"+KiwoomAccount.getInstance().getAccountNumber());
        System.out.println("예수금:"+KiwoomAccount.getInstance().getReadyPrice());
        System.out.println("평가액:"+KiwoomAccount.getInstance().getValuePrice());

        // 보유 종목 상세 정보
        Map<String, KiwoomItem> codeKiwoomItemMap = KiwoomAccount.getInstance().getCodeKiwoomItemMap();
        for (String s : codeKiwoomItemMap.keySet()) {
            KiwoomItem kiwoomItem = codeKiwoomItemMap.get(s);
            System.out.println("####################################");
            System.out.println("\t종목코드:"+kiwoomItem.getItemCode());
            System.out.println("\t종목명:"+kiwoomItem.getItemName());
            System.out.println("\t종목수량:"+kiwoomItem.getItemCount());
            System.out.println("\t평단가:"+kiwoomItem.getAvgPrice());
            System.out.println("\t평가액:"+kiwoomItem.getValuePriceTotal());
            System.out.println("\t손익액:"+kiwoomItem.getProfitLossPriceTotal());
        }

        // 분봉 데이터 얻기
        KiwoomApiCallbackData minuteData = KiwoomApiSender.getInstance().getMinuteData("048550", 5, 0);
        String callbackData = minuteData.getCallbackData();
        System.out.println(callbackData);
//        System.out.println();
    }
}
