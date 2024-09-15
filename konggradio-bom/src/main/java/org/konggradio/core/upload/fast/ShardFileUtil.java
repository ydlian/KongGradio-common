/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: ShardFileUtil.java
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

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

@Slf4j
public class ShardFileUtil {

    private ShardFileUtil() {

    }

    @SneakyThrows
    public static List<InputStream> splitFileInputStreams(File file, long splitSize) {
        if (splitSize < (5 * 1024 * 1024)) {
            throw new Exception("File too small");
        }
        List<InputStream> inputStreams = new ArrayList<>();
        InputStream bis = null;
        OutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            long writeByte = 0;
            int len = 0;
            byte[] bt = new byte[5 * 1024 * 1024];
            while (-1 != (len = bis.read(bt))) {
                if (writeByte % splitSize == 0) {
                    bos = new ByteArrayOutputStream();
                }
                writeByte += len;
                bos.write(bt, 0, len);
                if (writeByte % splitSize == 0) {
                    InputStream inputStream = IOConvertUtil.oConvertI(bos);
                    inputStreams.add(inputStream);
                }
            }
            InputStream inputStream = IOConvertUtil.oConvertI(bos);
            inputStreams.add(inputStream);
            log.info("File {} sharding successful! Start preparing for uploading.", file.getName());
        } catch (Exception e) {
            log.error("File sharding failed！Cause:{}", e.getMessage());
            e.printStackTrace();
        }
        return inputStreams;
    }

    public static List<String> splitFile(String filePath) {
        long splitSize = 5 * 1024 * 1024;
        return splitFile(filePath, splitSize);
    }


    public static List<String> splitFile(String filePath, long splitSize) {
        List<String> fileList = new ArrayList<>();
        InputStream bis = null;
        OutputStream bos = null;
        try {
            File file = new File(filePath);

            bis = new BufferedInputStream(new FileInputStream(file));
            long writeByte = 0;
            int len = 0;
            byte[] bt = new byte[(int) splitSize];
            int flag = 0;
            while (-1 != (len = bis.read(bt))) {
                if (writeByte % splitSize == 0) {
                    if (bos != null) {
                        bos.flush();
                        bos.close();
                    }
                    String partFileName = filePath + "." + (writeByte / splitSize + 1) + ".part";
                    bos = new BufferedOutputStream(new FileOutputStream(partFileName));
                    fileList.add(partFileName);
                }
                writeByte += len;
                bos.write(bt, 0, len);
            }
            log.info("File {} sharding successful! Start preparing for uploading.", file.getName());
        } catch (Exception e) {
            log.error("File sharding failed！Cause:{}", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileList;
    }


    public static void mergeFile(String splitDir, String newFilePath) {
        File dir = new File(splitDir);

        File[] fileArr = dir.listFiles((dir1, name) -> name.endsWith(".part"));
        List<File> fileList = Arrays.asList(fileArr);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                int lastIndex11 = o1.getName().lastIndexOf(".");
                int lastIndex12 = o1.getName().substring(0, lastIndex11).lastIndexOf(".") + 1;
                int lastIndex21 = o2.getName().lastIndexOf(".");
                int lastIndex22 = o2.getName().substring(0, lastIndex21).lastIndexOf(".") + 1;
                int num1 = Integer.parseInt(o1.getName().substring(lastIndex12, lastIndex11));
                int num2 = Integer.parseInt(o2.getName().substring(lastIndex22, lastIndex21));
                return num1 - num2;
            }
        });
        OutputStream bos = null;
        InputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(newFilePath));//定义输出流
            for (File file : fileList) {//按顺序合成文件
                bis = new BufferedInputStream(new FileInputStream(file));
                int len = 0;
                byte[] bt = new byte[1024];
                while (-1 != (len = bis.read(bt))) {
                    bos.write(bt, 0, len);
                }
                bis.close();
            }
            bos.flush();
            bos.close();
            System.out.println("File merge succeed！");
        } catch (Exception e) {
            System.out.println("File merge failed！");
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
