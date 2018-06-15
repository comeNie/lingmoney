package com.mrbt.lingmoney.admin.service.product.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.product.GiftExchangeInfoService;
import com.mrbt.lingmoney.mapper.ActivityProductMapper;
import com.mrbt.lingmoney.mapper.EmployeeRelationsMappingMapper;
import com.mrbt.lingmoney.mapper.GiftExchangeInfoMapper;
import com.mrbt.lingmoney.mapper.GiftExchangeInfoNewMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.ActivityProduct;
import com.mrbt.lingmoney.model.ActivityProductExample;
import com.mrbt.lingmoney.model.EmployeeRelationsMapping;
import com.mrbt.lingmoney.model.EmployeeRelationsMappingExample;
import com.mrbt.lingmoney.model.GiftExchangeInfo;
import com.mrbt.lingmoney.model.GiftExchangeInfoNew;
import com.mrbt.lingmoney.model.GiftExchangeInfoVo;
import com.mrbt.lingmoney.model.GiftExchangeInfoVo2;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 产品设置——》拉新活动礼品兑换信息
 */
@SuppressWarnings("deprecation")
@Service
public class GiftExchangeInfoServiceImpl implements GiftExchangeInfoService {
	@Autowired
	private ActivityProductMapper activityProductMapper;
	@Autowired
	private GiftExchangeInfoMapper giftExchangeInfoMapper;
	@Autowired
	private EmployeeRelationsMappingMapper employeeRelationsMappingMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private TradingMapper tradingMapper;
    @Autowired
    private GiftExchangeInfoNewMapper giftExchangeInfoNewMapper;

