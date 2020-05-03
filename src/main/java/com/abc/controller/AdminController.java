package com.abc.controller;

import com.abc.pojo.Admin;
import com.abc.pojo.vo.Result;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.crypto.SecureUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yan
 * @since 2020-04-19
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @PostMapping("/login")
    public Result login(@RequestBody Admin  admin){
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(admin.getUsername(),SecureUtil.md5(admin.getPassword())));
            return new Result(true,"登录成功");
        } catch (Exception e) {
            return new Result(false,"登录失败");
        }
    }

    @GetMapping("/logout")
    public Result logout(){
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return new Result(true,"退出成功");
        } catch (Exception e) {
            return new Result(false,"退出失败");
        }
     
    }
}
