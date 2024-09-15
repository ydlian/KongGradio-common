/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: Log.java
 *  <p>
 *  Licensed under the Apache License Version 2.0;
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package org.konggradio.core.encryption.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Log {
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void debug(Object msg) {

        String log = datetimeFormat.format(new Date()) + " [DEBUG] " + msg;

        System.out.println(log);

    }


    public static void println(String obj) {
        System.out.println(obj);
    }


    public static void print(String obj) {
        System.out.print(obj);
    }

    public static void println() {
        System.out.println();
    }
}
