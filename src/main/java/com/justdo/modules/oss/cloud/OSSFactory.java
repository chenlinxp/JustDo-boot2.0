package com.justdo.modules.oss.cloud;

import com.justdo.common.utils.ConfigConstant;
import com.justdo.common.utils.SpringContextUtils;
import com.justdo.config.ConstantConfig;
import com.justdo.system.configsettings.service.ConfigSettingsService;


/**
 * 文件上传Factory
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public final class OSSFactory {

    private static ConfigSettingsService configSettingsService;

    static {
        OSSFactory.configSettingsService = (ConfigSettingsService) SpringContextUtils.getBean("configSettingsService");
    }

    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = configSettingsService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if(config.getType() == ConstantConfig.CloudServeType.QINIU.getValue()){
            return new QiniuCloudStorageService(config);
        }else if(config.getType() == ConstantConfig.CloudServeType.ALIYUN.getValue()){
            return new AliyunCloudStorageService(config);
        }else if(config.getType() == ConstantConfig.CloudServeType.QCLOUD.getValue()){
            return new QcloudCloudStorageService(config);
        }else if(config.getType() == ConstantConfig.CloudServeType.MINIO.getValue()){
            return new MinioCloudStorageService(config);
        }
        return null;
    }

}
