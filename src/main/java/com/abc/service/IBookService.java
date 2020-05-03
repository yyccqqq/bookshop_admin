package com.abc.service;

import com.abc.pojo.Book;
import com.abc.pojo.vo.BookVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yan
 * @since 2020-04-19
 */
public interface IBookService extends IService<Book> {

	PageInfo<BookVo> search(Integer pageNum, Integer rows, Book book);

	BookVo findOne(Integer id);

	void delete(Integer[] ids);

	PageInfo<BookVo> findBookReview(Integer pageNum, Integer rows);

	void updateBookType(Integer[] ids, Integer bookType);

	void updateOne(Integer id, Integer bookType);

}
