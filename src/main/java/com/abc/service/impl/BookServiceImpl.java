package com.abc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abc.mapper.BookMapper;
import com.abc.mapper.BookimageMapper;
import com.abc.mapper.CategoryMapper;
import com.abc.mapper.UserMapper;
import com.abc.pojo.Book;
import com.abc.pojo.Bookimage;
import com.abc.pojo.Category;
import com.abc.pojo.User;
import com.abc.pojo.vo.BookVo;
import com.abc.service.IBookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BookimageMapper bookimageMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public PageInfo<BookVo> search(Integer pageNum, Integer rows, Book book) {
        PageHelper.startPage(pageNum, rows);
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(book.getName())) {
            wrapper.like(Book::getName, book.getName());
        }
        wrapper.eq(Book::getBookType, 1).or().eq(Book::getBookType, 3);
        List<Book> bookList = bookMapper.selectList(wrapper);
        PageInfo<Book> pageInfo = new PageInfo<>(bookList);

        List<BookVo> bookVos = new ArrayList<>();
        for (Book book2 : pageInfo.getList()) {
            BookVo bookVo = getBookVo(book2);
            bookVos.add(bookVo);
        }

        PageInfo<BookVo> pageInfo2 = new PageInfo<>();
        pageInfo2.setList(bookVos);
        pageInfo2.setTotal(pageInfo.getTotal());
        return pageInfo2;
    }

    @Override
    public BookVo findOne(Integer id) {
        Book book = bookMapper.selectById(id);
        BookVo bookVo = getBookVo(book);
        return bookVo;
    }

    private BookVo getBookVo(Book book2) {
        String bookStr = JSONUtil.toJsonStr(book2);
        BookVo bookVo = JSONUtil.toBean(bookStr, BookVo.class);
        Category category = categoryMapper.selectById(book2.getCid());
        bookVo.setCategory(category.getName());
        User user = userMapper.selectById(book2.getUid());
        if (user != null) {
            bookVo.setUsername(user.getName());
        } else {
            bookVo.setUsername("用户已注销");
        }
        LambdaQueryWrapper<Bookimage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bookimage::getBookId, book2.getId());
        Bookimage bookimage = bookimageMapper.selectOne(wrapper);
        bookVo.setImage(bookimage.getImage());
        return bookVo;
    }

    @Override
    public void delete(Integer[] ids) {
        bookMapper.deleteBatchIds(CollUtil.newArrayList(ids));
    }

    @Override
    public PageInfo<BookVo> findBookReview(Integer pageNum, Integer rows) {
        PageHelper.startPage(pageNum, rows);
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getBookType, 0);
        List<Book> books = bookMapper.selectList(wrapper);
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        List<BookVo> bookVos = new ArrayList<>();
        for (Book book : pageInfo.getList()) {
            BookVo bookVo = getBookVo(book);
            bookVos.add(bookVo);
        }
        PageInfo<BookVo> pageInfo2 = new PageInfo<>();
        pageInfo2.setList(bookVos);
        pageInfo2.setTotal(pageInfo.getTotal());
        return pageInfo2;
    }

    @Override
    public void updateBookType(Integer[] ids, Integer bookType) {
        for (Integer id : ids) {
            LambdaUpdateWrapper<Book> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(Book::getId, id).set(Book::getBookType, bookType);
            bookMapper.update(null, wrapper);
        }
        if (bookType == 1) {
            Map<String, List<Integer>> map = new HashMap<>();
            map.put("bookIdList", CollUtil.newArrayList(ids));
            rabbitTemplate.convertAndSend("topicExchange", "topic.add", map);
        }
    }

    @Override
    public void updateOne(Integer id, Integer bookType) {
        LambdaUpdateWrapper<Book> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Book::getId, id).set(Book::getBookType, bookType);
        bookMapper.update(null, wrapper);
        if (bookType == 1) {
            List<Integer> bookIdList = new ArrayList<>();
            bookIdList.add(id);
            Map<String, List<Integer>> map = new HashMap<>();
            map.put("bookIdList",bookIdList);
            rabbitTemplate.convertAndSend("topicExchange", "topic.add", map);
        }
    }

}
