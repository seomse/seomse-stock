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


package com.seomse.stock.kiwoom.process;

import com.seomse.commons.utils.ExceptionUtil;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.slf4j.LoggerFactory.getLogger;

public class ProcessRunner {

    private static final Logger logger = getLogger(ProcessRunner.class);

    /**
     * 프로세스 실행시 OS 차이에 따른 명령어 실행 오류를 방지한다.
     * 다른 OS 라도 같은 명령 실행이 가능하면 한 OS 로 취급한다.
     * @author hyh
     *
     */
    private enum ProcessOSType {
        WINDOWS , UNIX
    }

    /**
     * OS 정보 객체
     */
    private static class OSInfo {
        public static ProcessOSType OS_TYPE;
        static {
            String os = System.getProperty("os.name").toLowerCase();
            if(os.contains("window")) {
                OS_TYPE = ProcessOSType.WINDOWS;
            } else {
                OS_TYPE = ProcessOSType.UNIX;
            }
        }
    }
    /**
     * 프로세스를 실행한다.
     * @param command 실행 명령어
     * @param sync 동기화 여부 설정, 참일 경우 프로세스가 종료될때까지 기다린다.
     * @return
     */
    public static boolean runProcess(String command , boolean sync){
        String [] commandList;
        if(OSInfo.OS_TYPE.equals(ProcessOSType.WINDOWS)){
            commandList = new String[]{"cmd","/c",command};
        } else if(OSInfo.OS_TYPE.equals(ProcessOSType.UNIX)){
            commandList = new String[]{"/bin/sh","-c",command};
        } else {
            commandList = new String[]{"/bin/sh","-c",command};
        }
        boolean result = false;

        try{
            ProcessBuilder builder = new ProcessBuilder(commandList);
            builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            builder.redirectError(ProcessBuilder.Redirect.INHERIT);
            if(sync){
                Process ps = builder.start();
                ps.waitFor();
                ps.destroy();
            } else {
                builder.start();
            }
            result = true;
        }catch(Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }
        return result;
    }

    /**
     * 프로세스를 실행한다.
     * @param command 실행 명령어
     * @return
     */
    public static String runProcessAndReceive(String command){
        String [] commandList;

        InputStream is = null , es = null;
        InputStreamReader isr = null , esr = null;
        BufferedReader bfr = null;

        StringBuilder result = new StringBuilder();;

        if(OSInfo.OS_TYPE.equals(ProcessOSType.WINDOWS)){
            commandList = new String[]{"cmd","/c",command};
        } else if(OSInfo.OS_TYPE.equals(ProcessOSType.UNIX)){
            commandList = new String[]{"/bin/sh","-c",command};
        } else {
            commandList = new String[]{"/bin/sh","-c",command};
        }

        try{
            Process ps = Runtime.getRuntime().exec(commandList);

            ps.waitFor();
            is =  ps.getInputStream();
            isr = new InputStreamReader(is,"EUC-KR");
            bfr = new BufferedReader(isr);
            String line;
            while((line = bfr.readLine()) != null ){
                result.append(line).append("\n");
            }

            es =  ps.getErrorStream();
            esr = new InputStreamReader(es,"EUC-KR");
            bfr = new BufferedReader(esr);

            while((line = bfr.readLine()) != null ){
                result.append(line).append("\n");
            }
        } catch(Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        } finally {
            try { if(bfr != null) bfr.close(); } catch (IOException e) {}
            try { if(is != null) is.close(); } catch (IOException e) {}
            try { if(es != null) es.close(); } catch (IOException e) {}
            try { if(isr != null) isr.close(); } catch (IOException e) {}
            try { if(esr != null) esr.close(); } catch (IOException e) {}
        }
        return result.toString();
    }
}
