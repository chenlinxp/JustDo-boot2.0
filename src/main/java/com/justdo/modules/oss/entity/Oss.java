package com.justdo.modules.oss.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


/**
 * 文件上传
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Oss extends Model<Oss> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//URL地址
	private String url;
	//创建时间
	private Date createDate;
}
