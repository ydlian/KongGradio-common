/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: GatewayConstant.java
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

import cn.hutool.core.lang.UUID;
import org.springframework.util.StringUtils;

public class GatewayConstant {

    public static final String LAUNCH_SUCCEED_TIP = "Check succeed!";
    public static final String LAUNCH_FAIL_TIP = "Application launch failed!";
    public static final String METRICS_INIT_TIMES_KEY = "gateway.metrics.init.times";
    public static final String METRICS_INIT_SECONDS_KEY = "gateway.metrics.init.seconds";
    public static final String METRICS_INIT_HOURS_KEY = "gateway.metrics.init.hours";

    public static final String APP_METRICS_REG_TIME_KEY = "gateway.app.reg.time";
    public static final String APP_METRICS_LAST_CONNECT_TIME_KEY = "gateway.app.last.connect.time";
    public static final String APP_METRICS_REG_RESULT_KEY = "gateway.app.reg.result";
    public static final String APP_METRICS_LEFT_TIMES_KEY = "gateway.app.left.times";
    public static final String APP_METRICS_LEFT_SECONDS_KEY = "gateway.app.left.seconds";
    public static final String APP_METRICS_LEFT_HOURS_KEY = "gateway.app.left.hours";
    public static final String APP_METRICS_FULL_INFO_KEY = "gateway.app.metric.full.info";

    public static final Long DEF_METRICS_INIT_TIMES = 100L;
    public static final Long DEF_METRICS_INIT_SECONDS = 3600L * 24 * 365;
    public static final Long DEF_METRICS_INIT_HOURS = 24L * 365;

    static final String APPLICATION_GATEWAY_NAME = "konggradio-gateway";

    static final String GATEWAY_ADMIN_DEFAULT_ADDRESS = "http://";
    static final String GATEWAY_ADMIN_DEFAULT_LOCAL_HOST_ADDRESS = "http://admin.gateway.com";

    static final String WHITE_LIST_PREFIX = "WHITE_LIST_";
    static final String BLACK_LIST_PREFIX = "BLACK_LIST_";

    static final String NOTICE_ERP_LIST_PREFIX = "NOTICE_ERP_LIST_";
    static final String NOTICE_SMS_LIST_PREFIX = "NOTICE_SMS_LIST_";
    static final String REGISTER_CENTER_LIST_PREFIX = "REGISTER_CENTER_LIST_";
    static final String DOT = ":";

    private static final String NOISE = ":K-NOISE:";

    private static final String DEFAULT_CTL_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMMF9Qy9V071h4E3c4OcqaJMLOw3mdzESr3CpL1hsiTuMIzAsWM6spEOGuTnSiWr8GNzRcmb+gJszN5LDGzBI3MyAX8G/MObBAxRIxFslRYU1ioHwIbsnryanOOfPrZ+iAeVVlQbJG6rEdmEgy6Kzcbe4h3kRNCAsbvygJhiOMKBAgMBAAECgYEAlZVNJo9XlyV14KOCg1E3P1fEIQ5ogk9AsJnJt8RmWjMjo+HsbRrJeb7i2+2XDpv5jp4Myh1Lvm5rGJlhl0zwfYQBypWk0SDkQpnPYdodzl4pAiz+pSQm3C4O6vzZ9RtYzPwXImSoYZjVVof26Z9DBEJ/jNODpJFKLvA8YRu400ECQQDw6eJ/h3IrdyW29dRq41QboKYAz4Hpa7iz6wmmsXRNMDkNLRSo6IxbrtcW+gun69ZbYTVbfuF6Svyqfs45wE6JAkEAzzxmGu8EVQkHwYyc7AVo3rRE0f9ivIV2AX9m4ofhepVFJ27Rc/Dv4FFv/OK8cEK7DpbJDilZk7ZxfdydlIuWOQJBAKrR29T7MBvbtrU9J6nGmMuiJDKs7/uXcva3sDcu2j0k9T7/eQevJF6Z4U8cawlc8rgGfDPtCmmRJAisMwPF9KkCQQCCe1UOwE/J+tWXWCG1ufI9xuNOFa7lZGLYro7pMwUVWccg/PWEOr/OTXhlkZ10UY/Vr7sH36E3EDiFNtNBJGKBAkB9N6OEuodjNC99Wn96HIakPRhW88BJny8CZs9gRT8RFwBuVNhC6sef7P4Yvsx2JiZfFwqTuL/PSqAnrlfwsLwM";
    private static final String DEFAULT_CTL_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDBfUMvVdO9YeBN3ODnKmiTCzsN5ncxEq9wqS9YbIk7jCMwLFjOrKRDhrk50olq/Bjc0XJm/oCbMzeSwxswSNzMgF/BvzDmwQMUSMRbJUWFNYqB8CG7J68mpzjnz62fogHlVZUGyRuqxHZhIMuis3G3uId5ETQgLG78oCYYjjCgQIDAQAB";

