package top.thesky341.blogapi.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import top.thesky341.blogapi.util.JWTUtil;

import javax.servlet.Filter;
import java.util.*;

@Configuration
public class ShiroConfig {
    /**
     * 设置凭证匹配时用的算法：MD5 盐值加密 3 次
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        String algorithmName = "MD5";
        int iterations = 3;

        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(algorithmName);
        credentialsMatcher.setHashIterations(iterations);

        return credentialsMatcher;
    }

    /**
     * userReaml 设置凭证匹配方式
     */
    @Bean("userRealm")
    public UserRealm getUserRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }

    @Bean("jwtMatcher")
    public JWTMatcher getJWTMatcher() {
        return new JWTMatcher();
    }

    @Bean("jwtRealm")
    public JWTRealm getJwtRealm(@Qualifier("jwtMatcher") JWTMatcher jwtMatcher) {
        JWTRealm jwtRealm = new JWTRealm();
        jwtRealm.setCredentialsMatcher(jwtMatcher);
        return jwtRealm;
    }

    @Bean("subjectFactory")
    public MySubjectFactory getMySubjectFactory() {
        return new MySubjectFactory();
    }

    /**
     * securityManager 设置 userRealm
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm,
                                           @Qualifier("jwtRealm") JWTRealm jwtRealm,
                                           @Qualifier("subjectFactory") MySubjectFactory subjectFactory) {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();

        List<Realm> realms = new ArrayList<>();
        realms.add(userRealm);
        realms.add(jwtRealm);

        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(realms);
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);


        //设置 securityManager 的 subjectDao 的 SessionStorageEvaluator 的 SessionStorageEnabled 为 false
        //因为面向接口编程，导致部分方法在接口中不存在却要使用，因此强转
        ((DefaultWebSessionStorageEvaluator)(((DefaultSubjectDAO)(securityManager.getSubjectDAO())).getSessionStorageEvaluator()))
                .setSessionStorageEnabled(false);

        securityManager.setSubjectFactory(subjectFactory);

        return securityManager;
    }

    /**
     * shiroFilterFactory 设置了 SecurityManager
     * 设置了权限，项目实际需要权限是通过注解方式声明的
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager,
                                                         @Qualifier("jwtUtil")JWTUtil jwtUtil) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> m = new HashMap<>();
        m.put("/**", "anon");
        m.put("/**", "jwtFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(m);
        shiroFilterFactoryBean.setLoginUrl("/shiro/login");
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwtFilter", new JWTFilter(jwtUtil));
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
 　　* 开启shiro aop注解支持.
 　　* 使用代理方式;所以需要开启代码支持;
 　　*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
