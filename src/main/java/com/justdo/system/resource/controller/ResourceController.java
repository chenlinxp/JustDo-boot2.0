package com.justdo.system.resource.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.base.AbstractController;
import com.justdo.common.exception.RRException;
import com.justdo.common.utils.R;
import com.justdo.config.ConstantConfig;
import com.justdo.system.resource.entity.Resource;
import com.justdo.system.resource.service.ResourceService;
import com.justdo.system.user.service.ShiroService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 系统资源菜单
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */

@RestController
@RequestMapping("/system/resource")
@AllArgsConstructor
public class ResourceController extends AbstractController {

	private final ResourceService resourceService;

	/**
	 * 所有菜单列表
	 */
	@Log("菜单列表")
	@GetMapping("/list")
	@RequiresPermissions("system:resource:list")
	public List<Resource> list(){
		List<Resource> resourceList = resourceService.list();

		return resourceList;
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */

	@GetMapping("/select")
	@RequiresPermissions("system:resource:select")
	public R select(){
		//查询列表数据
		List<Resource> resourceList = resourceService.queryNotButtonList();
		
		//添加顶级菜单
		Resource root = new Resource();
		root.setResourceId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		resourceList.add(root);
		
		return R.ok().put("resourceList", resourceList);
	}
	
	/**
	 * 菜单信息
	 */
	@Log("菜单详情")
	@GetMapping("/info/{resourceId}")
	@RequiresPermissions("system:resource:info")
	public R info(@PathVariable("resourceId") Long resourceId){
		Resource resource = resourceService.getById(resourceId);
		return R.ok().put("resource", resource);
	}
	
	/**
	 * 保存
	 */
	@Log("保存菜单")
	@PostMapping("/save")
	@RequiresPermissions("system:resource:save")
	public R save(@RequestBody Resource resource){
		//数据校验
		verifyForm(resource);

		resourceService.save(resource);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@Log("修改菜单")
	@PostMapping("/update")
	@RequiresPermissions("system:resource:update")
	public R update(@RequestBody Resource resource){
		//数据校验
		verifyForm(resource);

		resourceService.updateById(resource);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除菜单")
	@PostMapping("/delete")
	@RequiresPermissions("system:resource:delete")
	public R delete(long resourceId){
		//判断是否有子菜单或按钮
		List<Resource> resourceList = resourceService.queryListParentId(resourceId);
		if(resourceList.size() > 0){
			return R.error("请先删除子菜单或按钮");
		}

		resourceService.removeById(resourceId);
		
		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(Resource resource){
		if(StringUtils.isBlank(resource.getName())){
			throw new RRException("菜单名称不能为空");
		}
		
		if(resource.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(resource.getType() == ConstantConfig.ResourceType.MENU.getValue()){
			if(StringUtils.isBlank(resource.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = ConstantConfig.ResourceType.CATALOG.getValue();
		if(resource.getParentId() != 0){
			Resource parentMenu = resourceService.getById(resource.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(resource.getType() == ConstantConfig.ResourceType.CATALOG.getValue() ||
				resource.getType() == ConstantConfig.ResourceType.MENU.getValue()){
			if(parentType != ConstantConfig.ResourceType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(resource.getType() == ConstantConfig.ResourceType.BUTTON.getValue()){
			if(parentType != ConstantConfig.ResourceType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
