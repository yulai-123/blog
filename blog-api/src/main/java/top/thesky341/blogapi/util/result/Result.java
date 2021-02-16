package top.thesky341.blogapi.util.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import top.thesky341.blogapi.util.JWTUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *@author thesky
 *@date 2020/12/5
 */
public class Result implements Serializable {
    /**
     * 状态码
     */
    @JsonProperty
    private int code;
    /**
     * 提示信息
     */
    @JsonProperty
    private String message;
    /**
     * 返回的数据
     */
    @JsonProperty
    private Object data;

    @JsonProperty
    private String token;

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        Subject subject = SecurityUtils.getSubject();
        try {
            if(subject.isAuthenticated()) {
                JWTUtil
            }
        }
    }

    public Result() {
        this(-1, null, null);
    }

    public Result(int code, String message) {
        this(code, message, null);
    }

    public Result(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public Result(ResultCode resultCode, Object data) {
        this(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    /**
     * 大多数数据均为 Map<String, Object> 类型，且只有一个数据
     * 将其封装，简化开发
     * @param singleDataKey 唯一的数据的key
     * @param singleDataValue 唯一的数据的value
     * @return 返回封装好的 Result
     */
    public static Result success(String singleDataKey, Object singleDataValue) {
        Map<String, Object> data = new HashMap<>();
        data.put(singleDataKey, singleDataValue);
        return success(data);
    }

    public static Result error() {
        return new Result(ResultCode.ERROR);
    }

    public static Result error(Object data) {
        return new Result(ResultCode.ERROR, data);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
