package com.mrbt.lingmoney.admin.controller.festival;

import java.awt.image.BufferedImage;
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
import com.mrbt.lingmoney.admin.service.festival.BayWindowService;
import com.mrbt.lingmoney.model.BayWindow;
import com.mrbt.lingmoney.model.BayWindowExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 活动飘窗设置
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("festival/bayWindow")
public class BayWindowController extends BaseController {
	private Logger log = MyUtils.getLogger(BayWindowController.class);

	@Autowired
	private BayWindowService bayWindowService;

	@Autowired
	private FtpUtils ftpUtils;

	private String indexPic = "bayWindow";

	/**
	 * 更改活动飘窗状态
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
			pageInfo = bayWindowService.changeStatus(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("活动飘窗，更改活动飘窗状态，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 新增或修改活动飘窗
	 * @param request req
	 * @param vo 活动飘窗
	 * @return 分页实体类
	 */
	@RequestMapping("saveAndUpdate")
	public @ResponseBody Object saveAndUpdate(BayWindow vo, MultipartHttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			MultipartFile file = request.getFile("appImg");
			if (file.getSize() > 0) {
				BufferedImage img = ImageIO.read(file.getInputStream());
				if (img != null) {
					String fileName = UUID.randomUUID().toString();
					String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
					if (StringUtils.isNotBlank(type)) {
						fileName += type;
					} else {
						fileName += ".jpg";
					}
					ftpUtils.upload(file.getInputStream(), indexPic, fileName);

					String url = ftpUtils.getUrl() + indexPic + "/" + fileName;
					vo.setAppImgUrl(url);
				} else {
					pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
					pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
					return pageInfo;
				}
			}
			MultipartFile file2 = request.getFile("pcImg");
			if (file2.getSize() > 0) {
				BufferedImage img = ImageIO.read(file2.getInputStream());
				if (img != null) {
					String fileName = UUID.randomUUID().toString();
					String type = file2.getOriginalFilename().substring(file2.getOriginalFilename().indexOf("."));
					if (StringUtils.isNotBlank(type)) {
						fileName += type;
					} else {
						fileName += ".jpg";
					}
					ftpUtils.upload(file2.getInputStream(), indexPic, fileName);

					String url = ftpUtils.getUrl() + indexPic + "/" + fileName;
					vo.setPcImgUrl(url);
				} else {
					pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
					pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
					return pageInfo;
				}
			}
			if (vo.getId() == null || vo.getId() <= 0) { // 添加
				vo.setStatus(0);
				vo.setCreateTime(new Date());
				bayWindowService.save(vo);
			} else { // 修改
				bayWindowService.update(vo);
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("活动飘窗，新增或修改活动飘窗，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询活动飘窗列表
	 * 
	 * @param vo 活动飘窗
	 * @param page 当前页
	 * @param rows 每页显示的条数
	 * @param q 每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(BayWindow vo, Integer page, Integer rows, String q) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			BayWindowExample example = new BayWindowExample();
			BayWindowExample.Criteria cri = example.createCriteria();
			if (vo.getId() != null) {
				cri.andIdEqualTo(vo.getId());
			}
			if (StringUtils.isNotBlank(vo.getTitle())) {
				cri.andTitleLike("%" + vo.getTitle() + "%");
			}
			if (vo.getStatus() != null) {
				cri.andStatusEqualTo(vo.getStatus());
			}
			if (page != null && rows != null) {
				example.setLimitStart(pageInfo.getFrom());
				example.setLimitEnd(pageInfo.getSize());
			}
			pageInfo = bayWindowService.getList(example);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("活动飘窗，查询活动飘窗列表，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 删除活动飘窗
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
				bayWindowService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("活动飘窗，删除活动飘窗，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据主键查找活动飘窗
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
				BayWindow record = bayWindowService.findById(id);
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
			log.error("活动飘窗，根据主键查找活动飘窗，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
