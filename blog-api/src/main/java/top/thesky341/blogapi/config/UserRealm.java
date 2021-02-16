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
     * 这里实际上并不是进行密码匹配的地方
     * 用户信息验证分为两个部分，先查询是否存在用户，如果存在则获取它的相关信息（例如密码），然后再进行密码匹配
     * 这里就是第一步，不过 Realm 也会负责密码匹配，只是这已经写好了，不需要我再进行重写
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
