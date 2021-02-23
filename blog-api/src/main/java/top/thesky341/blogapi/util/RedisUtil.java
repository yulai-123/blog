package top.thesky341.blogapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * @author thesky
 * @date 2021/2/23
 */
@Component
public class RedisUtil {
    /**
     * 默认过期时间，单位为秒
     */
    private final long DEFAULT_EXPIRE_TIME = 3 * 60 * 60;

    @Autowired
    private RedisTemplate<String, Object> template;

    public boolean updateExpire(String key) {
        return setExpire(key, DEFAULT_EXPIRE_TIME);
    }

    public boolean setExpire(String key, long time) {
        try {
            if(time > 0) {
                template.expire(key, time, TimeUnit.SECONDS);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getExpire(String key) {
        return template.getExpire(key, TimeUnit.SECONDS);
    }

    public boolean hasKey(String key) {
        try {
            return template.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void delKey(String key) {
        if(key != null) {
            template.delete(key);
        }
    }

    public Object getKey(String key) {
        return key == null ? null : template.opsForValue().get(key);
    }

    /**
     * 默认过期时间 3 小时
     * @param key
     * @param value
     * @return
     */
    public boolean setKey(String key, Object value) {
        return setKey(key, value, DEFAULT_EXPIRE_TIME);
    }

    public boolean setKey(String key, Object value, long time) {
        try {
            if(time > 0) {
                template.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                template.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
