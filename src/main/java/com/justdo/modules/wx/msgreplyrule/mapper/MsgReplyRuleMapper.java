package com.justdo.modules.wx.msgreplyrule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.justdo.modules.wx.msgreplyrule.entity.MsgReplyRule;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@CacheNamespace(flushInterval = 300000L)//缓存五分钟过期
public interface MsgReplyRuleMapper extends BaseMapper<MsgReplyRule> {
}
