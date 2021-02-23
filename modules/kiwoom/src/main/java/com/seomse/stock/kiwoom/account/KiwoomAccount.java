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
package com.seomse.stock.kiwoom.account;

import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import static org.slf4j.LoggerFactory.getLogger;

public class KiwoomAccount {
    private static final Logger logger = getLogger(KiwoomAccount.class);
    private static class SingleTonHolder{ private static final KiwoomAccount INSTANCE = new KiwoomAccount();}
    private KiwoomAccount(){}
    public static KiwoomAccount getInstance(){return SingleTonHolder.INSTANCE;}
    private ReentrantLock lock = new ReentrantLock();
    private String accountNumber;
    private int readyPrice;
    private int valuePrice;
    private Map<String,Integer> codeReserveMap = new HashMap<>();
    private Map<String,KiwoomItem> codeKiwoomItemMap = new HashMap<>();

    /**
     * 계좌번호
     * @return
     */
    public String getAccountNumber() {
        lock.lock();
        String result = accountNumber;
        lock.unlock();
        return result;
    }

    /**
     * 계좌번호
     * @param accountNumber String
     */
    public void setAccountNumber(String accountNumber) {
        lock.lock();
        this.accountNumber = accountNumber;
        lock.unlock();
    }

    /**
     * 예수금
     * @return
     */
    public int getReadyPrice() {
        lock.lock();
        int result =  readyPrice;
        lock.unlock();
        return result;
    }

    /**
     * 예수금
     * @param readyPrice int
     */
    public void setReadyPrice(int readyPrice) {
        lock.lock();
        this.readyPrice = readyPrice;
        lock.unlock();
    }

    /**
     * 총 매입액
     * @return
     */
    public int getValuePrice() {
        lock.lock();
        int result= valuePrice;
        lock.unlock();
        return result;
    }

    /**
     * 총 매입액
     * @param valuePrice int
     */
    public void setValuePrice(int valuePrice) {
        lock.lock();
        this.valuePrice = valuePrice;
        lock.unlock();
    }

    /**
     * 코드별 보유수량 (간편)
     * @return
     */
    public Integer getCodeReserve(String itemCode) {
        lock.lock();
        Integer result = codeReserveMap.get(itemCode);
        lock.unlock();
        return result;
    }

    /**
     * 코드별 보유수량 입력
     * @param itemCode String
     * @param reserveCount int
     */
    public void putCodeReserve(String itemCode , int reserveCount) {
        lock.lock();
        codeReserveMap.put(itemCode,reserveCount);
        lock.unlock();
    }

    /**
     * 코드별 키움 종목 정보 (상세)
     * @return
     */
    public final Map<String, KiwoomItem> getCodeKiwoomItemMap() {
        lock.lock();
        Map<String,KiwoomItem> result =  codeKiwoomItemMap;
        lock.unlock();
        return result;
    }

    /**
     * 코드 정보로 보유 종목 정보를 얻는다
     * @param itemCode String
     * @return
     */
    public final KiwoomItem getKiwoomItem(String itemCode){
        lock.lock();
        KiwoomItem result = codeKiwoomItemMap.get(itemCode);
        lock.unlock();
        return result;
    }

    /**
     * 종목 정보를 업데이트 한다.
     * @param itemCode String
     * @param kiwoomItem KiwoomItem
     */
    public void putKiwoomItem(String itemCode , KiwoomItem kiwoomItem) {
        lock.lock();
        codeKiwoomItemMap.put(itemCode, kiwoomItem);
        lock.unlock();
    }

    /**
     * 계좌 정보 문자열을 데이터로 셋팅 한다.
     * @param accountNumber String
     * @param accountInfoText String
     */
    public void parseAccountInfoText(String accountNumber , String accountInfoText){
        lock.lock();
        this.accountNumber = accountNumber;
        this.codeKiwoomItemMap.clear();
        this.codeReserveMap.clear();

        int accountSplitIndex = accountInfoText.indexOf("^");
        String priceText = accountInfoText.substring(0,accountSplitIndex);
        this.readyPrice = Integer.parseInt(priceText.split("\\|")[0]);
        this.valuePrice = Integer.parseInt(priceText.split("\\|")[1]);

        String itemInfoAllText = accountInfoText.substring(accountSplitIndex+1);
        if(itemInfoAllText.length() <= 1){
            lock.unlock();
            return;
        }

        String[] itemInfoAllTextArr = itemInfoAllText.split("\n", -1);
        for (String itemInfoText : itemInfoAllTextArr) {

            if(itemInfoText.length() < 1){
                continue;
            }

            KiwoomItem kiwoomItem = new KiwoomItem();
            String itemCode = "";
            int itemCount = 0;
            String[] itemInfoArr = itemInfoText.split("\\|", -1);
            for (int i = 0; i < itemInfoArr.length; i++) {

                switch (i){
                    case 0 -> { kiwoomItem.setItemCode(itemInfoArr[i]); itemCode=itemInfoArr[i]; }
                    case 1 -> kiwoomItem.setItemName(itemInfoArr[i]);
                    case 2 -> { kiwoomItem.setItemCount( Integer.parseInt(itemInfoArr[i]) ); itemCount = Integer.parseInt(itemInfoArr[i]); }
                    case 3 -> kiwoomItem.setAvgPrice( Integer.parseInt(itemInfoArr[i]) );
                    case 4 -> kiwoomItem.setNowPrice( Integer.parseInt(itemInfoArr[i]) );
                    case 5 -> kiwoomItem.setValuePriceTotal( Integer.parseInt(itemInfoArr[i]) );
                    case 6 -> kiwoomItem.setProfitLossPriceTotal( Integer.parseInt(itemInfoArr[i]) );
                }
            }
            this.codeKiwoomItemMap.put(itemCode,kiwoomItem);
            this.codeReserveMap.put(itemCode , itemCount);
        }
        lock.unlock();
    }


    public static void main(String [] args){

//        String accountNumber = KiwoomConfig.getConfig(KiwoomConfig.ACCOUNT_NUMBER);
//        System.out.println("accountNumber:"+accountNumber);
//        KiwoomApiStart start = new KiwoomApiStart(33333,33334);
//        start.start();
//        KiwoomApiCallbackData accountDetail = KiwoomApiSender.getInstance().getAccountDetail(accountNumber);
//        System.out.println("accountDetail.getCallbackCode():"+accountDetail.getCallbackCode());
//        System.out.println("accountDetail.getCallbackData():"+accountDetail.getCallbackData());

    }
}
