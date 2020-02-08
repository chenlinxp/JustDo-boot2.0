package com.justdo.system.generator.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 数据信息
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class InfoRmationSchema extends Model<InfoRmationSchema> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tableName;
    private String engine;
    private String tableComment;
    private String createTime;
}
