package com.justdo.modules.wx.assets.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.base.AbstractController;
import com.justdo.common.utils.R;
import com.justdo.config.ConstantConfig;
import com.justdo.modules.wx.assets.form.MaterialDeleteForm;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 微信公众号素材管理
 * 参考官方文档：https://developers.weixin.qq.com/doc/offiaccount/Asset_Management/New_temporary_materials.html
 * 参考WxJava开发文档：https://github.com/Wechat-Group/WxJava/wiki/MP_永久素材管理
 */
@RestController
@RequestMapping("/wx/assets")
@RequiredArgsConstructor
public class AssetsController extends AbstractController {

    private final WxMpService wxService;

    /**
     * 获取素材总数
     * @return
     * @throws WxErrorException
     */
    @Log("获取素材总数")
    @GetMapping("/count")
    public R materialCount() throws WxErrorException {
        WxMpMaterialCountResult res = wxService.getMaterialService().materialCount();
        return R.ok().put(res);
    }

    /**
     * 根据类别分页获取非图文素材列表
     * @param type
     * @param page
     * @return
     * @throws WxErrorException
     */
    @Log("获取非图文素材列表")
    @GetMapping("/fileList")
    @RequiresPermissions("wx:assets:list")
    public R materialFileBatchGet(@RequestParam(defaultValue = "image") String type,
                                       @RequestParam(defaultValue = "1") int page) throws WxErrorException {
        int offset=(page-1)* ConstantConfig.PAGE_SIZE_SMALL;
        WxMpMaterialFileBatchGetResult res = wxService.getMaterialService().materialFileBatchGet(type,offset, ConstantConfig.PAGE_SIZE_SMALL);
        return R.ok().put(res);
    }

    /**
     * 分页获取图文素材列表
     * @param page
     * @return
     * @throws WxErrorException
     */
    @Log("获取图文素材列表")
    @GetMapping("/newsList")
    @RequiresPermissions("wx:assets:list")
    public R materialNewsBatchGet(@RequestParam(defaultValue = "1") int page) throws WxErrorException {
        int offset=(page-1)*ConstantConfig.PAGE_SIZE_SMALL;
        WxMpMaterialNewsBatchGetResult res = wxService.getMaterialService().materialNewsBatchGet(offset, ConstantConfig.PAGE_SIZE_SMALL);
        return R.ok().put(res);
    }

    /**
     * 添加图文永久素材
     * @param mpMaterialNewsArticle
     * @return
     * @throws WxErrorException
     */
    @Log("添加图文永久素材")
    @PostMapping("/save")
    @RequiresPermissions("wx:assets:save")
    public R materialNewsUpload(@RequestBody WxMpMaterialNews.WxMpMaterialNewsArticle mpMaterialNewsArticle) throws WxErrorException {
        WxMpMaterialNews wxMpMaterialNewsSingle = new WxMpMaterialNews();
        mpMaterialNewsArticle.setShowCoverPic(true);
        wxMpMaterialNewsSingle.addArticle(mpMaterialNewsArticle);
        WxMpMaterialUploadResult res = wxService.getMaterialService().materialNewsUpload(wxMpMaterialNewsSingle);
        return R.ok().put(res);
    }

    /**
     * 添加多媒体永久素材
     * @param file
     * @param fileName
     * @param mediaType
     * @return
     * @throws WxErrorException
     * @throws IOException
     */
    @Log("添加多媒体永久素材")
    @PostMapping("/upload")
    @RequiresPermissions("wx:assets:save")
    public R materialFileUpload(MultipartFile file, String fileName, String mediaType) throws WxErrorException, IOException {
        if(file==null){
            return R.error("文件不得为空");
        }
        String originalFilename=file.getOriginalFilename();
        File tempFile = File.createTempFile("tmp", originalFilename.substring(originalFilename.lastIndexOf(".")));
        file.transferTo(tempFile);
        WxMpMaterial wxMaterial = new WxMpMaterial();
        wxMaterial.setFile(tempFile);
        wxMaterial.setName(fileName);
        WxMpMaterialUploadResult res = wxService.getMaterialService().materialFileUpload(mediaType,wxMaterial);
        tempFile.deleteOnExit();
        return R.ok().put(res);
    }

    /**
     * 删除素材
     * @param form
     * @return
     * @throws WxErrorException
     * @throws IOException
     */
    @Log("删除素材")
    @PostMapping("/delete")
    @RequiresPermissions("wx:assets:delete")
    public R materialDelete(@RequestBody MaterialDeleteForm form) throws WxErrorException, IOException {
        boolean res = wxService.getMaterialService().materialDelete(form.getMediaId());
        return R.ok().put(res);
    }

}
