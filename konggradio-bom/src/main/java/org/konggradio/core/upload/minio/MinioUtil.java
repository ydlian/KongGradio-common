/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: MinioUtil.java
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

package org.konggradio.core.upload.minio;


import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.konggradio.core.upload.fast.StringComparator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
public class MinioUtil {

    private static MinioClient minioClient;

    public static String DEFAULT_DIR = "";

    public static String CN_REGION = "cn-north-1";
    public static String userEndpoint = "https://oss.com";
    private static String endpoint = "https://s3.cn";
    ;
    private static String bucketName = "";
    private static String accessKey = "";
    private static String secretKey = "";

    private static final String SEPARATOR = "/";

    private MinioUtil() {
    }

    public static MinioUtil build() {
        return new MinioUtil(endpoint, bucketName, accessKey, secretKey);
    }

    public static MinioUtil rebuild(String userEndpoint, String endpoint, String bucketName, String accessKey, String secretKey) {
        MinioUtil.userEndpoint = userEndpoint;
        MinioUtil.endpoint = endpoint;
        MinioUtil.bucketName = bucketName;
        MinioUtil.accessKey = accessKey;
        MinioUtil.secretKey = secretKey;
        return new MinioUtil(endpoint, bucketName, accessKey, secretKey);
    }

    public MinioUtil(String userEndpoint, String endpoint, String bucketName, String accessKey, String secretKey) {
        MinioUtil.userEndpoint = userEndpoint;
        MinioUtil.endpoint = endpoint;
        MinioUtil.bucketName = bucketName;
        MinioUtil.accessKey = accessKey;
        MinioUtil.secretKey = secretKey;

    }

    public MinioUtil(String endpoint, String bucketName, String accessKey, String secretKey) {
        MinioUtil.endpoint = endpoint;
        MinioUtil.bucketName = bucketName;
        MinioUtil.accessKey = accessKey;
        MinioUtil.secretKey = secretKey;
        createMinioClient();
    }

    /**
     *  create minioClient
     */
    public void createMinioClient() {
        try {
            if (null == minioClient) {
                minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey)
                        .region(CN_REGION)
                        .build();
                createBucket();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get root url
     *
     * @return url
     */
    public static String getBasisUrl(String prefix) {
        return userEndpoint + SEPARATOR + prefix + SEPARATOR;
    }

    /**
     * init Bucket
     *
     * @throws Exception
     */
    private static void createBucket()
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, RegionConflictException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }


    public static boolean bucketExists()
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }


    public static void createBucket(String bucketName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, RegionConflictException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    private String getBucketPolicy(String bucketName)
            throws IOException, InvalidKeyException, InvalidResponseException, BucketPolicyTooLargeException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        String bucketPolicy = minioClient
                .getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
        return bucketPolicy;
    }


