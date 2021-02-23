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
package com.seomse.stock.kiwoom.config;

import com.seomse.commons.utils.FileUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class KiwoomConfig {

    /**
     * "api_receive_port":33333,
     *     "api_send_port":33334,
     *     "process_name":"KiwoomApi.exe",
     *     "process_file_path":"C:\workspace\git\seomse-kiwoom-api\KiwoomApi\bin\x86\Debug\KiwoomApi.exe",
     *     "version_up_file_path":"C:\OpenAPI\OPVersionUp.exe",
     *     "telegram_token":"1405493802:AAGNODKnzSyRHiN72IzaUx0GYP4UrLxmemo",
     *     "telegram_chatid":"1417951085",
     *     "telegram_alert_use":"true",
     *     "account_number":""
     */

    public static final String API_RECEIVE_PORT="api_receive_port";
    public static final String API_SEND_PORT="api_send_port";
    public static final String PROCESS_NAME="process_name";
    public static final String PROCESS_FILE_PATH="process_file_path";
    public static final String VERSION_UP_FILE_PATH="version_up_file_path";
    public static final String TELEGRAM_TOKEN="telegram_token";
    public static final String TELEGRAM_CHAT_ID="telegram_chatid";
    public static final String TELEGRAM_ALERT_USE="telegram_alert_use";
    public static final String ACCOUNT_NUMBER="account_number";


    static JSONObject jsonObject;
    static String configPath = "config/kiwoom_config";
    static {
        File file = new File(configPath);
        if(file.exists()){
            String fileContents = FileUtil.getFileContents(file, "UTF-8");
            fileContents = fileContents.replace("\\\\","\\");
            fileContents = fileContents.replace("\\","\\\\");
            jsonObject = new JSONObject(fileContents);
        }
    }

    /**
     * 설정 경로 지정
     * @param newConfigPath
     */
    public static void setConfigPath(String newConfigPath) {
        configPath = newConfigPath;
        String fileContents = FileUtil.getFileContents(new File(configPath), "UTF-8");
        fileContents = fileContents.replace("\\\\","\\");
        fileContents = fileContents.replace("\\","\\\\");
        jsonObject = new JSONObject(fileContents);
    }

    /**
     * 설정 리로드
     */
    public static void reLoad(){
        String fileContents = FileUtil.getFileContents(new File(configPath), "UTF-8");
        fileContents = fileContents.replace("\\\\","\\");
        fileContents = fileContents.replace("\\","\\\\");
        jsonObject = new JSONObject(fileContents);
    }

    /**
     * 설정 정보를 문자열로 받아 온다
     * @param configKey
     * @return
     */
    public static String getConfig(String configKey){
        String result=null;
        try{
            result = jsonObject.getString(configKey);
        } catch(JSONException e){
            return null;
        }
        return result;
    }

    /**
     * 설정 정보를 Integer 값으로 가져온다
     * @param configKey
     * @return
     */
    public static Integer getConfigInt(String configKey){
        Integer result = null;
        try{
            result = jsonObject.getInt(configKey);
        } catch(JSONException e){
            return null;
        }
        return result;
    }
}
