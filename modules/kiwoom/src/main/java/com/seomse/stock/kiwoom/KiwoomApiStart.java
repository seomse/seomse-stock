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

package com.seomse.stock.kiwoom;

import com.seomse.api.server.ApiRequestConnectHandler;
import com.seomse.api.server.ApiRequestServer;
import com.seomse.api.server.ApiServer;
import com.seomse.commons.utils.ExceptionUtil;
import com.seomse.commons.utils.time.DateUtil;
import com.seomse.stock.kiwoom.account.KiwoomAccount;
import com.seomse.stock.kiwoom.api.KiwoomApiCallbackData;
import com.seomse.stock.kiwoom.api.KiwoomApiSender;
import com.seomse.stock.kiwoom.api.KiwoomClientManager;
import com.seomse.stock.kiwoom.config.KiwoomConfig;
import com.seomse.stock.kiwoom.process.KiwoomProcessMonitorService;
import org.slf4j.Logger;

import java.net.InetAddress;
import java.net.Socket;

import static org.slf4j.LoggerFactory.getLogger;

public class KiwoomApiStart extends Thread{
    private static final Logger logger = getLogger(KiwoomApiStart.class);
    int receivePort,sendPort;

    /**
     * 키움 API 실행
     * @param receivePort 수신포트
     * @param sendPort 송신포트
     */
    public KiwoomApiStart(int receivePort , int sendPort){
        this.receivePort = receivePort;
        this.sendPort = sendPort;
    }


    boolean isComplete = false;

    /**
     * 실행하고 기다린다
     */
    public void runAndWait(){
        run();
        while(!isComplete){
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                logger.error(ExceptionUtil.getStackTrace(e));
            }
        }
    }
    @Override
    public void run(){
        ApiRequestConnectHandler handler =  request -> {
            Socket socket = request.getSocket();
            InetAddress inetAddress = socket.getInetAddress();
            String nodeKey = inetAddress.getHostAddress() + "," + inetAddress.getHostName();
            logger.debug("NEW NODE CONNECTED : " + nodeKey);
            KiwoomClientManager.getInstance().addClient(nodeKey,request);
        };
        // Kiwoom API 송신용
        ApiRequestServer apiRequestServer = new ApiRequestServer(sendPort, handler);
        apiRequestServer.start();

        /// Kiwoom API 수신용
        ApiServer apiServer = new ApiServer(receivePort,"com.seomse.stock");
        apiServer.start();

        // 오전 9시 모니터링 시작
        KiwoomProcessMonitorService monitorService = new KiwoomProcessMonitorService();
        monitorService.setSleepTime(60000L);
        monitorService.start();
        
        // 키움 프로세스 종료후 재 실행
        //KiwoomProcess.rerunKiwoomApi();

        // 계좌정보 업데이트
        String accountNumber = KiwoomConfig.getConfig( KiwoomConfig.ACCOUNT_NUMBER );
        if(accountNumber!=null){
            KiwoomApiCallbackData accountDetail = KiwoomApiSender.getInstance().getAccountDetail(accountNumber);
            KiwoomAccount.getInstance().parseAccountInfoText(accountNumber,accountDetail.getCallbackData());
        }
        logger.debug("START SERVER : " + DateUtil.getDateYmd(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss"));
        isComplete = true;
    }
}
