/*
 * Copyright (c) 2018-2024. 
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: AppConstant.java
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


/**
 * System defines
 *
 * @author whulyd@foxmail.com
 */
public interface AppConstant {

    static final String URL_SEPARATOR = "/";
    static final String GATEWAY_REGISTER_URI = "/config/registerCenterList";
    static final String GATEWAY_METRIC_INIT_DATA_URI = "/config/metricInit";
    static final String GATEWAY_MASTER_STATUS_URI = "/config/materStatus";
    static final String GATEWAY_STL_STATUS_URI = "/config/getGatewayStl";

    static final String GATEWAY_SEND_MSG_URI = "/config/sendLaunchCode";
    static final String GATEWAY_NOTICE_DONG_LIST_URI = "/config/getDongList";
    static final String GATEWAY_NOTICE_MOBILE_LIST_URI = "/config/getMobileList";

    static final String SEND_MESSAGE_URI = "/szy-message-gate/gate/push";
    static final String GATEWAY_ADMIN_INTER_PROXY_URI_PREFIX = "/inter-proxy";
    static final String APP_HEALTH_URI = "/actuator/health";

    static final String APPLICATION_NAME_SEPARATOR = "-";

    static final String APPLICATION_ROOT_USER_NAME = "konggradio";
    
    static final long GW_CONFIG_CACHE_NUMBER = (3600 * 24 * 365 * 3)/2L;

    String ENV_KONG_CLOUD_GW_SYS_STL_STATUS_KEY = "konggradio.env.gw.system.status";
  

    static final String APPLICATION_NAME_PREFIX = "konggradio-";

    String APPLICATION_NAME_KEY = "APPLICATION_NAME_KEY_";

    String REGISTER_SERVER_SEPARATOR = ",";

    String REGISTER_SERVER_LIST_PROP = "konggradio.register.server.list";


    /**
     * app version
     */
    String APPLICATION_VERSION = "1.0.6-SNAPSHOT";

    String BASE_PACKAGES = "org.konggradio";

    String CACHE_BASE_BIND_USER_KEY_PREFIX = "org:konggradio:bind:user:";
    String CACHE_USER_ACC_PASS_KEY_PREFIX = "org:konggradio:init:user:pass:";
    String CACHE_USER_ACC_SETS_KEY_PREFIX = "org:konggradio:user:sets:";

    String KONGGRADIO_ENV_KEY = "konggradio.env";
    String KONGGRADIO_ENV_KEY_FAKE = "konggradio.env.fake";
    String KONGGRADIO_GW_MASTER_STATUS = "konggradio.env.gw.master.status";
    String KONGGRADIO_GW_MASTER_DOMAIN = "konggradio.env.gw.master.domain";
    String KONGGRADIO_GW_DOMAIN = "konggradio.env.this.gw.domain";

    String KONGGRADIO_CUSTOMIZED_CONSUL = "konggradio.customized.consul";
    String KONGGRADIO_CUSTOMIZED_CONSUL_CHECK = "konggradio.customized.consul.check";
    String KONGGRADIO_CUSTOMIZED_NACOS_CHECK = "konggradio.customized.nacos.check";

    Long HOT_APP_ALARM_LIMIT = 1200000L;
    Long HOT_APP_METRICS_LIMIT = 1000000L;
   
    Long HOT_APP_KEEP_METRICS_SEC_HOLDER = 3600L * 24 * 365 * 2000;
    Long TR_ACC_SEC_HOLDER = 3600L * 24 * 7;
    String KONGGRADIO_TMP_DIR = "user.env.temp.dir";

    /**
     * api gateway
     */
    String APPLICATION_GATEWAY_NAME = APPLICATION_NAME_PREFIX + "gateway";
    
    /**
     * resources
     */
    String APPLICATION_RESOURCE_NAME = APPLICATION_NAME_PREFIX + "resource";
   
    /**
     * test
     */
    String APPLICATION_TEST_NAME = APPLICATION_NAME_PREFIX + "test";

    /**
     * demo
     */
    String APPLICATION_DEMO_NAME = APPLICATION_NAME_PREFIX + "demo";

    String JAVA_TMP_DIR = "java.io.tmpdir";
    
    String DEV_IP = "127.0.0.1";
    /**
     * for dev
     */
    String DEV_CODE = "dev";

    /**
     * for pre env
     */
    String PRE_CODE = "pre";

    /**
     * for pre env
     */
    String PRE_PREFIX_CODE = "pre-";
    /**
     * for prod env
     */
    String PROD_CODE = "prod";

    /**
     *  for prod env
     */
    String PROD_PREFIX_CODE = "prod-";

    /**
     * for QA
     */
    String QA_CODE = "qa";
    /**
     * for test
     */
    String TEST_CODE = "test";

    /**
     * default OS is linux
     */
    String OS_NAME_LINUX = "LINUX";

}
