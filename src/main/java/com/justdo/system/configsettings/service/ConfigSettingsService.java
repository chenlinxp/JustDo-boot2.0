package com.justdo.system.configsettings.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.system.configsettings.entity.ConfigSettings;

/**
 * 系统配置信息
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public interface ConfigSettingsService extends IService<ConfigSettings> {

	/**
	 * 根据key，更新value
	 */
	void updateValueByKey(String key, String value);

	/**
	 * 根据key，获取配置的value值
	 * 
	 * @param key           key
	 */
	String getValue(String key);
	
	/**
	 * 根据key，获取value的Object对象
	 * @param key    key
	 * @param clazz  Object对象
	 */
	<T> T getConfigObject(String key, Class<T> clazz);
	
}
