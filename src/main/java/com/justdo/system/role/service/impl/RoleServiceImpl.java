package com.justdo.system.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.common.exception.RRException;
import com.justdo.config.ConstantConfig;
import com.justdo.system.role.entity.Role;
import com.justdo.system.role.mapper.RoleMapper;
import com.justdo.system.role.service.RoleResourceService;
import com.justdo.system.role.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 角色
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */

@Lazy
@Service
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements RoleService {

	private final RoleMapper roleMapper;
	private final RoleResourceService roleResourceService;

	@Override
	@Transactional
	public boolean save(Role role) {
		role.setCreateTime(new Date());
		roleMapper.insert(role);
		
		//检查权限是否越权
		checkPrems(role);
		
		//保存角色与菜单关系
		roleResourceService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
		return true;
	}

	@Transactional
	public void update(Role role) {
		roleMapper.updateById(role);
		
		//检查权限是否越权
		checkPrems(role);
		
		//更新角色与菜单关系
		roleResourceService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	}

	@Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return roleMapper.queryRoleIdList(createUserId);
	}

	/**
	 * @Author
	 * @Description //TODO deleteById  报 Parameter 'roleId' not found. Available parameters are [array]
	 * @Date 15:58 2019/4/18
	 * @Param [ids]
	 * @return void
	 **/
	@Override
	public void deleteBath(Long[] ids) {
		baseMapper.deleteBatchIds(Arrays.asList(ids));
		//Arrays.stream(ids).forEach(id->baseMapper.deleteById(id));
	}

	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(Role role){
		//如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if(role.getCreateUserId() == ConstantConfig.SUPER_ADMIN){
			return ;
		}
		
		//查询用户所拥有的菜单列表
		List<Long> menuIdList = roleMapper.queryAllMenuId(role.getCreateUserId());
		
		//判断是否越权
		if(!menuIdList.containsAll(role.getMenuIdList())){
			throw new RRException("新增角色的权限，已超出你的权限范围");
		}
	}
}
