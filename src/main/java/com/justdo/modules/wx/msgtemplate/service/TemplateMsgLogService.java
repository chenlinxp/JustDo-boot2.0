package com.justdo.modules.wx.msgtemplate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.common.utils.PageUtils;
import com.justdo.modules.wx.msgtemplate.entity.TemplateMsgLog;

import java.util.Map;

/**
 * 发送模板消息的日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2020-01-19 16:02:20
 */
public interface TemplateMsgLogService extends IService<TemplateMsgLog> {

    PageUtils queryPage(Map<String, Object> params);
    /**
     * 添加访问log到队列中，队列数据会定时批量插入到数据库
     * @param log
     */
    void addLog(TemplateMsgLog log);
}
