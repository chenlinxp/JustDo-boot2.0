package com.justdo.system.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.system.role.entity.Role;


import java.util.List;


/**
 * 角色
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface RoleService extends IService<Role> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);

	void deleteBath(Long[] ids);

}
