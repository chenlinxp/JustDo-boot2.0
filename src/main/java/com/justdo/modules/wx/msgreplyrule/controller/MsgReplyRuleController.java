package com.justdo.modules.wx.msgreplyrule.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.R;
import com.justdo.config.RegexConstant;
import com.justdo.modules.wx.msgreplyrule.entity.MsgReplyRule;
import com.justdo.modules.wx.msgreplyrule.service.MsgReplyRuleService;
import me.chanjar.weixin.common.api.WxConsts;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * 自动回复规则
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/3/1 上午11:19
 */
@RestController
@RequestMapping("/wx/msgreplyrule")
public class MsgReplyRuleController {
    @Autowired
    private MsgReplyRuleService msgReplyRuleService;

    /**
     * 获取自动回复规则列表
     */
    @Log("获取自动回复规则列表")
    @GetMapping("/list")
    @RequiresPermissions("wx:msgreplyrule:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = msgReplyRuleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 获取自动回复规则信息
     */
    @Log("获取自动回复规则信息")
    @GetMapping("/info/{ruleId}")
    @RequiresPermissions("wx:msgreplyrule:info")
    public R info(@PathVariable("ruleId") Integer ruleId){
		MsgReplyRule msgReplyRule = msgReplyRuleService.getById(ruleId);

        return R.ok().put("msgReplyRule", msgReplyRule);
    }

    /**
     * 保存自动回复规则
     */
    @Log("保存自动回复规则")
    @PostMapping("/save")
    @RequiresPermissions("wx:msgreplyrule:save")
    public R save(@RequestBody MsgReplyRule msgReplyRule){
        if(WxConsts.KefuMsgType.NEWS.equals(msgReplyRule.getReplyType()) &&
                !Pattern.matches(RegexConstant.NUMBER_ARRAY, msgReplyRule.getReplyContent())){
            return R.error("图文消息ID格式不正确");
        }
		msgReplyRuleService.save(msgReplyRule);

        return R.ok();
    }

    /**
     * 修改自动回复规则
     */
    @Log("修改自动回复规则")
    @PostMapping("/update")
    @RequiresPermissions("wx:msgreplyrule:update")
    public R update(@RequestBody MsgReplyRule msgReplyRule){
		msgReplyRuleService.updateById(msgReplyRule);

        return R.ok();
    }

    /**
     * 删除自动回复规则
     */
    @Log("删除自动回复规则")
    @PostMapping("/delete")
    @RequiresPermissions("wx:msgreplyrule:delete")
    public R delete(@RequestBody Integer[] ruleIds){
		msgReplyRuleService.removeByIds(Arrays.asList(ruleIds));

        return R.ok();
    }

}
