package com.justdo.authentication;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public class OAuth2Token implements AuthenticationToken {


    private String username;


    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OAuth2Token(String token){
        this.token = token;
    }

    public OAuth2Token(String username,String token){
        this.username = username;
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
