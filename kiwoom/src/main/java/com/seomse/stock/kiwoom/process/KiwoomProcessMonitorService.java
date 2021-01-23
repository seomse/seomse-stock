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

import com.seomse.commons.service.Service;
import com.seomse.commons.utils.time.DateUtil;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class KiwoomProcessMonitorService extends Service {
    private static final Logger logger = getLogger(KiwoomProcessMonitorService.class);
    private String lastExecuteDate = "19700101";

    /**
     * 키움 프로세스를 모니터링 하고 있다가
     * 오전 8시에 종료후 재 실행
     */
    @Override
    public void work() {


        String nowYmd = DateUtil.getDateYmd(System.currentTimeMillis(),"yyyyMMdd");
        String hh = DateUtil.getDateYmd(System.currentTimeMillis(),"HH");
        int mm = Integer.parseInt(DateUtil.getDateYmd(System.currentTimeMillis(),"mm"));
        if(hh.equals("8") && !nowYmd.equals(lastExecuteDate)){
            if(mm <= 10){
                return;
            } else {
                KiwoomProcess.startVersionUp();
                lastExecuteDate = nowYmd;
            }
        } else {
            return;
        }
    }
}
