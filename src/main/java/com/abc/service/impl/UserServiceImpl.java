package com.abc.service.impl;

import com.abc.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abc.mapper.UserMapper;
import com.abc.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yan
 * @since 2020-04-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public PageInfo<User> search(Integer pageNum, Integer rows, User user) {
        PageHelper.startPage(pageNum, rows);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>(user);
        List<User> userList = userMapper.selectList(wrapper);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public User findOne(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public void deleteOne(Integer id) {
        userMapper.deleteById(id);
        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(id);
        map.put("userList", list);
        rabbitTemplate.convertAndSend("userExchange", "userDirectRouting", map);
    }

    @Override
    public void delete(Integer[] ids) {
        userMapper.deleteBatchIds(CollUtil.newArrayList(ids));
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("userList", CollUtil.newArrayList(ids));
        rabbitTemplate.convertAndSend("userExchange", "userDirectRouting", map);
    }

}
