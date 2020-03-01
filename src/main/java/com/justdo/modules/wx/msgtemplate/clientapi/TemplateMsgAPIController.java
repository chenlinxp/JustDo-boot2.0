package com.justdo.modules.wx.msgtemplate.clientapi;

import com.justdo.common.utils.IPUtils;
import com.justdo.common.utils.R;
import com.justdo.modules.wx.msgtemplate.form.TemplateMsgForm;
import com.justdo.modules.wx.msgtemplate.service.TemplateMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 消息模板的发送
 */
@RestController
@RequestMapping("/templateMsg")
public class TemplateMsgAPIController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    TemplateMsgService templateMsgService;

    @PostMapping("/sendToUser")
    public R sendToUser(HttpServletRequest request, @RequestBody TemplateMsgForm form){
        String ip = IPUtils.getIpAddr(request);
        logger.info("发送模板消息，ip={},detail={}",ip,form);
        if(form.isValid()){
            templateMsgService.sendToUser(form);
        }
        return R.ok();
    }
}
