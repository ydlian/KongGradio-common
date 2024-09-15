/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: NacosConstant.java
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

public interface NacosConstant {


	String NACOS_ADDR_ONLINE_TEST = "127.0.0.1:8848";
	String NACOS_ADDR = "127.0.0.1:8848";
	String NACOS_ADDR_PROD = "127.0.0.1:8848";

	/**
	 * nacos
	 */
	String NACOS_CONFIG_PREFIX = "konggradio";

	String NACOS_GROUP_SUFFIX = "-group";


	String NACOS_CONFIG_FORMAT = "yaml";


	String NACOS_CONFIG_JSON_FORMAT = "json";


	String NACOS_CONFIG_REFRESH = "true";


	String NACOS_CONFIG_GROUP = "DEFAULT_GROUP";

	String NACOS_DISCOVERY_SERVER_ADDR = "spring.cloud.nacos.discovery.server-addr";
	String NACOS_CONFIG_PREFIX_PROPS_NAME = "spring.cloud.nacos.config.prefix";
	String NACOS_CONFIG_SERVER_ADDR = "spring.cloud.nacos.config.server-addr";

	static String dataId(String appName, String profile) {
		return dataId(appName, profile, NACOS_CONFIG_FORMAT);
	}

	static String dataId(String appName, String profile, String format) {
		return appName + "-" + profile + "." + format;
	}

}
