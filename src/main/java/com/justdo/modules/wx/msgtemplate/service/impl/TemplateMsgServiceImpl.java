package com.justdo.modules.wx.msgtemplate.service.impl;

import com.justdo.common.exception.RRException;
import com.justdo.modules.wx.msgtemplate.entity.MsgTemplate;
import com.justdo.modules.wx.msgtemplate.entity.TemplateMsgLog;
import com.justdo.modules.wx.msgtemplate.form.TemplateMsgForm;
import com.justdo.modules.wx.msgtemplate.service.MsgTemplateService;
import com.justdo.modules.wx.msgtemplate.service.TemplateMsgLogService;
import com.justdo.modules.wx.msgtemplate.service.TemplateMsgService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateMsgServiceImpl implements TemplateMsgService {
	@Autowired
	private TemplateMsgLogService templateMsgLogService;
	private final WxMpService wxService;
	@Autowired
	MsgTemplateService msgTemplateService;

	/**
	 * 发送微信模版消息
	 */
	@Override
	@Async
	public void sendTemplateMsg(WxMpTemplateMessage msg) {
		String result =null;
		try {
			result = wxService.getTemplateMsgService().sendTemplateMsg(msg);
		} catch (WxErrorException e) {
			result= e.getMessage();
		}

		//保存发送日志
		TemplateMsgLog log = new TemplateMsgLog(msg, result);
		templateMsgLogService.addLog(log);
	}

	/**
	 * 以默认方式向特定用户发送消息
	 * @param form
	 */
	@Override
	public void sendToUser(TemplateMsgForm form){
		MsgTemplate msgTemplate = msgTemplateService.selectByName(form.getTemplate());
		if(msgTemplate==null){
			throw new RRException("消息模板不存在");
		}
		List<WxMpTemplateData> list = new LinkedList<>();
		String msg=msgTemplate.getData();
		if(msg==null){
			msg="";
		}
		msg=msg.replace("${msg}",form.getMsg());
		list.add(new WxMpTemplateData("first", msg));
		list.add(new WxMpTemplateData("keyword1", msgTemplate.getTitle(), msgTemplate.getColor()));
		list.add(new WxMpTemplateData("keyword2", LocalDate.now().toString(), msgTemplate.getColor()));
		WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder()
				.toUser(form.getOpenid())
				.templateId(msgTemplate.getTemplateId())
				.url(msgTemplate.getUrl())
				.build();
		wxMpTemplateMessage.setData(list);
		this.sendTemplateMsg(wxMpTemplateMessage);
	}


}
