package top.thesky341.blogapi.util.encrypt;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 封装好的 MD5 盐值加密类
 * @author thesky
 * @date 2020/12/5
 */
public class MD5SaltEncryption {
    /**
     * 实现 MD5 盐值加密
     * @param credentials 待加密的凭证/数据
     * @param salt 盐值
     * @return 返回加密好的数据
     */
    public static String encrypt(String credentials, String salt) {
        String algorithmName = "MD5";
        ByteSource saltBytes = ByteSource.Util.bytes(salt);
        //加密次数
        int iterations = 3;

        return new SimpleHash(algorithmName, credentials, saltBytes, iterations).toString();
    }
}
