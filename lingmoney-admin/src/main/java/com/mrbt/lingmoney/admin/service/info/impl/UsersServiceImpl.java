package com.mrbt.lingmoney.admin.service.info.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.info.UsersService;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersBankMapper;
import com.mrbt.lingmoney.mapper.UsersCustomerMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.BuyRecordsVo;
import com.mrbt.lingmoney.model.EmployeeRelationsMapping;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.model.UsersExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.model.UsersVo;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.MD5Utils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.QRCodeUtil;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 用户设置
 * 
 */
@SuppressWarnings("deprecation")
@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private FtpUtils ftpUtils;
	@Autowired
	private UsersBankMapper usersBankMapper;
	@Autowired
	private UsersCustomerMapper usersCustomerMapper;
	@Autowired
	private CustomQueryMapper customQueryMapper;

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	@Override
	public void delete(String id) {
		usersMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 */
	@Transactional
	@Override
	public void save(Users vo) {
		usersMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Transactional
	@Override
	public void update(Users vo) {
		usersMapper.updateByPrimaryKeySelective(vo);
	}

	/**
	 * 用户注册
	 */
	@Transactional
	@Override
	public void registerUsers(String account, String password, String referralCode, String phone, boolean isPhone,
			String name, String cardNo, String path) {

		// 添加用户（手机端，手机号码即为用户名）
		String userId = UUID.randomUUID().toString().replace("-", "");
		Users users = new Users();
		users.setId(userId);
		users.setLoginAccount(account);
		users.setLoginPsw(MD5Utils.MD5(password));
		users.setLastLogin(new Date()); // 最后第一登陆时间默认为当天
		users.setType(0);
		usersMapper.insertSelective(users);

		// 添加用户属性
		UsersProperties usersProperties = new UsersProperties();
		String id = users.getId();

		usersProperties.setBank(0); // 默认未绑定银行卡
		usersProperties.setCertification(0); // 默认未通过身份验证
		usersProperties.setLevel(1); // 默认会员等级为零级
		usersProperties.setNickName("领钱儿网用户" + "" + id);

		if (name != null && name.length() > 0) {
			usersProperties.setName(name);
		}

		if (cardNo != null && cardNo.length() > 0) {
			usersProperties.setIdCard(cardNo);
		}

		usersProperties.setReferralCode(referralCode); // 推荐码
		usersProperties.setRegDate(new Date()); // 注册日期为当天
		usersProperties.setuId(users.getId()); // 对应用户
		usersProperties.setAutoPay(0);
		usersProperties.setFirstBuy(0);
		usersProperties.setUserLevel(0);
		usersProperties.setPlatformType(1);
		usersProperties.setRecommendedLevel(id + "");

		// 生存二维码并上传
		try {

			String text = AppCons.COMPANY_URL + "?referralTel=" + referralCode;
			// 二维码的图片格式
			String format = "gif";

			String fileName = "referralCode" + usersProperties.getuId() + ".gif";

			BufferedImage buffer = QRCodeUtil.encodeBufferedImage(text, path + "/resource/images/lingqian.gif", false);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(buffer, format, os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());

			ftpUtils.upload(is, "TUPAN" + "/" + usersProperties.getuId(), fileName);

			String basePath = ftpUtils.getUrl() + "TUPAN" + "/" + usersProperties.getuId() + "/" + fileName;
			usersProperties.setCodePath(basePath);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		usersPropertiesMapper.insertSelective(usersProperties);

		/**
		 * 用户账户表增加
		 */
		UsersAccount usersAccount = new UsersAccount();
		usersAccount.setuId(users.getId());
		usersAccountMapper.insertSelective(usersAccount);

		UsersBank usersBank = new UsersBank();
		usersBank.setuId(users.getId());
		usersBankMapper.insertSelective(usersBank);

	}

	@Override
	public List<String> selectAllUserId() {
		return usersMapper.selectAllUserId();
	}

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return ProductUsersExample辅助类
	 */
	private UsersExample createUsersExample(Users vo) {
		UsersExample example = new UsersExample();
		UsersExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(vo.getLoginAccount())) {
			criteria.andLoginAccountLike("%" + vo.getLoginAccount() + "%");
		}
		if (StringUtils.isNotBlank(vo.getTelephone())) {
			criteria.andTelephoneLike("%" + vo.getTelephone() + "%");
		}
		return example;
	}

	@Override
	public void listGrid(Users vo, PageInfo pageInfo) {
		UsersExample example = createUsersExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		pageInfo.setRows(usersMapper.selectByExample(example));
		pageInfo.setTotal(usersMapper.countByExample(example));
	}

	@Override
	public void selectRecommendUser(String referralCode, PageInfo pageInfo) {
		UsersPropertiesExample usersPropertiesExample = new UsersPropertiesExample();
		if (StringUtils.isNotBlank(referralCode)) {
			usersPropertiesExample.createCriteria().andReferralCodeEqualTo(referralCode);
		}
		List<UsersProperties> list = usersPropertiesMapper.selectByExample(usersPropertiesExample);
		if (list != null && list.size() > 0) {
			List<UsersProperties> list2 = usersPropertiesMapper.selectRecommendUser(list.get(0).getuId());
			pageInfo.setRows(list2);
		}
	}

	@Override
	public Users findByPk(String id) {
		return usersMapper.selectByPrimaryKey(id);
	}

	@Override
	public void upload(MultipartHttpServletRequest request, String uploadFilePath, PageInfo pageInfo) {
		try {
			MultipartFile file = request.getFile("txtFile");
			String fileName = file.getOriginalFilename();
			String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (file.getSize() <= 0 || !"txt".equals(prefix)) {
				pageInfo.setCode(ResultParame.ResultInfo.TXT_FILE.getCode());
				pageInfo.setMsg(ResultParame.ResultInfo.TXT_FILE.getMsg());
				return;
			}

			InputStream is = file.getInputStream();
			String path = request.getSession().getServletContext().getRealPath("");
			uploadFilePath = path + File.separator + "temp";
			// 如果不存在该文件夹，则创建
			File tempFileDir = new File(uploadFilePath);
			if (!tempFileDir.exists()) {
				tempFileDir.mkdirs();
			}
			// 如果服务器已经存在和上传文件同名的文件，则输出提示信息
			File tempFile = new File(uploadFilePath + File.separator + fileName);
			if (tempFile.exists()) {
			    tempFile.delete();
			}
			// 开始保存文件到服务器
			if (!fileName.equals("")) {

				FileOutputStream fos = new FileOutputStream(uploadFilePath + File.separator + fileName);

				byte[] buffer = new byte[ResultParame.ResultNumber.EIGHT_ONE_NINETY_TWO.getNumber()]; // 每次读8K字节
				int count = 0;
				// 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count); // 向服务端文件写入字节流
				}
				fos.close(); // 关闭FileOutputStream对象
				is.close(); // InputStream对象
			}

			File files = new File(uploadFilePath + File.separator + fileName);
			BufferedReader reader = null;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(files), "gbk"));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				String[] strArr = tempString.split("\t");

				// 判读是不是手机号码
				int len = strArr.length;

				if (len >= 1) {
					// String account = strArr[0];
					String code = strArr[0];
					String name = "";
					String cardNo = "";
					if (len >= ResultParame.ResultNumber.TWO.getNumber()) {
						name = strArr[ResultParame.ResultNumber.ONE.getNumber()].trim();
					}
					if (len >= ResultParame.ResultNumber.THREE.getNumber()) {
						cardNo = strArr[ResultParame.ResultNumber.TWO.getNumber()].trim();
					}

					String psw = AppCons.DEFAULT_PSW;
					String accountStr = UUID.randomUUID().toString();
					String account = MD5Utils.MD5(accountStr).substring(ResultParame.ResultNumber.ZERO.getNumber(),
							ResultParame.ResultNumber.TEN.getNumber());

					registerUsers(account, psw, code, account, true, name, cardNo, path);

				}
			}
			reader.close();
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
	}

	@Override
	public void selectUsersList(UsersVo vo, Double financialMin, Double financialMax, Double holdMin, Double holdMax,
            PageInfo pageInfo, String sort, String order, Boolean showMgr, String regDateStr, String regDateStrEnd) {
		List<UsersVo> list = this.usersList(vo, financialMin, financialMax, holdMin, holdMax, pageInfo, sort, order,
                showMgr, regDateStr, regDateStrEnd);
		pageInfo.setRows(list);
		pageInfo.setTotal(usersCustomerMapper.selectUsersVoCountByCondition(pageInfo));
		pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
	}

	/**
	 * 查询封装用户列表
	 * 
	 * @param vo
	 *            UsersVo
	 * @param financialMin
	 *            financialMin
	 * @param financialMax
	 *            financialMax
	 * @param holdMin
	 *            holdMin
	 * @param holdMax
	 *            holdMax
	 * @param pageInfo
	 *            pageInfo
	 * @param sort
	 *            sort
	 * @param order
	 *            order
	 * @param showMgr
	 *            showMgr
	 * @return 数据返回
	 */
	public List<UsersVo> usersList(UsersVo vo, Double financialMin, Double financialMax, Double holdMin, Double holdMax,
 PageInfo pageInfo, String sort, String order, Boolean showMgr, String regDateStr,
            String regDateStrEnd) {
		Map<String, Object> condition = new HashMap<String, Object>();
		if (vo != null) {
			// 用户id
			if (StringUtils.isNotBlank(vo.getId())) {
				condition.put("id", vo.getId());
			}
			// 用户姓名
			if (StringUtils.isNotBlank(vo.getName())) {
				condition.put("name", vo.getName());
			}
			// 用户手机号
			if (StringUtils.isNotBlank(vo.getTelephone())) {
				condition.put("telephone", vo.getTelephone());
			}
			// 用户注册推荐码
			if (StringUtils.isNotBlank(vo.getReferralCode())) {
				condition.put("referralCode", vo.getReferralCode());
			}
			// 平台类型
			if (vo.getPlatformType() != null) {
				condition.put("platformType", vo.getPlatformType());
			}
			// 用户注册渠道
			if (StringUtils.isNotBlank(vo.getRegistChannel())) {
				condition.put("registChannel", vo.getRegistChannel());
			}
			// 是否有理财经理
			if (vo.getIsHaveManager() != null) {
				condition.put("isHaveManager", vo.getIsHaveManager());
			}
			// 是否绑卡激活
			if (vo.getIsBindCard() != null) {
				condition.put("isBindCard", vo.getIsBindCard());
			}
			// 是否买过产品
			if (vo.getIsFinancialProduct() != null) {
				condition.put("isFinancialProduct", vo.getIsFinancialProduct());
			}
			// 是否持有产品
			if (vo.getIsHoldProduct() != null) {
				condition.put("isHoldProduct", vo.getIsHoldProduct());
			}
			// 理财经理编号
			if (StringUtils.isNotBlank(vo.getManagerId())) {
				condition.put("managerId", vo.getManagerId());
			}
			// 理财经理姓名
			if (StringUtils.isNotBlank(vo.getManagerName())) {
				condition.put("managerName", vo.getManagerName());
			}
			// 理财经理公司
			if (StringUtils.isNotBlank(vo.getManagerCom())) {
				condition.put("managerCom", vo.getManagerCom());
			}
			// 理财经理部门
			if (StringUtils.isNotBlank(vo.getManagerDept())) {
				condition.put("managerDept", vo.getManagerDept());
			}
			// 理财金额区间
			condition.put("financialMin", financialMin == null ? 0 : financialMin);
			condition.put("financialMax", financialMax == null ? Integer.MAX_VALUE : financialMax);
			// 持有金额区间
			condition.put("holdMin", holdMin == null ? 0 : holdMin);
			condition.put("holdMax", holdMax == null ? Integer.MAX_VALUE : holdMax);
			// 是否显示理财经理
			condition.put("showMgr", showMgr);
		}
        if (StringUtils.isNotBlank(regDateStr)) {
            condition.put("regDateStr", regDateStr);
        }
        if (StringUtils.isNotBlank(regDateStrEnd)) {
            condition.put("regDateStrEnd", regDateStrEnd);
        }
		pageInfo.setCondition(condition);
		sort = sort == null ? "id" : sort;
		order = order == null ? "asc" : order;
		pageInfo.setSort(sort);
		pageInfo.setOrder(order);
		List<UsersVo> list = usersCustomerMapper.selectUsersVoByCondition(pageInfo);
		if (!showMgr) {
			for (UsersVo usersVo : list) {
				// 获取推荐人id
				String recomLevel = usersVo.getRecommendedLevel();
				// 如果推荐层级是本身，则无推荐人
				if (!StringUtils.isEmpty(recomLevel) && recomLevel.contains(",")) {
					// 拆分推荐人id
					String[] uids = recomLevel.split(",");
					// 转换成集合
					List<String> uidList = Arrays.asList(uids);
					// 反转集合，从第一层推荐人往上找
					Collections.reverse(uidList);
					// 循环查找次数，只往上找三级，第一次为用户自己的id

					int count = ResultParame.ResultNumber.THREE.getNumber();
					if (uidList.size() < ResultParame.ResultNumber.THREE.getNumber()) {
						count = uidList.size();
					}
					// 顶层推荐人id，如果size小于等于3则取最后一个，否则取第三个
					String referralId = uidList.get(count - 1);
					EmployeeRelationsMapping employee = customQueryMapper.queryEmployeeInfoByUid(referralId);
					if (employee != null) {
						usersVo.setIsHaveManager(1);
						usersVo.setManagerId(employee.getEmployeeid());
						usersVo.setManagerName(employee.getEmployeeName());
						usersVo.setManagerCom(employee.getCompName());
						usersVo.setManagerDept(employee.getDepartment());
					} else {
						usersVo.setIsHaveManager(0);
					}
				} else {
					usersVo.setIsHaveManager(0);
				}
			}
		}
		return list;
	}

	@Override
	public void selectTradingList(BuyRecordsVo vo, PageInfo pageInfo, String sort, String order) {
		Map<String, Object> condition = new HashMap<String, Object>();
		if (vo != null) {
			// 用户id
			if (vo.getuId() != null) {
				condition.put("uId", vo.getuId());
			}
			// 订单状态
			if (vo.gettStatus() != null) {
				condition.put("tStatus", vo.gettStatus());
			}
		}
		pageInfo.setCondition(condition);
		sort = sort == null ? "t.id" : sort;
		order = order == null ? "asc" : order;
		pageInfo.setSort(sort);
		pageInfo.setOrder(order);
		List<BuyRecordsVo> list = usersCustomerMapper.selectTradingListByUsersId(pageInfo);

		for (BuyRecordsVo buyRecordsVo : list) {
			BigDecimal interest = buyRecordsVo.getInterest(); // 预期收益
			BigDecimal financialMoney = buyRecordsVo.getFinancialMoney(); // 理财金额
			BigDecimal fYield = buyRecordsVo.getfYield(); // 收益率
			Date minSellDt = buyRecordsVo.getMinSellDt(); // 到期时间
			Date edDt = buyRecordsVo.geteDdt(); // 结束时间
			Integer fTime = buyRecordsVo.getfTime(); // 固定期限
			// 到期时间
			minSellDt = minSellDt == null ? DateUtils.addDay(edDt, fTime) : minSellDt;
			// 预期收益
			if (fTime != null && fTime != 0 && fYield != null) {
				interest = interest == null ? (financialMoney.multiply(fYield).multiply(new BigDecimal(fTime))
						.divide(new BigDecimal(ResultParame.ResultNumber.THREE_SIXTY_FIVE.getNumber()),
								RoundingMode.HALF_EVEN))
						: interest;
			}

			buyRecordsVo.setMinSellDt(minSellDt);
			buyRecordsVo.setInterest(interest);

		}

		pageInfo.setRows(list);
		pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
	}

	@Override
	public PageInfo exportExcel(HttpServletRequest request, HttpServletResponse response, UsersVo vo,
			Double financialMin, Double financialMax, Double holdMin, Double holdMax, PageInfo pageInfo,
            Boolean showMgr, String regDateStr, String regDateStrEnd) {
		// 文件输出流
		FileOutputStream fo = null;
		try {
			if (vo != null) {
				List<UsersVo> list = this.usersList(vo, financialMin, financialMax, holdMin, holdMax, pageInfo, null,
                        null, showMgr, regDateStr, regDateStrEnd);
				if (list != null) {
					// 文件名
					String fileName = "users_vo_" + DateUtils.getDateStrByBiz(new Date()) + ".xls";
					// 保存根路径 tomcat
					String saveRootPath = request.getSession().getServletContext().getRealPath("/users/");
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
					exportExcel(fo, list, showMgr);
					// 返回文件目录
					String redirectPath = "/users/" + fileName;
					pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					pageInfo.setObj(redirectPath);
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultParame.ResultInfo.NOT_FOUND.getCode());
			pageInfo.setMsg("未找到记录");
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
	 * @param showMgr
	 *            showMgr
	 */
	public static void exportExcel(OutputStream outputStream, List<UsersVo> list, Boolean showMgr) {
		try {
			// 1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();

			// 1.1、创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(ResultParame.ResultNumber.ZERO.getNumber(),
					ResultParame.ResultNumber.ZERO.getNumber(), ResultParame.ResultNumber.ZERO.getNumber(),
					ResultParame.ResultNumber.FOUR.getNumber()); // 起始行号，结束行号，起始列号，结束列号

			// 1.2、头标题样式
			HSSFCellStyle style1 = createCellStyle(workbook, (short) ResultParame.ResultNumber.SIXTEEN.getNumber());

			// 1.3、列标题样式
			HSSFCellStyle style2 = createCellStyle(workbook, (short) ResultParame.ResultNumber.THIRTEEN.getNumber());

			// 创建表
			HSSFSheet sheet = workbook.createSheet("用户信息");

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
			cell.setCellValue("用户信息列表");

			// 创建第二行
			HSSFRow row2 = sheet.createRow(1);

			// 标题
			String[] titles = {"用户id", "用户姓名", "用户手机号", "用户推荐码", "是否有理财经理", "是否绑卡激活", "是否买过产品", "是否持有产品", "理财金额",
                    "持有金额", "理财经理编号", "理财经理姓名", "理财经理公司", "理财经理部门", "注册时间", "平台类型", "注册渠道", "年龄", "性别" };
			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			if (list != null) {
				// 加载用户数据
				HSSFRow rowi = null;
				UsersVo vo = null;
				for (int i = 0; i < list.size(); i++) {
					rowi = sheet.createRow(i + ResultParame.ResultNumber.TWO.getNumber());
					vo = list.get(i);
					int j = ResultParame.ResultNumber.MINUS_ONE.getNumber();
					rowi.createCell(++j).setCellValue(vo.getId());
					rowi.createCell(++j).setCellValue(vo.getName());
					rowi.createCell(++j).setCellValue(vo.getTelephone());
					rowi.createCell(++j).setCellValue(vo.getReferralCode());
					rowi.createCell(++j).setCellValue(vo.getIsHaveManager() == 0 ? "否" : "是");
					rowi.createCell(++j).setCellValue(vo.getIsBindCard() == 0 ? "否" : "是");
					rowi.createCell(++j).setCellValue(vo.getIsFinancialProduct() == 0 ? "否" : "是");
					rowi.createCell(++j).setCellValue(vo.getIsHoldProduct() == 0 ? "否" : "是");
					rowi.createCell(++j)
							.setCellValue(vo.getFinancialMoney() == null ? 0 : vo.getFinancialMoney().doubleValue());
					rowi.createCell(++j).setCellValue(vo.getHoldMoney() == null ? 0 : vo.getHoldMoney().doubleValue());
					rowi.createCell(++j).setCellValue(StringUtils.isBlank(vo.getManagerId()) ? "" : vo.getManagerId());
					rowi.createCell(++j)
							.setCellValue(StringUtils.isBlank(vo.getManagerName()) ? "" : vo.getManagerName());
					rowi.createCell(++j)
							.setCellValue(StringUtils.isBlank(vo.getManagerCom()) ? "" : vo.getManagerCom());
					rowi.createCell(++j)
							.setCellValue(StringUtils.isBlank(vo.getManagerDept()) ? "" : vo.getManagerDept());
					rowi.createCell(++j)
							.setCellValue(vo.getRegDate() == null ? "" : DateUtils.sf.format(vo.getRegDate()));
					rowi.createCell(++j).setCellValue(vo.getPlatformType() == 0 ? "注册用户" : "导入用户");
					rowi.createCell(++j)
							.setCellValue(StringUtils.isBlank(vo.getRegistChannel()) ? "" : vo.getRegistChannel());
                    rowi.createCell(++j).setCellValue(vo.getAge() == null ? "" : vo.getAge() + "");
                    rowi.createCell(++j).setCellValue(StringUtils.isBlank(vo.getSex()) ? "" : vo.getSex());
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

	@Override
	public List<Map<String, Object>> queryManagerCom(String q) {
		List<Map<String, Object>> list = usersCustomerMapper.queryManagerCom(q);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryManagerDept(String q) {
		List<Map<String, Object>> list = usersCustomerMapper.queryManagerDept(q);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryRegistChannel(String q) {
		List<Map<String, Object>> list = usersCustomerMapper.queryRegistChannel(q);
		return list;
	}
}
