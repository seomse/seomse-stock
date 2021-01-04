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

package com.seomse.stock.data.sync;

import com.seomse.commons.utils.time.Times;
import com.seomse.commons.utils.time.YmdUtil;
import com.seomse.jdbc.JdbcQuery;
import com.seomse.jdbc.admin.RowDataInOut;
import com.seomse.jdbc.annotation.Table;
import com.seomse.jdbc.connection.ConnectionFactory;
import com.seomse.jdbc.naming.JdbcNaming;
import com.seomse.stock.data.sync.tables.daily.*;
import com.seomse.stock.data.sync.tables.minute.Etf5mNo;
import com.seomse.stock.data.sync.tables.minute.Item5mNo;
import com.seomse.stock.data.sync.tables.minute.Market5mNo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * 분석을 위해 중앙 DB 와 데이터 동기화
 * 용량이 너무커서 통신속도를 줄이기 위한
 * local 처리를 위한 용도
 *
 * DB는 혀용된 아이피에서만 접근가능하므로 데이터 사용을 원할경우 README 에 있는 정보를 이용하여 연락 바람람 
 * 데이터만 복사함 스키마는 생성되어 있어야 함
 * @author macle
 */
public class CenterDatabaseSync {
    
    private static final Logger logger = LoggerFactory.getLogger(CenterDatabaseSync.class);

    public static String [] INFO_TABLES = """
            T_STOCK_ETF
            T_STOCK_FSMT
            T_STOCK_ITEM
            T_STOCK_ITEM_HOLDER
            T_STOCK_ITEM_PREFERRED
            T_STOCK_MARKET
            T_STOCK_MARKET_INDEX
            """.split("\n");


    public static String [] DAILY_TABLES = """
            T_STOCK_ETF_DAILY
            T_STOCK_MARKET_DAILY
            T_STOCK_MARKET_INDEX_DAILY
            T_STOCK_WICS_DAILY
            T_STOCK_ITEM_DAILY
            """.split("\n");


    public static String [] MINUTE_TABLES = """
            T_STOCK_ETF_5M
            T_STOCK_MARKET_5M
            T_STOCK_ITEM_5M
            """.split("\n");


    private final String centerDatabaseType;
    private final String centerUrl;
    private final String centerId;
    private final String centerPassword;

    private final String syncDatabaseType;
    private final String syncUrl;
    private final String syncId;
    private final String syncPassword;


