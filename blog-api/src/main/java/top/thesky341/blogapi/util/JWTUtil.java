package top.thesky341.blogapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.security.KeyStoreUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;

/**
 * @author thesky
 * @date 2021/2/13
 */
@ConfigurationProperties(prefix = "jwt")
@Component("jwtUtil")
public class JWTUtil implements InitializingBean {
    private String secret;
    private Long expire;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(secret);
        System.out.println(expire);
        try {
            key = Keys.hmacShaKeyFor(secret.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public JWTUtil() {
    }

    public String generateToken(Integer userId) {
        Date nowDate = new Date();
        Date expiredDate = new Date(nowDate.getTime() + expire);
        return Jwts.builder().setSubject(userId + "")
                .setIssuedAt(nowDate)
                .setExpiration(expiredDate)
                .signWith(key)
                .compact();
    }

    public Integer parseToken(String token) {
        return Integer.valueOf(Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject());
    }

    public boolean isTokenExpired(Date time) {
        return time.before(new Date());
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }
}
