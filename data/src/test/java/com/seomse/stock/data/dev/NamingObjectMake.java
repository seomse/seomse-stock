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

package com.seomse.stock.data.dev;

import com.seomse.jdbc.naming.JdbcNaming;

/**
 * jdbc 네이밍 객체 생성기
 * @author macle
 */
public class NamingObjectMake {

	public static void main(String [] args){
		String tableName = "T_STOCK_ITEM_DAILY";
		System.out.println("@Table(name=\"" +  tableName+ "\")\n");
		System.out.println(JdbcNaming.makeObjectValue(tableName));

	}
	
}
