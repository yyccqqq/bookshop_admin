package com.abc.component.shiro;


import com.abc.pojo.Admin;
import com.abc.service.IAdminService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyReleam extends AuthorizingRealm {

    @Autowired
    private IAdminService adminService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权认证。。。。");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        Admin admin = adminService.findAdmin(username);
        if(admin == null){
            throw new UnknownAccountException("账号不存在");
        } else{
            return new SimpleAuthenticationInfo(admin, admin.getPassword(), getName());
        }
    }
}
