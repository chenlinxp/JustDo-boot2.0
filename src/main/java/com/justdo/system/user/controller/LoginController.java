package com.justdo.system.user.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Producer;
import com.justdo.common.annotation.Log;
import com.justdo.common.base.AbstractController;
import com.justdo.common.utils.R;
import com.justdo.config.ConstantConfig;
import com.justdo.system.user.entity.User;
import com.justdo.system.user.form.LoginForm;
import com.justdo.system.user.service.UserService;
import com.justdo.system.user.service.UserTokenService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录相关
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2019-06-19 16:02:20
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/system")
public class LoginController extends AbstractController {

	private final Producer producer;
	private final UserService userService;
	private final UserTokenService userTokenService;
	private final RedisTemplate redisTemplate;

	/**
	 * 验证码
	 */
	@SneakyThrows
	@GetMapping("/verification/{time}")
	public void captcha(@PathVariable("time") String time, HttpServletResponse response){
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//生成文字验证码
		String text = producer.createText();
		log.info("==================验证码:{}====================",text);
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//redis 60秒
		redisTemplate.opsForValue().set(ConstantConfig.NUMBER_CODE_KEY + time,text,60, TimeUnit.SECONDS);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}
	/**
	 * 手机短信验证码
	 */
	@Log("手机短信验证码")
	@GetMapping("/mobilecode/{number}")
	public Map<String, Object> mobile(@PathVariable("number") String number){

		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("mobile",number);
		User user = userService.getOne(queryWrapper);

		//账号不存在
		if(user == null) {
			return R.error("手机号码未注册");
		}

		String mobile = (String) redisTemplate.opsForValue().get(ConstantConfig.MOBILE_CODE_KEY + number);
		if(!StrUtil.isEmpty(mobile)){
			return R.error("验证码未失效");
		}

		//生成4位验证码
		String code = RandomUtil.randomNumbers(ConstantConfig.CODE_SIZE);
		log.info("==================短信验证码:{}====================",code);
		//redis 60秒
		redisTemplate.opsForValue().set(ConstantConfig.MOBILE_CODE_KEY + number,code,60, TimeUnit.SECONDS);
		//调用短信服务去发送

		return R.ok("验证码发送成功");
	}

	/**
	 * 账号密码登录
	 */
	@Log("账号密码登录")
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody LoginForm loginForm){

		String code_key = (String) redisTemplate.opsForValue().get(ConstantConfig.NUMBER_CODE_KEY + loginForm.getRandomStr());
		if(StrUtil.isEmpty(code_key)){
			return R.error("验证码过期");
		}

		if(!loginForm.getCaptcha().equalsIgnoreCase(code_key)){
			return R.error("验证码不正确");
		}
		//{"t":1583415120206,"username":"admin","password":"888888","randomStr":580975185,"captcha":"c66wy"}
		//用户信息
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username",loginForm.getUsername());
		User user = userService.getOne(queryWrapper);

		String password = new Sha256Hash(loginForm.getPassword(), user.getSalt()).toHex();
		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(password)) {
			return R.error("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return R.error("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		R r = userTokenService.createToken(user.getUserId());
		return r;
	}

	/**
	 * 手机号码+验证码登录
	 */
	@Log("手机号登录")
	@PostMapping("/mobile/login")
	public Map<String, Object> mobileLogin(String mobile, String code){

		String code_key = (String) redisTemplate.opsForValue().get(ConstantConfig.MOBILE_CODE_KEY + mobile);
		if(StrUtil.isEmpty(code_key)){
			return R.error("验证码过期");
		}

		if(!code.equalsIgnoreCase(code_key)){
			return R.error("验证码不正确");
		}

		//用户信息
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("mobile",mobile);
		User user = userService.getOne(queryWrapper);

		//账号不存在
		if(user == null) {
			return R.error("账号不存在");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return R.error("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		R r = userTokenService.createToken(user.getUserId());
		return r;
	}


	/**
	 * 退出
	 */
	@Log("退出登录")
	@PostMapping("/logout")
	public R logout() {
		userTokenService.logout(getUserId());
		return R.ok();
	}

	/**
	 * 未认证
	 */
	@PostMapping("/unauthorized")
	public R unauthorized() {
		return R.error(HttpStatus.SC_UNAUTHORIZED, "unauthorized");
	}

}
