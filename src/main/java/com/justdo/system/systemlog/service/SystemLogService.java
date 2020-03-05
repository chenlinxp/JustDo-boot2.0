package com.justdo.system.systemlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.common.utils.PageUtils;
import com.justdo.system.systemlog.entity.SystemLog;
import com.justdo.system.systemlog.enums.SystemOperationEnum;

import java.util.Map;


/**
 * 系统日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface SystemLogService extends IService<SystemLog> {

//	PageUtils queryPage(Map<String, Object> params);
//
//	/**
//	 * 添加系统日志到队列中，队列数据会定时批量插入到数据库
//	 * @param operation
//	 */
//	public void addTaskLog(SystemOperationEnum operation, String params);
//
//	/**
//	 * 添加系统日志到队列中，队列数据会定时批量插入到数据库
//	 * @param operation
//	 */
//	public void addLog(SystemOperationEnum operation, String params);

}
