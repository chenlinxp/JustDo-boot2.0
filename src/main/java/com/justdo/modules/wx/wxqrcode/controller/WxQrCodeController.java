package com.justdo.modules.wx.wxqrcode.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.R;
import com.justdo.modules.wx.wxqrcode.entity.WxQrCode;
import com.justdo.modules.wx.wxqrcode.form.WxQrCodeForm;
import com.justdo.modules.wx.wxqrcode.service.WxQrCodeService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 公众号带参二维码管理
 * https://github.com/Wechat-Group/WxJava/wiki/MP_二维码管理
 */
@RestController
@RequestMapping("/wx/wxQrCode")
public class WxQrCodeController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxQrCodeService wxQrCodeService;


    /**
     * 二维码列表
     */
    @Log("二维码列表")
    @GetMapping("/list")
    @RequiresPermissions("wx:wxqrcode:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wxQrCodeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 二维码详情
     */
    @Log("二维码详情")
    @GetMapping("/info/{id}")
    @RequiresPermissions("wx:wxqrcode:info")
    public R info(@PathVariable("id") Long id){
        WxQrCode wxQrCode = wxQrCodeService.getById(id);

        return R.ok().put("wxQrCode", wxQrCode);
    }

    /**
     * 创建带参二维码ticket
     */
    @Log("创建带参二维码")
    @PostMapping("/createTicket")
    @RequiresPermissions("wx:wxqrcode:save")
    public R createTicket(@RequestBody WxQrCodeForm form) throws WxErrorException {
        WxMpQrCodeTicket ticket = wxQrCodeService.createQrCode(form);
        return R.ok().put(ticket);
    }

    /**
     * 删除二维码
     */
    @Log("删除二维码")
    @PostMapping("/delete")
    @RequiresPermissions("wx:wxqrcode:delete")
    public R delete(@RequestBody Long[] ids){
        wxQrCodeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
