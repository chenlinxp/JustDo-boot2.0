package com.justdo.system.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.justdo.system.role.entity.RoleResource;


import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface RoleResourceMapper extends BaseMapper<RoleResource> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryResourceIdList(Long roleId);


	/**
	 *  保存角色与菜单对应关系
	 *  @param map
	 */
	void saveUserResource(Map<String, Object> map);
}
