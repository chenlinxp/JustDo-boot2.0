package com.justdo.system.generator.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.system.generator.entity.GeneratorConfig;
import com.justdo.system.generator.entity.InfoRmationSchema;

public interface GeneratorService extends IService<InfoRmationSchema> {

    IPage<InfoRmationSchema> queryTableList(IPage page, QueryWrapper<InfoRmationSchema> entityWrapper);

    byte[] generatorCode(GeneratorConfig config);
}
