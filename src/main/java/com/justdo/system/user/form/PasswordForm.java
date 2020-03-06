package com.justdo.system.user.form;

import lombok.Data;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/3/6 下午6:08
 */
@Data
public class PasswordForm {
	/**
	 * 原密码
	 */
	private String password;
	/**
	 * 新密码
	 */
	private String newPassword;

}