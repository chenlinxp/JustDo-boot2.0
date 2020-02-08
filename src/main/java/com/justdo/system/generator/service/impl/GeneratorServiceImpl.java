package com.justdo.system.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.common.utils.GeneratorUtils;
import com.justdo.system.generator.entity.ColumnEntity;
import com.justdo.system.generator.entity.GeneratorConfig;
import com.justdo.system.generator.entity.InfoRmationSchema;
import com.justdo.system.generator.mapper.GeneratorMapper;
import com.justdo.system.generator.service.GeneratorService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Service
@AllArgsConstructor
public class GeneratorServiceImpl extends ServiceImpl<GeneratorMapper,InfoRmationSchema> implements GeneratorService {

    private final GeneratorMapper generatorMapper;
    private final GeneratorUtils genUtils;

    @Override
    public IPage<InfoRmationSchema> queryTableList(IPage page, QueryWrapper<InfoRmationSchema> entityWrapper) {
        return generatorMapper.queryTableList(page,entityWrapper);
    }

    @Override
    public byte[] generatorCode(GeneratorConfig config) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for(String tableName : config.getGenTable()){
            //查询表信息
            InfoRmationSchema table = generatorMapper.queryTableList(new QueryWrapper<InfoRmationSchema>().eq("tableName",tableName));
            //查询列信息
            List<ColumnEntity> columns = generatorMapper.queryColumns(new QueryWrapper<ColumnEntity>().eq("tableName",tableName));
            //生成代码
            genUtils.generatorCode(config,table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
