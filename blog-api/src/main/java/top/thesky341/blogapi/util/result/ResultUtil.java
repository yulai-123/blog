package top.thesky341.blogapi.util.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import top.thesky341.blogapi.util.JWTUtil;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *@author thesky
 *@date 2020/12/5
 */
@Component
public class ResultUtil implements Serializable {

    @Resource(name = "jwtUtil")
    private JWTUtil jwtUtil;

    /**
     * 大多数数据均为 Map<String, Object> 类型，且只有一个数据
     * 将其封装，简化开发
     *
     * @param singleDataKey   唯一的数据的key
     * @param singleDataValue 唯一的数据的value
     * @return 返回封装好的 Result
     */
    public Result success(String singleDataKey, Object singleDataValue) {
        Map<String, Object> data = new HashMap<>();
        data.put(singleDataKey, singleDataValue);
        return success(data);
    }

    public Result success() {
        return success(new HashMap<String, Object>());
    }

    public Result success(Map<String, Object> data) {
        return new JWTResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public Result error() {
        return error(new HashMap<String, Object>());
    }

    public Result error(Map<String, Object> data) {
        return new JWTResult(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage(), data);
    }

    public Result getResult(ResultCode resultCode) {
        return getResult(resultCode, new HashMap<String, Object>());
    }

    public Result getResult(ResultCode resultCode, Map<String, Object> data) {
        return new JWTResult(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public class JWTResult extends Result {
        public JWTResult(int code, String message, Map<String, Object> data) {
            super(code, message, data);
            Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                int userId = (int) subject.getPrincipal();
                String authToken = jwtUtil.generateToken(userId);
                data.put("Auth-Token", authToken);
            }
        }
    }
}
