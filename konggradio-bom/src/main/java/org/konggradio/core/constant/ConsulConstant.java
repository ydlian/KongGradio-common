/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: ConsulConstant.java
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
 * Consul defines
 *
 * @author yadlian
 */
public interface ConsulConstant {

	/**
	 * consul address
	 */
	String CONSUL_ADDR_LOCAL = "http://localhost";
	String CONSUL_ADDR_PROD_GGJ = "http://127.0.0.1";
	String CONSUL_ADDR_PROD = "http://127.0.0.1";
	String CONSUL_ADDR_PRE = "http://127.0.0.1";
	String CONSUL_ADDR_QA = "127.0.0.1";

	/**
	 * consul port
	 */
	String CONSUL_PORT = "8500";

	String CONSUL_CONFIG_FORMAT = "yml";


	String CONSUL_WATCH_DELAY = "1000";


	String CONSUL_WATCH_ENABLED = "true";

}
