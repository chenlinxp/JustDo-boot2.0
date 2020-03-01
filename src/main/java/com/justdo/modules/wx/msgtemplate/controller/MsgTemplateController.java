package com.justdo.modules.wx.msgtemplate.controller;

import com.justdo.common.base.AbstractController;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.R;
import com.justdo.modules.wx.msgtemplate.entity.MsgTemplate;
import com.justdo.modules.wx.msgtemplate.service.MsgTemplateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 消息模板
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/2/29 下午9:07
 */
@RestController
@RequestMapping("/manage/msgtemplate")
public class MsgTemplateController extends AbstractController {
    @Autowired
    private MsgTemplateService msgTemplateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wx:msgtemplate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = msgTemplateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{templateId}")
    @RequiresPermissions("wx:msgtemplate:info")
    public R info(@PathVariable("templateId") String templateId){
		MsgTemplate msgTemplate = msgTemplateService.getById(templateId);

        return R.ok().put("msgTemplate", msgTemplate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wx:msgtemplate:save")
    public R save(@RequestBody MsgTemplate msgTemplate){
		msgTemplateService.save(msgTemplate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wx:msgtemplate:update")
    public R update(@RequestBody MsgTemplate msgTemplate){
		msgTemplateService.updateById(msgTemplate);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wx:msgtemplate:delete")
    public R delete(@RequestBody String[] templateIds){
		msgTemplateService.removeByIds(Arrays.asList(templateIds));

        return R.ok();
    }

}
