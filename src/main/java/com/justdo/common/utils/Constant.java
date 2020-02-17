package com.justdo.common.utils;

/**
 * 常量
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
public class Constant {

    public static final String NUMBER_CODE_KEY = "justdo:number:code:";
    public static final String MOBILE_CODE_KEY = "justdo:mobile:code:";

	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;

	public static final int CODE_SIZE = 4;


    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3),
        /**
         * minio
         */
        MINIO(4);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
