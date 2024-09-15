/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: AESBase64Util.java
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

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESBase64Util {

	private static final String AES_ALGORITHM = "AES";

	private static final String UTF8 = StandardCharsets.UTF_8.name();

	public static boolean hasLength( String str) {
		return str != null && !str.isEmpty();
	}

	public static int getLength(Object array) {
		return array == null ? 0 : Array.getLength(array);
	}
	public static boolean isEmpty(byte[] array) {
		return getLength(array) == 0;
	}

	public static String encrypt(String text, String key) {
		if (!hasLength(text)) {
			throw new RuntimeException("encode text should not be null or empty.");
		}
		byte[] encodeBytes = encryptByteAES(text.getBytes(StandardCharsets.UTF_8), key);
		return Base64.encodeBase64String(encodeBytes);
	}


	public static String decrypt(String text, String key) {
		if (!hasLength(text)) {
			throw new RuntimeException("decode text should not be null or empty.");
		}
		byte[] decodeBytes = decryptByteAES(Base64.decodeBase64(text.getBytes(StandardCharsets.UTF_8)), key);
		return new String(decodeBytes, StandardCharsets.UTF_8);
	}


	public static byte[] encryptByteAES(byte[] originalBytes, String key) {
		if (isEmpty(originalBytes)) {
			throw new RuntimeException("encode originalBytes should not be empty.");
		}
		if (!hasLength(key)) {
			throw new RuntimeException("key :" + key + ", encode key should not be null or empty.");
		}
		Cipher cipher = getAESCipher(key, Cipher.ENCRYPT_MODE);
		byte[] encodeBytes = null;
		try {
			encodeBytes = cipher.doFinal(originalBytes);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e.getClass().getName() + ": encode byte fail. " + e.getMessage());
		}
		return encodeBytes;
	}


	public static byte[] decryptByteAES(byte[] encryptedBytes, String key) {
		if (isEmpty(encryptedBytes)) {
			throw new RuntimeException("decode encryptedBytes should not be empty.");
		}
		if (!hasLength(key)) {
			throw new RuntimeException("key :" + key + ", decode key should not be null or empty.");
		}
		Cipher cipher = getAESCipher(key, Cipher.DECRYPT_MODE);
		byte[] decodeBytes = null;
		try {
			decodeBytes = cipher.doFinal(encryptedBytes);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e.getClass().getName() + ": decode byte fail. " + e.getMessage());
		}
		return decodeBytes;
	}

	public static Cipher getAESCipher(String key, int mode) {
		if (!hasLength(key)) {
			throw new RuntimeException("key :" + key + ", should not be null or empty.");
		}
		Cipher cipher = null;
		SecretKey secretKey;
		try {
			cipher = Cipher.getInstance(AES_ALGORITHM);
			byte[] keyBytes = key.getBytes(UTF8);
			secretKey = new SecretKeySpec(keyBytes, AES_ALGORITHM);
			cipher.init(mode, secretKey);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e.getClass().getName() + ": get cipher instance wrong. " + e.getMessage());
		} catch (UnsupportedEncodingException u) {
			throw new RuntimeException(u.getClass().getName() + ": key transfer bytes fail. " + u.getMessage());
		} catch (InvalidKeyException i) {
			throw new RuntimeException(i.getClass().getName() + ": key is invalid. " + i.getMessage());
		}
		return cipher;
	}


}
