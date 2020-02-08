package com.justdo.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.justdo.system.user.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface UserMapper extends BaseMapper<User> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);

	void saveUserRole(Map<String, Object> map);
}
