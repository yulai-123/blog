package top.thesky341.blogapi.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import top.thesky341.blogapi.entity.Admin;
import top.thesky341.blogapi.mapper.AdminMapper;

import javax.annotation.Resource;

/**
 * @author thesky
 * @date 2021/2/14
 */
public class JWTRealm extends AuthorizingRealm {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        int userId = Integer.valueOf(((UsernamePasswordToken)token).getUsername());
        Admin admin = adminMapper.getAdminById(userId);
        if (admin == null) {
            throw new UnknownAccountException();
        } else {
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin.getId(), null, getName());
            return authenticationInfo;
        }
    }
}
