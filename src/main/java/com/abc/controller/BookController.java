package com.abc.controller;

import com.abc.pojo.Book;
import com.abc.pojo.vo.BookVo;
import com.abc.pojo.vo.Result;
import com.abc.service.IBookService;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yan
 * @since 2020-04-19
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @PostMapping("/search/{pageNum}/{rows}")
    public PageInfo<BookVo> search(@PathVariable Integer pageNum, @PathVariable Integer rows, @RequestBody Book book) {
        return bookService.search(pageNum, rows, book);
    }

    @GetMapping("/findOne/{id}")
    public BookVo findOne(@PathVariable Integer id) {
        return bookService.findOne(id);
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Integer[] ids) {
        try {
            bookService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }

    @GetMapping("/findBookReview/{pageNum}/{rows}")
    public PageInfo<BookVo> findBookReview(@PathVariable Integer pageNum, @PathVariable Integer rows) {
        return bookService.findBookReview(pageNum, rows);
    }

    @PutMapping(value = "/update/{bookType}")
    public Result update(@PathVariable Integer bookType, @RequestBody Integer[] ids) {
        try {
            bookService.updateBookType(ids, bookType);
            return new Result(true, "成功");
        } catch (Exception e) {
            return new Result(false, "失败");
        }
    }

    @GetMapping("/updateOne/{id}/{bookType}")
    public Result updateOne(@PathVariable Integer id,@PathVariable Integer bookType){
        try {
            bookService.updateOne(id, bookType);
            return new Result(true, "成功");
        } catch (Exception e) {
            return new Result(false, "失败");
        }
    }

}
