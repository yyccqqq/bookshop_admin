package com.abc.service;

import com.abc.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yan
 * @since 2020-04-19
 */
public interface IAdminService extends IService<Admin> {

	Admin findAdmin(String username);

	

}
