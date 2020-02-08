package com.justdo.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.justdo.common.utils.Constant;
import com.justdo.system.resource.entity.Resource;
import com.justdo.system.resource.service.ResourceService;
import com.justdo.system.user.entity.User;
import com.justdo.system.user.entity.UserToken;
import com.justdo.system.user.mapper.UserMapper;
import com.justdo.system.user.mapper.UserTokenMapper;
import com.justdo.system.user.service.ShiroService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;


@Lazy
@Service
@AllArgsConstructor
public class ShiroServiceImpl implements ShiroService {

    private final ResourceService resourceService;
    private final UserMapper userMapper;
    private final UserTokenMapper userTokenMapper;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<Resource> menuList = resourceService.list();
            permsList = new ArrayList<>(menuList.size());
            for(Resource menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = userMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public UserToken queryByToken(String token) {
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token",token);
        return userTokenMapper.selectOne(queryWrapper);
    }

    @Override
    public User queryUser(Long userId) {
        return userMapper.selectById(userId);
    }
}
