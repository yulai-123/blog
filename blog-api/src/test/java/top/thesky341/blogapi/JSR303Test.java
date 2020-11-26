package top.thesky341.blogapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.thesky341.blogapi.entity.Admin;

@SpringBootTest
public class JSR303Test {
    @Test
    public void jsr303Test() {
        try {
            Admin admin = new Admin(9, "", "");
            System.out.println(admin);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
