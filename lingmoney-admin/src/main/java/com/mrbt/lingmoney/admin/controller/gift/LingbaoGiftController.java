package com.mrbt.lingmoney.admin.controller.gift;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.gift.LingbaoGiftService;
import com.mrbt.lingmoney.model.AdminUser;
import com.mrbt.lingmoney.model.LingbaoGift;
import com.mrbt.lingmoney.model.LingbaoGiftExample;
import com.mrbt.lingmoney.model.LingbaoGiftWithBLOBs;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.StringFromatUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.utils.session.UserSession;

/**
 * 我的领地礼品
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("/gift/lingbaoGift")
public class LingbaoGiftController extends BaseController {

	private Logger log = MyUtils.getLogger(LingbaoGiftController.class);

	@Autowired
	private LingbaoGiftService lingbaoGiftService;

	/**
	 * 导入excel
	 * 
	 * @author 罗鑫
	 * @param excelFile excel文件
	 * @param session session
	 * @return 返回处理结果
	 */
	@RequestMapping("/importExcel")
	public @ResponseBody Object inportExcel(MultipartFile excelFile, HttpSession session) {
		PageInfo pageInfo = new PageInfo();
		try {
			UserSession userSession = (UserSession) session.getAttribute(AppCons.SESSION_USER);
			if (userSession == null) {
				pageInfo.setCode(ResultInfo.ADMIN_LOGO_OUTTIME.getCode());
				pageInfo.setMsg(ResultInfo.ADMIN_LOGO_OUTTIME.getMsg());
			} else {
				AdminUser adminUser = userSession.getUser();
				if (adminUser == null) {
					pageInfo.setCode(ResultInfo.ADMIN_LOGO_OUTTIME.getCode());
					pageInfo.setMsg(ResultInfo.ADMIN_LOGO_OUTTIME.getMsg());
					return pageInfo;
				}

				if (excelFile == null) {
					pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
					pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
					return pageInfo;
				}

				lingbaoGiftService.importExcel(excelFile.getInputStream(), excelFile.getOriginalFilename(),
						adminUser.getName());
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品，导入excel，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 查询我的领地礼品列表
	 * 
	 * @param vo 我的领地礼品
	 * @param page 当前页
	 * @param rows 每页显示的条数
	 * @param isLottery 是否为抽奖 0否 1是
	 * @param isReachWarning 是否达到预警 0否 1是
	 * @param sort 排序
	 * @param order 排序 
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(LingbaoGift vo, Integer page, Integer rows, Integer isLottery, Integer isReachWarning,
			String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			LingbaoGiftExample example = new LingbaoGiftExample();
			LingbaoGiftExample.Criteria cri = example.createCriteria();
			List<Integer> values = new LinkedList<Integer>();
			if (isLottery != null) {
				if (isLottery == 0) { // 为普通礼品，不是抽奖礼品
					values.add(ResultNumber.ZERO.getNumber());
					values.add(ResultNumber.TWO.getNumber());
					cri.andApplyTypeIn(values);
				} else if (isLottery == 1) { // 抽奖礼品
					values.add(ResultNumber.ONE.getNumber());
					values.add(ResultNumber.TWO.getNumber());
					cri.andApplyTypeIn(values);
				}
			}
			if (isReachWarning != null) {
				if (isReachWarning == 0) { // 未达到预警值
					cri.andNotStoreReachWarning();
				} else if (isReachWarning == 1) { // 已达到预警值
					cri.andStoreReachWarning();
				}
			}
			if (StringUtils.isNotBlank(vo.getName())) {
				cri.andNameLike("%" + vo.getName() + "%");
			}
			if (StringUtils.isNotBlank(vo.getCode())) {
				cri.andCodeLike("%" + vo.getCode() + "%");
			}
			if (vo.getId() != null) {
				cri.andIdEqualTo(vo.getId());
			}
			if (vo.getType() != null) {
				cri.andTypeEqualTo(vo.getType());
			}
			if (vo.getApplyType() != null) {
				cri.andApplyTypeEqualTo(vo.getApplyType());
			}
			if (vo.getSupplierId() != null) {
				cri.andSupplierIdEqualTo(vo.getSupplierId());
			}
			if (vo.getTypeId() != null) {
				cri.andTypeIdEqualTo(vo.getTypeId());
			}
			if (vo.getShelfStatus() != null) {
				cri.andShelfStatusEqualTo(vo.getShelfStatus());
			}
			if (vo.getNewStatus() != null) {
				cri.andNewStatusEqualTo(vo.getNewStatus());
			}
			if (vo.getHasStore() != null) {
				cri.andHasStoreEqualTo(vo.getHasStore());
			}
			if (vo.getRecommend() != null) {
				cri.andRecommendEqualTo(vo.getRecommend());
			}
			if (vo.getReceiveWay() != null) {
				cri.andReceiveWayEqualTo(vo.getReceiveWay());
			}
			if (page != null && rows != null) {
				example.setLimitStart(pageInfo.getFrom());
				example.setLimitEnd(pageInfo.getSize());
			}
			sort = sort == null ? "id" : sort;
			order = order == null ? "asc" : order;
			example.setOrderByClause(sort + " " + order);
			pageInfo = lingbaoGiftService.getList(example);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品，查询我的领地礼品列表，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 设置礼品上下架
	 * 
	 * @param id 主键
	 * @param session session
	 * @return 分页实体类
	 */
	@RequestMapping("shelf")
	@ResponseBody
	public Object shelf(Integer id, HttpSession session) {
		PageInfo pageInfo = new PageInfo();
		try {
			UserSession userSession = (UserSession) session.getAttribute(AppCons.SESSION_USER);
			if (userSession != null) {
				AdminUser adminUser = userSession.getUser();
				if (adminUser != null) {
					boolean flag = lingbaoGiftService.changeShelfStatus(id, adminUser.getName());
					if (flag) {
						pageInfo.setCode(ResultInfo.SUCCESS.getCode());
						pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
					} else {
						pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
						pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
					}
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultInfo.ADMIN_LOGO_OUTTIME.getCode());
			pageInfo.setMsg(ResultInfo.ADMIN_LOGO_OUTTIME.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品，设置礼品上下架，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 设置是否为最新上架礼品
	 * 
	 * @param id 主键
	 * @param session	session
	 * @return 分页实体类
	 */
	@RequestMapping("newStatus")
	@ResponseBody
	public Object newStatus(Integer id, HttpSession session) {
		PageInfo pageInfo = new PageInfo();
		try {
			UserSession userSession = (UserSession) session.getAttribute(AppCons.SESSION_USER);
			if (userSession != null) {
				AdminUser adminUser = userSession.getUser();
				if (adminUser != null) {
					boolean flag = lingbaoGiftService.changeNewStatus(id, adminUser.getName());
					if (flag) {
						pageInfo.setCode(ResultInfo.SUCCESS.getCode());
						pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
					} else {
						pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
						pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
					}
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultInfo.ADMIN_LOGO_OUTTIME.getCode());
			pageInfo.setMsg(ResultInfo.ADMIN_LOGO_OUTTIME.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品，设置是否为最新上架礼品，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 设置是否为推荐礼品
	 * 
	 * @param id 主键
	 * @param session  session
	 * @return 分页实体类
	 */
	@RequestMapping("recommend")
	@ResponseBody
	public Object recommend(Integer id, HttpSession session) {
		PageInfo pageInfo = new PageInfo();
		try {
			UserSession userSession = (UserSession) session.getAttribute(AppCons.SESSION_USER);
			if (userSession != null) {
				AdminUser adminUser = userSession.getUser();
				if (adminUser != null) {
					boolean flag = lingbaoGiftService.changeRecommend(id, adminUser.getName());
					if (flag) {
						pageInfo.setCode(ResultInfo.SUCCESS.getCode());
						pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
					} else {
						pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
						pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
					}
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultInfo.ADMIN_LOGO_OUTTIME.getCode());
			pageInfo.setMsg(ResultInfo.ADMIN_LOGO_OUTTIME.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品，设置是否为推荐礼品，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 添加、修改、复制礼品
	 * 
	 * @param vo 我的领地礼品
	 * @param changeType 变更类型 0添加：图片上传，code生成 1修改：图片上传 2复制：图片复制，code复制
	 * @param request req
	 * @param session	session
	 * @return 分页实体类
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(LingbaoGiftWithBLOBs vo, Integer changeType, MultipartHttpServletRequest request,
			HttpSession session) {
		PageInfo pageInfo = new PageInfo();
		try {
			UserSession userSession = (UserSession) session.getAttribute(AppCons.SESSION_USER);
			if (userSession == null) { 
				pageInfo.setCode(ResultInfo.ADMIN_LOGO_OUTTIME.getCode());
				pageInfo.setMsg(ResultInfo.ADMIN_LOGO_OUTTIME.getMsg());
				return pageInfo;
			}
			AdminUser adminUser = userSession.getUser();
			if (adminUser == null) {
				pageInfo.setCode(ResultInfo.ADMIN_LOGO_OUTTIME.getCode());
				pageInfo.setMsg(ResultInfo.ADMIN_LOGO_OUTTIME.getMsg());
				return pageInfo;
			}
			if (changeType == 0) { // 添加礼品，图片上传，code生成
				MultipartFile pictureBigImg = request.getFile("pictureBigImg");
				String pictureBig = lingbaoGiftService.uploadPicture(pictureBigImg);
				vo.setPictureBig(pictureBig);

				MultipartFile pictureMiddleImg = request.getFile("pictureMiddleImg");
				String pictureMiddle = lingbaoGiftService.uploadPicture(pictureMiddleImg);
				vo.setPictureMiddle(pictureMiddle);

				MultipartFile pictureSmallImg = request.getFile("pictureSmallImg");
				String pictureSmall = lingbaoGiftService.uploadPicture(pictureSmallImg);
				vo.setPictureSmall(pictureSmall);

				MultipartFile pictureMobileImg = request.getFile("pictureMobileImg");
				String pictureMobile = lingbaoGiftService.uploadPicture(pictureMobileImg);
				vo.setPictureMobile(pictureMobile);

				if (vo.getPreferentialIntegral() == null) {
					vo.setPreferentialIntegral(0);
				}
				// 首次入库，库存数量=入库数量
				vo.setStore(vo.getHasStore());
				vo.setShelfStatus(ResultNumber.ZERO.getNumber()); // 下架
				vo.setNewStatus(ResultNumber.ZERO.getNumber()); // 不是最新上架
				vo.setRecommend(ResultNumber.ZERO.getNumber()); // 不是推荐礼品
				vo.setExchangeCount(ResultNumber.ZERO.getNumber()); // 已兑换数量
				vo.setInstoreName(adminUser.getName());
				vo.setInstoreTime(new Date());
				vo.setOperateName(adminUser.getName());
				vo.setOperateTime(new Date());
				lingbaoGiftService.save(vo);
				StringBuffer sb = new StringBuffer();
				sb.append(StringFromatUtils.leftFill(ResultNumber.THREE.getNumber(), vo.getTypeId()))
						.append(StringFromatUtils.leftFill(ResultNumber.FOUR.getNumber(), vo.getId()));
				LingbaoGiftWithBLOBs record = new LingbaoGiftWithBLOBs();
				record.setId(vo.getId());
				record.setCode(sb.toString());
				lingbaoGiftService.updateByPrimaryKeySelective(record);
			} else if (changeType == ResultNumber.ONE.getNumber()) { // 修改礼品，图片上传
				MultipartFile pictureBigImg = request.getFile("pictureBigImg");
				String pictureBig = lingbaoGiftService.uploadPicture(pictureBigImg);
				vo.setPictureBig(pictureBig);

				MultipartFile pictureMiddleImg = request.getFile("pictureMiddleImg");
				String pictureMiddle = lingbaoGiftService.uploadPicture(pictureMiddleImg);
				vo.setPictureMiddle(pictureMiddle);

				MultipartFile pictureSmallImg = request.getFile("pictureSmallImg");
				String pictureSmall = lingbaoGiftService.uploadPicture(pictureSmallImg);
				vo.setPictureSmall(pictureSmall);

				MultipartFile pictureMobileImg = request.getFile("pictureMobileImg");
				String pictureMobile = lingbaoGiftService.uploadPicture(pictureMobileImg);
				vo.setPictureMobile(pictureMobile);

				if (vo.getPreferentialIntegral() == null) {
					vo.setPreferentialIntegral(ResultNumber.ZERO.getNumber());
				}
				vo.setOperateName(adminUser.getName());
				vo.setOperateTime(new Date());
				lingbaoGiftService.updateByPrimaryKeySelective(vo);
			} else if (changeType == ResultNumber.TWO.getNumber()) { // 复制礼品，图片复制，code复制
				if (vo.getPreferentialIntegral() == null) {
					vo.setPreferentialIntegral(0);
				}
				// 复制，库存数量=入库数量
				vo.setStore(vo.getHasStore());
				vo.setExchangeCount(ResultNumber.ZERO.getNumber()); // 已兑换数量
				vo.setInstoreName(adminUser.getName());
				vo.setInstoreTime(new Date());
				vo.setOperateName(adminUser.getName());
				vo.setOperateTime(new Date());
				lingbaoGiftService.save(vo);
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品，添加、修改、复制礼品，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据主键删除礼品
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
				lingbaoGiftService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品，根据主键删除礼品，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询大字段(礼品介绍和规格参数)
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("listEditor")
	@ResponseBody
	public Object listEditor(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				LingbaoGiftWithBLOBs record = lingbaoGiftService.findById(id);
				if (record != null) {
					List<String> list = new ArrayList<String>();
					String introduction = record.getIntroduction() == null ? "" : record.getIntroduction();
					String property = record.getProperty() == null ? "" : record.getProperty();
					list.add(introduction);
					list.add(property);
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
					pageInfo.setObj(list);
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地礼品，查询大字段(礼品介绍和规格参数)，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据主键查找礼品
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
				LingbaoGift record = lingbaoGiftService.findById(id);
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
			log.error("我的领地礼品，根据主键查找礼品，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
