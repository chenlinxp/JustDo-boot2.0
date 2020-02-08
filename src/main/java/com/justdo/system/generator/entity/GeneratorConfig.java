package com.justdo.system.generator.entity;

import lombok.Data;

import java.util.List;

/**
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Data
public class GeneratorConfig {
    private String mainPath;
    private String packagePath;
    private String moduleName;
    private String author;
    private String email;
    private List<String> genTable;
}
