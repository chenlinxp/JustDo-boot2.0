package com.justdo.modules.appversion.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.justdo.common.annotation.Log;
import com.justdo.common.base.AbstractController;
import com.justdo.common.utils.R;
import com.justdo.modules.appversion.entity.AppVersion;
import com.justdo.modules.appversion.service.AppVersionService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * APP版本管理
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/apkversion/apkversion")
public class AppVersionController  extends AbstractController {

    private final AppVersionService appVersionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("apkversion:apkversion:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        QueryWrapper<AppVersion> queryWrapper = new QueryWrapper<>();
        IPage<AppVersion> sysConfigList = appVersionService.page(mpPageConvert.<AppVersion>pageParamConvert(params),queryWrapper);
        return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("apkversion:apkversion:info")
    public R info(@PathVariable("id") Long id){
        return R.ok().put("apkVersion", appVersionService.getById(id));
    }


    /**
     * 新增APK版本管理
     */
    @Log("新增APK版本管理数据")
    @RequestMapping("/save")
    @RequiresPermissions("apkversion:apkversion:save")
    public R save(@RequestBody AppVersion appVersion){
        appVersion.setUserId(getUserId());
        appVersionService.save(appVersion);
        return R.ok();
    }


    /**
     * 修改
     */
    @Log("修改APK版本管理数据")
    @RequestMapping("/update")
    @RequiresPermissions("apkversion:apkversion:update")
    public R update(@RequestBody AppVersion appVersion){
        appVersion.setUpdateTime(new Date());
		appVersionService.updateById(appVersion);
        return R.ok();
    }


    /**
     * 删除
     */
    @Log("删除APK版本管理数据")
    @RequestMapping("/delete")
    @RequiresPermissions("apkversion:apkversion:delete")
    public R delete(@RequestBody Long[] ids){
		appVersionService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
	
}
