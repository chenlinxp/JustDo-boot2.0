package com.justdo.system.user.service;



import com.justdo.system.user.entity.User;
import com.justdo.system.user.entity.UserToken;

import java.util.Set;

/**
 * shiro相关接口'
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    UserToken queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    User queryUser(Long userId);
}
