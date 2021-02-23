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

import com.seomse.stock.kiwoom.api.callback.KiwoomConcludeData;
import com.seomse.stock.kiwoom.api.callback.KiwoomConcludeHandler;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static org.slf4j.LoggerFactory.getLogger;

public class KiwoomConcludeReceiver {
    private static final Logger logger = getLogger(KiwoomConcludeReceiver.class);
    private static class SingleTonHolder{ private static final KiwoomConcludeReceiver INSTANCE = new KiwoomConcludeReceiver();}
    private KiwoomConcludeReceiver(){}
    public static KiwoomConcludeReceiver getInstance(){return SingleTonHolder.INSTANCE;}
    ReentrantLock lock = new ReentrantLock();
    List<KiwoomConcludeHandler> handlerList = new LinkedList<>();

    /**
     * 체결정보 핸들러 추가
     * @param handler
     */
    public void addHandler(KiwoomConcludeHandler handler){
        lock.lock();
        handlerList.add(handler);
        lock.unlock();
    }

    /**
     * 체결정보 이벤트 리시버
     * @param data
     */
    public void concludeEventReceive(KiwoomConcludeData data){
        lock.lock();
        for (KiwoomConcludeHandler kiwoomConcludeHandler : handlerList) {
            kiwoomConcludeHandler.conclude(data);
        }
        lock.unlock();
    }
}
