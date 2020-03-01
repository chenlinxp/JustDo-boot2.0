package com.justdo.modules.wx.msgtemplate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.validator.Assert;
import com.justdo.modules.wx.msgtemplate.entity.MsgTemplate;
import com.justdo.modules.wx.msgtemplate.mapper.MsgTemplateMapper;
import com.justdo.modules.wx.msgtemplate.service.MsgTemplateService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("msgTemplateService")
public class MsgTemplateServiceImpl extends ServiceImpl<MsgTemplateMapper, MsgTemplate> implements MsgTemplateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MsgTemplate> page = this.page(
                new Query<MsgTemplate>().getPage(params),
                new QueryWrapper<MsgTemplate>()
        );

        return new PageUtils(page);
    }

    @Override
    public MsgTemplate selectByName(String name) {
        Assert.isBlank(name,"模板名称不得为空");
        return this.getOne(new QueryWrapper<MsgTemplate>()
                .eq("name",name)
                .eq("status",1)
                .last("LIMIT 1"));
    }

}