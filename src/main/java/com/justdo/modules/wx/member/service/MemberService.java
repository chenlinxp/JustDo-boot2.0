package com.justdo.modules.wx.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.justdo.common.utils.PageUtils;
import com.justdo.modules.wx.article.entity.Article;
import com.justdo.modules.wx.member.entity.Member;


import java.util.List;
import java.util.Map;

/**
 * 公众号用户
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2020-01-19 16:02:20
 */

public interface MemberService extends IService<Member> {
    /**
     * 根据openid更新用户信息
     *
     * @param openid
     * @return
     */
    Member refreshUserInfo(String openid);

    /**
     * 分页查询用户数据，每页50条数据
     *
     * @param pageNumber
     * @return
     */
    List<Member> getUserList(int pageNumber, String nickname);

    /**
     * 按条件分页查询
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);
    /**
     * 计数
     *
     * @return
     */
    @Override
    int count();

    /**
     * 检查用户是否关注微信，已关注时会保存用户的信息
     *
     * @param openid
     * @return
     */
    boolean wxSubscribeCheck(String openid);

    /**
     * 数据存在时更新，否则新增
     *
     * @param user
     */
    void updateOrInsert(Member user);
}
