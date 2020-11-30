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

package com.seomse.stock.analysis.store;

/**
 * 매매동향
 * @author macle
 */
public interface TradingTrend {

    /**
     * 기관 매매동향
     * @return 기관매매동향 정보가 없으면 null;
     */
    Double getInstitution();

    /**
     * 외국인 맴동향
     * @return 외국인 매매동향 정보가 없으면 null
     */
    Double getForeign();

    /**
     * 개인 매매동향
     * @return 개인매매동향 종보가 없으면 null
     */
    Double getIndividual();

}
