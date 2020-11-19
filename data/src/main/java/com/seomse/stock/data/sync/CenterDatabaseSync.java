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

import com.seomse.jdbc.admin.RowDataInOut;
import com.seomse.jdbc.annotation.Table;
import com.seomse.jdbc.connection.ConnectionFactory;
import com.seomse.jdbc.naming.JdbcNaming;
import com.seomse.stock.data.sync.tables.daily.*;
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
 * DB는 혀용된 아이피에서만 접근가능하므로 데이터 사용을 원할경우 README 에 있는 정보를 이용하여 연락 바람람 *
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
            logger.info("info table sync complete");

            dataInOut.tableSync(selectConn, insertConn, DAILY_TABLES);
            logger.info("daily table sync complete");

            dataInOut.tableSync(selectConn, insertConn, MINUTE_TABLES);
            logger.info("5 minute table sync complete");

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
            logger.info("table sync complete");
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
            logger.info("info table sync complete");


            //일봉
            update(insertConn, JdbcNaming.getObjList(selectConn, EtfDailyNo.class, "YMD >= '" + ymd + "'"));
            update(insertConn, JdbcNaming.getObjList(selectConn, ItemDailyNo.class, "YMD >= '" + ymd + "'"));
            update(insertConn, JdbcNaming.getObjList(selectConn, MarketDailyNo.class, "YMD >= '" + ymd + "'"));
            update(insertConn, JdbcNaming.getObjList(selectConn, MarketIndexDailyNo.class, "YMD >= '" + ymd + "'"));
            update(insertConn, JdbcNaming.getObjList(selectConn, WicsDailyNo.class, "YMD >= '" + ymd + "'"));
            logger.info("daily update complete");
            //분봉

//            dataInOut.tableSync(selectConn, insertConn, DAILY_TABLES, "");
//            logger.info("daily table update complete");
//
//            dataInOut.tableSync(selectConn, insertConn, MINUTE_TABLES);
//            logger.info("5 minute table update complete");

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



    public static void main(String[] args) {

        CenterDatabaseSync centerDatabaseSync = new CenterDatabaseSync();
        centerDatabaseSync.sync();
//        centerDatabaseSync.update("20201118");

//        try(InputStream inputStream  = new FileInputStream("config/database_center_sync.xml")){
//
//            Properties props  = new Properties();
//            props.loadFromXML(inputStream);
//
//            String centerDatabaseType = props.getProperty("center.jdbc.type");
//            String centerUrl = props.getProperty("center.jdbc.url");
//            String centerId = props.getProperty("center.jdbc.user.id");
//            String centerPassword = props.getProperty("center.jdbc.user.password");
//
//            String syncDatabaseType = props.getProperty("sync.jdbc.type");
//            String syncUrl = props.getProperty("sync.jdbc.url");
//            String syncId = props.getProperty("sync.jdbc.user.id");
//            String syncPassword = props.getProperty("sync.jdbc.user.password");
//            try(
//                    Connection selectConn = ConnectionFactory.newConnection(centerDatabaseType, centerUrl, centerId, centerPassword);
//                    Connection insertConn = ConnectionFactory.newConnection(syncDatabaseType, syncUrl, syncId, syncPassword)
//            ) {
//
//
//                RowDataInOut dataInOut = new RowDataInOut();
//                dataInOut.tableCopy(selectConn, insertConn, "T_STOCK_ITEM_DAILY");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }


    }
}
