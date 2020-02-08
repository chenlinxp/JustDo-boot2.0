package com.justdo.system.systemlog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.system.systemlog.entity.SystemLog;
import com.justdo.system.systemlog.mapper.SystemLogMapper;
import com.justdo.system.systemlog.service.SystemLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper,SystemLog> implements SystemLogService {

}
