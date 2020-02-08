package com.justdo.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.authentication.TokenGenerator;
import com.justdo.common.utils.R;
import com.justdo.system.user.entity.UserToken;
import com.justdo.system.user.mapper.UserTokenMapper;
import com.justdo.system.user.service.UserTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@AllArgsConstructor
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper,UserToken> implements UserTokenService {

	private final UserTokenMapper userTokenMapper;
	//12小时后过期
	private final static int EXPIRE = 3600 * 12;


	@Override
	public R createToken(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		UserToken tokenEntity = userTokenMapper.selectById(userId);
		if(tokenEntity == null){
			tokenEntity = new UserToken();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//保存token
			baseMapper.insert(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			userTokenMapper.updateById(tokenEntity);
		}

		R r = R.ok().put("token", token).put("expire", EXPIRE);

		return r;
	}

	@Override
	public void logout(long userId) {
		userTokenMapper.deleteById(userId);
	}
}
