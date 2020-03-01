package com.justdo.modules.wx.msgtemplate.controller;

import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.R;
import com.justdo.modules.wx.msgtemplate.entity.TemplateMsgLog;
import com.justdo.modules.wx.msgtemplate.service.TemplateMsgLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 模版消息发送记录
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/2/29 下午9:07
 */
@RestController
@RequestMapping("/manage/templatemsglog")
public class TemplateMsgLogController {
    @Autowired
    private TemplateMsgLogService templateMsgLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wx:templatemsglog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = templateMsgLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{logId}")
    @RequiresPermissions("wx:templatemsglog:info")
    public R info(@PathVariable("logId") Integer logId){
		TemplateMsgLog templateMsgLog = templateMsgLogService.getById(logId);

        return R.ok().put("templateMsgLog", templateMsgLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wx:templatemsglog:save")
    public R save(@RequestBody TemplateMsgLog templateMsgLog){
		templateMsgLogService.save(templateMsgLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wx:templatemsglog:update")
    public R update(@RequestBody TemplateMsgLog templateMsgLog){
		templateMsgLogService.updateById(templateMsgLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wx:templatemsglog:delete")
    public R delete(@RequestBody Integer[] logIds){
		templateMsgLogService.removeByIds(Arrays.asList(logIds));

        return R.ok();
    }

}
