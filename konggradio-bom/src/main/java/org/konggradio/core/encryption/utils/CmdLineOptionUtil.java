/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: CmdLineOptionUtil.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command Line tools
 *
 * @author
 */
public class CmdLineOptionUtil {
    /**
     * option
     */
    private List<String> options = new ArrayList<>();
    /**
     * hasArgs
     */
    private List<Boolean> hasArgs = new ArrayList<>();
    /**
     * descriptions
     */
    private List<String> descriptions = new ArrayList<>();
    /**
     * option and values
     */
    private Map<String, List<String>> optionsMap = new HashMap<>();

    /**
     * Add an option that only contains a short-name.
     *
     * <p>
     * It may be specified as requiring an argument.
     * </p>
     *
     * @param opt         Short single-character name of the option.
     * @param hasArg      flag signally if an argument is required after this option
     * @param description Self-documenting description
     * @return the resulting Options instance
     */
    public CmdLineOptionUtil addOption(String opt, boolean hasArg, String description) {
        opt = resolveOption(opt);
        if (!options.contains(opt)) {
            options.add(opt);
            hasArgs.add(hasArg);
            descriptions.add(description);
        }
        return this;
    }

    /**
     * Parse the arguments according to the specified options.
     *
     * @param arguments the command line arguments
     * @return CmdLineOption
     */
    public CmdLineOptionUtil parse(String[] arguments) {
        int optIndex = -1;
        for (int i = 0; i < arguments.length; i++) {
            String arg = arguments[i];

            if (arg.startsWith("-") || arg.startsWith("--")) {
                arg = resolveOption(arg);

                //check last option hasArg
                if (optIndex > -1 && hasArgs.get(optIndex)) {
                    String lastOption = options.get(optIndex);
                    if (optionsMap.get(lastOption).size() == 0) {
                        throw new IllegalArgumentException("Missing argument for option: " + lastOption);
                    }
                }

                optIndex = options.indexOf(arg);
                if (optIndex < 0) {
                    continue;
                    //throw new IllegalArgumentException("Unrecognized option: " + arguments[i]);
                }
                optionsMap.put(arg, new ArrayList<>());
            } else if (optIndex > -1) {
                String option = options.get(optIndex);
                optionsMap.get(option).add(arg);
            }
        }
        return this;
    }

    /**
     * Retrieve the first argument, if any, of this option.
     *
     * @param opt the name of the option
     * @return Value of the argument if option is set, and has an argument,
     * otherwise null.
     */
    public String getOptionValue(String opt) {
        return getOptionValue(opt, null);
    }

    /**
     * Retrieve the first argument, if any, of this option.
     *
     * @param opt the name of the option
     * @param dv  default value
     * @return Value of the argument if option is set, and has an argument,
     * otherwise null.
     */
    public String getOptionValue(String opt, String dv) {
        String[] values = getOptionValues(opt);
        return (values == null) ? dv : values[0];
    }

    /**
     * Retrieves the array of values, if any, of an option.
     *
     * @param opt string name of the option
     * @return Values of the argument if option is set, and has an argument,
     * otherwise null.
     */
    public String[] getOptionValues(String opt) {
        List<String> values = optionsMap.get(resolveOption(opt));
        return (values == null || values.isEmpty()) ? null : values.toArray(new String[values.size()]);
    }

    /**
     * Query to see if an option has been set.
     *
     * @param opt Short name of the option
     * @return true if set, false if not
     */
    public boolean hasOption(String opt) {
        return optionsMap.keySet().contains(resolveOption(opt));
    }

    /**
     * Remove the hyphens from the beginning of <code>str</code> and
     * return the new String.
     *
     * @param str The string from which the hyphens should be removed.
     * @return the new String.
     */
    private static String resolveOption(String str) {
        if (str == null) {
            return null;
        }
        if (str.startsWith("--")) {
            return str.substring(2, str.length());
        } else if (str.startsWith("-")) {
            return str.substring(1, str.length());
        }

        return str;
    }
}
