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

package com.seomse.stock.analysis.model.parallel;

import com.seomse.commons.utils.ExceptionUtil;
import com.seomse.stock.analysis.store.etf.Etf;
import com.seomse.stock.analysis.store.item.Item;
import com.seomse.stock.analysis.store.preferred.Preferred;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 병렬 분석자
 * @author macle
 */
public class ParallelAnalyst implements Runnable{

    private final Logger logger = LoggerFactory.getLogger(ParallelAnalyst.class);

    private final ParallelAnalysis parallelAnalysis;
    private final StockAnalyst stockAnalyst;

    /**
     * 생성자
     * @param parallelAnalysis 뱡랼분석
     */
    public ParallelAnalyst(ParallelAnalysis parallelAnalysis){
        this.parallelAnalysis = parallelAnalysis;
        this.stockAnalyst = parallelAnalysis.stockAnalyst;

    }

    @Override
    public void run() {
        
        try{
            int index = 0;
            for(;;){
                if(index ==0){
                    Item item = parallelAnalysis.getItem();
                    if(item == null){
                        index ++;
                        continue;
                    }
                    stockAnalyst.analysisItem(parallelAnalysis.ymd, item, parallelAnalysis.storeManager);
                }

                if(index == 1){
                    Preferred preferred = parallelAnalysis.getPreferred();
                    if(preferred == null){
                        index ++;
                        continue;
                    }
                    stockAnalyst.analysisPreferred(parallelAnalysis.ymd, preferred, parallelAnalysis.storeManager);
                }

                if(index == 2){
                    Etf etf = parallelAnalysis.getEtf();
                    if(etf == null){
                        break;
                    }
                    stockAnalyst.analysisEtf(parallelAnalysis.ymd, etf, parallelAnalysis.storeManager);
                }
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }
        parallelAnalysis.complete();
    }
}