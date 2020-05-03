package com.abc.component.rabbitmq.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.abc.component.elasticsearch.ESBookRepository;
import com.abc.mapper.BookMapper;
import com.abc.mapper.BookimageMapper;
import com.abc.pojo.Book;
import com.abc.pojo.Bookimage;
import com.abc.pojo.vo.ESBook;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.index")
public class IndexQueueListener {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookimageMapper bookimageMapper;

    @Autowired
    private ESBookRepository esBookRepository;

    @RabbitHandler
    public void process(Map<String, List<Integer>> message){
        List<Integer> bookIdList = message.get("bookIdList");
        List<ESBook> esBookList = new ArrayList<>();
        for (Integer bookId : bookIdList) {
            Book book = bookMapper.selectById(bookId);
            ESBook esBook = new ESBook();
            esBook.setId(book.getId());
            esBook.setName(book.getName());
            esBook.setPrice(book.getPrice());
            esBook.setUid(book.getUid());
            esBook.setAuthor(book.getAuthor());
            esBook.setPress(book.getPress());
            esBook.setVersion(book.getVersion());
            esBook.setPublishDate(book.getPublishDate());
            LambdaQueryWrapper<Bookimage> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Bookimage::getBookId, book.getId());
            Bookimage bookimage = bookimageMapper.selectOne(queryWrapper);
            esBook.setImage(bookimage.getImage());
            esBookList.add(esBook);
        }
        esBookRepository.saveAll(esBookList);
    }
}