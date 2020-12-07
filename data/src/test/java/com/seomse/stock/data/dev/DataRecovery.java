/*
 * Copyright (C) 2020 Wigo Inc.
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

package com.seomse.stock.data.dev;

import com.seomse.jdbc.connection.ConnectionFactory;
import com.seomse.jdbc.naming.JdbcNaming;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author macle
 */
public class DataRecovery {

    public static void main(String[] args) {
        try(InputStream inputStream  = new FileInputStream("config/database_center_sync.xml")){

            Properties props  = new Properties();
            props.loadFromXML(inputStream);

            String centerDatabaseType = props.getProperty("center.jdbc.type");
            String centerUrl = props.getProperty("center.jdbc.url");
            String centerId = props.getProperty("center.jdbc.user.id");
            String centerPassword = props.getProperty("center.jdbc.user.password");

            String syncDatabaseType = props.getProperty("sync.jdbc.type");
            String syncUrl = props.getProperty("sync.jdbc.url");
            String syncId = props.getProperty("sync.jdbc.user.id");
            String syncPassword = props.getProperty("sync.jdbc.user.password");



            try(
                    Connection  insertConn = ConnectionFactory.newConnection(centerDatabaseType, centerUrl, centerId, centerPassword);
                    Connection selectConn = ConnectionFactory.newConnection(syncDatabaseType, syncUrl, syncId, syncPassword)
            ){
                JdbcNaming.insert(insertConn, JdbcNaming.getObjList(selectConn, FsmtNo.class, "ESTIMATE_FG='N'"),true);

            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }
}
