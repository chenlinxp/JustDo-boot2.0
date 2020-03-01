package com.justdo.modules.wx.article.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章抽象：帮助中心文章、公告、资讯文章等分别存储到不同的表
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2020-01-19 16:02:20
 */
@Data
@Builder
@TableName("wx_article")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Article extends Model<Article> implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.AUTO)
	private int id;
	private int type;
	//@TableField(InsertStrategy= FieldStrategy.IGNORED)//title重复则不插入
	@NotEmpty(message = "标题不得为空")
	private String title;
	private String tags;
	private String summary;
	private String content;
	private String category;
	private String subCategory;
	private Date createTime;
	private Date updateTime;
	private Date startTime;
	private Date endTime;
	private int openCount;
	private String targetLink;
	private String image;

}
