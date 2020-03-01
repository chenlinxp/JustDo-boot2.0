package com.justdo.modules.wx.wxqrcode.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.justdo.modules.wx.wxqrcode.form.WxQrCodeForm;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公众号带参二维码
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2020-01-19 16:02:20
 */
@Data
@TableName("wx_qr_code")
public class WxQrCode implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 二维码类型
	 */
	private Boolean isTemp;
	/**
	 * 场景值ID
	 */
	private String sceneStr;
	/**
	 * 二维码ticket
	 */
	private String ticket;
	/**
	 * 二维码图片解析后的地址
	 */
	private String url;
	/**
	 * 该二维码失效时间
	 */
	private Date expireTime;
	/**
	 * 该二维码创建时间
	 */
	private Date createTime;
	public WxQrCode() {
	}
	public WxQrCode(WxQrCodeForm form) {
		this.isTemp=form.getIsTemp();
		this.sceneStr=form.getSceneStr();
		this.createTime=new Date();
	}
}
