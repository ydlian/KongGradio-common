/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: UploadKit.java
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

package org.konggradio.core.upload;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.konggradio.core.upload.fast.Md5Util;
import org.konggradio.core.upload.fast.ShardFileUtil;
import org.konggradio.core.upload.minio.MinioUtil;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@Getter
@Slf4j
public class UploadKit {

    public void setPackageSuffix(String packageSuffix) {
        this.packageSuffix = packageSuffix;
    }

    public static void main(String[] args) {

    }
    private String packageSuffix = "jar";
    private static final int TASK_NUM = (Runtime.getRuntime().availableProcessors() * 2) > 6 ? 6 : Runtime.getRuntime().availableProcessors() * 2;
    private static ExecutorService executor = ThreadUtil.newExecutor(TASK_NUM);
    private static CountDownLatch countDownLatch = ThreadUtil.newCountDownLatch(TASK_NUM);

    private void removePartFile(List<String> partFiles) {
        for (String filePath : partFiles) {
            if (FileUtil.exist(filePath)) {
                FileUtil.del(filePath);
            }
        }

    }

    public void mergeFile(String prefix, String mergeObjectName,String suffix) {
        StopWatch stopWatch = StopWatch.create("merge-build");
        stopWatch.start("Perform sharding and merging tasks");
        log.info("Merging ......");
        MinioUtil.mergePartObjects(prefix, mergeObjectName,suffix);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        log.info("Merging time(s):" + stopWatch.getTotalTimeSeconds());

    }

    private void uploadAndMergeFile(File uploadFile,String suffix,long partSize) {
        executor = ThreadUtil.newExecutor(TASK_NUM);
        countDownLatch = ThreadUtil.newCountDownLatch(TASK_NUM);
        List<String> partFiles = ShardFileUtil.splitFile(uploadFile.getAbsolutePath(),partSize);
        String hashCode = Md5Util.calculateMd5(uploadFile.getAbsolutePath());
        String dir = MinioUtil.DEFAULT_DIR + "/" + hashCode;
        double mbSize = (double) uploadFile.length() / 1024 / 1024;
        log.info("File name:[{}],size:[{}(MB)],uploading ...... ", uploadFile.getName(), String.format("%.2f", mbSize));
        try {
            uploadTask(uploadFile, dir, partFiles);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        String mergeObjectName = uploadFile.getName() + "." + DateUtil.date().toDateStr();
        log.info("mergeFile: {},{}",dir, mergeObjectName);
        mergeFile(dir + "/", mergeObjectName,suffix);
        removePartFile(partFiles);
        String outPutUrl = MinioUtil.userEndpoint + "/" + MinioUtil.DEFAULT_DIR + "/build-target/" + mergeObjectName;


        log.info("OSS output file: " + outPutUrl);
        executor.shutdown();
    }




    public void uploadTask(File uploadFile, String dir, List<String> partFiles) throws InterruptedException {
        List<List<String>> splitResult = ListTool.splitList(partFiles, TASK_NUM);
        StopWatch stopWatch = StopWatch.create("upload-build");
        stopWatch.start("Execute upload task");
        for (int i = 0; i < splitResult.size(); i++) {
            int finalI = i;
            executor.execute(() -> {
                handlerUpload(dir, splitResult.get(finalI));
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        stopWatch.stop();
        double uploadRate = (double) uploadFile.length() / 1024 / 1024;
        uploadRate = uploadRate / stopWatch.getTotalTimeSeconds();
        log.info("Uploading time(s):{},rate:{}(Mb/s)", stopWatch.getTotalTimeSeconds(), String.format("%.2f", uploadRate));
        System.out.println(stopWatch.prettyPrint());
    }

    public void handlerUpload(String dir, List<String> uploadList) {
        long total = uploadList.size();
        for (int i = 0; i < uploadList.size(); i++) {
            File partFile = new File(uploadList.get(i));
            MinioUtil.build().uploadLocalFile(dir, partFile.getName(), partFile.getAbsolutePath());
            log.info(Thread.currentThread().getName() + " [" + (i + 1) + "/" + total + "] uploading ...... ");
        }

    }

}
