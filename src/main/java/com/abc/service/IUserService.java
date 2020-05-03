package com.abc.service;

import com.abc.pojo.User;
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
public interface IUserService extends IService<User> {

	PageInfo<User> search(Integer pageNum, Integer rows, User user);

	User findOne(Integer id);

	void deleteOne(Integer id);

	void delete(Integer[] ids);

}
