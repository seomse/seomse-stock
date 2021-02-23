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

public class KiwoomApiCallbackData {
    private String callbackCode;
    private String callbackData;
    private boolean hasNext = false;

    public KiwoomApiCallbackData(String callbackCode, String callbackData, boolean hasNext) {
        this.callbackCode = callbackCode;
        this.callbackData = callbackData;
        this.hasNext = hasNext;
    }

    /**
     * 콜백코드 얻기
     * @return
     */
    public String getCallbackCode() {
        return callbackCode;
    }

    /**
     * 콜백코드 설정
     * @param callbackCode
     */
    public void setCallbackCode(String callbackCode) {
        this.callbackCode = callbackCode;
    }

    /**
     * 콜백데이터 얻기
     * @return
     */
    public String getCallbackData() {
        return callbackData;
    }

    /**
     * 콜백데이터 설정
     * @param callbackData
     */
    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    /**
     * 다음 데이터 존재 여부
     * @return
     */
    public boolean hasNext() {
        return hasNext;
    }

    /**
     * 다음 데이터 존재 여부 설정
     * @param hasNext
     */
    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
