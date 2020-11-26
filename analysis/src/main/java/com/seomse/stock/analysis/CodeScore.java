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

package com.seomse.stock.analysis;

/**
 * 코드와 점수
 * @author macle
 */
public class CodeScore {

    // 재무 점수가 좋을때
    public static final double MAX_SCORE = 100.0;
    //재무 점수 측정이 불가능할때
    public static final double DEFAULT_SCORE = 30.0;
    //재무 점수가 나쁠대
    public static final double MIN_SCORE = 0.0;


    String code;
    double score;

    /**
     * 생성자
     * @param code 종목코드, 시장코드, 지수코드
     * @param score 점수
     */
    public CodeScore(String code, double score){
        this.code = code;
        this.score = score;
    }

    /**
     * 코드 얻기
     * @return 종목코드, 시장코드, 지수코드
     */
    public String getCode() {
        return code;
    }

    /**
     * 점수얻기
     * @return 퀀트점수, 분석 점수
     */
    public double getScore() {
        return score;
    }
}
