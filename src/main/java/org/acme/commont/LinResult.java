package org.acme.commont;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LinResult implements Serializable {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    private Map<String, Object> data = new HashMap<>();

    public LinResult() {
    }

    public LinResult success() {
        this.code = 2000;
        this.message = "操作成功";
        return this;
    }

    public LinResult error() {
        this.code = 2001;
        this.message = "操作失败";
        return this;
    }

    public LinResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public LinResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public LinResult setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public LinResult setData(Object data) {
        this.data.put("data", data);
        return this;
    }

    public LinResult setData(String key, Object data) {
        this.data.put(key, data);
        return this;
    }

    @Override
    public String toString() {
        return "LinResult{" +
               "code=" + code +
               ", message='" + message + '\'' +
               ", data=" + data +
               '}';
    }
}
