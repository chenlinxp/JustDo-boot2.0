package com.justdo.system.generator.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.justdo.system.generator.entity.ColumnEntity;
import com.justdo.system.generator.entity.InfoRmationSchema;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GeneratorMapper extends BaseMapper<InfoRmationSchema> {

    IPage<InfoRmationSchema> queryTableList(IPage page, @Param("ew") QueryWrapper<InfoRmationSchema> entityWrapper);

    InfoRmationSchema queryTableList(@Param("ew") QueryWrapper<InfoRmationSchema> entityWrapper);

    List<ColumnEntity> queryColumns(@Param("ew") QueryWrapper<ColumnEntity> entityWrapper);
}
