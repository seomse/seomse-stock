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

package seomse.stock.analysis.fundamental;

import seomse.stock.analysis.store.ItemStore;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 종목 코드와 점수
 * 
 * @author macle
 */
public abstract class QuantScore {
    
    // 재무 점수가 좋을때
    public static final double MAX_SCORE = 100.0;
    //재무 점수 측정이 불가능할때
    public static final double DEFAULT_SCORE = 30.0;
    //재무 점수가 나쁠대
    public static final double MIN_SCORE = 0.0;

    
    protected String code;

    /**
     * score 기준일
     */
    protected String ymd = new SimpleDateFormat("yyyyMMdd").format(new Date());

    protected Double score;

    protected ItemStore itemStore;

    /**
     * 생성자
     * @param code String (종목 코드)
     * @param itemStore ItemStore (종목 정보 저장소)
     */
    public QuantScore(String code, ItemStore itemStore){
        this.code = code;
        this.itemStore = itemStore;
    }

    /**
     * 종목코드(item code) 얻기
     * @return String
     */
    public String getCode(){
        return code;
    }

    /**
     * ymd 설정
     * @param ymd String
     */
    public void setYmd(String ymd) {
        this.ymd = ymd;
    }


    /**
     * 기본적 분석 quant score 계산
     * @return double score
     */
    protected abstract double analysis();

    private final Object analysisLock = new Object();

    /**
     * socre get
     * @return double
     */
    public double getScore(){
        if(score != null){

            return score;
        }

        synchronized (analysisLock) {
            score = analysis();
        }

        return score;
    }

}
