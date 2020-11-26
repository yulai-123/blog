package top.thesky341.blogapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.thesky341.blogapi.mapper.AdminMapper;

@SpringBootTest
public class MybatisTest {
    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void mapperTest() {
        System.out.println(adminMapper);
    }
}
