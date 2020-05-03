package com.abc.component.rabbitmq.listener;

import java.io.BufferedOutputStream;
import java.util.List;
import java.util.Map;

import com.abc.component.beetl.LocalDateTimeFormat;
import com.abc.mapper.BookMapper;
import com.abc.mapper.BookimageMapper;
import com.abc.mapper.UserMapper;
import com.abc.pojo.Book;
import com.abc.pojo.Bookimage;
import com.abc.pojo.User;
import com.abc.pojo.vo.BookVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.WebAppResourceLoader;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;

@Component
@RabbitListener(queues = "topic.html")
public class HtmlQueueListener {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookimageMapper bookimageMapper;

    @Autowired
    private UserMapper userMapper;

    @RabbitHandler
    public void process(Map<String, List<Integer>> message) {
        // 路径
        String path = "D:/Projects/bookshop07/src/main/resources/static/view/book/";

        // 获取数据
        List<Integer> bookIdList = message.get("bookIdList");
        for (Integer bookId : bookIdList) {
            Book book = bookMapper.selectById(bookId);
            // beetl初始化
            try {
                Configuration cfg = Configuration.defaultConfiguration();
                WebAppResourceLoader resourceLoader = new WebAppResourceLoader();
                resourceLoader.setRoot("src/main/resources/templates/");
                GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, cfg);
                groupTemplate.registerFormat("localDateTimeFormat", new LocalDateTimeFormat());
                Template template = groupTemplate.getTemplate("bookDetail.html");
                LambdaQueryWrapper<Bookimage> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Bookimage::getBookId, book.getId());
                Bookimage bookimage = bookimageMapper.selectOne(queryWrapper);
                String bookStr = JSONUtil.toJsonStr(book);
                BookVo bookVo = JSONUtil.toBean(bookStr, BookVo.class);
                bookVo.setImage(bookimage.getImage());
                User user = userMapper.selectById(bookVo.getUid());
                template.binding("bookVo", bookVo);
                template.binding("user", user);
                BufferedOutputStream out = FileUtil.getOutputStream(path + bookVo.getId() + ".html");
                template.renderTo(out);
            } catch (Exception e) {
               e.printStackTrace();
            } 
        }
    }
}