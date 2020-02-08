package com.justdo.system.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.justdo.system.role.entity.Role;

import java.util.List;

/**
 * 角色管理
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface RoleMapper extends BaseMapper<Role> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

}
