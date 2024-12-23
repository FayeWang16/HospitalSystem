package com.example.outpatient.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Schema(description = "API Response Entity")
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Status Code")
    private int status;

    @Schema(description = "Success Indicator")
    private boolean success;

    @Schema(description = "Response Message")
    private String message;

    @Schema(description = "Data")
    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "Response Time")
    private Date time;

    public ApiResult() {
        this.time = new Date();
    }

    public static ApiResult<Boolean> result(int rows) {
        return result(rows > 0);
    }

    public static ApiResult<Boolean> result(boolean flag) {
        return flag ? ok() : fail();
    }

    public static ApiResult<Boolean> result(ResultEnum resultEnum) {
        return result(resultEnum, null);
    }

    public static <T> ApiResult<T> result(ResultEnum resultEnum, T data) {
        return result(resultEnum, (String) null, data);
    }

    public static <T> ApiResult<T> result(ResultEnum resultEnum, String message, T data) {
        boolean success = false;
        if (resultEnum.getCode() == ResultEnum.SUCCESS.getCode()) {
            success = true;
        }

        if (StringUtils.isBlank(message)) {
            message = resultEnum.getMessage();
        }

        return (ApiResult<T>) builder().status(resultEnum.getCode()).msg(message).data(data).success(success).time(new Date()).build();
    }

    public static ApiResult<Boolean> ok() {
        return ok(null);
    }

    public static <T> ApiResult<T> ok(T data) {
        return result(ResultEnum.SUCCESS, data);
    }

    public static <T> ApiResult<T> ok(T data, String message) {
        return result(ResultEnum.SUCCESS, message, data);
    }

    public static ApiResult<Map<String, Object>> okMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>(1);
        map.put(key, value);
        return ok(map);
    }

    public static ApiResult<Boolean> fail(ResultEnum resultEnum) {
        return result(resultEnum, null);
    }

    public static ApiResult<String> fail(String message) {
        return result(ResultEnum.FAIL, message, null);
    }

    public static <T> ApiResult<T> fail(ResultEnum resultEnum, T data) {
        if (ResultEnum.SUCCESS == resultEnum) {
            throw new RuntimeException("The status code for a failed result cannot be " + ResultEnum.SUCCESS.getCode());
        } else {
            return result(resultEnum, data);
        }
    }

    public static ApiResult<String> fail(Integer errorCode, String message) {
        return (new ApiResult()).setSuccess(false).setStatus(errorCode).setMessage(message);
    }

    public static ApiResult<Map<String, Object>> fail(String key, Object value) {
        Map<String, Object> map = new HashMap<>(1);
        map.put(key, value);
        return result(ResultEnum.FAIL, map);
    }

    public static ApiResult<Boolean> fail() {
        return fail(ResultEnum.FAIL);
    }

    public static <T> ApiResultBuilder<T> builder() {
        return new ApiResultBuilder<>();
    }

    public int getStatus() {
        return this.status;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public Date getTime() {
        return this.time;
    }

    public ApiResult<T> setStatus(final int status) {
        this.status = status;
        return this;
    }

    public ApiResult<T> setSuccess(final boolean success) {
        this.success = success;
        return this;
    }

    public ApiResult<T> setMessage(final String message) {
        this.message = message;
        return this;
    }

    public ApiResult<T> setData(final T data) {
        this.data = data;
        return this;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public ApiResult<T> setTime(final Date time) {
        this.time = time;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ApiResult)) return false;
        ApiResult<?> other = (ApiResult<?>) o;
        if (!other.canEqual(this)) return false;
        if (this.getStatus() != other.getStatus()) return false;
        if (this.isSuccess() != other.isSuccess()) return false;

        Object this$msg = this.getMessage();
        Object other$msg = other.getMessage();
        if (this$msg == null ? other$msg != null : !this$msg.equals(other$msg)) return false;

        Object this$data = this.getData();
        Object other$data = other.getData();
        if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;

        Object this$time = this.getTime();
        Object other$time = other.getTime();
        return this$time == null ? other$time == null : this$time.equals(other$time);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ApiResult;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getStatus();
        result = result * PRIME + (this.isSuccess() ? 79 : 97);
        Object $msg = this.getMessage();
        result = result * PRIME + ($msg == null ? 43 : $msg.hashCode());
        Object $data = this.getData();
        result = result * PRIME + ($data == null ? 43 : $data.hashCode());
        Object $time = this.getTime();
        result = result * PRIME + ($time == null ? 43 : $time.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ApiResult(status=" + this.getStatus() + ", success=" + this.isSuccess() + ", message=" + this.getMessage() + ", data=" + this.getData() + ", time=" + this.getTime() + ")";
    }

    public ApiResult(final int status, final boolean success, final String message, final T data, final Date time) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
        this.time = time;
    }

    public static class ApiResultBuilder<T> {
        private int status;
        private boolean success;
        private String msg;
        private T data;
        private Date time;

        ApiResultBuilder() {
        }

        public ApiResultBuilder<T> status(final int status) {
            this.status = status;
            return this;
        }

        public ApiResultBuilder<T> success(final boolean success) {
            this.success = success;
            return this;
        }

        public ApiResultBuilder<T> msg(final String msg) {
            this.msg = msg;
            return this;
        }

        public ApiResultBuilder<T> data(final T data) {
            this.data = data;
            return this;
        }

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        public ApiResultBuilder<T> time(final Date time) {
            this.time = time;
            return this;
        }

        public ApiResult<T> build() {
            return new ApiResult<>(this.status, this.success, this.msg, this.data, this.time);
        }

        @Override
        public String toString() {
            return "ApiResult.ApiResultBuilder(status=" + this.status + ", success=" + this.success + ", msg=" + this.msg + ", data=" + this.data + ", time=" + this.time + ")";
        }
    }
}