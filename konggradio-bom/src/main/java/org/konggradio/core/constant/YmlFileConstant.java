/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: YmlFileConstant.java
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

public class YmlFileConstant {
    static final String SPRING_APP_PREFIX = "spring.application";
    static final String DOT = ".";

    public static String getGatewayDomain(){
        StringBuffer name = new StringBuffer(SPRING_APP_PREFIX);
        return name.append(DOT).append("gateway").append(DOT).append("domain").toString();
    }

    public static String getGatewayActiveMode(){
        StringBuffer name = new StringBuffer(SPRING_APP_PREFIX);
        return name.append(DOT).append("gateway").append(DOT).append("active").toString();
    }

    public static String getGatewayActiveType(){
        StringBuffer name = new StringBuffer(SPRING_APP_PREFIX);
        return name.append(DOT).append("gateway").append(DOT).append("type").toString();
    }

    public static String getApplicationName(){
        StringBuffer name = new StringBuffer(SPRING_APP_PREFIX);
        return name.append(DOT).append("name").toString();
    }
}
