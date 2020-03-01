package com.justdo.modules.wx.wxqrcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.justdo.modules.wx.wxqrcode.entity.WxQrCode;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公众号带参二维码
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2020-01-19 16:02:20
 */
@Mapper
@CacheNamespace(flushInterval = 300000L)//缓存五分钟过期
public interface WxQrCodeMapper extends BaseMapper<WxQrCode> {
	
}
