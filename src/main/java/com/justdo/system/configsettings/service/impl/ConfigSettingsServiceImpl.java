package com.justdo.system.configsettings.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.justdo.common.exception.RRException;
import com.justdo.system.configsettings.entity.ConfigSettings;
import com.justdo.system.configsettings.mapper.ConfigSettingsMapper;
import com.justdo.system.configsettings.service.ConfigSettingsService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class ConfigSettingsServiceImpl extends ServiceImpl<ConfigSettingsMapper,ConfigSettings> implements ConfigSettingsService {

	private final ConfigSettingsMapper configSettingsMapper;
	
	@Override
	@Transactional
	public boolean save(ConfigSettings config) {
		configSettingsMapper.insert(config);
		return true;
	}

	@Transactional
	public void update(ConfigSettings config) {
		configSettingsMapper.updateById(config);
	}

	@Override
	@Transactional
	public void updateValueByKey(String key, String value) {
		UpdateWrapper<ConfigSettings> wrapper = new UpdateWrapper<>();
		wrapper.eq("config_key",key)
				.eq("config_value",value);
		baseMapper.update(ConfigSettings.builder().configKey(key).configValue(value).build(),wrapper);
	}

	@Transactional
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			ConfigSettings config = baseMapper.selectById(id);
		}
		configSettingsMapper.deleteById(ids);
	}

	@Override
	public String getValue(String key) {
		ConfigSettings config = baseMapper.selectOne(Wrappers.<ConfigSettings>query().lambda().eq(ConfigSettings::getConfigKey,key));
		return config == null ? null : config.getConfigValue();
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}
}
