package top.thesky341.blogapi.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

/**
 * 封装了常用的操作
 * @author thesky
 * @date 2020/12/5
 */
public class Common {
    /**
     * 返回当前用户的用户名，需要先登录
     */
    @RequiresAuthentication
    public static String getCurrentUserName() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }
}
