package com.justdo.system.configsettings.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.justdo.system.configsettings.entity.ConfigSettings;
import com.justdo.system.configsettings.service.ConfigSettingsService;
import com.justdo.common.annotation.Log;
import com.justdo.common.base.AbstractController;
import com.justdo.common.utils.R;
import com.justdo.common.validator.ValidatorUtils;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 * 系统参数信息
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@RestController
@RequestMapping("/sys/config")
@AllArgsConstructor
public class ConfigSettingsController extends AbstractController {

	private final ConfigSettingsService sysConfigService;
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		QueryWrapper<ConfigSettings> queryWrapper = new QueryWrapper<>();
		if(MapUtil.getStr(params,"key") != null){
			queryWrapper
					.like("remark", MapUtil.getStr(params,"key"));
		}
		IPage<ConfigSettings> sysConfigList = sysConfigService.page(mpPageConvert.<ConfigSettings>pageParamConvert(params),queryWrapper);

		return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public R info(@PathVariable("id") Long id){
		ConfigSettings config = sysConfigService.getById(id);
		
		return R.ok().put("config", config);
	}
	
	/**
	 * 保存配置
	 */
	@Log("保存配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public R save(@RequestBody ConfigSettings config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.save(config);
		
		return R.ok();
	}
	
	/**
	 * 修改配置
	 */
	@Log("修改配置")
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public R update(@RequestBody ConfigSettings config){
		ValidatorUtils.validateEntity(config);
		
		sysConfigService.updateById(config);
		
		return R.ok();
	}
	
	/**
	 * 删除配置
	 */
	@Log("删除配置")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public R delete(@RequestBody Long[] ids){
		sysConfigService.removeById((Serializable)Arrays.asList(ids));
		return R.ok();
	}

}
