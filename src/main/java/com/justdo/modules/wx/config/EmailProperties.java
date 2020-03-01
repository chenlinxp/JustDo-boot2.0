package com.justdo.modules.wx.config;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "email")
@Data
public class EmailProperties {
    private String host;
    private String username;
    private String password;
    private String port;
    @Override
    public String toString() {
        return new  Gson().toJson(this);
    }
}
