package com.abc.service.impl;

import com.abc.pojo.Askbook;
import com.abc.pojo.User;
import com.abc.pojo.vo.AskbookVo;

import java.util.List;

import com.abc.mapper.AskbookMapper;
import com.abc.mapper.UserMapper;
import com.abc.service.IAskbookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yan
 * @since 2020-04-19
 */
@Service
public class AskbookServiceImpl extends ServiceImpl<AskbookMapper, Askbook> implements IAskbookService {

    @Autowired
    private AskbookMapper askbookMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<Askbook> search(Integer pageNum, Integer rows, Askbook askbook) {
        PageHelper.startPage(pageNum, rows);
        LambdaQueryWrapper<Askbook> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(askbook.getName())) {
            wrapper.eq(Askbook::getName, askbook.getName());
        }
        List<Askbook> askBooks = askbookMapper.selectList(wrapper);
        PageInfo<Askbook> pageInfo = new PageInfo<>(askBooks);
        return pageInfo;
    }

    @Override
    public AskbookVo findOne(Integer id) {
        Askbook askbook = askbookMapper.selectById(id);
        String askbookStr = JSONUtil.toJsonStr(askbook);
        AskbookVo askbookVo = JSONUtil.toBean(askbookStr, AskbookVo.class);
        User user = userMapper.selectById(askbook.getUserId());
        askbookVo.setUsername(user.getName());
        return askbookVo;
    }

    @Override
    public void delete(Integer[] ids) {
       askbookMapper.deleteBatchIds(CollUtil.newArrayList(ids));
    }

}
