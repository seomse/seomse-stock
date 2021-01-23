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

public class KiwoomClient {
    private String id ;
    private ApiRequest request;
    public KiwoomClient(String id , ApiRequest request){
        this.id = id;
        this.request = request;
    }

    /**
     * 핑을 보낸다
     * @throws Exception
     */
    public void ping() throws Exception{
        try {
            request.sendToReceiveMessage("KWPING01", "PING!");
        } catch(Exception e){
            throw e;
        }
    }

    /**
     * ApiRequest를 얻는다
     * @return
     */
    public ApiRequest getRequest() {
        return request;
    }

    /**
     * 생존여부
     * @return
     */
    public boolean isAlive(){
        return request.isConnect();
    }

    /**
     * 아이디
     * @return
     */
    public String getId() {
        return id;
    }
}
