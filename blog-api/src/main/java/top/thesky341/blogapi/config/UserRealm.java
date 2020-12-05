package top.thesky341.blogapi.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import top.thesky341.blogapi.entity.Admin;
import top.thesky341.blogapi.mapper.AdminMapper;

import javax.annotation.Resource;

/**
 * 用户用户登录认证以及权限查询
 * @author thesky
 * @date 2020/12/5
 */
public class UserRealm extends AuthorizingRealm {
    @Resource
    AdminMapper adminMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 登录认证
     * @param token 保存有用户名、密码等信息
     * 这里用于获取 AuthenticationInfo, 之后还需要在另一个进行密码匹配
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String name = ((UsernamePasswordToken)token).getUsername();
        Admin admin = adminMapper.getAdminByName(name);
        if(admin == null) {
            throw new UnknownAccountException();
        } else {
            ByteSource salt = ByteSource.Util.bytes(name);
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(name, admin.getPasswd(), salt, getName());
            return authenticationInfo;
        }
    }
}
