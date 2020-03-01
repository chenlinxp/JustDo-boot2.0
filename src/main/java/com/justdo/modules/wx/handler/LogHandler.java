package com.justdo.modules.wx.handler;

import com.google.gson.Gson;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class LogHandler extends AbstractHandler {

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
      Map<String, Object> context, WxMpService wxMpService,
      WxSessionManager sessionManager) {
    try {
      this.logger.debug("\n接收到请求消息，内容：{}", new Gson().toJson(wxMessage));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

}
