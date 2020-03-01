package com.justdo.modules.wx.handler;


import com.justdo.modules.wx.msgnews.service.WeixinMsgService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang
 */
@Component
public class MenuHandler extends AbstractHandler {
    @Autowired
    WeixinMsgService weixinMsgService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        if (WxConsts.EventType.VIEW.equals(wxMessage.getEvent())) {
            return null;
        }
        logger.info("菜单事件：" + wxMessage.getEventKey());
        weixinMsgService.wxReplyMsg(true,wxMessage.getFromUser(), wxMessage.getEventKey());
        return null;
    }


}
