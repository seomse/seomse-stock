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

package com.seomse.stock.kiwoom.api.message;

import com.seomse.api.ApiMessage;
import com.seomse.commons.utils.ExceptionUtil;
import com.seomse.stock.kiwoom.api.KiwoomConcludeReceiver;
import com.seomse.stock.kiwoom.api.TelegramMessage;
import com.seomse.stock.kiwoom.api.callback.KiwoomConcludeData;
import com.seomse.stock.kiwoom.config.KiwoomConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KWCBCD01 extends ApiMessage {

    private static final Logger logger = LoggerFactory.getLogger(KWCBCD01.class);
    private static final String DATA_SEPARATOR = "\\|";
    private static final String CALLBACK_PACKAGE="com.seomse.stock.kiwoom.api.callback.control";
    private boolean telegramAlertUse = false;
    @Override
    public void receive(String message) {

        String telegramAlertUseStr = KiwoomConfig.getConfig("telegram_alert_use");
        if(telegramAlertUseStr != null){
            telegramAlertUse = Boolean.parseBoolean(telegramAlertUseStr);
        }

        try{
            String data = message;
            System.out.println(message);
            logger.debug("CALLBACK Recieved : KWCBCD01 data size :  "+data.split("\n").length);
            /**
             *
             [OnReceiveChejan()이벤트로 전달되는 FID목록정리]
             0  "9201" : "계좌번호"
             1  "9203" : "주문번호"
             2  "9001" : "종목코드"
             3  "302" : "종목명"
             4  "900" : "주문수량"
             5  "901" : "주문가격"
             6  "905" : "주문구분"
             7  "908" : "주문/체결시간"
             8  "909" : "체결번호"
             9  "910" : "체결가"
             10 "911" : "체결량"
             11 "913" : "주문상태"
             */
            String [] dataArr = data.split(DATA_SEPARATOR);
            KiwoomConcludeData concludeData = new KiwoomConcludeData();
            for (int i = 0; i < dataArr.length; i++) {
                switch (i){
                    case 0 -> concludeData.setDepositNumber(dataArr[i]);
                    case 1 -> concludeData.setOrderNumber(dataArr[i]);
                    case 2 -> concludeData.setItemCode(dataArr[i]);
                    case 3 -> concludeData.setItemName(dataArr[i]);
                    case 4 -> concludeData.setOrderCount(Integer.parseInt( dataArr[i]));
                    case 5 -> concludeData.setOrderPrice(Integer.parseInt(dataArr[i]));
                    case 6 -> concludeData.setTradeType(dataArr[i]);
                    case 7 -> concludeData.setConcludeTime(dataArr[i]);
                    case 8 -> concludeData.setConcludeNumber(dataArr[i]);
                    case 9 -> concludeData.setConcludePrice(Integer.parseInt(dataArr[i]));
                    case 10 -> concludeData.setConcludeCount(Integer.parseInt(dataArr[i]));
                    case 11 -> concludeData.setDepositVolume(Integer.parseInt(dataArr[i]));
                }
            }
            KiwoomConcludeReceiver.getInstance().concludeEventReceive(concludeData);
            if(telegramAlertUse){
                TelegramMessage.toStockMessageToMe(
                        concludeData.getItemName(),concludeData.getConcludePrice(),concludeData.getConcludeCount(),
                        concludeData.getDepositNumber(),concludeData.getDepositVolume(),
                        concludeData.getTradeType().equals("2")
                );
            }
        }catch(Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
            sendMessage("FAIL" + ExceptionUtil.getStackTrace(e));
        }
    }
}
