package com.justdo.system.resource.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.base.AbstractController;
import com.justdo.common.exception.RRException;
import com.justdo.common.utils.Constant.MenuType;
import com.justdo.common.utils.R;
import com.justdo.system.resource.entity.Resource;
import com.justdo.system.resource.service.ResourceService;
import com.justdo.system.user.service.ShiroService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */

@RestController
@RequestMapping("/sys/menu")
@AllArgsConstructor
public class ResourceController extends AbstractController {
	private final ResourceService resourceService;
	private final ShiroService shiroService;

	/**
	 * 导航菜单
	 */
	@RequestMapping("/nav")
	public R nav(){
		List<Resource> menuList = resourceService.getUserMenuList(getUserId());
		Set<String> permissions = shiroService.getUserPermissions(getUserId());
		return R.ok().put("menuList", menuList).put("permissions", permissions);
	}
	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public List<Resource> list(){
		List<Resource> menuList = resourceService.list();

		return menuList;
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public R select(){
		//查询列表数据
		List<Resource> menuList = resourceService.queryNotButtonList();
		
		//添加顶级菜单
		Resource root = new Resource();
		root.setResourceId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public R info(@PathVariable("menuId") Long menuId){
		Resource menu = resourceService.getById(menuId);
		return R.ok().put("menu", menu);
	}
	
	/**
	 * 保存
	 */
	@Log("保存菜单")
	@RequestMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public R save(@RequestBody Resource menu){
		//数据校验
		verifyForm(menu);

		resourceService.save(menu);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@Log("修改菜单")
	@RequestMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public R update(@RequestBody Resource menu){
		//数据校验
		verifyForm(menu);

		resourceService.updateById(menu);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除菜单")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	public R delete(long menuId){
		//判断是否有子菜单或按钮
		List<Resource> menuList = resourceService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("请先删除子菜单或按钮");
		}

		resourceService.removeById(menuId);
		
		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(Resource menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			Resource parentMenu = resourceService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == MenuType.CATALOG.getValue() ||
				menu.getType() == MenuType.MENU.getValue()){
			if(parentType != MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == MenuType.BUTTON.getValue()){
			if(parentType != MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
