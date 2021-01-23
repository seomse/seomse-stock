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

import com.seomse.api.ApiRequest;
import com.seomse.stock.kiwoom.process.KiwoomProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KiwoomClientManager {
    private static final Logger logger = LoggerFactory.getLogger(KiwoomClientManager.class);
    private static class SingleTonHolder{ private static final KiwoomClientManager INSTANCE = new KiwoomClientManager();}
    private KiwoomClientManager(){}
    public static KiwoomClientManager getInstance(){return SingleTonHolder.INSTANCE;}
//    private Map<String,KiwoomClient> kiwoomClientMap = new HashMap<>();
    private KiwoomClient client=null;

    /**
     * 클라이언트를 추가 한다
     * @param clientId
     * @param request
     */
    public void addClient(String clientId, ApiRequest request){
        KiwoomClient newClient = new KiwoomClient(clientId , request);
        if(newClient != null) {
            logger.debug("set new Client : " + clientId);
            this.client = newClient;
        } else {
            logger.debug("set new Client fail! ");
        }
    }

    /**
     * 클라이언트를 얻는다
     * @return
     */
    public KiwoomClient getClient() {
        while(true) {
            KiwoomClient kiwoomClient = this.client;
            if (kiwoomClient == null) {
                logger.error("kiwoomClient is null");
                KiwoomProcess.rerunKiwoomApi();
            } else {
                return kiwoomClient;
            }
        }
    }


}