    private static final String LEVEL_1_CODE = "KXLv7h9Qi/Ta/oFdD5VFM85ZKzmET69T6mb+f6Gavx6zvDq3jEtNS8PLWGxfUt10TvGI1xZiLGnHS2M0rxTtkl55AG6YzEU7ccdmXuHhQyKhxo8TBDpEO8e6x7edYnR6ubqGGDf1r/hlHQjvoxL0ddUFrERk0vE+UIe5z/Z8wsI=";

    private static final String LEVEL_0_CODE = "j6pDBph5+kKlKcVbB9MI5l9vVCoae6cgOl72VlsdjQlTuoBKoX5x7naEcIwgkyRjAxFJAigoqXpgLg5buyTl3OF5LbmqIEEFdGHtwjNZ+x3bNuUMVeH+IDpGe98j6yYVQPoVvDcbPwmXKhE+TXpc/O0BU994t5W0tw8RHsReZAE=";
    public static String getLevel1Code() {
        return LEVEL_1_CODE;
    }

    public static String getLevel0Code() {
        return LEVEL_0_CODE;
    }

    public static String getApplicationGatewayName() {
        return APPLICATION_GATEWAY_NAME;
    }

    public static String getGatewayAdminDefaultAddress() {
        return GATEWAY_ADMIN_DEFAULT_ADDRESS;
    }

    public static int getNoiseEndPos(String data) {
        if (!StringUtils.hasText(data)) return 0;
        if (!data.contains(NOISE)) return 0;
        int pos = data.indexOf(NOISE) + NOISE.length();
        return pos;
    }

    public static String getDefaultCtlPrivate() {

        StringBuffer name = new StringBuffer(UUID.fastUUID().toString());
        return name.append(NOISE).append(DEFAULT_CTL_PRIVATE).toString();
    }

    public static String getDefaultCtlPublic() {
        StringBuffer name = new StringBuffer(UUID.fastUUID().toString());
        return name.append(NOISE).append(DEFAULT_CTL_PUBLIC).toString();
    }

    public static String getWhiteListIpSetKey() {
        StringBuffer name = new StringBuffer(WHITE_LIST_PREFIX);
        return name.append("IP_SET_KEY").append(DOT).toString();
    }
    public static String getWhiteListUriSetKey() {
        StringBuffer name = new StringBuffer(WHITE_LIST_PREFIX);
        return name.append("URI_SET_KEY").append(DOT).toString();
    }

    public static String getWhiteListAppSetKey() {
        StringBuffer name = new StringBuffer(WHITE_LIST_PREFIX);
        return name.append("APP_SET_KEY").append(DOT).toString();
    }

    public static String getBlackListUriSetKey() {
        StringBuffer name = new StringBuffer(BLACK_LIST_PREFIX);
        return name.append("URI_LIST_SET_KEY").append(DOT).toString();
    }
    public static String getBlackListIPSetKey() {
        StringBuffer name = new StringBuffer(BLACK_LIST_PREFIX);
        return name.append("IP_LIST_SET_KEY").append(DOT).toString();
    }
    public static String getBlackListAppSetKey() {
        StringBuffer name = new StringBuffer(BLACK_LIST_PREFIX);
        return name.append("APP_LIST_SET_KEY").append(DOT).toString();
    }
    public static String getRegisterCenterSetKey() {
        StringBuffer name = new StringBuffer(REGISTER_CENTER_LIST_PREFIX);
        return name.append("ADDRESS_SET_KEY").append(DOT).toString();
    }

    public static String getNoticeErpListPrefix(String appName) {
        StringBuffer name = new StringBuffer(NOTICE_ERP_LIST_PREFIX);
        return name.append("KEY").append(DOT).append(appName).toString();
    }
    public static String getNoticeSmsListPrefix(String appName) {
        StringBuffer name = new StringBuffer(NOTICE_SMS_LIST_PREFIX);
        return name.append("KEY").append(DOT).append(appName).toString();
    }
    public static String getGatewayAdminDefaultLocalHostAddress(){
        return GATEWAY_ADMIN_DEFAULT_LOCAL_HOST_ADDRESS;
    }
}
