/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: GatewayAdminConfigBO.java
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

package org.konggradio.core.model.bo;

import cn.hutool.core.date.DateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GatewayAdminConfigBO {
    private DateTime localTime;
    private String appName;
    private String gatewayDomain;
    private DateTime remoteAppServerTime;
    private Long gapHourBetweenGwAndApp;
    private Long gapSecondBetweenGwAndApp;
    private String gatewayAdminDomain;
    private int curGatewayType;
    private String info;

}