    /**
     * 생성자
     */
    public CenterDatabaseSync(){
        try(InputStream inputStream  = new FileInputStream("config/database_center_sync.xml")){

            Properties props  = new Properties();
            props.loadFromXML(inputStream);

            centerDatabaseType = props.getProperty("center.jdbc.type");
            centerUrl = props.getProperty("center.jdbc.url");
            centerId = props.getProperty("center.jdbc.user.id");
            centerPassword = props.getProperty("center.jdbc.user.password");

            syncDatabaseType = props.getProperty("sync.jdbc.type");
            syncUrl = props.getProperty("sync.jdbc.url");
            syncId = props.getProperty("sync.jdbc.user.id");
            syncPassword = props.getProperty("sync.jdbc.user.password");


        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 전체 데이터 테이블 동기화
     */
    public void sync(){

        try(
                Connection selectConn = ConnectionFactory.newConnection(centerDatabaseType, centerUrl, centerId, centerPassword);
                Connection insertConn = ConnectionFactory.newConnection(syncDatabaseType, syncUrl, syncId, syncPassword)
        ){
            RowDataInOut dataInOut = new RowDataInOut();
            dataInOut.tableSync(selectConn, insertConn, INFO_TABLES);
            logger.info("info tables sync complete");

            dataInOut.tableSync(selectConn, insertConn, DAILY_TABLES);
            logger.info("daily tables sync complete");

            dataInOut.tableSync(selectConn, insertConn, MINUTE_TABLES);
            logger.info("5 minute tables sync complete");

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 지정한 테이블 동기화
     * @param tableNames String [] 테이블 목록
     */
    public void sync(String [] tableNames){
        try(
                Connection selectConn = ConnectionFactory.newConnection(centerDatabaseType, centerUrl, centerId, centerPassword);
                Connection insertConn = ConnectionFactory.newConnection(syncDatabaseType, syncUrl, syncId, syncPassword)
        ) {
            RowDataInOut dataInOut = new RowDataInOut();
            dataInOut.tableSync(selectConn, insertConn, tableNames);
            logger.info("tables sync complete");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 변경분만 업데이트
     * 일을 기준으로 데이터를 가져와서 업데이트
     * (제공 여정)
     */
    public void update(String ymd){

        try(
                Connection selectConn = ConnectionFactory.newConnection(centerDatabaseType, centerUrl, centerId, centerPassword);
                Connection insertConn = ConnectionFactory.newConnection(syncDatabaseType, syncUrl, syncId, syncPassword)
        ){
            RowDataInOut dataInOut = new RowDataInOut();
            dataInOut.tableSync(selectConn, insertConn, INFO_TABLES);
            logger.info("info tables sync complete");


            System.out.println(ymd);

            //일봉
            update(insertConn, JdbcNaming.getObjList(selectConn, EtfDailyNo.class, "YMD >= '" + ymd + "'"));
            update(insertConn, JdbcNaming.getObjList(selectConn, ItemDailyNo.class, "YMD >= '" + ymd + "'"));
            update(insertConn, JdbcNaming.getObjList(selectConn, MarketDailyNo.class, "YMD >= '" + ymd + "'"));
            update(insertConn, JdbcNaming.getObjList(selectConn, MarketIndexDailyNo.class, "YMD >= '" + ymd + "'"));
            update(insertConn, JdbcNaming.getObjList(selectConn, WicsDailyNo.class, "YMD >= '" + ymd + "'"));
            logger.info("daily tables update complete");

            //분봉 //느리면 delete insert로 변경예정
            String ymdhm = ymd +"0000";
            JdbcQuery.execute(insertConn, "DELETE FROM T_STOCK_MARKET_5M WHERE YMDHM >= '" + ymdhm + "'");
            JdbcNaming.insert(insertConn, JdbcNaming.getObjList(selectConn, Market5mNo.class, "YMDHM >= '" + ymdhm + "'"),true);
            logger.info("market 5m complete");

            JdbcQuery.execute(insertConn, "DELETE FROM T_STOCK_ETF_5M WHERE YMDHM >= '" + ymdhm + "'");
            JdbcNaming.insert(insertConn, JdbcNaming.getObjList(selectConn, Etf5mNo.class, "YMDHM >= '" + ymdhm + "'"),true);
            logger.info("etf 5m complete");

            JdbcQuery.execute(insertConn, "DELETE FROM T_STOCK_ITEM_5M WHERE YMDHM >= '" + ymdhm + "'");
            JdbcNaming.insert(insertConn, JdbcNaming.getObjList(selectConn, Item5mNo.class, "YMDHM >= '" + ymdhm + "'"),true);
            logger.info("5 minute tables update complete");

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * update
     * @param list database naming object list
     */
    public static void update(Connection insertConn, @SuppressWarnings("rawtypes") List list){

        if(list.size() == 0){
            return;
        }

        logger.info(list.get(0).getClass().getAnnotation(Table.class).name());

        for (Object o : list) {
            JdbcNaming.insertOrUpdate(insertConn, o, true);
        }
        list.clear();
    }
    
    public static void main(String[] args) throws InterruptedException {
        //오후 6시에 실행 시켜서 슬립시킴
//        Thread.sleep(Times.HOUR_1*8L);

//        for(;;) {
//
//            long startTime = System.currentTimeMillis();
//
//            String now = YmdUtil.now();
//
//            CenterDatabaseSync centerDatabaseSync = new CenterDatabaseSync();
//            centerDatabaseSync.update(YmdUtil.getYmd(now, -5));
//            long useTime = System.currentTimeMillis() - startTime;
//
//            logger.info(now + " update complete");
//            if(useTime < Times.DAY_1) {
//                Thread.sleep(Times.DAY_1 - useTime);
//            }
//
//        }


        CenterDatabaseSync centerDatabaseSync = new CenterDatabaseSync();
        centerDatabaseSync.sync(INFO_TABLES);
        centerDatabaseSync.sync(DAILY_TABLES);
        centerDatabaseSync.sync(MINUTE_TABLES);

    }
}
