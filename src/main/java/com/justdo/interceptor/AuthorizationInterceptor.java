package com.justdo.interceptor;


import com.justdo.common.annotation.AuthIgnore;
import com.justdo.common.exception.RRException;
import com.justdo.system.user.entity.UserToken;
import com.justdo.system.user.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ShiroService shiroService;

    public static final String USER_KEY = "userId";
    public static final String TOKEN = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthIgnore annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        }else{
            return true;
        }

        if(annotation != null){
            return true;
        }

        //获取用户凭证
        String token = request.getHeader(TOKEN);
        if(StringUtils.isBlank(token)){
            token = request.getParameter(TOKEN);
        }

        //凭证为空
        if(StringUtils.isBlank(token)){
            throw new RRException(TOKEN + "不能为空", HttpStatus.UNAUTHORIZED.value());
        }

        //根据accessToken，查询用户信息
        UserToken tokenEntity = shiroService.queryByToken(token);
        //token失效
        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new RRException("token:" + token + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, tokenEntity.getUserId());

        return true;
    }
}
