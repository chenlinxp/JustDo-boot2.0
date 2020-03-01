package com.justdo.modules.wx.msgtemplate.service;


import com.justdo.modules.wx.msgtemplate.form.TemplateMsgForm;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.scheduling.annotation.Async;

/**
 * 发送模板消息
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2020-01-19 16:02:20
 */
public interface TemplateMsgService {
    /**
     * 发送微信模版消息
     */
    @Async
    void sendTemplateMsg(WxMpTemplateMessage msg);

    /**
     * 以默认方式向特定用户发送消息
     * @param form
     */
    void sendToUser(TemplateMsgForm form);
}
