package com.justdo.common.aspect;

import com.google.gson.Gson;
import com.justdo.common.annotation.Log;
import com.justdo.common.utils.HttpContextUtils;
import com.justdo.common.utils.IPUtils;

import com.justdo.system.systemlog.entity.SystemLog;
import com.justdo.system.systemlog.service.SystemLogService;
import com.justdo.system.user.entity.User;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Aspect
@Component
public class LogAspect {
	@Autowired
	private SystemLogService systemLogService;
	
	@Pointcut("@annotation(com.justdo.common.annotation.Log)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SystemLog systemLog = new SystemLog();

		Log log = method.getAnnotation(Log.class);
		if(systemLog != null){
			//注解上的描述
			systemLog.setOperation(log.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		systemLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = new Gson().toJson(args[0]);
			systemLog.setParams(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		systemLog.setIp(IPUtils.getIpAddr(request));

		//用户名
		String username = ((User) SecurityUtils.getSubject().getPrincipal()).getUsername();
		systemLog.setUsername(username);

		systemLog.setTime(time);
		systemLog.setCreateDate(new Date());
		//保存系统日志
		systemLogService.save(systemLog);
	}
}
