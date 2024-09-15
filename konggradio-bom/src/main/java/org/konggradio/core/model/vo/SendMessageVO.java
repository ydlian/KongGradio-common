/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: SendMessageVO.java
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

package org.konggradio.core.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class SendMessageVO {
    @NotNull(message = "noticeType must NOT be null")
    @Range(min = 1, max = 4, message = "")
    private String noticeType;

    private String title;

    @NotNull(message = "content must NOT be null")
    @Length(min=1,message = "")
    private String content;

    @NotNull(message = "")
    private String sendTo;

    @NotNull(message = "")
    @Length(min=1,message = "")
    private String caller;
}
