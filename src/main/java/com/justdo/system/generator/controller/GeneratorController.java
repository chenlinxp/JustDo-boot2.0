package com.justdo.system.generator.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.justdo.common.annotation.Log;
import com.justdo.common.base.AbstractController;
import com.justdo.common.utils.R;

import com.justdo.system.generator.entity.GeneratorConfig;
import com.justdo.system.generator.entity.InfoRmationSchema;
import com.justdo.system.generator.service.GeneratorService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 代码生成功能
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@RestController
@RequestMapping("/system/generator")
@AllArgsConstructor
public class GeneratorController extends AbstractController {

    private final GeneratorService generatorService;

    /**
     * 数据库表列表
     */
    @Log("数据库表列表")
    @GetMapping("/list")
    @RequiresPermissions("system:generator:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        QueryWrapper<InfoRmationSchema> queryWrapper = new QueryWrapper<>();
        if(MapUtil.getStr(params,"tableName") != null){
            queryWrapper
                    .like("tableName", MapUtil.getStr(params,"tableName"));
        }
        IPage<InfoRmationSchema> sysConfigList = generatorService.queryTableList(mpPageConvert.<InfoRmationSchema>pageParamConvert(params),queryWrapper);
        return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));
    }

    /**
     * 生成代码
     */
    @Log("生成代码")
    @PostMapping("/code")
    @RequiresPermissions("system:generator:code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String data = request.getParameter("data");
        GeneratorConfig config = new Gson().fromJson(data,GeneratorConfig.class);
        byte[] zipData = generatorService.generatorCode(config);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"justdo2.0.zip\"");
        response.addHeader("Content-Length", "" + zipData.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(zipData, response.getOutputStream());
    }
}
