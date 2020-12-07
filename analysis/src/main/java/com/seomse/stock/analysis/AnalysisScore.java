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

import java.util.Comparator;

/**
 * 분석 점수
 * @author macle
 */
public interface AnalysisScore {
    // 점수 최대치
    double MAX_SCORE = 100.0;
    // 기본 점수
    double DEFAULT_SCORE = 30.0;
    // 점수 최하치
    double MIN_SCORE = 0.0;

    //분석 점수 역순 정렬
    Comparator<AnalysisScore> SORT_DESC =(s1, s2) -> Double.compare(s2.getScore(), s1.getScore());

    /**
     * 분석 점수얻기
     * @return 분석점수
     */
    double getScore();
}
