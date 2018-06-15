package com.mrbt.lingmoney.wap.controller.common;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.common.PictureVerifyService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ValidateCode;

/**
 * 图片验证码
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/commonsetVerify")
public class PictureVerifyCode {

	@Autowired
	private PictureVerifyService pictureVerifyService;

	private static final Logger LOGGER = LogManager.getLogger(PictureVerifyCode.class);
	
	/**
	 * 生成图形验证码配置
	 * @param width 图片宽
	 * @param height  图片高
	 * @param codeCount 字符个数
	 * @param lineCount 干扰线条数
	 */
	private static final int WIDTH = 160;
	private static final int HEIGHT = 40;
	private static final int CODE_COUNT = 4;
	private static final int LING_COUNT = 150;

	/**
	 * 生成图片验证码
	 * 
	 * @param response 响应
	 * @param request 请求
	 * @param picKey
	 *            图片验证码key
	 */
	@RequestMapping(value = "/pictureCode")
	public void getPictureCode(HttpServletResponse response, HttpServletRequest request, String picKey) {

		response.setContentType("image/jpeg"); // 设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache"); // 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Set-Cookie", "name=value; HttpOnly"); // 设置HttpOnly属性,防止Xss攻击
		response.setDateHeader("Expire", 0);
        ValidateCode vCode = new ValidateCode(WIDTH, HEIGHT, CODE_COUNT, LING_COUNT);
		LOGGER.info("请求图片验证码pickey:" + picKey + "\tcode:" + vCode.getCode());
		pictureVerifyService.save(picKey, vCode.getCode());

		try {
			ImageIO.write(vCode.getBuffImg(), "PNG", response.getOutputStream());
			response.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证图片验证码
	 * 
	 * @param response 响应
	 * @param request 请求
	 * @param picKey
	 *            图片验证码KEY
	 * @param code
	 *            图片验证码
	 * @return 图形验证码校验结果
	 */
	@RequestMapping(value = "/verPicCode", method = RequestMethod.POST)
	public @ResponseBody Object verPictureCode(HttpServletResponse response, HttpServletRequest request, String picKey,
			String code) {
		LOGGER.info("验证图片验证码pickey:" + picKey + "\tcode:" + code);
		
		PageInfo pageInfo = new PageInfo();
		pictureVerifyService.varifyPicCode(picKey, code, pageInfo);
		return pageInfo;
	}
}
