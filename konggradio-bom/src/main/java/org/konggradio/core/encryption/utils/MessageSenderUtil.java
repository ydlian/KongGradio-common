/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: MessageSenderUtil.java
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

import java.util.HashSet;
import java.util.Set;

public class MessageSenderUtil {
    private static final Set<String> DEFAULT_NOTIFY_LIST = new HashSet<>();
    private static final Set<String> DEFAULT_SMS_LIST = new HashSet<>();
    
    public static Set<String> getDefaultNotifyList(){
        return DEFAULT_NOTIFY_LIST;
    }
    public static Set<String> getDefaultSmsList(){
        return DEFAULT_SMS_LIST;
    }
}
