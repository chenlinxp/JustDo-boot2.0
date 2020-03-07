package com.justdo.system.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.system.role.entity.RoleResource;
import com.justdo.system.role.mapper.RoleResourceMapper;
import com.justdo.system.role.service.RoleResourceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 角色与菜单对应关系
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Service
@AllArgsConstructor
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper,RoleResource> implements RoleResourceService {

	private final RoleResourceMapper roleResourceMapper;

	@Override
	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> resourceIdList) {
		//先删除角色与菜单关系
		roleResourceMapper.deleteById(roleId);

		if(resourceIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("resourceIdList", resourceIdList);
		roleResourceMapper.saveUserResource(map);
	}

	@Override
	public List<Long> queryResourceIdList(Long roleId) {
		return roleResourceMapper.queryResourceIdList(roleId);
	}

}
