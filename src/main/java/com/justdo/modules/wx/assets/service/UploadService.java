package com.justdo.modules.wx.assets.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 上传文件转存
     * @return 返回图片在web服务器中的相对路径
     */
    String saveFile(MultipartFile file, String moduleName);
}
