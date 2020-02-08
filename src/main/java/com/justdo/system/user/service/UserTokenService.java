package com.justdo.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.system.user.entity.UserToken;
import com.justdo.common.utils.R;

/**
 * 用户Token
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface UserTokenService extends IService<UserToken> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
