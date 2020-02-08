package com.justdo.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.system.user.entity.User;


import java.util.List;


/**
 * 系统用户
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface UserService extends IService<User> {

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	int updatePassword(Long userId, String password, String newPassword);
}
