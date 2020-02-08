package com.justdo.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.system.user.entity.UserRole;
import com.justdo.system.user.mapper.UserRoleMapper;
import com.justdo.system.user.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 用户与角色对应关系
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Service
@AllArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper,UserRole> implements UserRoleService {

}
