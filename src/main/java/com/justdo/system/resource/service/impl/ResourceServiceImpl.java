package com.justdo.system.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.common.utils.Constant;
import com.justdo.system.resource.entity.Resource;
import com.justdo.system.resource.mapper.ResourceMapper;
import com.justdo.system.resource.service.ResourceService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Lazy
@Service
@AllArgsConstructor
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper,Resource> implements ResourceService {

	private final ResourceMapper resourceMapper;
	
	@Override
	public List<Resource> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<Resource> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<Resource> userMenuList = new ArrayList<>();
		for(Resource menu : menuList){
			if(menuIdList.contains(menu.getResourceId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<Resource> queryListParentId(Long parentId) {
		QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
		queryWrapper
				.eq("parent_id",parentId)
				.orderByAsc("order_num");
		return resourceMapper.selectList(queryWrapper);
	}

	@Override
	public List<Resource> queryNotButtonList() {
		return resourceMapper.queryNotButtonList();
	}

	@Override
	public List<Resource> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = resourceMapper.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}


	@Override
	public List<Resource> queryUserList(Long userId) {
		return resourceMapper.queryUserList(userId);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<Resource> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<Resource> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<Resource> getMenuTreeList(List<Resource> menuList, List<Long> menuIdList){
		List<Resource> subMenuList = new ArrayList<Resource>();
		
		for(Resource entity : menuList){
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getResourceId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
