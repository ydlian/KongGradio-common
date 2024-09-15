/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: SecDictionary.java
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

package org.konggradio.core.constant;

import java.util.HashSet;
import java.util.Set;

public class SecDictionary {
    private static final Set<String> PASS_TABLE = new HashSet<>();
    private static final Set<String> WHITE_LIST_PASS_TABLE = new HashSet<>();
    static {
        PASS_TABLE.add("e285e3be0060c1d2283603fdf567ebb9");
        PASS_TABLE.add("f857effe4d02c761d825ee1edf932861");
        PASS_TABLE.add("f9d960ca2d3187d29617f279a7480da9");
        PASS_TABLE.add("cb414bb58ac5b7ae58e4ea231535eeee");
    }

    static {
        WHITE_LIST_PASS_TABLE.add("b161388bb62ae0f6c69bbb1006a25bab");
        WHITE_LIST_PASS_TABLE.add("b8bf62f005e76a072e59a432bcb71d13");
        WHITE_LIST_PASS_TABLE.add("833c9945b2b2c18a8806bd8b2638bdfb");
        WHITE_LIST_PASS_TABLE.add("bff8b2be0fb6f58deb8adb30747b8f41");
    }

    public static Set<String> getPassTable(){
        return PASS_TABLE;
    }
    public static Set<String> getWhiteListPassTable(){
        return WHITE_LIST_PASS_TABLE;
    }
}
