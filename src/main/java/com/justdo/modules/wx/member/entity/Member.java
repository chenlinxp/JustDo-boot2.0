package com.justdo.modules.wx.member.entity;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信用户信息
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@TableName("wx_member")
public class Member implements Serializable {
        @TableId(type = IdType.INPUT)
        private String openid;
        private String phone;
        private String nickname;
        private int sex;
        private String city;
        private String province;
        private String headimgurl;
        private Date subscribetime;

    public Member() {
    }
    public Member(String openid) {
        this.openid = openid;
    }

	public Member(WxMpUser userWxInfo) {
		this.openid = userWxInfo.getOpenId();
		this.nickname = userWxInfo.getNickname();
		this.sex = userWxInfo.getSex();
		this.city = userWxInfo.getCity();
		this.province = userWxInfo.getProvince();
		this.headimgurl = userWxInfo.getHeadImgUrl();
		this.subscribetime = new Date(userWxInfo.getSubscribeTime());
	}

	@Override
	public String toString() {
		return JSONUtil.toJsonStr(this);
	}
	
	public String getPhone() {
		return phone;
    }
	public void setPhone(String phone) {
			this.phone = phone;
    }
	public String getNickname() {
		return nickname;
    }
	public void setNickname(String nickname) {
			this.nickname = nickname;
    }
	public int getSex() {
		return sex;
    }
	public void setSex(int sex) {
			this.sex = sex;
    }
	public String getCity() {
		return city;
    }
	public void setCity(String city) {
			this.city = city;
    }
	public String getProvince() {
		return province;
    }
	public void setProvince(String province) {
			this.province = province;
    }
	public String getHeadimgurl() {
		return headimgurl;
    }
	public void setHeadimgurl(String headimgurl) {
			this.headimgurl = headimgurl;
    }
	public String getOpenid() {
		return openid;
    }
	public void setOpenid(String openid) {
			this.openid = openid;
    }
	public Date getSubscribetime() {
		return subscribetime;
    }
	public void setSubscribetime(Date subscribeTime) {
			this.subscribetime = subscribetime;
    }
	
}