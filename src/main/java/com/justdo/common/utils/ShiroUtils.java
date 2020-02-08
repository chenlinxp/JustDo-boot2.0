package com.justdo.common.utils;

import com.justdo.system.user.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * Shiro工具类
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Component
public class ShiroUtils {

	public Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public User getUserEntity() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}

	public Long getUserId() {
		return getUserEntity().getUserId();
	}
	
	public void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}
}
