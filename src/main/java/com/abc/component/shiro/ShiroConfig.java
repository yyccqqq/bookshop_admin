package com.abc.component.shiro;

import java.util.Properties;

import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;



@Configuration
public class ShiroConfig {

    @Bean
    public MyReleam myReleam() {
        return new MyReleam();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myReleam());
        return securityManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/css/**", "anon");
        definition.addPathDefinition("/img/**", "anon");
        definition.addPathDefinition("/js/**", "anon");
        definition.addPathDefinition("/plugins/**", "anon");
        definition.addPathDefinition("/layui/**", "anon");
        definition.addPathDefinition("/admin/login", "anon");
        definition.addPathDefinition("/admin/**", "authc");
        definition.addPathDefinition("/**", "authc");
        return definition;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/403.html");
        properties.setProperty("org.apache.shiro.authz.UnauthenticatedException", "/login.html");
        resolver.setExceptionMappings(properties);
        return resolver;
    }

}