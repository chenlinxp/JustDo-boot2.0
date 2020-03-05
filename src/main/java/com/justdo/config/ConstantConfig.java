package com.justdo.config;

/**
 * 系统参数相关Key
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */

public class ConstantConfig {

    public static final String NUMBER_CODE_KEY = "justdo:number:code:";

    public static final String MOBILE_CODE_KEY = "justdo:mobile:code:";

    /**
     * 云存储配置KEY
     */
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";

    /**
     * 默认分页大小
     */
    public static final int PAGE_SIZE_SMALL = 20;

    public static final int PAGE_SIZE_MEDIUM = 50;

    public static final int PAGE_SIZE_BIG = 100;
    /**
     * 验证码的位数
     */
    public static final int CODE_SIZE = 4;
	/**
	 * 资源类型
	 */
    public enum ResourceType {
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

        ResourceType(int value) {
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
    public enum CloudServeType {
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

        CloudServeType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
