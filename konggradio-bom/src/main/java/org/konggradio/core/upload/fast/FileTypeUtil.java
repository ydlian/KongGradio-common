/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: FileTypeUtil.java
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

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Slf4j
public final class FileTypeUtil {

    private static final Map<String, List<String>> MIME_TYPE_MAP;

    static {
        MIME_TYPE_MAP = new HashMap<>();
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(
                    "mime/mime-types.xml"));

            Element rootElement = document.getRootElement();
            List<Element> mimeTypeElements = rootElement.elements("mime-type");
            for (Element mimeTypeElement : mimeTypeElements) {
                String type = mimeTypeElement.attributeValue("type");
                List<Element> globElements = mimeTypeElement.elements("glob");
                List<String> fileTypeList = new ArrayList<>(globElements.size());
                for (Element globElement : globElements) {
                    String fileType = globElement.getTextTrim();
                    fileTypeList.add(fileType);
                }
                MIME_TYPE_MAP.put(type, fileTypeList);
            }
        } catch (DocumentException e) {
            log.error("", e);
        }

    }

    private FileTypeUtil() {
    }

    public static String getFileMimeType(InputStream inputStream, String fileName, Long fileSize) {
        AutoDetectParser parser = new AutoDetectParser();
        parser.setParsers(new HashMap<>());
        Metadata metadata = new Metadata();


        if (!ObjectUtil.isEmpty(fileName)) {
            metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, fileName);
        }

        if (!ObjectUtil.isEmpty(fileSize)) {
            metadata.set(Metadata.CONTENT_LENGTH, Long.toString(fileSize));
        }
        try (InputStream stream = inputStream) {
            parser.parse(stream, new DefaultHandler(), metadata, new ParseContext());
        } catch (IOException | SAXException | TikaException e) {
            log.error("", e);
            throw new IllegalArgumentException("cause：" + e.getMessage());
        }
        return metadata.get(HttpHeaders.CONTENT_TYPE);
    }

    public static String getFileMimeType(InputStream inputStream) throws IllegalArgumentException {
        return getFileMimeType(inputStream, null, null);
    }


    public static List<String> getFileRealTypeList(InputStream inputStream, String fileName, Long fileSize) {
        String fileMimeType = getFileMimeType(inputStream, fileName, fileSize);
        log.info("fileMimeType:{}", fileMimeType);
        return getFileRealTypeList(fileMimeType);
    }


    public static List<String> getFileRealTypeList(InputStream inputStream) throws IOException {
        return getFileRealTypeList(inputStream, null, null);
    }

    public static List<String> getFileRealTypeList(String mimeType) {
        if (ObjectUtil.isEmpty(mimeType)) {
            return Collections.emptyList();
        }

        List<String> fileTypeList = MIME_TYPE_MAP.get(mimeType.replace(" ", ""));
        if (fileTypeList == null) {
            log.info("mimeType:{}, FileTypeList is null", mimeType);
            return Collections.emptyList();
        }
        return fileTypeList;
    }
}
