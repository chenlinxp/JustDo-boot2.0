package com.justdo.modules.wx.msgtemplate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * 消息模版
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2020-01-19 16:02:20
 */
@Data
@Builder
@TableName("wx_msg_template")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class MsgTemplate extends Model<MsgTemplate> implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.INPUT)
    private String templateId;
    private String title;
    private String data;
    private String url;
    private String color;
    @TableField(value = "`status`")
    private int status;
    @TableField(value = "`name`")
    private String name;

}