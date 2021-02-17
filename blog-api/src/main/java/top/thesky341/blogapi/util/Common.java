package top.thesky341.blogapi.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import top.thesky341.blogapi.mapper.AdminMapper;

import javax.annotation.Resource;

/**
 * 封装了常用的操作
 *
 * @author thesky
 * @date 2020/12/5
 */
@Component
public class Common {
    @Autowired
    private AdminMapper adminMapper;
    /**
     * 返回当前用户的用户名，需要先登录
     */
    @RequiresAuthentication
    public String getCurrentUserName() {
        Assert.isTrue(SecurityUtils.getSubject().isAuthenticated(), "获取用户名，必须先登录");
        int userId = (int)(SecurityUtils.getSubject().getPrincipal());
        return adminMapper.getAdminById(userId).getName();
    }
}
