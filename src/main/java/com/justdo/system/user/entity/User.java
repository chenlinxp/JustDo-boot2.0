package com.justdo.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Model<User> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	@TableId(value = "user_id", type = IdType.AUTO)
	private Long userId;

	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空")
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message="密码不能为空")
	private String password;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 邮箱
	 */
	@NotBlank(message="邮箱不能为空")
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;

	
	/**
	 * 创建者ID
	 */
	private Long createUserId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	@TableField(exist = false)
	private List<Long> roleIdList;
}