	/**
	 * 定时查询满足送话费条件的记录，排除gift_exchange_info中已经存在的记录，批量插入gift_exchange_info表
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public void batchInsertGiftExchangeInfo(int category) {
		try {
			// 随心取产品ID
			// String pid = ProductUtils.getContent("takeHeart_pID");
			String pid = "391";
			// 查询拉新活动
			String key = "recommend";
			if (category == ResultParame.ResultNumber.ZERO.getNumber()) {
				key = "recommend";
				System.out.print("拉新活动：");
			} else if (category == ResultParame.ResultNumber.ONE.getNumber()) {
				key = "newrecommend";
				System.out.print("新吉粉活动：");
			} else if (category == ResultParame.ResultNumber.TWO.getNumber()) { // 拉新活动第二季
				key = "invitation";
				System.out.print("拉新活动第二季：");
			}

			ActivityProductExample example = new ActivityProductExample();
			ActivityProductExample.Criteria criteria = example.createCriteria();
			criteria.andStatusEqualTo(1).andPriorityEqualTo(0).andActUrlEqualTo(key);
			example.setOrderByClause("create_time desc");
			List<ActivityProduct> list = activityProductMapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				ActivityProduct ap = list.get(0);
				Map<String, Object> params = new HashMap<String, Object>();
				String empStartDate = "2017-05-27 00:00:00";
				params.put("startDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(empStartDate));
				params.put("endDate", ap.getEndDate());
				params.put("pid", pid);
				params.put("category", category);
				// 1、满足送话费条件的记录 referralId, uId, sumMoney, productName, buyDt
				List<GiftExchangeInfoVo> giftCallList = giftExchangeInfoMapper.queryGiftCall(params);

				if (giftCallList != null && giftCallList.size() > 0) {
					// 保存有效结果
					List<GiftExchangeInfoVo> validInfo = new ArrayList<GiftExchangeInfoVo>();
					for (GiftExchangeInfoVo giftExchangeInfoVo : giftCallList) {
						// 剔除未满足条件用户
						if (category == 1) {
							if (!isValidNewGiFan(giftExchangeInfoVo.getuId())) {
								continue;
							}
						}

						giftExchangeInfoVo.setActivityId(ap.getId()); // 活动id
						giftExchangeInfoVo.setNum(1); // 数量
						giftExchangeInfoVo.setStatus(0); // 状态 0兑换成功 1已发货 2已收货
						giftExchangeInfoVo.setExchangeTime(new Date()); // 兑换时间定时任务执行时间
						giftExchangeInfoVo.setType(0); // 类型 0话费 1大奖
						giftExchangeInfoVo.setCategory(category); // 类别 0拉新活动
																	// 1新吉粉活动
																	// 2新的拉新活动
						validInfo.add(giftExchangeInfoVo);
					}
					// 批量插入
					if (category == 0 || category == 1) {
						giftExchangeInfoMapper.batchInsertGiftExchangeInfo(validInfo);
					} else if (category == ResultParame.ResultNumber.TWO.getNumber()) {
						giftExchangeInfoMapper.batchInsertGiftExchangeInfoNew(validInfo);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询数据
	 * 
	 * @param pageInfo
	 */
	@Override
	public void findDataGrid(PageInfo pageInfo) {
		List<GiftExchangeInfoVo> list = giftExchangeInfoMapper.findGiftExchangeInfoCondition(pageInfo);
		if (list != null && list.size() > 0) {
			// 遍历list，判断推荐人有无上级推荐人
			String referralId; // 推荐人用户id
			String referralCode = null; // 推荐人推荐码
			String uId; // 新客户用户id
			Double threeBuyMoney = 0d; // 新客户购买3个月以上产品总金额
			for (GiftExchangeInfoVo giftExchangeInfoVo : list) {
				referralId = giftExchangeInfoVo.getReferralId(); // 258
                if (referralId != null) {
                    referralCode = giftExchangeInfoVo.getReferralCode(); // MPX65301
                    uId = giftExchangeInfoVo.getuId(); // 4589
                    // 查询新客户购买3个月以上产品总金额
                    threeBuyMoney = giftExchangeInfoMapper.queryThreeMonthBuy(uId);
                    giftExchangeInfoVo.setThreeBuyMoney(new BigDecimal(threeBuyMoney));
                    // 推荐人属性信息
                    UsersPropertiesExample usersPropertiesExample = new UsersPropertiesExample();
                    usersPropertiesExample.createCriteria().andUIdEqualTo(referralId);
                    List<UsersProperties> listUsersProperties = usersPropertiesMapper
                            .selectByExample(usersPropertiesExample);
                    if (listUsersProperties != null && listUsersProperties.size() > 0) {
                        UsersProperties usersProperties = listUsersProperties.get(0);
                        String upReferralId = usersProperties.getReferralId(); // 59
                        // 如果推荐人有上级，则找到上一级推荐人的推荐码，关联employee_relations_mapping表查出信息
                        if (upReferralId != null) {
                            // 上级推荐人属性信息
                            UsersPropertiesExample upUsersPropertiesExample = new UsersPropertiesExample();
                            upUsersPropertiesExample.createCriteria().andUIdEqualTo(upReferralId);
                            List<UsersProperties> listUpUsersProperties = usersPropertiesMapper
                                    .selectByExample(upUsersPropertiesExample);
                            if (listUpUsersProperties != null && listUpUsersProperties.size() > 0) {
                                UsersProperties upUsersProperties = listUpUsersProperties.get(0);
                                // 上级推荐人推荐码000197
                                String upReferralCode = upUsersProperties.getReferralCode();
                                // 上级推荐人的推荐人的uid
                                String upReferralIdUp = upUsersProperties.getReferralId();
                                // 如果上级推荐人有上上级，则找到上上级推荐人的推荐码，关联employee_relations_mapping表查出信息
                                if (upReferralIdUp != null) {
                                    // 上上级推荐人属性信息
                                    UsersPropertiesExample upUsersPropertiesExampleUp = new UsersPropertiesExample();
                                    upUsersPropertiesExampleUp.createCriteria().andUIdEqualTo(upReferralIdUp);
                                    List<UsersProperties> listUpUsersPropertiesUp = usersPropertiesMapper
                                            .selectByExample(upUsersPropertiesExampleUp);
                                    if (listUpUsersPropertiesUp != null && listUpUsersPropertiesUp.size() > 0) {
                                        UsersProperties upUsersPropertiesUp = listUpUsersPropertiesUp.get(0);
                                        // 上上级推荐人推荐码000197
                                        String upReferralCodeUp = upUsersPropertiesUp.getReferralCode();
                                        // 查询映射表
                                        EmployeeRelationsMappingExample employeeRelationsMappingExample = new EmployeeRelationsMappingExample();
                                        employeeRelationsMappingExample.createCriteria().andEmployeeidEqualTo(
                                                upReferralCodeUp);
                                        List<EmployeeRelationsMapping> listEmployeeRelationsMapping = employeeRelationsMappingMapper
                                                .selectByExample(employeeRelationsMappingExample);
                                        if (listEmployeeRelationsMapping != null
                                                && listEmployeeRelationsMapping.size() > 0) {
                                            EmployeeRelationsMapping employeeRelationsMapping = listEmployeeRelationsMapping
                                                    .get(0);
                                            // 赋值
                                            giftExchangeInfoVo.setEmployeeIDUp(upReferralCodeUp);
                                            giftExchangeInfoVo.setEmployeeNameUp(employeeRelationsMapping
                                                    .getEmployeeName());
                                            giftExchangeInfoVo
                                                    .setDepartmentUp(employeeRelationsMapping.getDepartment());
                                        }
                                    }
                                }
                                // 查询映射表
                                EmployeeRelationsMappingExample employeeRelationsMappingExample = new EmployeeRelationsMappingExample();
                                employeeRelationsMappingExample.createCriteria().andEmployeeidEqualTo(upReferralCode);
                                List<EmployeeRelationsMapping> listEmployeeRelationsMapping = employeeRelationsMappingMapper
                                        .selectByExample(employeeRelationsMappingExample);
                                if (listEmployeeRelationsMapping != null && listEmployeeRelationsMapping.size() > 0) {
                                    EmployeeRelationsMapping employeeRelationsMapping = listEmployeeRelationsMapping
                                            .get(0);
                                    // 赋值
                                    giftExchangeInfoVo.setEmployeeID(upReferralCode);
                                    giftExchangeInfoVo.setEmployeeName(employeeRelationsMapping.getEmployeeName());
                                    giftExchangeInfoVo.setDepartment(employeeRelationsMapping.getDepartment());
                                }
                            }
                        } else { // 推荐人无上级
                            // 如果推荐码以“0”开始，（000197）则无上级推荐人，直接查出映射表赋值
                            if (referralCode.startsWith("0")) {
                                // 查询映射表
                                EmployeeRelationsMappingExample employeeRelationsMappingExample = new EmployeeRelationsMappingExample();
                                employeeRelationsMappingExample.createCriteria().andEmployeeidEqualTo(referralCode);
                                List<EmployeeRelationsMapping> listEmployeeRelationsMapping = employeeRelationsMappingMapper
                                        .selectByExample(employeeRelationsMappingExample);
                                if (listEmployeeRelationsMapping != null && listEmployeeRelationsMapping.size() > 0) {
                                    EmployeeRelationsMapping employeeRelationsMapping = listEmployeeRelationsMapping
                                            .get(0);
                                    // 赋值
                                    giftExchangeInfoVo.setEmployeeID(referralCode);
                                    giftExchangeInfoVo.setEmployeeName(employeeRelationsMapping.getEmployeeName());
                                    giftExchangeInfoVo.setDepartment(employeeRelationsMapping.getDepartment());
                                }
                            }
                        }
                    }

				}

			}
		}

		pageInfo.setRows(list);
		pageInfo.setTotal(giftExchangeInfoMapper.findGiftExchangeInfoCount(pageInfo));
	}

