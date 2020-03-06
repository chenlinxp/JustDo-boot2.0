package com.justdo.system.systemlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.common.utils.CookieUtils;
import com.justdo.common.utils.IPUtils;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.system.systemlog.entity.SystemLog;
import com.justdo.system.systemlog.enums.SystemOperationEnum;
import com.justdo.system.systemlog.mapper.SystemLogMapper;
import com.justdo.system.systemlog.service.SystemLogService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;


@Service
@AllArgsConstructor
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper,SystemLog> implements SystemLogService {

	/**
	 * 未保存的日志队列
	 */
	private static ConcurrentLinkedQueue<SystemLog> SysLogsQueue = new ConcurrentLinkedQueue<>();
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String key = (String)params.get("key");

		IPage<SystemLog> page = this.page(
				new Query<SystemLog>().getPage(params),
				new QueryWrapper<SystemLog>().like(StringUtils.isNotBlank(key),"username", key)
		);

		return new PageUtils(page);
	}
	/**
	 * 添加系统日志到队列中，队列数据会定时批量插入到数据库
	 * @param operation
	 */
	@Override
	public void addTaskLog(SystemOperationEnum operation, String params) {
		SystemLog sysLog = new SystemLog(null,operation,params,null);
		SysLogsQueue.offer(sysLog);
	}
	/**
	 * 添加系统日志到队列中，队列数据会定时批量插入到数据库
	 * @param operation
	 */
	@Override
	public void addLog(SystemOperationEnum operation, String params) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String openid = CookieUtils.getCookieValue(request,"openid");
		String ip = IPUtils.getIpAddr(request);
		SystemLog sysLog = new SystemLog(openid,operation,params,ip);
		SysLogsQueue.offer(sysLog);
	}

	/**
	 * 定时将日志插入到数据库
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	synchronized void batchAddSysLog(){
		List<SystemLog> logs = new ArrayList<>();
		while (!SysLogsQueue.isEmpty()){
			logs.add(SysLogsQueue.poll());
		}
		if(!logs.isEmpty()){
			this.saveBatch(logs);
		}
	}
}
