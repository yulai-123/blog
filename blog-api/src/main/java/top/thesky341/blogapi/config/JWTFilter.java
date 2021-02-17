package top.thesky341.blogapi.config;

import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import top.thesky341.blogapi.util.JWTUtil;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author thesky
 * @date 2021/2/13
 */
public class JWTFilter extends FormAuthenticationFilter {
    private JWTUtil jwtUtil = null;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Assert.notNull(jwtUtil, "jwtUtil 不允许为null");
        String token = getRequestToken((HttpServletRequest) request);
        if(token != null) {
            try {
                int userId = jwtUtil.parseToken(token);
                Subject subject = SecurityUtils.getSubject();
                System.out.println(subject);
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(String.valueOf(userId), "");
                subject.login(usernamePasswordToken);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("isAccessAllowed错误");
            }
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    private String getRequestToken(HttpServletRequest request) {
        String token = request.getHeader("Token");
        return token;
    }
}
