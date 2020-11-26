package top.thesky341.blogapi.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import top.thesky341.blogapi.entity.Admin;
import top.thesky341.blogapi.mapper.AdminMapper;

import javax.annotation.Resource;

public class UserRealm extends AuthorizingRealm {
    @Resource
    AdminMapper adminMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

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
