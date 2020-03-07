package com.justdo.system.resource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.system.resource.entity.Resource;


import java.util.List;


/**
 * 资源管理
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface ResourceService extends IService<Resource> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param resourceIdList  用户菜单ID
	 */
	List<Resource> queryListParentId(Long parentId, List<Long> resourceIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<Resource> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<Resource> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 */
	List<Resource> getUserResourceList(Long userId);

	/**
	 * 查询用户的权限列表
	 */
	List<Resource> queryUserList(Long userId);
}
