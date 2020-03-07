package com.justdo.system.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.config.ConstantConfig;
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
	public List<Resource> queryListParentId(Long parentId, List<Long> resourceIdList) {
		List<Resource> resourceList = queryListParentId(parentId);
		if(resourceIdList == null){
			return resourceList;
		}
		
		List<Resource> userResourceList = new ArrayList<>();
		for(Resource resource : resourceList){
			if(resourceIdList.contains(resource.getResourceId())){
				userResourceList.add(resource);
			}
		}
		return userResourceList;
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
	public List<Resource> getUserResourceList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == ConstantConfig.SUPER_ADMIN){
			return getAllResourceList(null);
		}
		
		//用户菜单列表
		List<Long> resourceIdList = resourceMapper.queryAllResourceId(userId);
		return getAllResourceList(resourceIdList);
	}


	@Override
	public List<Resource> queryUserList(Long userId) {
		return resourceMapper.queryUserList(userId);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<Resource> getAllResourceList(List<Long> resourceIdList){
		//查询根菜单列表
		List<Resource> resourceList = queryListParentId(0L, resourceIdList);
		//递归获取子菜单
		getResourceTreeList(resourceList, resourceIdList);
		
		return resourceList;
	}

	/**
	 * 递归
	 */
	private List<Resource> getResourceTreeList(List<Resource> resourceList, List<Long> resourceIdList){
		List<Resource> subReourceList = new ArrayList<Resource>();
		
		for(Resource entity : resourceList){
			if(entity.getType() == ConstantConfig.ResourceType.CATALOG.getValue()){//目录
				entity.setList(getResourceTreeList(queryListParentId(entity.getResourceId(), resourceIdList), resourceIdList));
			}
			subReourceList.add(entity);
		}
		
		return subReourceList;
	}
}
