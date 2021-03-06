package com.justdo.modules.wx.member.clientapi;

import cn.hutool.crypto.SecureUtil;
import com.justdo.common.utils.CookieUtils;
import com.justdo.common.utils.MD5Utils;
import com.justdo.common.utils.R;
import com.justdo.common.utils.URIUtils;
import com.justdo.modules.wx.member.form.CodeToOpenidForm;
import com.justdo.system.systemlog.enums.SystemOperationEnum;
import com.justdo.system.systemlog.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 微信网页授权相关
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthAPIController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SystemLogService sysLogService;
    private final WxMpService wxService;
    @Value("${wx.mp.configs[0].appId}")
    private String appId;
    @Value("${server.baseAddress}")
    private String appBaseAddress;
    
    @GetMapping("/getCode")
    @CrossOrigin
    public String getCode(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(required = false) String state,
                          @RequestParam(required = false) String code,
                          @RequestParam(required = false)String redirect) throws Exception {
        logger.info("获取微信授权code,redirect="+redirect);
        logger.info("获取微信授权code,state="+state);
        if(StringUtils.isEmpty(code) && !StringUtils.isEmpty(redirect)){
            String authUrl=wxService.oauth2buildAuthorizationUrl(appBaseAddress+request.getRequestURI(), WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(redirect,"utf-8"));
            logger.info("获取微信授权code,重定向到"+authUrl);
            response.sendRedirect(authUrl);
            return null;
        }else if(!StringUtils.isEmpty(code) && Pattern.matches("[a-zA-z]+://[^\\s]*", state)){
            String returnUrl= URIUtils.appendUri(state,"code="+code);
            logger.info("授权完成，重定向到："+returnUrl);
            response.sendRedirect(returnUrl);
            return null;
        }
        return "parameters error";

    }
    
    
    /**
     * 使用微信授权code换取openid
     *
     * @param request
     * @param response
     * @param form
     * @return
     */
    @PostMapping("/codeToOpenid")
    @CrossOrigin
    public R codeToOpenid(HttpServletRequest request, HttpServletResponse response, @RequestBody CodeToOpenidForm form) {
        String code=form.getCode();
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
            String openid = wxMpOAuth2AccessToken.getOpenId();
            String accessToken =  wxMpOAuth2AccessToken.getAccessToken();
            String freshAccessToken = wxMpOAuth2AccessToken.getRefreshToken();
            CookieUtils.setCookie(response, "openid", openid, 365 * 24 * 60 * 60);
            String openidToken = MD5Utils.getMD5AndSalt(openid);

            CookieUtils.setCookie(response, "openidToken", openidToken, 365 * 24 * 60 * 60);
            //sysLogService.addLog(SystemOperationEnum.微信授权,"code:"+code);
            return R.ok().put(openid);
        }catch (WxErrorException e){
            logger.error("code换取openid失败",e);
            return R.error(e.getError().getErrorMsg());
        }

    }

    /**
     * 获取微信分享的签名配置
     * 允许跨域（只有微信公众号添加了js安全域名的网站才能加载微信分享，故这里不对域名进行校验）
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/getShareSignature")
    public R getShareSignature(HttpServletRequest request, HttpServletResponse response) throws WxErrorException {
        // 1.拼接url（当前网页的URL，不包含#及其后面部分）
        String wxShareUrl = request.getHeader("Referer");
        if (!StringUtils.isEmpty(wxShareUrl)) {
            wxShareUrl = wxShareUrl.split("#")[0];
        } else {
            return R.error("地址不正确，微信分享加载失败");
        }
        Map<String, String> wxMap = new TreeMap<>();
        String wxNoncestr = UUID.randomUUID().toString();
        String wxTimestamp = (System.currentTimeMillis() / 1000) + "";
        wxMap.put("noncestr", wxNoncestr);
        wxMap.put("timestamp", wxTimestamp);
        wxMap.put("jsapi_ticket", wxService.getJsapiTicket());
        wxMap.put("url", wxShareUrl);

        // 加密获取signature
        StringBuilder wxBaseString = new StringBuilder();
        wxMap.forEach((key,value)-> wxBaseString.append(key).append("=").append(value).append("&"));
        String wxSignString = wxBaseString.substring(0, wxBaseString.length() - 1);
        // signature
        String wxSignature = SecureUtil.sha1(wxSignString);
        Map<String, String> resMap = new TreeMap<>();
        resMap.put("appId",appId );
        resMap.put("wxTimestamp",wxTimestamp );
        resMap.put("wxNoncestr",wxNoncestr );
        resMap.put("wxSignature",wxSignature );
        //sysLogService.addLog(SystemOperationEnum.加载微信分享,"wxShareUrl:"+wxShareUrl);
        return R.ok().put(resMap);
    }



    /**
     * 获取微信用户账号的相关信息
     * http：GET（请使用https协议） https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     * @param opendID  用户的openId，这个通过当用户进行了消息交互的时候，才有
     * @return
     */
//    public static String getUserInfo(String opendID) {
//        AccessToken accessToken = WeiXinUtils.getAccessToken();
//        //获取access_token
//        String token = accessToken.getToken();
//        String url = GET_USERINFO_URL.replace("ACCESS_TOKEN", token);
//        url = url.replace("OPENID", opendID);
//        JSONObject jsonObject = WeiXinUtils.doGetStr(url);
//        return jsonObject.toString();
//    }
}
