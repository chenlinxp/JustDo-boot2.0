package com.justdo.modules.wx.assets.form;


import cn.hutool.json.JSONUtil;

public class MaterialDeleteForm {
    String mediaId;
    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