    public static List<Bucket> getAllBuckets()
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return minioClient.listBuckets();
    }


    public static Optional<Bucket> getBucket(String bucketName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }


    public static void removeBucket(String bucketName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }


    public static boolean doesObjectExist(String bucketName, String objectName) {
        boolean exist = true;
        try {
            minioClient
                    .statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }


    public static boolean doesFolderExist(String bucketName, String objectName) {
        boolean exist = false;
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).prefix(objectName).recursive(false).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir() && objectName.equals(item.objectName())) {
                    exist = true;
                }
            }
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }


    public static List<Item> getAllObjectsByPrefix(String bucketName, String prefix,
                                                   boolean recursive)
            throws ErrorResponseException, InsufficientDataException, InternalException, InvalidBucketNameException, InvalidKeyException, InvalidResponseException,
            IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        List<Item> list = new ArrayList<>();
        Iterable<Result<Item>> objectsIterator = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
        if (objectsIterator != null) {
            for (Result<Item> o : objectsIterator) {
                Item item = o.get();
                list.add(item);
            }
        }
        return list;
    }


    public static InputStream getObject(String bucketName, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return minioClient
                .getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }


    public InputStream getObject(String bucketName, String objectName, long offset, long length)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return minioClient.getObject(
                GetObjectArgs.builder().bucket(bucketName).object(objectName).offset(offset).length(length)
                        .build());
    }


    public static Iterable<Result<Item>> listObjects(String bucketName, String prefix,
                                                     boolean recursive) {
        return minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName)/*.prefix(prefix)*/.recursive(recursive).build());
    }


    public static ObjectWriteResponse putObject(String bucketName, String objectName,
                                                String fileName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName).object(objectName).filename(fileName).build());
    }


    public static ObjectWriteResponse putObject(String bucketName, String objectName,
                                                InputStream inputStream)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                                inputStream, inputStream.available(), -1)
                        .build());
    }


    public static ObjectWriteResponse putDirObject(String bucketName, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                                new ByteArrayInputStream(new byte[]{}), 0, -1)
                        .build());
    }


    public static ObjectStat statObject(String bucketName, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return minioClient
                .statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }


    public static ObjectWriteResponse copyObject(String bucketName, String objectName,
                                                 String srcBucketName, String srcObjectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return minioClient.copyObject(
                CopyObjectArgs.builder()
                        .source(CopySource.builder().bucket(bucketName).object(objectName).build())
                        .bucket(srcBucketName)
                        .object(srcObjectName)
                        .build());
    }


    public static void removeObject(String bucketName, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        minioClient
                .removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }


    public static void removeObjects(String bucketName, List<String> keys) {
        List<DeleteObject> objects = new LinkedList<>();
        keys.forEach(s -> {
            objects.add(new DeleteObject(s));
            try {
                removeObject(bucketName, s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public static String getOssObjectUrl(String bucketName, String objectName,
                                         Integer expires)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, InvalidExpiresRangeException, ServerException, InternalException, NoSuchAlgorithmException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return minioClient.presignedGetObject(bucketName, objectName, expires);
    }


    public static Map<String, String> presignedGetObject(String bucketName, String objectName,
                                                         Integer expires)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, InvalidExpiresRangeException, ServerException, InternalException, NoSuchAlgorithmException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        PostPolicy policy = new PostPolicy(bucketName, objectName,
                ZonedDateTime.now().plusDays(7));
        policy.setContentType("image/png");
        return minioClient.presignedPostPolicy(policy);
    }



    public static String getUtf8ByURLDecoder(String str) throws UnsupportedEncodingException {
        String url = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        return URLDecoder.decode(url, "UTF-8");
    }

    public static List<String> mergePartObjects(String prefix, String mergeObjectName, String packageSuffix) {
        MinioUtil minioUtil = new MinioUtil(endpoint, bucketName, accessKey, secretKey);
        Iterable<Result<Item>> res = MinioUtil.listObjects(bucketName, prefix, true);
        List<String> objectNameList = new ArrayList<>();
        try {
            for (Result<Item> result : res) {
                Item item = result.get();
                if (item.isDir()) {
                    continue;
                }
                String objectName = item.objectName();
                if (objectName.startsWith(prefix)) {
                    objectNameList.add(objectName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        mergeFile(objectNameList, prefix, mergeObjectName, packageSuffix);
        return objectNameList;
    }

    private static void mergeFile(List<String> objectNameList, String prefix, String mergeObjectName, String packageSuffix) {
        List<ComposeSource> composeSourceList = new ArrayList<>(objectNameList.size());

        StringComparator comparator = new StringComparator();
        comparator.setPackageSuffix(packageSuffix);
        objectNameList.sort(comparator);

        for (String object : objectNameList) {
            composeSourceList.add(ComposeSource.builder()
                    .bucket(bucketName)
                    .object(object)
                    .build());
        }
        String targetBucketName = bucketName;
        log.info("Start merging OSS sharding files");
        composeObject(composeSourceList, targetBucketName, "xxx/build-target/" + mergeObjectName);
        for (String object : objectNameList) {
            try {
                MinioUtil.removeObject(targetBucketName, object);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            } catch (InvalidResponseException e) {
                throw new RuntimeException(e);
            } catch (InsufficientDataException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (ServerException e) {
                throw new RuntimeException(e);
            } catch (InternalException e) {
                throw new RuntimeException(e);
            } catch (XmlParserException e) {
                throw new RuntimeException(e);
            } catch (InvalidBucketNameException e) {
                throw new RuntimeException(e);
            } catch (ErrorResponseException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void composeObject(List<ComposeSource> sourceObjectList, String destBucketName, String objectName) {
        try {
            minioClient.composeObject(ComposeObjectArgs.builder()
                    .bucket(destBucketName)
                    .object(objectName)
                    .sources(sourceObjectList)
                    .build());
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidBucketNameException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }

    }

    private String makeUrl(String userEndpoint, String dir, String objectName) {
        if (dir.endsWith(SEPARATOR)) {
            return userEndpoint + SEPARATOR + dir + objectName;
        }
        return userEndpoint + SEPARATOR + dir + SEPARATOR + objectName;
    }

    public String uploadLocalFile(String dir, String objectName, String localFilePath) {
        if (dir == null || dir.isEmpty()) {
            dir = DEFAULT_DIR;
        }
       try {
            ObjectWriteResponse resp = MinioUtil.putObject(bucketName, dir + SEPARATOR + objectName, localFilePath);

            return makeUrl(userEndpoint, dir, objectName);
        } catch (IOException | ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (InvalidBucketNameException e) {
            throw new RuntimeException(e);
        }
    }

    public String uploadLocalFile(String dir, String objectName, InputStream inputStream) {
        if (dir == null || dir.isEmpty()) {
            dir = DEFAULT_DIR;
        }
        try {
            ObjectWriteResponse resp = MinioUtil.putObject(bucketName, dir + SEPARATOR + objectName, inputStream);
            return makeUrl(userEndpoint, dir, objectName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (InvalidBucketNameException e) {
            throw new RuntimeException(e);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        }
    }


    public static String share(String dir, String objectName) {
        try {

            MinioClient minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();

            try {
                return minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .bucket(bucketName)
                                .region(CN_REGION)
                                .expiry(7 * 24 * 60 * 60)
                                .object(dir + objectName)
                                .method(Method.GET)
                                .build()
                );
            } catch (InvalidBucketNameException e) {
                throw new RuntimeException(e);
            } catch (InvalidExpiresRangeException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            } catch (InvalidResponseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (XmlParserException e) {
                throw new RuntimeException(e);
            } catch (ServerException e) {
                throw new RuntimeException(e);
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException e) {
            e.printStackTrace();
        }
        return "";
    }
}