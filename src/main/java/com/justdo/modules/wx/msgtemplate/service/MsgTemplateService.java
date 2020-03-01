package com.justdo.modules.wx.msgtemplate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.common.utils.PageUtils;
import com.justdo.modules.wx.msgtemplate.entity.MsgTemplate;

import java.util.Map;

/**
 * 消息模板
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2020-01-19 16:02:20
 */
public interface MsgTemplateService extends IService<MsgTemplate> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 通过模板名称查询
     * @param name
     * @return
     */
    MsgTemplate selectByName(String name);
}

