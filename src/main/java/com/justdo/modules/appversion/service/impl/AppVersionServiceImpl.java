package com.justdo.modules.appversion.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.modules.appversion.entity.AppVersion;
import com.justdo.modules.appversion.mapper.AppVersionMapper;
import com.justdo.modules.appversion.service.AppVersionService;
import org.springframework.stereotype.Service;


@Service
public class AppVersionServiceImpl extends ServiceImpl<AppVersionMapper, AppVersion> implements AppVersionService {
	
}
