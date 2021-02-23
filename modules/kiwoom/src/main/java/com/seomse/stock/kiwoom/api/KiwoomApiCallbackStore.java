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

import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import static org.slf4j.LoggerFactory.getLogger;

public class KiwoomApiCallbackStore {
    private static final Logger logger = getLogger(KiwoomApiCallbackStore.class);
    private static class SingleTonHolder{ private static final KiwoomApiCallbackStore INSTANCE = new KiwoomApiCallbackStore();}
    private KiwoomApiCallbackStore(){}
    public static KiwoomApiCallbackStore getInstance(){return SingleTonHolder.INSTANCE;}
    private ReentrantLock lock = new ReentrantLock();
    private Map<String,KiwoomApiCallbackData> callbackMap = new HashMap<>();

    /**
     * put callback ID to map
     * @param callbackId
     */
    public void putCallbackId(String callbackId){
        lock.lock();
        callbackMap.put(callbackId,null);
        lock.unlock();
    }

    /**
     * 콜백 데이터를 적재
     * @param callbackId
     * @param data
     */
    public void putCallbackData(String callbackId , KiwoomApiCallbackData data){
        lock.lock();
        callbackMap.put(callbackId,data);
        lock.unlock();
    }

    /**
     * 콜백 데이터를 가져온다.
     * 데이터가 존재시엔, 해당 데이터를 삭제 하고 가져온다.
     * @param callbackId
     * @return
     */
    public KiwoomApiCallbackData getCallbackData(String callbackId){
        lock.lock();
        KiwoomApiCallbackData data = callbackMap.get(callbackId);
        if(data != null){
            callbackMap.remove(callbackId);
        }
        lock.unlock();
        return data;
    }


}
