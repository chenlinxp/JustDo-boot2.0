package com.justdo.system.user.form;

import lombok.Data;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/3/6 下午6:06
 */
@Data
public class LoginForm {
	private String username;
	private String password;
	private String captcha;
	private String randomStr;
}
