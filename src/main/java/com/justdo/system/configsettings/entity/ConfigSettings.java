package com.justdo.system.configsettings.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 系统配置信息
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Data
@Builder
@TableName("sys_config")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ConfigSettings extends Model<ConfigSettings> {

	@TableId
	private Long id;

	@NotBlank(message="参数名不能为空")
	private String configKey;

	@NotBlank(message="参数值不能为空")
	private String configValue;

	private Integer configStatus;

	private String remark;

}
