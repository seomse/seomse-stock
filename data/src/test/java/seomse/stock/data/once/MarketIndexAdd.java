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

package seomse.stock.data.once;

import com.seomse.commons.utils.FileUtil;
import com.seomse.jdbc.naming.JdbcNaming;

import java.io.File;
import java.util.List;

/**
 * market index 정보 추가
 * @author macle
 */
public class MarketIndexAdd {


    /**
     * 파일을 이용한 market index 정보추가
     * 날짜 역 순서로 정렬되어 있을때 (최신날짜가 최상위일떄)
     * @param filePath String file path
     * @param code String index code
     */
    public void addDesc(String filePath, String code){


        List<String> dataList = FileUtil.getFileContentsList(new File(filePath), "euc-kr");

        String first = dataList.get(dataList.size()-1);

        MarketIndexNo indexNo = new MarketIndexNo();
        indexNo.INDEX_CD = code;

        double previous = Double.parseDouble(first.split("\t")[1]);

        for (int i = dataList.size() -2; i > -1; i--) {

            String [] array = dataList.get(i).split("\t");
            indexNo.YMD = array[0].trim().replace("-","");
            double close = Double.parseDouble(array[1].trim());
            indexNo.CLOSE_PRC = close;
            indexNo.PREVIOUS_PRC = previous;
            indexNo.CHANGE_PRC = close - previous;

            if(previous == 0){
                indexNo.CHANGE_RT = 0.0;
            }else{
                indexNo.CHANGE_RT = indexNo.CHANGE_PRC/previous * 100.0;
            }
            indexNo.CHANGE_RT = Math.floor(indexNo.CHANGE_RT * 100.0)/100.0;
            JdbcNaming.insertOrUpdate(indexNo, false);

            previous = close;
        }
        dataList.clear();
    }


    public static void main(String[] args) {
        MarketIndexAdd fundsExcelDataAdd = new MarketIndexAdd();
        fundsExcelDataAdd.addDesc("doc/data/market_funds.txt", "FUND_DEPOSIT");
    }

}
