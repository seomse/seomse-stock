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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KWCONN01 extends ApiMessage {

    private static final Logger logger = LoggerFactory.getLogger(KWCONN01.class);

    @Override
    public void receive(String message) {
        try{
            String apiId = message;
            logger.debug("ADD CLIENT! : " + apiId);
            sendMessage("SUCCESS");
        }catch(Exception e){
            sendMessage("FAIL" + ExceptionUtil.getStackTrace(e));
        }
    }
}
