package com.abc.component.rabbitmq.listener;

import java.util.List;
import java.util.Map;

import com.abc.mapper.AskbookMapper;
import com.abc.mapper.BookMapper;
import com.abc.pojo.Askbook;
import com.abc.pojo.Book;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "userQueue")
public class UserQueueListener {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AskbookMapper askbookMapper;

    @RabbitHandler
    public void process(Map<String, List<Integer>> message) {
        List<Integer> userList = message.get("userList");
        for (Integer id : userList) {
            LambdaUpdateWrapper<Book> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(Book::getUid, id);
            bookMapper.delete(wrapper);
            LambdaUpdateWrapper<Askbook> wrapper2 = new LambdaUpdateWrapper<>();
            wrapper2.eq(Askbook::getUserId, id);
            askbookMapper.delete(wrapper2);
        }
    }
}