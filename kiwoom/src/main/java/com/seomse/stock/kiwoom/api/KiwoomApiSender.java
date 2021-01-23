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
import com.seomse.commons.utils.ExceptionUtil;
import com.seomse.stock.kiwoom.process.KiwoomProcess;
import org.slf4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

import static org.slf4j.LoggerFactory.getLogger;

public class KiwoomApiSender {

    private static class SingleTonHolder{ private static final KiwoomApiSender INSTANCE = new KiwoomApiSender();}
    private KiwoomApiSender(){}
    public static KiwoomApiSender getInstance(){return SingleTonHolder.INSTANCE;}

    private static final Logger logger = getLogger(KiwoomApiSender.class);
    private int callbackNumber = 0;
    private ReentrantLock apiLock = new ReentrantLock();

    public ReentrantLock getApiLock() { return apiLock; }

    private static final int MAX_WAIT_SECONDS = 10;



    /**
     * 일별 데이터를 얻어온다
     * @param itemCode 주식코드
     * @param date 날짜 문자열
     */
    @Deprecated
    public void getDatePriceData(String itemCode,String date){
        apiLock.lock();
        KiwoomClient kiwoomClient = KiwoomClientManager.getInstance().getClient();
        final ApiRequest request = kiwoomClient.getRequest();
        request.sendMessage(KiwoomApiUtil.makeCodeParam(KiwoomApiCallCode.CALL_TR,KiwoomApiCode.DAILY_STOCK_INFO),KiwoomApiUtil.makeDataParam(itemCode,date,"1"));
        apiLock.unlock();
    }

    /**
     * 일별 신용 정보를 얻어온다
     * @param itemCode 일별 코드
     * @param date 날짜 문자열
     * @return
     */
    public String getDateCreditData(String itemCode , String date){
        apiLock.lock();
        String callbackId = (callbackNumber++) + "";
        KiwoomApiCallbackData apiResult = apiSend(KiwoomApiUtil.makeCodeParam(KiwoomApiCallCode.CALL_TR,KiwoomApiCode.DAILY_STOCK_CREDIT,callbackId),
                KiwoomApiUtil.makeDataParam(itemCode,date,"1"),callbackId);
        apiLock.unlock();
        if(apiResult== null){
            return null;
        }
        return apiResult.getCallbackData();

    }

    /**
     * 일별 신용 정보를 얻어온다
     * @param itemCode 일별 코드
     * @param continueCode 연속조회 코드
     * @return
     */
    public KiwoomApiCallbackData getDateStrengthData(String itemCode , int continueCode){
        apiLock.lock();
        String callbackId = (callbackNumber++) + "";
        KiwoomApiCallbackData apiResult = apiSend(KiwoomApiUtil.makeCodeParam(KiwoomApiCallCode.CALL_TR,KiwoomApiCode.DAILY_STRENGTH_INFO,callbackId),
                KiwoomApiUtil.makeDataParam(itemCode,continueCode+""),callbackId,true);
        apiLock.unlock();
        if(apiResult== null){
            return null;
        }
        return apiResult;

    }
    /**
     * API 를 전송하고 데이터를 얻는다.
     * @param code API 코드
     * @param data API 데이터
     * @param callbackId 콜백ID
     * @return
     */
    private KiwoomApiCallbackData apiSend(String code , String data , String callbackId) {
        return apiSend(code,data,callbackId,false);
    }

    /**
     * 주문 실행
     * @param itemCode 주식코드
     * @param accountNumber 계좌번호 : 계좌번호10자리
     * @param orderType 주문유형 : 1:신규매수, 2:신규매도 3:매수취소, 4:매도취소, 5:매수정정, 6:매도정정
     * @param orderCount 주문수량 : 주문수량
     * @param orderPrice 주문가격 : 주문가격
     * @param hoga 호가구분 : 00:지정가,03:시장가,05:조건부지정가,06:최유리지정가,07:최우선지정가,10:지정가IOC,13:시장가IOC,16:최유리IOC,20:지정가FOK,23:시장가FOK,26:최유리FOK,61:장전시간외종가,62:시간외단일가매매,81:장후시간외종가
     * @param preOrderNum 원주문번호 : 신규주문에는 공백, 정정(취소)주문할 원주문번호를 입력합니다
     */
    public KiwoomApiCallbackData sendOrder(String itemCode , String accountNumber , String orderType,
                                                  int orderCount , int orderPrice , String hoga , String preOrderNum ){
        apiLock.lock();
        String callbackId = (callbackNumber++) + "";
        /*         ///<param name="arg1">계좌번호 : 계좌번호10자리</param>
         *         ///<param name="arg2">주문유형 : 1:신규매수, 2:신규매도 3:매수취소, 4:매도취소, 5:매수정정, 6:매도정정</param>
         *         ///<param name="arg3">종목코드 : 종목코드</param>
         *         ///<param name="arg4">주문수량 : 주문수량</param>
         *         ///<param name="arg5">주문가격 : 주문가격</param>
         *         ///<param name="arg6">호가구분 : 00:지정가,03:시장가,05:조건부지정가,06:최유리지정가,07:최우선지정가,10:지정가IOC,13:시장가IOC,16:최유리IOC,20:지정가FOK,23:시장가FOK,26:최유리FOK,61:장전시간외종가,62:시간외단일가매매,81:장후시간외종가</param>
         *         ///<param name="arg7">원주문번호 : 신규주문에는 공백, 정정(취소)주문할 원주문번호를 입력합니다.</param>
         */
        KiwoomApiCallbackData apiResult = apiSend(KiwoomApiUtil.makeCodeParam(KiwoomApiCallCode.SEND_ORDER,callbackId),
                KiwoomApiUtil.makeDataParam(
                        accountNumber,orderType,itemCode,orderCount+"",orderPrice+"",hoga,preOrderNum
                ),callbackId);
        apiLock.unlock();
        return apiResult;
    }

