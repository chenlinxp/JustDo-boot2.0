package com.justdo.modules.wx.member.form;


import cn.hutool.json.JSONUtil;

import javax.validation.constraints.NotEmpty;

public class CodeToOpenidForm {
    @NotEmpty(message = "code不得为空")
    private String code;
    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
