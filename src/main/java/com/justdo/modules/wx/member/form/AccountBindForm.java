package com.justdo.modules.wx.member.form;


import cn.hutool.json.JSONUtil;

public class AccountBindForm {
	String phoneNum;
	String idCodeSuffix;
	@Override
	public String toString() {
		return JSONUtil.toJsonStr(this);
	}
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getIdCodeSuffix() {
		return idCodeSuffix;
	}

	public void setIdCodeSuffix(String idCodeSuffix) {
		this.idCodeSuffix = idCodeSuffix;
	}
}
