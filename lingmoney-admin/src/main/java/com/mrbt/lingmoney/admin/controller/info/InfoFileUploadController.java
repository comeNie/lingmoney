package com.mrbt.lingmoney.admin.controller.info;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.MyUtils;

/**
 * 富文本图片上传
 * 
 * @author lihq
 * @date 2017年5月19日 上午10:40:37
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("info")
public class InfoFileUploadController {
	private Logger log = MyUtils.getLogger(InfoFileUploadController.class);
	@Autowired
	private FtpUtils ftpUtils;
	/**
	 * 图片保存的根目录
	 */
	private String infoRootPath = "info";

	/**
	 * 上传图片
	 * @param request request
	 * @return	返回结果
	 */
	@RequestMapping("uploadImg")
	@ResponseBody
	public Map<String, Object> uploadImg(MultipartHttpServletRequest request) {
		MultipartFile file = request.getFile("imgFile");
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		reMap.put("error", 1);
		try {
			if (file != null) {
				BufferedImage img = ImageIO.read(file.getInputStream());
				if (img != null) {
					String fileName = UUID.randomUUID().toString();
					String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
					if (StringUtils.isNotBlank(type)) {
						fileName += type;
					} else {
						fileName += ".jpg";
					}
					ftpUtils.upload(file.getInputStream(), infoRootPath, fileName);

					String url = ftpUtils.getUrl() + infoRootPath + "/" + fileName;
					reMap.put("url", url);
					reMap.put("error", 0);
				} else {
					reMap.put("message", "上传文件错误，文件类型不是图片");
				}
			}
		} catch (IOException e) {
			log.error(e);
			reMap.put("message", e.getMessage());
		}
		return reMap;
	}
}