	/**
	 * 发货
	 * 
	 * @param vo
	 * @return
	 */
	@Override
	@Transactional
	public PageInfo processingDelivery(GiftExchangeInfo vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo != null && vo.getId() != null) {
				vo.setStatus(1);
				vo.setSendTime(vo.getSendTime() == null ? new Date() : vo.getSendTime());
				giftExchangeInfoMapper.updateByPrimaryKeySelective(vo);
				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				return pageInfo;
			}
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无记录");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 确认收货
	 * 
	 * @param ids
	 * @return
	 */
	@Override
	@Transactional
	public PageInfo comfirmReceipt(String ids) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] idArr = ids.split(",");
				if (idArr != null && idArr.length > 0) {
					Date now = new Date();
					for (String string : idArr) {
						GiftExchangeInfo record = giftExchangeInfoMapper.selectByPrimaryKey(NumberUtils.toInt(string));
						record.setStatus(ResultParame.ResultNumber.TWO.getNumber());
						record.setReceiveTime(now);
						giftExchangeInfoMapper.updateByPrimaryKeySelective(record);
					}
					pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无记录");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 导出到excel
	 * 
	 * @description
	 * @param request
	 * @param response
	 * @param json
	 *            后台查询后的rows组成的json串
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	@Transactional
	public PageInfo exportExcel(HttpServletRequest request, HttpServletResponse response, String json) {
		PageInfo pageInfo = new PageInfo();
		// 文件输出流
		FileOutputStream fo = null;
		try {
			if (StringUtils.isNotBlank(json) && !"[]".equals(json)) {
				JSONArray jsonArray = JSONArray.fromObject(json);
				List<GiftExchangeInfoVo2> list = JSONArray.toList(jsonArray, new GiftExchangeInfoVo2(),
						new JsonConfig()); // 参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据
				if (list != null) {
					// 文件名
					String fileName = "gift_exchange_info_" + DateUtils.getDateStrByBiz(new Date()) + ".xls";
					// 保存根路径 tomcat
					String saveRootPath = request.getSession().getServletContext().getRealPath("/recommend/");
					// 文件保存路径
					String fileSavePath = saveRootPath.endsWith(File.separator) ? saveRootPath
							: (saveRootPath + File.separator) + fileName;
					response.setCharacterEncoding("utf-8");
					response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					response.setHeader("content-disposition", "attachment;filename=\"" + fileName);
					// 创建文件夹
					File fileRootPath = new File(saveRootPath);
					if (!fileRootPath.exists()) {
						fileRootPath.mkdirs();
					}
					fo = new FileOutputStream(new File(fileSavePath));
					// 导出excel
					exportExcel(fo, list);
					// 返回文件目录
					String redirectPath = "/recommend/" + fileName;
					pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					pageInfo.setObj(redirectPath);
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无记录");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

	/**
	 * 导出excel
	 * 
	 * @description
	 * @param outputStream
	 *            输出流
	 * @param list
	 *            对象list
	 */
	public static void exportExcel(OutputStream outputStream, List<GiftExchangeInfoVo2> list) {
		try {
			// 1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();

			// 1.1、创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0,
					ResultParame.ResultNumber.FOUR.getNumber()); // 起始行号，结束行号，起始列号，结束列号

			// 1.2、头标题样式
			HSSFCellStyle style1 = createCellStyle(workbook, (short) ResultParame.ResultNumber.SIXTEEN.getNumber());

			// 1.3、列标题样式
			HSSFCellStyle style2 = createCellStyle(workbook, (short) ResultParame.ResultNumber.THIRTEEN.getNumber());

			// 创建表
			HSSFSheet sheet = workbook.createSheet("拉新活动兑换信息");

			// 加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);

			// 设置默认列宽
			sheet.setDefaultColumnWidth(ResultParame.ResultNumber.EIGHTEEN.getNumber());

			// 创建头标题行
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell = row1.createCell(0);

			// 加载头标题样式
			cell.setCellStyle(style1);

			// 头标题文字
			cell.setCellValue("兑换信息列表");

			// 创建第二行
			HSSFRow row2 = sheet.createRow(1);

			// 标题
			String[] titles = {"推荐人id", "推荐人手机号", "推荐人姓名", "推荐人推荐码", "上级推荐人姓名", "上级推荐人员工号", "上级推荐人部门", "上上级推荐人姓名",
					"上上级推荐人员工号", "上上级推荐人部门", "充值卡号", "充值卡密码", "充值手机号码", "推荐人所在省", "推荐人所在市", "推荐人所在区/县", "推荐人详细地址",
					"新客户首投金额", "新客户首投金额(3个月及以上产品)", "新客户首投产品名称", "新客户首投时间", "新客户姓名", "新客户手机号", "新客户注册日期", "新客户身份证号",
					"新客户年龄", "活动名称", "礼品名称", "礼品数量", "礼品状态", "礼品类型", "快递单号", "快递公司", "兑换时间", "发货时间", "收货时间 " };

			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			if (list != null) {
				// 加载用户数据
				HSSFRow rowi = null;
				GiftExchangeInfoVo2 vo = null;
				for (int i = 0; i < list.size(); i++) {
					rowi = sheet.createRow(i + ResultParame.ResultNumber.TWO.getNumber());
					vo = list.get(i);
					int j = ResultParame.ResultNumber.MINUS_ONE.getNumber();
					rowi.createCell(++j).setCellValue(vo.getReferralId());
					rowi.createCell(++j).setCellValue(vo.getTelephone());
					rowi.createCell(++j).setCellValue(vo.getName());
					rowi.createCell(++j).setCellValue(vo.getReferralCode());
					rowi.createCell(++j).setCellValue(vo.getEmployeeName());
					rowi.createCell(++j).setCellValue(vo.getEmployeeID());
					rowi.createCell(++j).setCellValue(vo.getDepartment());
					rowi.createCell(++j).setCellValue(vo.getEmployeeNameUp());
					rowi.createCell(++j).setCellValue(vo.getEmployeeIDUp());
					rowi.createCell(++j).setCellValue(vo.getDepartmentUp());
					rowi.createCell(++j).setCellValue(vo.getRechargeCode());
					rowi.createCell(++j).setCellValue(vo.getRechargeCode());
					rowi.createCell(++j).setCellValue(vo.getMobile());
					rowi.createCell(++j).setCellValue(vo.getProvince());
					rowi.createCell(++j).setCellValue(vo.getCity());
					rowi.createCell(++j).setCellValue(vo.getTown());
					rowi.createCell(++j).setCellValue(vo.getAddress());
					rowi.createCell(++j).setCellValue(vo.getBuyMoney() != null ? vo.getBuyMoney().doubleValue() : 0);
					rowi.createCell(++j)
							.setCellValue(vo.getThreeBuyMoney() != null ? vo.getThreeBuyMoney().doubleValue() : 0);
					rowi.createCell(++j).setCellValue(vo.getProductName());
					rowi.createCell(++j)
							.setCellValue(vo.getBuyDt() != null ? DateUtils.sf.format(new Date(vo.getBuyDt())) : "");
					rowi.createCell(++j).setCellValue(vo.getNewName());
					rowi.createCell(++j).setCellValue(vo.getNewTelephone());
					rowi.createCell(++j).setCellValue(
							vo.getNewRegDate() != null ? DateUtils.sf.format(new Date(vo.getNewRegDate())) : "");
					rowi.createCell(++j).setCellValue(vo.getNewIdCard());
					rowi.createCell(++j).setCellValue(vo.getNewAge() != null ? vo.getNewAge() : 0);
					rowi.createCell(++j).setCellValue(vo.getActivityName());
					rowi.createCell(++j).setCellValue(vo.getGiftName());
					rowi.createCell(++j).setCellValue(vo.getNum());
					Integer status = vo.getStatus();
					String statusStr = "兑换成功";
					if (status == ResultParame.ResultNumber.ONE.getNumber()) {
						statusStr = "已发货";
					}
					if (status == ResultParame.ResultNumber.TWO.getNumber()) {
						statusStr = "已收货";
					}
					rowi.createCell(++j).setCellValue(statusStr);
					Integer type = vo.getType();
					String getTypeStr = "50元话费充值卡";
					if (type == 1) {
						getTypeStr = "超值大礼包";
					}
					rowi.createCell(++j).setCellValue(getTypeStr);
					rowi.createCell(++j).setCellValue(vo.getExpressNumber());
					rowi.createCell(++j).setCellValue(vo.getExpressCompany());
					rowi.createCell(++j).setCellValue(
							vo.getExchangeTime() != null ? DateUtils.sft.format(new Date(vo.getExchangeTime())) : "");
					rowi.createCell(++j).setCellValue(
							vo.getSendTime() != null ? DateUtils.sft.format(new Date(vo.getSendTime())) : "");
					rowi.createCell(++j).setCellValue(
							vo.getReceiveTime() != null ? DateUtils.sft.format(new Date(vo.getReceiveTime())) : "");
				}
			}
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * excel样式
	 * 
	 * @description
	 * @param workbook
	 *            workbook
	 * @param s
	 *            s
	 * @return 数据返回
	 */
	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short s) {
		HSSFCellStyle createCellStyle = workbook.createCellStyle();
		// 水平居中
		createCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 垂直居中
		createCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 创建字体
		HSSFFont font = workbook.createFont();
		// 加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 字体
		font.setFontHeightInPoints(s);
		createCellStyle.setFont(font);
		return createCellStyle;
	}

	/**
	 * 新吉粉送礼金额验证 新吉粉送礼活动条件 三个条件满足任一一个即赠送 2017-09-19 更新 1、首笔购买5000 2、当天累计5000
	 * 3、同期产品累计5000
	 *
	 * @Description
	 * @param uid
	 *            uid
	 * @return 数据返回
	 */
	private boolean isValidNewGiFan(String uid) {
		BigDecimal buyMoney = new BigDecimal("0");
		// 1.验证首笔购买是否满足5000
		buyMoney = tradingMapper.queryFirstTradingAmount(uid);
		if (buyMoney != null
				&& buyMoney.compareTo(new BigDecimal("5000")) != ResultParame.ResultNumber.MINUS_ONE.getNumber()) {
			// 满足
			return true;
		}

		// 2.验证当天累计是否满5000
		buyMoney = tradingMapper.sumTodayBuyMoney(uid);
		if (buyMoney != null
				&& buyMoney.compareTo(new BigDecimal("5000")) != ResultParame.ResultNumber.MINUS_ONE.getNumber()) {
			// 满足
			return true;
		}

		// 3.同期产品是否累计满5000
		buyMoney = tradingMapper.sumSameProductBuyMoney(uid);
		if (buyMoney != null
				&& buyMoney.compareTo(new BigDecimal("5000")) != ResultParame.ResultNumber.MINUS_ONE.getNumber()) {
			// 满足
			return true;
		}

		return false;
	}

    /**
     * 发货
     * 
     * @param vo
     * @return
     */
    @Override
    public PageInfo processingDeliveryNew(GiftExchangeInfoNew vo) {
        PageInfo pageInfo = new PageInfo();
        try {
            if (vo != null && vo.getId() != null) {
                vo.setStatus(1);
                vo.setSendTime(vo.getSendTime() == null ? new Date() : vo.getSendTime());
                giftExchangeInfoNewMapper.updateByPrimaryKeySelective(vo);
                pageInfo.setResultInfo(ResultInfo.SUCCESS);
            } else {
                pageInfo.setResultInfo(ResultInfo.NO_DATA);
            }

        } catch (Exception e) {
            e.printStackTrace();
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
        }
        return pageInfo;
    }

    /**
     * 确认收货
     * 
     * @param ids
     * @return
     */
    @Override
    public PageInfo comfirmReceiptNew(String ids) {
        PageInfo pageInfo = new PageInfo();
        try {
            if (StringUtils.isNotBlank(ids)) {
                String[] idArr = ids.split(",");
                if (idArr != null && idArr.length > 0) {
                    Date now = new Date();
                    for (String string : idArr) {
                        GiftExchangeInfoNew record = giftExchangeInfoNewMapper.selectByPrimaryKey(NumberUtils
                                .toInt(string));
                        record.setStatus(2);
                        record.setReceiveTime(now);
                        giftExchangeInfoNewMapper.updateByPrimaryKeySelective(record);
                    }
                    pageInfo.setResultInfo(ResultInfo.SUCCESS);
                } else {
                    pageInfo.setResultInfo(ResultInfo.NO_DATA);
                }
            } else {
                pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
            ;
        }
        return pageInfo;
    }

}
