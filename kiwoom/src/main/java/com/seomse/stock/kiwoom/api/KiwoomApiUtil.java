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

public class KiwoomApiUtil {

    private static final String PARAM_CODE_SEPARATOR = ",";
    private static final String PARAM_DATA_SEPARATOR = "|";

    /**
     * 데이터 파라미터 문자열을 만든다
     * @param params
     * @return
     */
    public static String makeDataParam(String ... params){
        return makeParam(PARAM_DATA_SEPARATOR, params);
    }

    /**
     * 코드 파라미터 문자열을 만든다.
     * @param params
     * @return
     */
    public static String makeCodeParam(String ... params){
        return makeParam(PARAM_CODE_SEPARATOR, params);
    }

    /**
     * 파라미터 문자열을 만든다.
     * @param params
     * @return
     */
    public static String makeParam(String paramDataSeparator, String[] params) {
        StringBuilder result = new StringBuilder();
        for (String param : params) {
            result.append(param).append(paramDataSeparator);
        }
        if(result.length() > 0){
            result.setLength(result.length()-1);
        }
        return result.toString();
    }

}
