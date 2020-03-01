package com.justdo.modules.wx.msgnews.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.modules.wx.msgnews.entity.MsgNews;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;

import java.util.List;

/**
 * 消息新闻
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2020-01-19 16:02:20
 */
public interface MsgNewsService extends IService<MsgNews> {
    /**
     * 保存图文消息，数据库不存在时插入，存在时更新
     *
     * @param msgNews
     */
    boolean save(MsgNews msgNews);

    /**
     * 获取所有的图文消息
     *
     * @return
     */
    List<MsgNews> getMsgNews();

    /**
     * 根据ID列表查询
     *
     * @param ids
     * @return
     */
    List<WxMpKefuMessage.WxArticle> findByIds(String ids);
}
