package top.thesky341.blogapi.util.encrypt;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5SaltEncryption {
    public static String encrypt(String credentials, String salt) {
        String algorithmName = "MD5";
        ByteSource saltBytes = ByteSource.Util.bytes(salt);
        int iterations = 3;

        return new SimpleHash(algorithmName, credentials, saltBytes, iterations).toString();
    }
}
