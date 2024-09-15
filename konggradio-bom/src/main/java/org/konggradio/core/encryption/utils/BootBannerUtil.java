/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: BootBannerUtil.java
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

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import org.konggradio.core.constant.AppConstant;
import org.konggradio.core.model.InputForm;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Scanner;

public class BootBannerUtil {
    public static void printInfo() {
        String sysName = System.getProperty("os.name");
        if (sysName.contains("Windows")) {
            System.out.println();
            System.out.println("===================================================================");
            System.out.println("=                                                                 =");
            System.out.println("      Java Code Encryption " + AppConstant.APPLICATION_VERSION + "   by KongGradio    ");
            System.out.println("=                                                                 =");
            System.out.println("===================================================================");
            System.out.println();
            return;
        }


        String[] color = {"\033[31m", "\033[32m", "\033[33m", "\033[34m", "\033[35m", "\033[36m",
                "\033[90m", "\033[92m", "\033[93m", "\033[94m", "\033[95m", "\033[96m", "\033[93m", "\033[94m", "\033[95m", "\033[96m"};
        System.out.println();

        for (int i = 0; i < 69; i++) {
            System.out.print(color[i % color.length] + "=\033[0m");
        }
        System.out.println();
        System.out.println("\033[34m=                                                                   \033[92m=");
        System.out.println("\033[35m       \033[31mJava \033[92mCode \033[95mEncryption "
                + AppConstant.APPLICATION_VERSION + "\033[0m   by \033[91mKongGradio\033[0m     \033[93m");
        System.out.println("\033[36m=                                                                   \033[94m=");
        for (int i = 68; i >= 0; i--) {
            System.out.print(color[i % color.length] + "=\033[0m");
        }
        System.out.println();
        System.out.println();
    }

    public static boolean checkCodePass(String[] args, int index, String code) {
        if (!StringUtils.hasText(code)) return false;
        Scanner scanner = new Scanner(System.in);
        CmdLineOptionUtil cmd = new CmdLineOptionUtil();
        cmd.addOption("pwd", true, "password");
        cmd.addOption("spring.profiles.active", true, "config file");
        cmd.addOption("konggradio.customized.consul", true, "consul");

        cmd.parse(args);

        String password = null;

        if (args == null || args.length == 0) {
            while (!StringUtils.hasText(password)) {
                Log.print("index is: " + index + ", input pass:");
                password = scanner.nextLine();
            }
        } else {
            password = cmd.getOptionValue("pwd", "");
        }

        if (!StringUtils.hasText(password)) {

            InputForm input = new InputForm();
            boolean gui = input.showForm();
            if (gui) {
                Log.debug("GUI input");
                password = String.valueOf(input.nextPasswordLine());
                input.closeForm();
            }
        }
        if (!StringUtils.hasText(password)) {
            long now = System.currentTimeMillis();
            String pwdFile = "pwd.dat";
            if (!FileUtil.exist(pwdFile)) {
                pwdFile = "/root/.local/pwd.dat";
            }
            while (true) {
                String content = null;
                try {
                    content = FileUtil.readString(new File(pwdFile), Charset.defaultCharset());
                    content = content.replaceAll("\r|\n", "");
                    password = content.trim();
                } catch (Exception e) {

                }
                if (code.equals(password)) {
                    break;
                }
                long timeMillis = System.currentTimeMillis();
                if (timeMillis - now > 90 * 1000) break;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return code.equals(password);
    }

    public static void main(String[] args) {
        printInfo();
        Scanner scanner = new Scanner(System.in);
        CmdLineOptionUtil cmd = new CmdLineOptionUtil();
        cmd.addOption("pwd", true, "password");

        cmd.parse(args);
        if (cmd.hasOption("C")) {
            System.out.println("ok");
        }

        String password = null;
        System.out.println(String.format("%06d", RandomUtil.randomLong(0, 999999L)));

        if (args == null || args.length == 0) {
            while (!StringUtils.hasText(password)) {
                Log.print("please input password:");
                password = scanner.nextLine();
            }
        }
    }
}
