package com.justdo.system.role.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.justdo.common.annotation.Log;
import com.justdo.common.base.AbstractController;
import com.justdo.common.utils.Constant;
import com.justdo.common.utils.R;
import com.justdo.common.validator.ValidatorUtils;
import com.justdo.system.role.entity.Role;
import com.justdo.system.role.entity.RoleResource;
import com.justdo.system.role.service.RoleResourceService;
import com.justdo.system.role.service.RoleService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 **/

@RestController
@RequestMapping("/sys/role")
@AllArgsConstructor
public class RoleController extends AbstractController {
	private final RoleService roleService;
	private final RoleResourceService roleResourceService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(@RequestParam Map<String, Object> params){
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			queryWrapper.eq("create_user_id",getUserId());
		}

		//查询列表数据
		if(MapUtil.getStr(params,"key") != null){
			queryWrapper
					.like("role_name", MapUtil.getStr(params,"key"));
		}
		IPage<Role> sysConfigList = roleService.page(mpPageConvert.<Role>pageParamConvert(params),queryWrapper);

		return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select(){
		Map<String, Object> map = new HashMap<>();
		List<Role> list;
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
		 list = roleService.list(Wrappers
					.<Role>query()
					.lambda()
					.eq(Role::getCreateUserId,getUserId())
			);
		}else {
			list = roleService.list();
		}
		return R.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId){
		Role role = roleService.getById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = roleResourceService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		
		return R.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@Log("保存角色")
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@RequestBody Role role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		roleService.save(role);
		
		return R.ok();
	}
	
	/**
	 * 修改角色
	 */
	@Log("修改角色")
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@RequestBody Role role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		roleService.updateById(role);
		
		return R.ok();
	}
	
	/**
	 * 删除角色
	 */
	@Log("删除角色")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Long[] roleIds){
		roleService.deleteBath(roleIds);
		return R.ok();
	}
}
