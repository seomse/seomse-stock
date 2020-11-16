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

package seomse.stock.data.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 분석을 위해 중앙 DB 와 데이터 동기화
 * 용량이 너무커서 통신속도를 줄이기 위한
 * local 처리를 위한 용도
 *
 * DB는 혀용된 아이피에서만 접근가능하므로 데이터 사용을 원할경우 README 에 있는 정보를 이용하여 연락 바람람 *
 * @author macle
 */
public class CenterDatabaseSync {

    private static final Logger logger = LoggerFactory.getLogger(CenterDatabaseSync.class);

    private String url;
    private String databaseType;
    private String id;
    private String password;

    /**
     * 생성자
     */
    public CenterDatabaseSync(){
        try{



        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 전체 데이터 싱크
     */
    public void sync(){
        
    }

    /**
     * 변경분만 업데이트
     * (제공 여정)
     */
    public void update(){
        
    }
}
