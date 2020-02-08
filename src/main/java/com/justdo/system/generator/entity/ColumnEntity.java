package com.justdo.system.generator.entity;

import lombok.Data;

/**
 * 列的属性
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Data
public class ColumnEntity {
	//表名
    private String tableName;
    //列名
    private String columnName;
    //列名类型
    private String dataType;
    //列名备注
    private String columnComment;
    //
    private String columnKey;
    
    //属性名称(第一个字母大写)，如：user_name => UserName
    private String attrName;
    //属性名称(第一个字母小写)，如：user_name => userName
    private String attrNameMin;
    //属性类型
    private String attrType;
    //auto_increment
    private String extra;
}
