package com.justdo.system.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.system.role.entity.RoleResource;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface RoleResourceService  extends IService<RoleResource> {
	
	void saveOrUpdate(Long roleId, List<Long> ResourceIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryResourceIdList(Long roleId);
	
}
