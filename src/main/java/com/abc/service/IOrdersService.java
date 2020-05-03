package com.abc.service;

import java.util.List;
import java.util.Map;

import com.abc.pojo.Orders;
import com.abc.pojo.vo.OrderVo;
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
public interface IOrdersService extends IService<Orders> {

	Map<String,List<String>> getData();

	PageInfo<Orders> search(Integer pageNum, Integer rows);

	OrderVo findOne(String id);

	void delete(String[] ids);

}
