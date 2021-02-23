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
import com.seomse.stock.kiwoom.api.KiwoomApiCallbackData;
import com.seomse.stock.kiwoom.api.KiwoomApiCallbackStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KWCBTR01 extends ApiMessage {

    private static final Logger logger = LoggerFactory.getLogger(KWCBTR01.class);
    private static final String PARAM_SEPARATOR = ",";
    private static final String CALLBACK_PACKAGE="com.seomse.stock.kiwoom.api.callback.control";
    @Override
    public void receive(String message) {
        try{
            String kiwoomApiCode = message.split(PARAM_SEPARATOR)[0];
            String result = message.substring(kiwoomApiCode.length()+1);
            String callbackId=null,data=null;
            boolean hasNextData = false;
            if(result.contains(PARAM_SEPARATOR)) {
                try {
                    callbackId = result.substring(0, result.indexOf(PARAM_SEPARATOR));
                    result = result.substring(callbackId.length()+1);
                    String hasNextDataStr = result.substring(0, result.indexOf(PARAM_SEPARATOR));
                    if(hasNextDataStr.equals("2")){
                        hasNextData = true;
                    }
                    result = result.substring(hasNextDataStr.length()+1);
                    data = result;
                } catch(IndexOutOfBoundsException e){
                    logger.error(ExceptionUtil.getStackTrace(e));
                    callbackId = "";
                    data = result;
                }
            } else {
                callbackId = "";
                data = result;
            }
            logger.debug("CALLBACK Received : " + kiwoomApiCode + " data size :  "+data.split("\n").length + "("+callbackId+")");
            KiwoomApiCallbackData callbackData = new KiwoomApiCallbackData(kiwoomApiCode,data,hasNextData);
            KiwoomApiCallbackStore.getInstance().putCallbackData(callbackId,callbackData);
            sendMessage("SUCCESS");
        }catch(Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
            sendMessage("FAIL" + ExceptionUtil.getStackTrace(e));
        }
    }
}
