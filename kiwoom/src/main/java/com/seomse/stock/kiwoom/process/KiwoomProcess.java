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
import com.seomse.commons.utils.time.DateUtil;
import com.seomse.stock.kiwoom.api.KiwoomApiSender;
import com.seomse.stock.kiwoom.config.KiwoomConfig;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Scanner;

import static org.slf4j.LoggerFactory.getLogger;

public class KiwoomProcess {

    private static final Logger logger = getLogger(KiwoomProcess.class);

    private static String apiProcessName=null;
    private static String apiProcessPath=null;
    private static String apiVersionUpPath=null;

    private static final String TASK_PROCESS = "tasklist.exe";
    private static final String KILL = "taskkill /F /IM ";
    private static final String VERSION_PROCESS = "opversionup.exe";

    /**
     * CONFIG 설정
     */
    static {
        apiProcessName = KiwoomConfig.getConfig(KiwoomConfig.PROCESS_NAME);
        apiProcessPath = KiwoomConfig.getConfig(KiwoomConfig.PROCESS_FILE_PATH);
        apiVersionUpPath = KiwoomConfig.getConfig(KiwoomConfig.VERSION_UP_FILE_PATH);
    }

    /**
     * 키움 API를 실행한다.
     * 이미 실행시, 종료후 실행 한다.
     */
    public static void rerunKiwoomApi(){
        KiwoomApiSender.getInstance().getApiLock().lock();
        logger.info("checkProcess..");
        new Thread(() -> {
            try {
                final Process process = new ProcessBuilder(TASK_PROCESS, "/fo", "csv", "/nh").start();

                Scanner sc = new Scanner(process.getInputStream());
                if (sc.hasNextLine()) sc.nextLine();
                boolean doKillProcess = false;
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] parts = line.split(",");
                    String unq = parts[0].substring(1).replaceFirst(".$", "");
                    if(unq.toLowerCase().equals(apiProcessName.toLowerCase() )){
                        try {
                            killProcess(unq);
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {
                                logger.error(ExceptionUtil.getStackTrace(e));
                            }
                            doKillProcess = true;
                        } catch (Exception e) {
                            logger.error(ExceptionUtil.getStackTrace(e));
                        }
                    }
                }
                if(doKillProcess) {
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        logger.error(ExceptionUtil.getStackTrace(e));
                    }
                }
                logger.info("runProcess");
                ProcessRunner.runProcess(apiProcessPath,true);
            }catch (IOException e) {}
        }).start();
        try {
            Thread.sleep(60000L);
        } catch (InterruptedException e) {
            logger.error(ExceptionUtil.getStackTrace(e));
        }
        KiwoomApiSender.getInstance().getApiLock().unlock();
    }

    /**
     * 키움 API 를 종료 한다.
     */
    public static void killKiwoomApi(){
        new Thread(() -> {
            try {
                final Process process = new ProcessBuilder(TASK_PROCESS, "/fo", "csv", "/nh").start();

                Scanner sc = new Scanner(process.getInputStream());
                if (sc.hasNextLine()) sc.nextLine();
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] parts = line.split(",");
                    String unq = parts[0].substring(1).replaceFirst(".$", "");
                    if(unq.equalsIgnoreCase(apiProcessName)){
                        try {
                            killProcess(unq);
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {
                                logger.error(ExceptionUtil.getStackTrace(e));
                            }
                        } catch (Exception e) {
                            logger.error(ExceptionUtil.getStackTrace(e));
                        }
                    }
                }
            }catch (IOException e) {}
        }).start();
    }

    /**
     * 키움 API의 버전업을 실행 한다.
     */
    public static void startVersionUp(){

        KiwoomApiSender.getInstance().getApiLock().lock();

        String nowYmd = DateUtil.getDateYmd(System.currentTimeMillis(),"yyyy-MM-dd");
        logger.info("startVersionUp process.. ["+nowYmd+"] ");
        try {Thread.sleep(1000l * 60l);} catch (InterruptedException e) {}
        try {
            final Process process = new ProcessBuilder(TASK_PROCESS, "/fo", "csv", "/nh").start();
            new Thread(() -> {
                Scanner sc = new Scanner(process.getInputStream());
                if (sc.hasNextLine()) sc.nextLine();
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] parts = line.split(",");
                    String unq = parts[0].substring(1).replaceFirst(".$", "");
                    if(unq.equals(VERSION_PROCESS)){
                        try {
                            killProcess(unq);
                        } catch (Exception e) {
                            logger.error(ExceptionUtil.getStackTrace(e));
                        }
                    }
                }
            }).start();
            process.waitFor();
        }
        catch (IOException | InterruptedException e) {
            logger.error(ExceptionUtil.getStackTrace(e));
        }
        ProcessRunner.runProcess(apiVersionUpPath,true);
        try {
            Thread.sleep(60000L);
        } catch (InterruptedException e) {
            logger.error(ExceptionUtil.getStackTrace(e));
        }
        KiwoomApiSender.getInstance().getApiLock().unlock();
    }

    /**
     * 프로세스를 종료 한다
     * @param serviceName 서비스명
     * @throws Exception
     */
    public static void killProcess(String serviceName) throws Exception {

        try {
            Runtime.getRuntime().exec(KILL + serviceName);
            logger.info(serviceName+" killed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String [] args){
        startVersionUp();
    }
}
