package com.justdo.modules.wx.member.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.base.AbstractController;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.R;
import com.justdo.modules.wx.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 微信用户信息
 * 获取微信用户账号的相关信息
 * http：GET（请使用https协议） https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/3/1 上午11:19
 */

@RestController
@RequestMapping("/wx/member")
@RequiredArgsConstructor
public class MemberController extends AbstractController {

	private final MemberService MemberService;

	/**
	 * 微信用户信息列表
	 */
	@Log("文章列表")
	@GetMapping("/list")
	@RequiresPermissions("wx:member:list")
	public R list(@RequestParam Map<String, Object> params){

		PageUtils page = MemberService.queryPage(params);

		return R.ok().put("page", page);
	}


}


