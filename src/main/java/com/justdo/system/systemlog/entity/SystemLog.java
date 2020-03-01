package com.justdo.system.systemlog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.justdo.system.systemlog.enums.SystemOperationEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 系统日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Data
@TableName("sys_log")
@EqualsAndHashCode(callSuper = true)
public class SystemLog extends Model<SystemLog> {

	private static final long serialVersionUID = 1L;
	@TableId
	private Long id;
	//用户名
	private String username;
	//用户操作
	private String operation;
	//请求方法
	private String method;
	//请求参数
	private String params;
	//执行时长(毫秒)
	private Long time;
	//IP地址
	private String ip;
	//创建时间
	private Date createDate;


	public SystemLog() {
	}

	public SystemLog(String openid, SystemOperationEnum operation, String params, String ip) {
		this.username = openid;
		this.operation = operation.toString();
		this.params = params;
		this.ip = ip;
		this.createDate=new Date();
	}
}
