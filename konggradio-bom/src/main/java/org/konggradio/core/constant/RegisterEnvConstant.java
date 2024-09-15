/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: RegisterEnvConstant.java
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

public class RegisterEnvConstant {
	//metric
	public static int METRIC_DEFAULT_MOD_NUM  = 50;
	public static int METRIC_DEFAULT_VALUE  = 10000;

	public static int METRIC_STEP_LIMIT  = 50000;
	public static String KONGGRADIO_REGISTER_ENV_MOD_NUM_KEY = "app.register.env.mod.num";
	public static String KONGGRADIO_REGISTER_ENV_MARK = "app.register.env.mark";
	public static String KONGGRADIO_REGISTER_COUNTER = "app.register.counter.a";
	public static String KONGGRADIO_REGISTER_BOOT_DETAIL = "app.register.boot.c";
	public static String KONGGRADIO_REGISTER_INTER_DETAIL = "app.register.inter.d";

	public static void init(String application) {
		if (!KONGGRADIO_REGISTER_ENV_MARK.endsWith(application)) {
			KONGGRADIO_REGISTER_ENV_MARK = KONGGRADIO_REGISTER_ENV_MARK + "." + application;
		}
		if (!KONGGRADIO_REGISTER_COUNTER.endsWith(application)) {
			KONGGRADIO_REGISTER_COUNTER = KONGGRADIO_REGISTER_COUNTER + "." + application;
		}

		if (!KONGGRADIO_REGISTER_BOOT_DETAIL.endsWith(application)) {
			KONGGRADIO_REGISTER_BOOT_DETAIL = KONGGRADIO_REGISTER_BOOT_DETAIL + "." + application;
		}
		if (!KONGGRADIO_REGISTER_INTER_DETAIL.endsWith(application)) {
			KONGGRADIO_REGISTER_INTER_DETAIL = KONGGRADIO_REGISTER_INTER_DETAIL + "." + application;
		}

	}


}
