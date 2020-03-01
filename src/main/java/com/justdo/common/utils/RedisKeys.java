/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 *
 * 版权所有，侵权必究！
 */

package com.justdo.common.utils;

/**
 * Redis所有Keys
 *
 * @author
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
}
