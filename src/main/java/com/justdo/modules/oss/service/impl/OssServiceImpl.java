package com.justdo.modules.oss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.modules.oss.entity.Oss;
import com.justdo.modules.oss.mapper.OssMapper;
import com.justdo.modules.oss.service.OssService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class OssServiceImpl extends ServiceImpl<OssMapper,Oss> implements OssService {

	
}
