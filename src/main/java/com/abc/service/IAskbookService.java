package com.abc.service;

import com.abc.pojo.Askbook;
import com.abc.pojo.vo.AskbookVo;
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
public interface IAskbookService extends IService<Askbook> {

	PageInfo<Askbook> search(Integer pageNum, Integer rows, Askbook askbook);

	AskbookVo findOne(Integer id);

	void delete(Integer[] ids);

}
