package com.justdo.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.common.exception.RRException;
import com.justdo.common.utils.Constant;
import com.justdo.system.role.service.RoleService;
import com.justdo.system.user.entity.User;
import com.justdo.system.user.mapper.UserMapper;
import com.justdo.system.user.mapper.UserRoleMapper;
import com.justdo.system.user.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */

@Lazy
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
	private final UserMapper userMapper;
	private final UserRoleMapper userRoleMapper;
	private final RoleService roleService;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return userMapper.queryAllPerms(userId);
	}


	@Override
	@Transactional
	public boolean save(User user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		userMapper.insert(user);
		
		//检查角色是否越权
		checkRole(user);

		userRoleMapper.deleteById(user.getUserId());

		//保存用户与角色关系
		saveUserRoleList(user);
		return true;
	}

	@Transactional
	public void update(User user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		baseMapper.updateById(user);
		
		//检查角色是否越权
		checkRole(user);

		userRoleMapper.deleteById(user.getUserId());

		//保存用户与角色关系
		saveUserRoleList(user);
	}


	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return userMapper.updatePassword(map);
	}

	public void saveUserRoleList(User user){
		if(user.getRoleIdList() != null && user.getRoleIdList().size() != 0){
			Map<String, Object> map = new HashMap<>();
			map.put("userId", user.getUserId());
			map.put("roleIdList", user.getRoleIdList());
			userMapper.saveUserRole(map);
		}
	}
	
	/**
	 * 检查角色是否越权
	 */
	private void checkRole(User user){
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}
		
		//查询用户创建的角色列表
		List<Long> roleIdList = roleService.queryRoleIdList(user.getCreateUserId());
		
		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}
}
