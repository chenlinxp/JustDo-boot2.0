package com.justdo.modules.wx.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.config.ConstantConfig;
import com.justdo.modules.wx.article.entity.Article;
import com.justdo.modules.wx.article.mapper.ArticleMapper;
import com.justdo.modules.wx.member.entity.Member;
import com.justdo.modules.wx.member.mapper.MemberMapper;
import com.justdo.modules.wx.member.service.MemberService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;


@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private WxMpService wxService;

	private static final String LIST_FILEDS="openid,phone,nickname,sex,city,province,headimgurl,subscribe_time";

	/**
	 * 根据openid更新用户信息
	 *
	 * @param openid
	 * @return
	 */
	@Override
	public Member refreshUserInfo(String openid) {

		try {
			// 获取微信用户基本信息
			WxMpUser userWxInfo = wxService.getUserService().userInfo(openid, null);
			if (userWxInfo != null) {
				Member member = new Member(userWxInfo);
				this.updateOrInsert(member);
				return member;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分页查询用户数据，每页50条数据
	 *
	 * @param pageNumber
	 * @return
	 */
	@Override
	public List<Member> getUserList(int pageNumber, String nickname) {
		return memberMapper.selectPage(new Page<Member>(pageNumber, ConstantConfig.PAGE_SIZE_SMALL),
				new QueryWrapper<Member>().like("nickname", nickname).orderByDesc("subscribe_time")).getRecords();
	}
	/**
	 * 查询文章列表时返回的字段（过滤掉详情字段以加快速度）
	 */
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String nickname = (String)params.get("nickname");
		String phone = (String)params.get("phone");
		String openid = (String)params.get("openid");
		IPage<Member> page = this.page(
				new Query<Member>().getPage(params),
				new QueryWrapper<Member>()
						.select(LIST_FILEDS)
						.eq(!StringUtils.isEmpty(nickname),"nickname",nickname)
						.like(!StringUtils.isEmpty(phone),"phone",phone)
						.like(!StringUtils.isEmpty(openid),"openid",openid)
						.orderByDesc("subscribe_time")
		);

		return new PageUtils(page);
	}
	/**
	 * 计数
	 *
	 * @return
	 */
	@Override
	public int count() {
		return memberMapper.selectCount(new QueryWrapper<>());
	}

	/**
	 * 检查用户是否关注微信，已关注时会保存用户的信息
	 *
	 * @param openid
	 * @return
	 */
	@Override
	public boolean wxSubscribeCheck(String openid) {
		try {
			// 获取微信用户基本信息
			WxMpUser memberWxInfo = wxService.getUserService().userInfo(openid, null);
			if (memberWxInfo != null && memberWxInfo.getSubscribe()) {
				Member member = new Member(memberWxInfo);
				this.updateOrInsert(member);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 数据存在时更新，否则新增
	 *
	 * @param member
	 */
	@Override
	public void updateOrInsert(Member member) {
		Integer updateCount = memberMapper.updateById(member);
		if (updateCount < 1) {
			memberMapper.insert(member);
		}
	}
}
