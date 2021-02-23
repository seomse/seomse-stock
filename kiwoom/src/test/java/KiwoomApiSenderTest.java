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

import com.seomse.stock.kiwoom.KiwoomApiStart;
import com.seomse.stock.kiwoom.api.KiwoomApiCallbackData;
import com.seomse.stock.kiwoom.api.KiwoomApiSender;
import com.seomse.stock.kiwoom.api.KiwoomConcludeReceiver;

public class KiwoomApiSenderTest {

    public static void main(String [] args){

        Thread t = new KiwoomApiStart(33333,33334);
        t.start();
        /**
         *     String depositNumber;
         *     String orderNumber;
         *     String itemCode;
         *     String itemName;
         *     int orderCount;
         *     int orderPrice;
         *     String tradeType;
         *     String concludeTime;
         *     String concludeNumber;
         *     int concludePrice;
         *     int concludeCount;
         *     String orderStatus;
         */
        KiwoomConcludeReceiver.getInstance().addHandler(concludeData -> {
            System.out.println(
                    """
                       ###################
                       계약체결 확정!
                       계좌번호 : "%s"
                       주분번호 : "%s"
                       종목코드 : "%s"
                       종목명 : "%s"
                       주문수량 : "%s"
                       주문가격 : "%s"
                       주문구분 : "%s"
                       체결시간 : "%s"
                       체결번호 : "%s"
                       체결가격 : "%s"
                       체결수량 : "%s"
                       예수금 : "%s"   
                       ###################                         
                    """.formatted(
                            concludeData.getDepositNumber(),
                            concludeData.getConcludeNumber(),
                            concludeData.getItemCode(),
                            concludeData.getItemName(),
                            concludeData.getOrderCount(),
                            concludeData.getOrderPrice(),
                            concludeData.getTradeType(),
                            concludeData.getConcludeTime(),
                            concludeData.getConcludeNumber(),
                            concludeData.getConcludePrice(),
                            concludeData.getConcludeCount(),
                            concludeData.getDepositVolume()
                    )
            );
        });
//
//        KiwoomApiCallbackData kiwoomApiCallbackData = KiwoomApiSender.getInstance().sendMarketPriceOrder(
//                "048550", "", "2", 1);
//
//        System.out.println("[1] "+kiwoomApiCallbackData.getCallbackCode() + " : " + kiwoomApiCallbackData.getCallbackData());
//
//        try {Thread.sleep(10000L);} catch (InterruptedException e) {}
//        kiwoomApiCallbackData = KiwoomApiSender.getInstance().getMinuteData("252670",1,0,1);
//        System.out.println("[2] "+kiwoomApiCallbackData.getCallbackCode() + " : " + kiwoomApiCallbackData.getCallbackData());
    }
}