    /**
     * 주문 실행
     * @param itemCode 주식코드
     * @param accountNumber 계좌번호 : 계좌번호10자리
     * @param orderType 주문유형 : 1:신규매수, 2:신규매도
     * @param orderCount 주문수량 : 주문수량
     */
    public KiwoomApiCallbackData sendMarketPriceOrder(String itemCode , String accountNumber , String orderType,
                                                  int orderCount ){
        apiLock.lock();
        String callbackId = (callbackNumber++) + "";
        KiwoomApiCallbackData apiResult = apiSend(KiwoomApiUtil.makeCodeParam(KiwoomApiCallCode.SEND_ORDER,callbackId),
                KiwoomApiUtil.makeDataParam(
                        accountNumber,orderType,itemCode,orderCount+"","0","03",""
                ),callbackId);
        apiLock.unlock();
        return apiResult;
    }

    /**
     * 계좌상세현황요청
     * @param accountNumber
     * @return
     */
    public KiwoomApiCallbackData getAccountDetail(String accountNumber) {
        apiLock.lock();
        String callbackId = (callbackNumber++) + "";
        KiwoomApiCallbackData apiResult = apiSend(KiwoomApiUtil.makeCodeParam(KiwoomApiCallCode.CALL_TR,KiwoomApiCode.ACCOUNT_ITEM_DETAIL,callbackId),
                KiwoomApiUtil.makeDataParam(accountNumber,"","00","1"),callbackId,true);
        apiLock.unlock();
        return apiResult;
    }

    /**
     * 현재 분봉 정보를 얻어온다
     * @param itemCode
     * @param minuteType 틱범위 : 1:1분, 3:3분, 5:5분, 10:10분, 15:15분, 30:30분, 45:45분, 60:60분
     * @param chartType 수정주가구분 : 0 or 1, 수신데이터 1:유상증자, 2:무상증자, 4:배당락, 8:액면분할, 16:액면병합, 32:기업합병, 64:감자, 256:권리락
     * @return 현재가 / 거래량 / 체결시간 / 시가 / 고가 / 저가 / 그외 나머지 데이터 빈값
     *         숫자앞 +/- 기호 => 상승/하락의 표기뿐, 지워서 숫자값만 사용 해야 함
     */
    public KiwoomApiCallbackData getMinuteData(String itemCode , int minuteType , int chartType , int continueCode ){
        apiLock.lock();
        String callbackId = (callbackNumber++) + "";
        KiwoomApiCallbackData apiResult = apiSend(KiwoomApiUtil.makeCodeParam(KiwoomApiCallCode.CALL_TR,KiwoomApiCode.MINUTE_CHART_DATA,callbackId),
                KiwoomApiUtil.makeDataParam(itemCode,minuteType+"",chartType+"",continueCode+""),callbackId,true);
        apiLock.unlock();
        return apiResult;
    }


    /**
     * API 를 전송하고 데이터를 얻는다.
     * @param code API 코드
     * @param data API 데이터
     * @param callbackId 콜백ID
     * @return
     */
    private KiwoomApiCallbackData apiSend(String code , String data , String callbackId , boolean hasContinueData) {

        KiwoomApiCallbackStore.getInstance().putCallbackId(callbackId);
        KiwoomClient kiwoomClient = KiwoomClientManager.getInstance().getClient();
        ApiRequest request = kiwoomClient.getRequest();
        logger.debug("apiSend:"+code + " ["+data+"] ["+callbackId+"] ["+hasContinueData+"] ");
        request.sendMessage(code,data);

        // 0.01초 마다 재시도를 하기 때문에 100을 곱해줌
        int maxTryCount = (int)(MAX_WAIT_SECONDS * 100);

        int tryCount = 0;
        int maxRerunCount = 2;
        int rerunCount = 0;
        while(true){
            try {
                Thread.sleep(10L);
                KiwoomApiCallbackData callbackData = KiwoomApiCallbackStore.getInstance().getCallbackData(callbackId);
                if(callbackData != null){
                    return callbackData;
                }
            } catch (InterruptedException e) {
                logger.error(ExceptionUtil.getStackTrace(e));
            }
            // 설정시간 초과시 키움 API 종료후 재시도.
            if(++tryCount > maxTryCount) {
                logger.error("maxTryCount:"+maxTryCount + ", tryCount:"+tryCount);
                KiwoomProcess.rerunKiwoomApi();
                rerunCount++;

                if(rerunCount > maxRerunCount){
                    return null;
                }

                kiwoomClient = KiwoomClientManager.getInstance().getClient();
                request = kiwoomClient.getRequest();

                if(hasContinueData){
                    return null;
                }

                request.sendMessage(code,data);
                tryCount = 0;
            }
        }
    }
}
