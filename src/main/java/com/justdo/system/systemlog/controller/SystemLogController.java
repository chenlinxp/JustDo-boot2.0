package com.justdo.system.systemlog.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.justdo.common.annotation.Log;
import com.justdo.common.base.AbstractController;
import com.justdo.common.utils.R;
import com.justdo.system.systemlog.entity.SystemLog;
import com.justdo.system.systemlog.service.SystemLogService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 系统日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/system/log")
public class SystemLogController extends AbstractController {
	private final SystemLogService systemLogService;


	/**
	 * 系统日志列表
	 */
	@Log("系统日志列表")
	@GetMapping("/list")
	@RequiresPermissions("system:log:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		QueryWrapper<SystemLog> queryWrapper = new QueryWrapper<>();
		if(MapUtil.getStr(params,"key") != null){
			queryWrapper
					.like("username", MapUtil.getStr(params,"key"))
					.or()
					.like("operation", MapUtil.getStr(params,"key"));
		}
		IPage<SystemLog> systemLogList = systemLogService.page(mpPageConvert.<SystemLog>pageParamConvert(params),queryWrapper);
		return R.ok().put("page", mpPageConvert.pageValueConvert(systemLogList));
	}
	
}
