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

package com.seomse.stock.fundamental.analysis;

/**
 * 퀀트에서 사용하는 ymd 관련 유틸성 메소드 년월일
 * @author macle
 */
public class QuantYmd {

    /**
     * 년원일을 입력했을때 제무가 등록되어있다고 생각되는 년월을 얻기
     * 시뮬레이터 용
     * @param ym String yyyyMMdd
     * @return String yyyyMMdd
     */
    public static String getMinYm(String ym){

        String yy = ym.substring(0,4);

        String mm  = ym.substring(ym.length()-2);

        int month = Integer.parseInt(mm);

        if(month ==12 || month ==1 || month==2){
            if(month ==12){
                return yy+"09";
            }else{
                int year = Integer.parseInt(yy);
                year = year-1;
                return year + "09";
            }

        }else if(month>=9 && month<12){
            return yy+"06";
        }else if(month>=6 && month<9){
            return yy+"03";
        }
//		else if(month>=3 && month<6){
        else{
            int year = Integer.parseInt(yy);
            year = year-1;
            return year + "12";
        }
    }


}
