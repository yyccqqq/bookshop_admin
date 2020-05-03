package com.abc.controller;

import com.abc.pojo.User;
import com.abc.pojo.vo.Result;
import com.abc.service.IUserService;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yan
 * @since 2020-04-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/search/{pageNum}/{rows}")
    public PageInfo<User> search(@PathVariable Integer pageNum, @PathVariable Integer rows, @RequestBody User user) {
        return userService.search(pageNum,rows,user);
    }

    @GetMapping("/findOne/{id}")
    public User findOne(@PathVariable Integer id){
        return userService.findOne(id);
    }

    @GetMapping("/deleteOne/{id}")
    public Result deleteOne(@PathVariable Integer id){
       try {
           userService.deleteOne(id);
           return new Result(true,"删除成功");
       } catch (Exception e) {
           return new Result(false,"删除失败");
       }
    }

    @PostMapping("/delete")
    public Result delte(@RequestBody Integer[] ids){
        try {
            userService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            return new Result(false,"删除失败");
        }
    }

   
}
