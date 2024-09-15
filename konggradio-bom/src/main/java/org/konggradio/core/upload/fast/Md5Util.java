/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: Md5Util.java
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

package org.konggradio.core.upload.fast;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Md5Util {
    private static final int BUFFER_SIZE = 8 * 1024;

    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private Md5Util() {
    }


    public static String calculateMd5(byte[] bytes) {
        try {
            MessageDigest md5MessageDigest = MessageDigest.getInstance("MD5");
            return encodeHex(md5MessageDigest.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("no md5 found");
        }
    }


    public static String calculateMd5(InputStream inputStream) {
        try {
            MessageDigest md5MessageDigest = MessageDigest.getInstance("MD5");
            try (BufferedInputStream bis = new BufferedInputStream(inputStream);
                 DigestInputStream digestInputStream = new DigestInputStream(bis, md5MessageDigest)) {

                final byte[] buffer = new byte[BUFFER_SIZE];

                while (digestInputStream.read(buffer) > 0) {

                    md5MessageDigest = digestInputStream.getMessageDigest();
                }

                return encodeHex(md5MessageDigest.digest());
            } catch (IOException ioException) {
                throw new IllegalArgumentException(ioException.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("no md5 found");
        }
    }

    public static String calculateMd5(String input) {
        try {

            MessageDigest md5MessageDigest = MessageDigest.getInstance("MD5");
            byte[] inputByteArray = input.getBytes(StandardCharsets.UTF_8);
            md5MessageDigest.update(inputByteArray);
            byte[] resultByteArray = md5MessageDigest.digest();
            return encodeHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("md5 not found");
        }
    }

    private static String encodeHex(byte[] bytes) {
        char[] chars = new char[32];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        return new String(chars);
    }

}

