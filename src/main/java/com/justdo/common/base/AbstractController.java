package com.justdo.common.base;

import com.justdo.common.utils.MPPageConvert;
import com.justdo.system.user.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller公共组件
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */

public abstract class AbstractController {
	@Autowired
	protected MPPageConvert mpPageConvert;

	protected User getUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}
}
