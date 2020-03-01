package com.justdo.modules.wx.wxqrcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.common.utils.PageUtils;
import com.justdo.modules.wx.wxqrcode.entity.WxQrCode;
import com.justdo.modules.wx.wxqrcode.form.WxQrCodeForm;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

import java.util.Map;

/**
 * 公众号带参二维码
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2020-01-19 16:02:20
 */
public interface WxQrCodeService extends IService<WxQrCode> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 创建公众号带参二维码
     * @param form
     * @return
     */
    WxMpQrCodeTicket createQrCode(WxQrCodeForm form) throws WxErrorException;
}

