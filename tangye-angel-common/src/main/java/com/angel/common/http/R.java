/*
 *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  <p>
 *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 * https://www.gnu.org/licenses/lgpl.html
 *  <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.angel.common.http;

import com.angel.common.utils.ServiceConstant;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


/**
 * 响应信息主体,参照lengleng的代码完善分页结果返回
 * @param <T>
 * @author GilbertPan
 * @date 2020-04-11
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private int status;

    @Getter
    @Setter
    private String msg;


    @Getter
    @Setter
    private T data;

    @Getter
    @Setter
    private long page;

    @Getter
    @Setter
    private long count;

    public static <T> R<T> ok() {
        return restResult(null, ServiceConstant.SUCCESS, HttpStatus.OK.value(), null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, ServiceConstant.SUCCESS, HttpStatus.OK.value(), null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, ServiceConstant.SUCCESS, HttpStatus.OK.value(), msg);
    }

    public static <T> R<T> ok(long page, long count, T data) {
        return restResult(page, count, data, ServiceConstant.SUCCESS, HttpStatus.OK.value(), null);
    }

    public static <T> R<T> ok(long page, long count, T data, String msg) {
        return restResult(page, count, data, ServiceConstant.SUCCESS, HttpStatus.OK.value(), msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, ServiceConstant.FAIL, HttpStatus.BAD_REQUEST.value(), null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, ServiceConstant.FAIL, HttpStatus.BAD_REQUEST.value(), msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, ServiceConstant.FAIL, HttpStatus.BAD_REQUEST.value(), null);
    }

    public static <T> R<T> failed(T data, int status, String msg) {
        return restResult(data, ServiceConstant.FAIL, status, msg);
    }

    private static <T> R<T> restResult(T data, int code, int status, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setStatus(status);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    private static <T> R<T> restResult(long page, long count, T data, int code, int status, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setPage(page);
        apiResult.setCount(count);
        apiResult.setCode(code);
        apiResult.setStatus(status);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}

