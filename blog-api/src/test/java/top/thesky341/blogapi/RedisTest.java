package top.thesky341.blogapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author thesky
 * @date 2021/2/23
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisTest() {
        redisTemplate.opsForValue().set("test2", "qw");
        System.out.println(redisTemplate.opsForValue().get("test2"));
    }

    @Test
    public void test() {
        Integer a = 3;
        if (a instanceof Integer) {
            System.out.println("66");
        } else {
            System.out.println("q");
        }
    }
}
