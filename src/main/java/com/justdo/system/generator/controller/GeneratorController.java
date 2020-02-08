package com.justdo.system.generator.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.justdo.common.base.AbstractController;
import com.justdo.common.utils.R;

import com.justdo.system.generator.entity.GeneratorConfig;
import com.justdo.system.generator.entity.InfoRmationSchema;
import com.justdo.system.generator.service.GeneratorService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/sys/gen")
@AllArgsConstructor
public class GeneratorController extends AbstractController {

    private final GeneratorService generatorService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
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
    @RequestMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String data = request.getParameter("data");
        GeneratorConfig config = new Gson().fromJson(data,GeneratorConfig.class);
        byte[] zipData = generatorService.generatorCode(config);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"x-springboot.zip\"");
        response.addHeader("Content-Length", "" + zipData.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(zipData, response.getOutputStream());
    }
}
