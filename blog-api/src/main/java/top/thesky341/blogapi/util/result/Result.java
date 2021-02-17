package top.thesky341.blogapi.util.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Map;

/**
 * @author thesky
 * @date 2021/2/17
 */
public class Result implements Serializable {
    /**
     * 状态码
     */
    @JsonProperty
    protected int code;
    /**
     * 提示信息
     */
    @JsonProperty
    protected String message;
    /**
     * 返回的数据
     */
    @JsonProperty
    protected Map<String, Object> data;

    public Result() {}

    public Result(int code, String message, Map<String, Object> data) {
        Assert.notNull(data, "data不允许为null");
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
