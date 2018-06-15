package com.mrbt.lingmoney.admin.controller.info;

import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.info.SupportBankService;
import com.mrbt.lingmoney.model.SupportBank;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 京东支付，支持银行管理
 * 
 * @author 000608
 *
 */

@Controller
@RequestMapping("/info/supportBank")
public class SupportBankController {
	
	private Logger log = MyUtils.getLogger(SupportBankController.class);
	@Autowired
	private SupportBankService supportBankService;
	
	@Autowired
	private FtpUtils ftpUtils;

	private String bankLogoUrl = "bankLogoUrl";

	/***
	 * 删除支付银行
	 * @param id	数据ID
	 * @return	处理结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		log.info("/info/supportBank/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			supportBankService.delete(id);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 支付银行列表
	 * 
	 * @param vo	封装对象
	 * @param bankName	银行名称
	 * @param page	页数
	 * @param rows	条数
	 * @return	处理结果
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(SupportBank vo, String bankName, Integer rows, Integer page) {
		log.info("/info/supportBank/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			if (StringUtils.isNotBlank(bankName)) {
				vo.setBankName(bankName);
			}
			supportBankService.listGrid(vo, pageInfo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 支付银行保存
	 * @param vo 封装对象
	 * @param request request
	 * @return	处理结果
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(final SupportBank vo, final MultipartHttpServletRequest request) {
		log.info("/info/supportBank/save");
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo.getId() == null || vo.getId() <= 0) {
				ExecutorService executorService = Executors.newFixedThreadPool(ResultNumber.TEN.getNumber());
				Future<Boolean> future = executorService.submit(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						MultipartFile file = request.getFile("bankImg");
						if (file != null) {
							if (file.getSize() > ResultNumber.UPLOAD_FILE_SIZE.getNumber()) {
								log.error("上传文件不能大于10kb");
								return false;
							}
							BufferedImage img = ImageIO.read(file.getInputStream());
							if (img != null) {
								String fileName = UUID.randomUUID().toString();
								String type = file.getOriginalFilename()
										.substring(file.getOriginalFilename().indexOf("."));
								if (StringUtils.isNotBlank(type)) {
									fileName += type;
								} else {
									fileName += ".jpg";
								}
								ftpUtils.upload(file.getInputStream(), bankLogoUrl, fileName);

								String bankLogo = ftpUtils.getUrl() + bankLogoUrl + "/" + fileName;
								vo.setBankLogo(bankLogo);
								return true;
							} else {
								return false;
							}
						}
						return false;
					}
				});
				executorService.shutdown();
				if (!future.get()) {
					pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
					pageInfo.setMsg("上传的文件可能不是图片格式或者尺寸超过10kb");
					return pageInfo;
				}
				supportBankService.save(vo);
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 更新支付银行
	 * @param vo	封装对象
	 * @param request	request
	 * @return	返回结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(final SupportBank vo, final MultipartHttpServletRequest request) {
		log.info("/info/supportBank/update");
		PageInfo pageInfo = new PageInfo();
		try {
			supportBankService.update(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 更新排序
	 * @param vo	封装对象
	 * @param request	req
	 * @return	返回结果
	 */
	@RequestMapping("saveOrder")
	@ResponseBody
	public Object saveOrder(final SupportBank vo, final HttpServletRequest request) {
		log.info("/info/supportBank/saveOrder");
		PageInfo pageInfo = new PageInfo();
		try {
			supportBankService.updateOrder(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/***
	 * 获取最新的Order，用于添加
	 * @param request	request
	 * @return	返回结果
	 */
	@RequestMapping("getOrder")
	@ResponseBody
	public Object getOrderOrder(final HttpServletRequest request) {
		log.info("/info/supportBank/getOrder");
		try {
			Integer result = supportBankService.getOrderOrder();
			if (result == ResultNumber.MINUS_ONE.getNumber()) {
				return ResultNumber.ONE.getNumber();
			}
			return ++result;
		} catch (Exception ex) {
			log.error(ex);
			return ResultNumber.MINUS_ONE.getNumber();
		}
	}

	/***
	 * 添加
	 * @param bankcode	银行CODE
	 * @param request	request
	 * @return	返回结果
	 */
	@RequestMapping("checkBankCode")
	@ResponseBody
	public Object isExistsBankCode(String bankcode, final HttpServletRequest request) {
		log.info("/info/supportBank/checkBankCode");
		try {
			if (StringUtils.isEmpty(bankcode) || StringUtils.isBlank(bankcode)) {
				return ResultNumber.MINUS_ONE.getNumber();
			}
			return supportBankService.isExistsBankCode(bankcode);
		} catch (Exception ex) {
			log.error(ex);
			return ResultNumber.MINUS_ONE.getNumber();
		}
	}

	/**
	 * 编辑
	 * @param bankcode	银行CODE
	 * @param id	数据ID
	 * @param request	request
	 * @return	处理结果
	 */
	@RequestMapping("checkBankCodeEdit")
	@ResponseBody
	public Object isExistsBankCodeEdit(String bankcode, int id, final HttpServletRequest request) {
		log.info("/info/supportBank/checkBankCodeEdit");
		try {
			if (id < 0 || StringUtils.isEmpty(bankcode) || StringUtils.isBlank(bankcode)) {
				return ResultNumber.MINUS_ONE.getNumber();
			}
			return supportBankService.isExistsBankCodeEdit(bankcode, id);
		} catch (Exception ex) {
			log.error(ex);
			return ResultNumber.MINUS_ONE.getNumber();
		}
	}

	/**
	 * 添加银行维护状态
	 * @param bankId	数据ID
	 * @param request	request
	 * @param response	response
	 * @return	返回结果
	 */
	@RequestMapping("addbankMaintain")
	public @ResponseBody Object addbankMaintain(Integer bankId, HttpServletRequest request, HttpServletResponse response) {
		log.info("/info/supportBank/addbankMaintain");
		PageInfo pageInfo = new PageInfo();
		try {
			Integer res = supportBankService.appendBankMaintain(bankId);
			if (res > 0) {
				pageInfo.setMsg("添加维护成功!");
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			} else {
				pageInfo.setCode(ResultInfo.UPDATE_ERROR.getCode());
				pageInfo.setMsg("添加维护失败!");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}
	
	/**
	 * 添加银行维护状态
	 * @param bankId	银行ID
	 * @param request	request
	 * @param response response
	 * @return	处理结果
	 */
	@RequestMapping("delbankMaintain")
	public @ResponseBody Object delbankMaintain(Integer bankId, HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer res = supportBankService.deleteBankMaintain(bankId);
			if (res > 0) {
				return ResponseUtils.success("删除维护成功!");
			} else {
				return ResponseUtils.failure(ResultInfo.UPDATE_ERROR.getCode(), "删除维护失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.failure(ResultInfo.SERVER_ERROR.getCode(), "删除维护失败!服务器错误！");
		}
	}
	
	/**
	 * 停止购买随心取
	 * @param status	状态
	 * @param msg	提示语
	 * @param request	req
	 * @param response	res
	 * @return	返回结果
	 */
	@RequestMapping("takeHeartAllowBuy")
	public @ResponseBody Object takeHeartAllowBuy(Integer status, String msg, HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer res = supportBankService.takeHeartAllowBuy(status, msg);
			if (res > 0) {
				return ResponseUtils.success("修改成功!");
			} else {
				return ResponseUtils.failure(ResultInfo.UPDATE_ERROR.getCode(), "修改失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.failure(ResultInfo.SERVER_ERROR.getCode(), "修改维护失败!服务器错误！");
		}
	}

}
