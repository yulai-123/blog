package top.thesky341.blogapi.util.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class Result implements Serializable {
    @JsonProperty
    private int code;
    @JsonProperty
    private String message;
    @JsonProperty
    private Object data;

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public Result(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
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
}
