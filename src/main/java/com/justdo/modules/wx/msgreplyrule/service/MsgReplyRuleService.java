package com.justdo.modules.wx.msgreplyrule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.common.utils.PageUtils;
import com.justdo.modules.wx.msgreplyrule.entity.MsgReplyRule;

import java.util.List;
import java.util.Map;


/**
 * 公众号消息回复规则
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2020-01-19 16:02:20
 */
public interface MsgReplyRuleService extends IService<MsgReplyRule> {
    PageUtils queryPage(Map<String, Object> params);
    /**
     * 保存自动回复规则
     *
     * @param msgReplyRule
     */

    boolean save(MsgReplyRule msgReplyRule);

    /**
     * 获取所有的回复规则
     *
     * @return
     */
    List<MsgReplyRule> getRules();

    /**
     * 获取当前时段内所有有效的回复规则
     *
     * @return 有效的规则列表
     */
    List<MsgReplyRule> getValidRules();

    /**
     * 筛选符合条件的回复规则
     * @param exactMatch 是否精确匹配
     * @param keywords 关键词
     * @return 规则列表
     */
    List<MsgReplyRule> getMatchedRules(boolean exactMatch, String keywords);
}
