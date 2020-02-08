package com.justdo.system.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.justdo.system.resource.entity.Resource;


import java.util.List;

/**
 * 菜单管理
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface ResourceMapper extends BaseMapper<Resource> {
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<Resource> queryNotButtonList();
	
	/**
	 * 查询用户的权限列表
	 */
	List<Resource> queryUserList(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
}
