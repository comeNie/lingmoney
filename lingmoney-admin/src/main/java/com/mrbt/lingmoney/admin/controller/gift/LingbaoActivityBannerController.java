package com.mrbt.lingmoney.admin.controller.gift;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
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

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.gift.LingbaoActivityBannerService;
import com.mrbt.lingmoney.model.LingbaoActivityBanner;
import com.mrbt.lingmoney.model.LingbaoActivityBannerExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 我的领地活动banner
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("/gift/lingbaoActivityBanner")
public class LingbaoActivityBannerController extends BaseController {

	private Logger log = MyUtils.getLogger(LingbaoActivityBannerController.class);

	@Autowired
	private LingbaoActivityBannerService lingbaoActivityBannerService;
	@Autowired
	private FtpUtils ftpUtils;

	private String indexPic = "lingbaoActivityBanner";

	/**
	 * 查询我的领地活动banner列表
	 * @param vo 我的领地活动banner
	 * @param page 当前页
	 * @param rows 每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	public @ResponseBody Object list(LingbaoActivityBanner vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			LingbaoActivityBannerExample example = new LingbaoActivityBannerExample();
			LingbaoActivityBannerExample.Criteria cri = example
					.createCriteria();
			if (StringUtils.isNotBlank(vo.getName())) {
				cri.andNameLike("%" + vo.getName() + "%");
			}
			if (vo.getStatus() != null) {
				cri.andStatusEqualTo(vo.getStatus());
			}
			if (page != null && rows != null) {
				example.setLimitStart(pageInfo.getFrom());
				example.setLimitEnd(pageInfo.getSize());
			}
			pageInfo = lingbaoActivityBannerService.getList(example);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地活动banner，查询我的领地活动banner列表，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 更改我的领地活动banner状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = lingbaoActivityBannerService.changeStatus(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地活动banner，更改我的领地活动banner状态，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 添加或修改我的领地活动banner
	 * @param vo 我的领地活动banner
	 * @param request input 
	 * @return 分页实体类 
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(LingbaoActivityBanner vo, MultipartHttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file1 = request.getFile("path1");
			String codePath1 = this.uploadBanner(file1);
			vo.setCodePath1(codePath1);

			MultipartFile file2 = request.getFile("path2");
			String codePath2 = this.uploadBanner(file2);
			vo.setCodePath2(codePath2);

			MultipartFile file3 = request.getFile("path3");
			String codePath3 = this.uploadBanner(file3);
			vo.setCodePath3(codePath3);

			MultipartFile file4 = request.getFile("path4");
			String codePath4 = this.uploadBanner(file4);
			vo.setCodePath4(codePath4);

			MultipartFile file5 = request.getFile("path5");
			String codePath5 = this.uploadBanner(file5);
			vo.setCodePath5(codePath5);

			MultipartFile file6 = request.getFile("path6");
			String codePath6 = this.uploadBanner(file6);
			vo.setCodePath6(codePath6);

			MultipartFile webPath = request.getFile("webPath");
			String webBannerPath = this.uploadBanner(webPath);
			vo.setWebBannerPath(webBannerPath);

			if (vo.getId() == null || vo.getId() <= 0) { // 添加
				vo.setStatus(0);
				vo.setCreateTime(new Date());
				lingbaoActivityBannerService.save(vo);
			} else { // 修改
				lingbaoActivityBannerService.update(vo);
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地活动类型，添加或修改我的领地活动类型，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据主键删除活动banner
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				lingbaoActivityBannerService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地活动banner，根据主键删除活动banner，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据主键查找活动banner
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("findById")
	@ResponseBody
	public Object findById(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				LingbaoActivityBanner record = lingbaoActivityBannerService
						.findById(id);
				if (record != null) {
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
					pageInfo.setObj(record);
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地活动banner，根据主键查找活动banner，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 上传BANNER
	 * @param file file input
	 * @return 返回结果
	 */
	public String uploadBanner(MultipartFile file) {
		String url = null;
		try {
			if (file.getSize() > 0) {
				BufferedImage img = ImageIO.read(file.getInputStream());
				if (img != null) {
					String fileName = UUID.randomUUID().toString();
					String type = file.getOriginalFilename().substring(
							file.getOriginalFilename().indexOf("."));
					if (StringUtils.isNotBlank(type)) {
						fileName += type;
					} else {
						fileName += ".jpg";
					}
					ftpUtils.upload(file.getInputStream(), indexPic, fileName);
					url = ftpUtils.getUrl() + indexPic + "/" + fileName;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("我的领地活动banner，banner上传失败，失败原因是：" + e);
		}
		return url;
	}
}
