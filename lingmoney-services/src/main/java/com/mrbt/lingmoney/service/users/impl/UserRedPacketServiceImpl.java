package com.mrbt.lingmoney.service.users.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketUnionMapper;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketUnion;
import com.mrbt.lingmoney.model.UsersRedPacketUnionExample;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.service.users.UserRedPacketService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

import net.sf.json.JSONArray;

/**
 * @author syb
 * @date 2017年7月6日 下午4:57:08
 * @version 1.0
 * @description
 **/
@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	@Autowired
	private UsersRedPacketUnionMapper usersRedPacketUnionMapper;
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private UtilService utilService;
	@Autowired
	private VerifyService verifyService;

	@Override
	public Map<String, Object> queryFinancialAvailableRedPacket(String pCode, String uid, Double buyMoney, Integer redPacketType)
			throws Exception {
		verifyService.verifyEmpty(pCode, "产品编码为空");

		Product product = productCustomerMapper.selectByCode(pCode);
		verifyService.verifyEmpty(product, "产品信息不存在");

		// 可用数据
		List<Map<String, Object>> availableList = new ArrayList<Map<String, Object>>();
		// 不可用数据
		List<Map<String, Object>> unAvailableList = new ArrayList<Map<String, Object>>();

		UsersRedPacketUnionExample example = new UsersRedPacketUnionExample();
		example.createCriteria().andUIdEqualTo(uid).andUserStatusEqualTo(0).andValidityTimeGreaterThan(new Date());
		example.setOrderByClause("validity_time , create_time desc, hrp_number desc");

		List<UsersRedPacketUnion> usersRedPacketUnionList = usersRedPacketUnionMapper.selectByExample(example);
		if (usersRedPacketUnionList == null || usersRedPacketUnionList.size() == 0) {
            throw new PageInfoException("无可用优惠券", ResultInfo.SUBMIT_PARAMS_ERROR.getCode());
		}
		
		// 可用张数
		int availableCount = 0;
		for (UsersRedPacketUnion urp : usersRedPacketUnionList) {
			Map<String, Object> resultMap = packageRedPacketViewFieldToMap(urp);
			// 如果是加息券计算获得利息=加息率*理财天数*理财金额/365
			if (redPacketType.equals(1)) {
				BigDecimal interest = new BigDecimal("0.00");
				if (buyMoney != null && buyMoney > 0) {
					System.out.println("计算红包利息：↓↓↓↓↓↓↓↓↓↓↓↓↓");
					interest = ProductUtils.countInterest(new BigDecimal(buyMoney.toString()),
							ProductUtils.getFinancialDays(product), new BigDecimal(urp.getHrpNumber().toString()));
				}
				resultMap.put("addInterest", interest);
			} else {
				// 红包=红包金额
				resultMap.put("addInterest", urp.getHrpNumber());
			}
			
			//兼容老版本APP
			if (urp.getHrpType() == 3) {
				resultMap.put("hrpType", 2);
			} else {
				resultMap.put("hrpType", urp.getHrpType());
			}

			HxRedPacket redPacket = new HxRedPacket();
			BeanUtils.copyProperties(urp, redPacket);
			
			// 获取该产品是否支持使用优惠券，isRedPacket：0：不可用 1：可用
			if (product.getIsRedPacket() == 0) {
				unAvailableList.add(resultMap);
			} else {
				// 分离可用不可用
				if (validRedPacket(product, buyMoney, redPacket, urp.getUserRedPacketId())) {
					availableCount++;
					availableList.add(resultMap);
				} else {
					unAvailableList.add(resultMap);
				}
			}
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("availableList", availableList);
		resMap.put("unAvailableList", unAvailableList);
		resMap.put("availableCount", availableCount);
		return resMap;
	}

	@Override
    public PageInfo queryUserRedPacketByType(String uid, Integer type, Integer hrpType, Integer pageSize, Integer pageNo)
            throws Exception {
		if (pageSize == null) {
			pageSize = AppCons.DEFAULT_PAGE_SIZE;
		}

		if (pageNo == null) {
			pageNo = AppCons.DEFAULT_PAGE_START;
		}

		PageInfo pi = new PageInfo(pageNo, pageSize);
		
		
		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(type)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		// 0未使用 1已使用 2 已过期 3全部
		UsersRedPacketUnionExample example = new UsersRedPacketUnionExample();
		UsersRedPacketUnionExample.Criteria criteria = example.createCriteria();
		if (hrpType == 2) {
			List<Integer> hrpTypeList = new ArrayList<Integer>();
			hrpTypeList.add(2);
			hrpTypeList.add(3);
			criteria.andUIdEqualTo(uid).andHrpTypeIn(hrpTypeList);
		} else {
			criteria.andUIdEqualTo(uid).andHrpTypeEqualTo(hrpType);
		}
		
		example.setLimitStart(pi.getFrom());
		example.setLimitEnd(pi.getSize());
		

        if (type == 0) { // 未使用，过滤已过期
			criteria.andUserStatusEqualTo(type);
			criteria.andValidityTimeGreaterThan(new Date());
        } else if (type == 1) { //已使用
			criteria.andUserStatusEqualTo(type);

        } else if (type == 2) { //已过期
			criteria.andValidityTimeLessThan(new Date());
		}

		if (type == 0) {
			/**
			 * 加息券未使用的排序规则： A：按获得加息券时间倒叙排序 B：按照加息利率高低排序：由高到低排序 C：如有即将到期加息券，则优先显示
			 */
			example.setOrderByClause("users_red_packet.validity_time , create_time desc, hrp_number desc ");
		} else if (type == 1 || type == 2) {
			/*
			 * 加息券已使用、已到期的排序规则： A：已使用，按照使用倒序排列 B：已过期，按照加息券到期时间倒序排列；
			 * 及加息券利率高低排序：由高到低
			 */
			example.setOrderByClause("used_time desc, users_red_packet.validity_time desc, hrp_number desc ");
		}

		List<UsersRedPacketUnion> usersRedPacketUnionList = usersRedPacketUnionMapper.selectByExample(example);

		if (usersRedPacketUnionList != null && usersRedPacketUnionList.size() > 0) {

			List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
			
			for (UsersRedPacketUnion urp : usersRedPacketUnionList) {
				Map<String, Object> map = packageRedPacketViewFieldToMap(urp);
				resList.add(map);
			}

			int total = usersRedPacketUnionMapper.countByExample(example);

			pi.setRows(resList);
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("success");
			pi.setTotal(total);
			pi.setNowpage(pageNo);
			pi.setPagesize(pageSize);

		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("无数据");
		}

		return pi;
	}

	@Override
	public PageInfo removeUserRedPacket(String uid, Integer id) {
		PageInfo pi = new PageInfo();
		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(id)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		UsersRedPacket urp = usersRedPacketMapper.selectByPrimaryKey(id);
		if (urp == null || !urp.getuId().equals(uid)) {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("信息有误");
			
		} else {
			UsersRedPacket record = new UsersRedPacket();
			record.setId(id);
			record.setStatus(2);
			int result = usersRedPacketMapper.updateByPrimaryKeySelective(record);

			if (result > 0) {
                pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg("删除成功");
			} else {
                pi.setCode(ResultInfo.SERVER_ERROR.getCode());
				pi.setMsg("删除失败");
			}
		}

		return pi;
	}

	@Override
	public boolean validRedPacket(Product product, Double buyMoney, HxRedPacket hxRedPacket, Integer userRedPacketId) {

		// 获取用户红包对象
		UsersRedPacket usersRedPacket = usersRedPacketMapper.selectByPrimaryKey(userRedPacketId);

		// 验证规则
		// 1.按产品id检索 2.没有则按产品类别检索 3.加息券的期限>=产品表f_time 4.用户输入金额>=加息券表use_limit
		if (hxRedPacket == null) {
			return false;
		}

		//验证是否已过期
		if (usersRedPacket.getValidityTime().getTime() < new Date().getTime()) {
			// 不可用 已过期
			return false;
		}
		
		// 验证产品批次使用限制
		if (hxRedPacket.getuInvestProBatch() != null && !hxRedPacket.getuInvestProBatch().equals("")) {
			if (product.getBatch() != null && hxRedPacket.getuInvestProBatch().indexOf(product.getBatch()) < 0) {
				return false;
			}
		} else if (hxRedPacket.getuInvestProType() != null && !hxRedPacket.getuInvestProType().equals("")) {
			// 没有产品ID 判断产品类型和产品期限，值如果为空则不判断 。
            if (hxRedPacket.getuInvestProType().indexOf(product.getPcId().toString()) < 0) {
				// 不可用 类型不符
				return false;
			}
		}

		// 判断产品期限
		if (hxRedPacket.getuInvestProDay() != null && hxRedPacket.getuInvestProMixday() != null) {
			Integer x = ProductUtils.getFinancialDays(product);
			
			//如果优惠券中的使用期限下限小于999，判断产品期限是否大于使用期限下限，如果是优惠券不可用
            if (hxRedPacket.getuInvestProMixday() > 0) {
                if (x > hxRedPacket.getuInvestProMixday()) {
					return false;
				}
			}
			
			//如果优惠券中的使用期限上线大于30，判断产品期限是否小于使用期限上限，如果是优惠券不可用
            if (hxRedPacket.getuInvestProDay() > 0) {
                if (x < hxRedPacket.getuInvestProDay()) {
					return false;
				}
			}
		}

		// 购买金额大于起投金额
		if (buyMoney > 0) {
			if (hxRedPacket.getuInvestProAmount() != null && hxRedPacket.getuInvestProAmount() > buyMoney) {
				return false;
			}
		} else {
			return false;
		}

		// 验证通过
		return true;
	}

	/**
	 * 包装加息券页面展示数据
	 * 
	 * @param unionInfo
	 * @return
	 */
	private Map<String, Object> packageRedPacketViewFieldToMap(UsersRedPacketUnion unionInfo) {
		// 页面展示数据
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// ID
		resultMap.put("id", unionInfo.getUserRedPacketId());
		// 名称
		resultMap.put("hrName", unionInfo.getHrpType() == 1 ? "加息券" : "返现红包");
		// 红包类型
		resultMap.put("hrpType", unionInfo.getHrpType());
		// 使用优惠券的金额下限
		resultMap.put("useLimit", unionInfo.getaInvestProAmount());
		// 金额/加息率
		if (unionInfo.getHrpType() == 1) {
			// 加息券，格式化展示数据
			Double rate = unionInfo.getHrpNumber() * 100;
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			resultMap.put("amount", decimalFormat.format(rate));
		} else {
			resultMap.put("amount", unionInfo.getHrpNumber());
		}
		// 活动备注 格式为json数组
		if (unionInfo.getHrpRemarks() != null) {
			resultMap.put("activityRemark", JSON.parse(unionInfo.getHrpRemarks()));
		} else {
			resultMap.put("activityRemark", null);
		}
		// 获取方式 ：0-手动，1-注册后，2-开通E账户后，3-激活E账户后，4-首次理财后，5-理财后'
		switch (unionInfo.getAcquisMode()) {
			case 0:
				resultMap.put("obtianWay", "手动");
				break;
			case 1:
				resultMap.put("obtianWay", "注册后");
				break;
			case 2:
				resultMap.put("obtianWay", "开通E账户后");
				break;
			case 3:
				resultMap.put("obtianWay", "激活E账户后");
				break;
			case 4:
				resultMap.put("obtianWay", "首次理财后");
				break;
			case 5:
				resultMap.put("obtianWay", "理财后");
				break;
        default:
            break;
		}
		// 距离过期天数
		resultMap.put("dayOfOvertime", DateUtils.dateDiff(new Date(),
				unionInfo.getValidityTime() != null ? unionInfo.getValidityTime() : null));
		// 到期日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		resultMap.put("overtimeDate",
				sdf.format(unionInfo.getValidityTime() != null ? unionInfo.getValidityTime() : null));
		// 使用状态
		resultMap.put("useStatus", unionInfo.getUserStatus());

		return resultMap;
	}

	public static void main(String[] args) {
		JSONArray list = new JSONArray();
		list.add("适用90天以上稳赢宝产品");
		list.add("单笔购买金额≥5000元可用");
		System.out.println(list.toString());
	}

	@Override
	public PageInfo queryUserRedPacketByTypeOfWap(String uid, Integer type, Integer pageSize,
			Integer pageNo) throws Exception {
		if (pageSize == null) {
			pageSize = AppCons.DEFAULT_PAGE_SIZE;
		}

		if (pageNo == null) {
			pageNo = AppCons.DEFAULT_PAGE_START;
		}

		PageInfo pi = new PageInfo(pageNo, pageSize);

		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(type)) {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		// 0未使用 1已使用 2 已过期 3全部
		UsersRedPacketUnionExample example = new UsersRedPacketUnionExample();
		UsersRedPacketUnionExample.Criteria criteria = example.createCriteria();
		example.setLimitStart(pi.getFrom());
		example.setLimitEnd(pi.getSize());

		if (type == 1) { // 已使用
			criteria.andUserStatusEqualTo(type);

		} else if (type == 2) { // 已过期
			criteria.andValidityTimeLessThan(new Date());
		}

		if (type == 1 || type == 2) {
			/*
			 * 加息券已使用、已到期的排序规则： A：已使用，按照使用倒序排列 B：已过期，按照加息券到期时间倒序排列；
			 * 及加息券利率高低排序：由高到低
			 */
			example.setOrderByClause("used_time desc, users_red_packet.validity_time desc, hrp_number desc ");
		}

		List<UsersRedPacketUnion> usersRedPacketUnionList = usersRedPacketUnionMapper.selectByExample(example);

		if (usersRedPacketUnionList != null && usersRedPacketUnionList.size() > 0) {

			List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();

			for (UsersRedPacketUnion urp : usersRedPacketUnionList) {
				Map<String, Object> map = packageRedPacketViewFieldToMap(urp);
				resList.add(map);
			}

			int total = usersRedPacketUnionMapper.countByExample(example);

			pi.setRows(resList);
			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("success");
			pi.setTotal(total);
			pi.setNowpage(pageNo);
			pi.setPagesize(pageSize);

		} else {
			pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("无数据");
		}

		return pi;
	}

}
