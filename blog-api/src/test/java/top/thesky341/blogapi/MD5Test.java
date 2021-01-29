package top.thesky341.blogapi;

import org.junit.jupiter.api.Test;
import top.thesky341.blogapi.util.encrypt.MD5SaltEncryption;

/**
 * @author thesky
 * @date 2020/12/8
 */
public class MD5Test {
    @Test
    public void md5ResultLength() {
        System.out.println(MD5SaltEncryption.encrypt("abcd1234", "abcdqwe").length());
    }
}
