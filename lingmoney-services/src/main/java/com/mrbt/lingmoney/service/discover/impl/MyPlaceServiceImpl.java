package com.mrbt.lingmoney.service.discover.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.LingbaoActivityBannerMapper;
import com.mrbt.lingmoney.mapper.LingbaoAddressMapper;
import com.mrbt.lingmoney.mapper.LingbaoExchangeInfoMapper;
import com.mrbt.lingmoney.mapper.LingbaoGiftCartMapper;
import com.mrbt.lingmoney.mapper.LingbaoGiftMapper;
import com.mrbt.lingmoney.mapper.LingbaoGiftTypeMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.LingbaoActivityBanner;
import com.mrbt.lingmoney.model.LingbaoActivityBannerExample;
import com.mrbt.lingmoney.model.LingbaoAddress;
import com.mrbt.lingmoney.model.LingbaoAddressExample;
import com.mrbt.lingmoney.model.LingbaoExchangeInfo;
import com.mrbt.lingmoney.model.LingbaoExchangeInfoExample;
import com.mrbt.lingmoney.model.LingbaoGift;
import com.mrbt.lingmoney.model.LingbaoGiftCart;
import com.mrbt.lingmoney.model.LingbaoGiftCartExample;
import com.mrbt.lingmoney.model.LingbaoGiftType;
import com.mrbt.lingmoney.model.LingbaoGiftTypeExample;
import com.mrbt.lingmoney.model.LingbaoGiftWithBLOBs;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.service.discover.MyPlaceService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年4月12日 下午3:16:48
 * @version 1.0
 * @description
 **/
@Service
public class MyPlaceServiceImpl implements MyPlaceService {
	private static final Logger LOG = LogManager.getLogger(MyPlaceServiceImpl.class);

	@Autowired
	private LingbaoActivityBannerMapper lingbaoActivityBannerMapper;
	@Autowired
	private LingbaoGiftTypeMapper lingbaoGiftTypeMapper;
	@Autowired
	private LingbaoGiftMapper lingbaoGiftMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private LingbaoGiftCartMapper lingbaoGiftCartMapper;
	@Autowired
	private LingbaoExchangeInfoMapper lingbaoExchangeInfoMapper;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	@Autowired
	private LingbaoAddressMapper lingbaoAddressMapper;
	@Autowired
	private RedisDao redisDao;

	@Override
	public PageInfo getBannerList(Integer type) {
		PageInfo pageInfo = new PageInfo();

		List<LingbaoActivityBanner> bannerList = null;

		// 1.banner列表
		if (!StringUtils.isEmpty(type)) {
			bannerList = lingbaoActivityBannerMapper.listBannerByLotteryType(type);
		} else {
			LingbaoActivityBannerExample example = new LingbaoActivityBannerExample();
			example.createCriteria().andStatusEqualTo(1);
			example.setOrderByClause("level desc");
			bannerList = lingbaoActivityBannerMapper.selectByExample(example);
		}

		if (bannerList != null && bannerList.size() > 0) {
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("success");
			pageInfo.setRows(bannerList);
			pageInfo.setTotal(bannerList.size());
		} else {
            pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
			pageInfo.setMsg("查询数据为空");
		}

		return pageInfo;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public PageInfo exchangeGift(String uid, Integer giftId, Integer num, Integer addressID) {
		PageInfo pi = new PageInfo();
		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(giftId) || StringUtils.isEmpty(num) || num < 1) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			LOG.info("礼品兑换失败，参数错误：uid:" + uid + ",giftId:" + giftId + ",num:" + num + ",addressID:" + addressID);
			return pi;
		}

		// 控制连点事件，避免数据重复添加
		String doubleClickKey = "exchangeGift_" + uid + giftId;
		if (!redisLockOfDoubleClick(doubleClickKey)) {
            pi.setResultInfo(ResultInfo.NOT_CONTINUITY_CHECK);
			return pi;
		}

		// 验证礼品信息
		PageInfo validInfo = validGift(giftId, num);
		if (validInfo == null) {
			UsersAccount account = usersAccountMapper.selectByUid(uid);
			LingbaoGift gift = lingbaoGiftMapper.selectByPrimaryKey(giftId);

			int cost = gift.getIntegral();

			// 验证兑换信息
			validInfo = validExchangeInfo(account, gift, num, addressID);
            if (validInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
				cost = (int) validInfo.getObj();

				// 领宝兑换的数据库操作
				boolean exchangeDataOk = exchangeDataOperate(account, gift, num, addressID, cost);

				if (exchangeDataOk) {
                    pi.setCode(ResultInfo.SUCCESS.getCode());
					pi.setMsg("兑换成功");
					pi.setObj(account.getCountLingbao());

					LOG.info("领宝兑换成功");
				} else {
                    pi.setCode(ResultInfo.EXCHANGE_NOT_SUCCESS.getCode());
					pi.setMsg("兑换失败");

					LOG.info("兑换失败： uid:" + uid + " giftid:" + giftId + " addressID:" + addressID + " num:" + num);

					// 兑换失败，执行回滚
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}

			} else {
				return validInfo;
			}

		} else {
			return validInfo;
		}

		redisDao.delete(doubleClickKey);
		return pi;
	}

	@Override
	public PageInfo queryExchangeRecord(String uid, Integer status, Integer pageNo, Integer pageSize) {
		PageInfo pi = new PageInfo(pageNo, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("status", null);
		map.put("start", null);
		map.put("number", null);
		map.put("start", pi.getFrom());
		map.put("number", pi.getSize());
		if (!StringUtils.isEmpty(status)) {
			map.put("status", status);
		}
		List<Map<String, Object>> list = lingbaoExchangeInfoMapper.queryExchangeInfoViewList(map);

		if (null != list && list.size() > 0) {
			int total = lingbaoExchangeInfoMapper.countExchangeInfoViewList(map);
			int pages = total / pi.getPagesize();
			if (total % pi.getPagesize() != 0) {
				pages += 1;
			}

            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("success");
			pi.setRows(list);
			pi.setTotal(total);
			pi.setSize(pages);

		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("未查询到数据");
		}

		return pi;
	}

	@Override
	public PageInfo confirmAcceptGift(Integer id, String uid) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(uid)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		// 更新收货状态为2（已收货）、收货时间
		LingbaoExchangeInfoExample example = new LingbaoExchangeInfoExample();
		example.createCriteria().andIdEqualTo(id).andUIdEqualTo(uid);
		LingbaoExchangeInfo record = new LingbaoExchangeInfo();
		record.setStatus(2);
		record.setReceiveTime(new Date());
		int i = lingbaoExchangeInfoMapper.updateByExampleSelective(record, example);

		if (i > 0) {
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("操作成功");

		} else {
            pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("操作失败");
			LOG.info("确认收货失败，数据更新失败： id:" + id + " uid:" + uid);
		}

		return pi;
	}

	@Override
	public PageInfo queryShopCart(String uid, Integer pageNo, Integer pageSize) {
		PageInfo pi = new PageInfo(pageNo, pageSize);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("start", null);
		map.put("number", null);
		map.put("start", pi.getFrom());
		map.put("number", pi.getSize());
		List<Map<String, Object>> list = lingbaoGiftCartMapper.queryGiftCartInfoList(map);

		if (null != list && list.size() > 0) {
			int total = lingbaoGiftCartMapper.countGiftCartInfoList(map);
			int pages = total / pi.getPagesize();
			if (total % pi.getPagesize() != 0) {
				pages += 1;
			}

            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("success");
			pi.setRows(list);
			pi.setTotal(total);
			pi.setSize(pages);

		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("暂无数据");
		}

		return pi;
	}

	@Override
	public PageInfo editShopCart(Integer id, Integer num, String uid) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(num) || StringUtils.isEmpty(uid) || num < 1) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		// 根据ID从购物车查询记录
		LingbaoGiftCart record = lingbaoGiftCartMapper.selectByPrimaryKey(id);
		if (record == null || !record.getuId().equals(uid)) {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("操作失败,请刷新后重试");
			LOG.info("修改购物车操作失败，未查询到购物车数据。 id:" + id + " uid:" + uid);
			return pi;
		}

		// 验证礼品信息
		PageInfo validInfo = validGift(record.getGiftId(), num);
		if (validInfo != null) {
			return validInfo;
		}

		LingbaoGift gift = lingbaoGiftMapper.selectByPrimaryKey(record.getGiftId());

		// 库存必须满足购买件数
		if (gift.getStore() >= record.getNum()) {
			record.setNum(num);
			int cost = gift.getIntegral();
			if (gift.getPreferentialIntegral() != null && gift.getPreferentialIntegral() > 0) {
				cost = gift.getPreferentialIntegral();
			}
			record.setIntegral(num * cost);

			UsersAccount account = usersAccountMapper.selectByUid(uid);
			if (account != null && account.getCountLingbao() >= record.getIntegral()) {
				int i = lingbaoGiftCartMapper.updateByPrimaryKeySelective(record);
				if (i > 0) {
                    pi.setCode(ResultInfo.SUCCESS.getCode());
					pi.setMsg("操作成功");

				} else {
                    pi.setCode(ResultInfo.SERVER_ERROR.getCode());
					pi.setMsg("操作失败");
					LOG.info("更新购物车失败 id：" + id + " num:" + num);
				}

			} else {
                pi.setCode(ResultInfo.LINGBAO_INSUFFICIENT.getCode());
				pi.setMsg("您的领宝不足");
				LOG.info("更新购物车失败 ，领宝不足；uid：" + uid + "; id:" + id + ";num:" + num);
			}

		} else {
			// 原购物车数量大于剩余库存时 修改购物车数量为1
			record.setNum(1);
			lingbaoGiftCartMapper.updateByPrimaryKeySelective(record);

			pi.setObj(gift.getStore());
            pi.setCode(ResultInfo.BUYCOUNT_ERROE.getCode());
			pi.setMsg("库存不足");
		}

		return pi;
	}

	@Override
	@Transactional
	public PageInfo addToShopCart(String uid, Integer giftId, Integer num) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(giftId) || StringUtils.isEmpty(num)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		// 控制连点事件，避免数据重复添加
		String doubleClickKey = "addToCart_" + uid + giftId;
		if (!redisLockOfDoubleClick(doubleClickKey)) {
            pi.setCode(ResultInfo.NOT_CONTINUITY_CHECK.getCode());
			pi.setMsg("请勿连续点击");
			return pi;
		}

		// 验证用户账户
		UsersAccount account = usersAccountMapper.selectByUid(uid);
		if (account == null) {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("操作失败");
			LOG.info("添加购物车失败，查询不到用户账户信息。 uid=" + uid);
			return pi;
		}

		// 验证礼品
		PageInfo validInfo = validGift(giftId, num);
		if (validInfo != null) {
			return validInfo;
		}

		LingbaoGift gift = lingbaoGiftMapper.selectByPrimaryKey(giftId);
		// 礼品单价
		int unitPrice = gift.getIntegral();
		if (gift.getPreferentialIntegral() != null && gift.getPreferentialIntegral() > 0) {
			unitPrice = gift.getPreferentialIntegral();
		}

		// 查询该产品是否在购物车
		LingbaoGiftCartExample gcExample = new LingbaoGiftCartExample();
		gcExample.createCriteria().andUIdEqualTo(uid).andGiftIdEqualTo(giftId);
		List<LingbaoGiftCart> gcList = lingbaoGiftCartMapper.selectByExample(gcExample);

		// 已存在商品需要加上原有数量后重新验证
		if (null != gcList && gcList.size() > 0) {
			LingbaoGiftCart lgc = gcList.get(0);

			int lastNum = lgc.getNum() + num;
			if (lastNum > gift.getExchangeNumber()) {
                pi.setCode(ResultInfo.EXCHANGE_COUNT_ERROR.getCode());
				pi.setMsg("该产品每次限购" + gift.getExchangeNumber() + "件,您的购物车已存在" + lgc.getNum() + "件该商品");
				return pi;
			}

			if (lastNum * unitPrice > account.getCountLingbao()) {
                pi.setCode(ResultInfo.LINGBAO_INSUFFICIENT.getCode());
				pi.setMsg("领宝不足");
				return pi;
			}

			if (lastNum > gift.getStore()) {
                pi.setCode(ResultInfo.PROJECT_INSUFFICIENT.getCode());
				pi.setMsg("产品库存不足");
				return pi;
			}

			// 验证通过，修改购物车数量和所需领宝：此处数据库更新操作采取累加方式，而不是直接更新值。
			lgc.setNum(num);
			lgc.setIntegral(num * unitPrice);
			int i = lingbaoGiftCartMapper.updateNumById(lgc);

			if (i > 0) {
                pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg("添加成功");
			} else {
                pi.setCode(ResultInfo.SERVER_ERROR.getCode());
				pi.setMsg("添加失败");
				LOG.info("更新购物车失败，更新数据失败：uid:" + uid + " giftid:" + giftId + " num:" + num);
			}
        } else { // 不存在
			if (account.getCountLingbao() < unitPrice * num) {
                pi.setResultInfo(ResultInfo.LINGBAO_INSUFFICIENT);
				return pi;
			}

			// 购物车限额KEY
			String cartLimitKey = AppCons.CARTCOUNT_REDIS_KEY;

			// 当前购物车数量
			int cartNum = getGiftCartNum(uid);

			// 获取购物车数量限制，默认20个
			int cartLimit = AppCons.SHOP_CART_LIMIT;

			if (redisDao.hasKey(cartLimitKey) && (Integer) redisDao.get(cartLimitKey) > 0) {
				cartLimit = (Integer) redisDao.get("cartcount");
			}
			System.out.println("购物车REDIS数量限制为：" + cartLimit);

			if (cartNum >= cartLimit) {
                pi.setResultInfo(ResultInfo.SHOPPING_CART_FULL);
				return pi;
			}

			// 添加到购物车
			LingbaoGiftCart record = new LingbaoGiftCart();
			record.setAddTime(new Date());
			record.setNum(num);
			record.setuId(uid);
			record.setGiftId(giftId);
			record.setIntegral(unitPrice * num);
			int i = lingbaoGiftCartMapper.insert(record);

			if (i > 0) {
                pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg("添加成功");

				// 修改redis中购物车记录
				String gkey = AppCons.GIFT_CART_NUMBER + uid;
				redisDao.set(gkey, cartNum + 1);
				redisDao.expire(gkey, 6, TimeUnit.HOURS);
				System.out.println("购物车REDIS现有数量" + (cartNum + 1));
			} else {
                pi.setCode(ResultInfo.SERVER_ERROR.getCode());
				pi.setMsg("添加失败");
				LOG.info("添加购物车失败，插入数据失败：uid:" + uid + " giftid:" + giftId + " num:" + num);
			}
		}

		// 删除redis key
		redisDao.delete(doubleClickKey);
		
		return pi;
	}

	@Override
	public PageInfo removeFromShopCart(Integer id, String uid) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(uid)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		// 验证用户&购物车信息是否匹配
		LingbaoGiftCart lgc = lingbaoGiftCartMapper.selectByPrimaryKey(id);
		if (lgc == null || !lgc.getuId().equals(uid)) {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("操作失败,请刷新后重试");
			LOG.info("删除购物车失败,未查询到购物车数据。 id=" + id + " uid:" + uid);
			return pi;
		}

		// 中奖礼品禁止删除
		if (lgc.getIntegral() == 0) {
            pi.setResultInfo(ResultInfo.LUCK_PROJECT_NOT_DEL);
			return pi;
		}

		int i = lingbaoGiftCartMapper.deleteByPrimaryKey(lgc.getId());

		if (i > 0) {
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("删除成功");

			// 修改redis中购物车数量
			int cartNum = getGiftCartNum(uid);
			if (cartNum > 0) {
				String gkey = AppCons.GIFT_CART_NUMBER + uid;
				redisDao.set(gkey, cartNum - 1);
			}
			LOG.info("从购物车删除数据成功。id:" + id + ";uid:" + uid + "\n 修改redis数量为：" + (cartNum - 1));

		} else {
            pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("删除失败");
			LOG.info("购物车删除失败：id:" + id + " uid:" + uid);
		}

		return pi;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public PageInfo batchExchangeGift(String uid, String giftCartIds, Integer addressID) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(giftCartIds)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		// 处理多余逗号
		if (giftCartIds.lastIndexOf(",") == giftCartIds.length() - 1) {
			giftCartIds = giftCartIds.substring(0, giftCartIds.length() - 1);
		}

		String[] ids = giftCartIds.split(",");

		pi = batchExchange(uid, ids, addressID);

        if (pi.getCode() != ResultInfo.SUCCESS.getCode()) { // 批量兑换失败，整体回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return pi;
	}

	@Override
	public PageInfo queryDetail(Integer id) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(id)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		// 验证礼品信息
		PageInfo validInfo = validGift(id, 1);
		if (validInfo == null) {
			LingbaoGift gift = lingbaoGiftMapper.selectColumnsById(id);
			if (gift != null) {
                pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg("success");
				pi.setObj(gift);
			} else {
                pi.setCode(ResultInfo.EMPTY_DATA.getCode());
				pi.setMsg("未查询到数据");
			}
		} else {
			pi = validInfo;
		}

		return pi;
	}

	@Override
	public PageInfo getGiftTypeList(Integer number) {
		PageInfo pi = new PageInfo();

		LingbaoGiftTypeExample example = new LingbaoGiftTypeExample();
		example.createCriteria().andStatusEqualTo(1);
		example.setOrderByClause("level asc");
		if (!StringUtils.isEmpty(number) && number > 0) {
			example.setLimitStart(0);
			example.setLimitEnd(number);
		}
		List<LingbaoGiftType> list = lingbaoGiftTypeMapper.selectByExample(example);

		if (null != list && list.size() > 0) {
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("success");
			pi.setRows(list);
		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("未查询到数据");
		}

		return pi;
	}

	@Override
	public PageInfo getGiftListWithCondition(Integer typeId, Integer recommend, Integer number, String giftName) {
		PageInfo pi = new PageInfo();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", typeId);
		map.put("isRecommend", recommend);
		map.put("number", number);
		map.put("giftName", giftName);
		List<LingbaoGift> list = lingbaoGiftMapper.queryGiftListWithCondition(map);

		if (null != list && list.size() > 0) {
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("success");
			pi.setRows(list);

		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("未查询到数据");
		}

		return pi;
	}

	@Override
	public PageInfo validateExchange(Integer giftId, String uid, Integer number) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(giftId) || StringUtils.isEmpty(number)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		// 1.验证礼品信息
		PageInfo validInfo = validGift(giftId, number);
		if (validInfo != null) {
			return validInfo;
		}

		// 2.用户领宝余额 是否足够
		UsersAccount account = usersAccountMapper.selectByUid(uid);
		LingbaoGift gift = lingbaoGiftMapper.selectColumnsById(giftId);
		int allCost = number * gift.getIntegral();
		// 如果有特惠积分，使用特惠积分
		if (null != gift.getPreferentialIntegral() && gift.getPreferentialIntegral() > 0) {
			allCost = number * gift.getPreferentialIntegral();
		}
		if (null == account || account.getCountLingbao() < allCost) {
            pi.setCode(ResultInfo.LINGBAO_INSUFFICIENT.getCode());
			pi.setMsg("您的领宝余额不足");
			return pi;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("allCost", allCost);

		// 3.收货方式为0表示需要地址
		if (null != gift.getReceiveWay() && gift.getReceiveWay() == 0) {
			LingbaoAddressExample laExample = new LingbaoAddressExample();
			laExample.createCriteria().andUIdEqualTo(uid).andStatusEqualTo("1");
			laExample.setOrderByClause("is_default DESC,create_time DESC");
			laExample.setLimitStart(0);
			laExample.setLimitEnd(AppCons.DEFAULT_PAGE_SIZE);
			List<LingbaoAddress> addressList = lingbaoAddressMapper.selectByExample(laExample);

			resultMap.put("addressList", addressList);
			resultMap.put("type", 0);
		} else {
			resultMap.put("type", 1);
		}

		pi.setObj(resultMap);
        pi.setCode(ResultInfo.SUCCESS.getCode());
		pi.setMsg("success");
		LOG.info("领宝兑换校验信息：\n 礼品id：" + giftId + " 用户id：" + uid + "礼品类型： " + gift.getType() + " \n msg：" + pi);

		return pi;
	}

	@Override
	public PageInfo batchExchangeGiftValidate(String cids, String uid) {
		PageInfo pi = new PageInfo();
		LOG.info("进行批量兑换礼品验证");

		int type = 1; // 是否需要地址标识,1表示不需要
		int allCost = 0; // 兑换所需积分

		String[] ids = cids.split(",");
		for (int i = 0; i < ids.length; i++) {
			// 1.根据购物车ID查询所要兑换的产品
			LingbaoGiftCart lgc = lingbaoGiftCartMapper.selectByPrimaryKey(Integer.parseInt(ids[i]));

			// 2.根据礼品ID查询相应礼品信息
			LingbaoGift gift = lingbaoGiftMapper.selectByPrimaryKey(lgc.getGiftId());

			// 3.如果花费0领宝，表示抽奖所得，不验证
			if (lgc.getIntegral() != 0) {
				// 4.验证商品是否能正常兑换
				PageInfo validInfo = validGift(gift.getId(), lgc.getNum());
				if (null != validInfo) {
					return validInfo;
				}

				// 5.计算积分
				if (null != gift.getPreferentialIntegral() && gift.getPreferentialIntegral() > 0) { // 特惠积分
					allCost += gift.getPreferentialIntegral() * lgc.getNum();
				} else { // 正常积分
					allCost += gift.getIntegral() * lgc.getNum();
				}
			}

			// 6.验证礼品是否需要地址
			if (null != gift.getReceiveWay() && gift.getReceiveWay() == 0) {
				type = 0;
			}
		}

		// 7.验证领宝余额
		UsersAccount account = usersAccountMapper.selectByUid(uid);
		if (account.getCountLingbao() < allCost) {
            pi.setCode(ResultInfo.LINGBAO_INSUFFICIENT.getCode());
			pi.setMsg("兑换所需:" + allCost + "个领宝,您的剩余领宝为:" + account.getCountLingbao() + "个");
			LOG.info("领宝批量兑换，验证失败信息：" + pi);
			return pi;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("allCost", allCost);

		// 8. 如果需要地址，验证地址信息
		if (type == 0) {
			LingbaoAddressExample laExample = new LingbaoAddressExample();
			laExample.createCriteria().andUIdEqualTo(uid).andStatusEqualTo("1");
			laExample.setOrderByClause("is_default DESC,create_time DESC");
			laExample.setLimitStart(0);
			laExample.setLimitEnd(AppCons.DEFAULT_PAGE_SIZE);
			List<LingbaoAddress> addressList = lingbaoAddressMapper.selectByExample(laExample);

			resultMap.put("addressList", addressList);
		}

		resultMap.put("type", type);
		pi.setObj(resultMap);
        pi.setCode(ResultInfo.SUCCESS.getCode());
		pi.setMsg("success");

		return pi;
	}

	@Override
	@Transactional
	public PageInfo batchRemoveFromShopCart(String ids, String uid) {
		PageInfo pi = new PageInfo();

		// 处理多余逗号
		if (ids.lastIndexOf(",") == ids.length() - 1) {
			ids = ids.substring(0, ids.length() - 1);
		}

		String[] groupId = ids.split(",");
		for (int i = 0; i < groupId.length; i++) {
			pi = removeFromShopCart(NumberUtils.toInt(groupId[i]), uid);
            if (pi.getCode() != ResultInfo.SUCCESS.getCode() && pi.getCode() != ResultInfo.LUCK_PROJECT_NOT_DEL.getCode()) {
				return pi;
			}
		}

		return pi;
	}

	/**
	 * 控制连点事件，避免数据重复添加
	 * 
	 * @param key key
	 * @return true false
	 */
	private boolean redisLockOfDoubleClick(String key) {
		if (redisDao.hasKey(key)) {
			return false;
		} else {
			redisDao.set(key, true);
			redisDao.expire(key, 1, TimeUnit.SECONDS);
			return true;
		}
	}

	/**
	 * 验证礼品是否可以兑换
	 * 
	 * @param giftId
	 *            礼品id
	 * @param num
	 *            礼品数量
	 * @return obj gift.store礼品库存
	 */
	private PageInfo validGift(int giftId, int num) {
		PageInfo pi = new PageInfo();
        pi.setCode(ResultInfo.SUCCESS.getCode());
		LingbaoGift gift = lingbaoGiftMapper.selectByPrimaryKey(giftId);

		if (null != gift) {
			if (gift.getShelfStatus() == 0) {
                pi.setCode(ResultInfo.PROJECT_NOT_LINE.getCode());
				pi.setMsg("产品" + gift.getName() + "已下架");

			} else if (gift.getApplyType() == 1) {
                pi.setCode(ResultInfo.NOT_RECEIVING_PROJECT.getCode());
				pi.setMsg("产品" + gift.getName() + "不可兑换");

			} else if (gift.getStore() < 1 || num > gift.getStore()) {
                pi.setCode(ResultInfo.PROJECT_INSUFFICIENT.getCode());
				pi.setMsg("产品" + gift.getName() + "库存不足");

			} else if (gift.getExchangeNumber() > 0 && num > gift.getExchangeNumber()) {
                pi.setCode(ResultInfo.EXCHANGE_COUNT_ERROR.getCode());
				pi.setMsg("产品" + gift.getName() + "每次仅限兑换" + gift.getExchangeNumber() + "件");
			}
			// 库存不足时提示剩余库存数量
			pi.setObj(gift.getStore());

		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("查询不到该商品:" + giftId);
		}

        return pi.getCode() == ResultInfo.SUCCESS.getCode() ? null : pi;
	}

	/**
	 * 从redis获取用户当前购物车数量，不存在则添加
	 * 
	 * @param uid 用户id
	 * @return 购物车数量
	 */
	private int getGiftCartNum(String uid) {
		int num = 0;

		// 先从redis中查询
		String key = AppCons.GIFT_CART_NUMBER + uid;
		if (redisDao.hasKey(key)) {
			num = (int) redisDao.get(key);
		} else {
			// 从数据库查询，并保存到redis，6小时过期
			LingbaoGiftCartExample example = new LingbaoGiftCartExample();
			example.createCriteria().andUIdEqualTo(uid);
			num = (int) lingbaoGiftCartMapper.countByExample(example);

			redisDao.set(key, num);
		}
		return num;
	}

    /**
     * 批量兑换执行
     * 
     * @param uid 用户id
     * @param giftIds 礼品id 多个用英文逗号分隔
     * @param addressID 地址id
     * @return pageinfo
     *
     */
	private PageInfo batchExchange(String uid, String[] giftIds, Integer addressID) {
		PageInfo pi = new PageInfo();

        int allCost = 0; // 所需领宝总额
        int redisCardNum = 0; // 需要修改的购物车redis数量

		for (int j = 0; j < giftIds.length; j++) {
			// 查询购物车信息
			LingbaoGiftCart lgc = lingbaoGiftCartMapper.selectByPrimaryKey(NumberUtils.toInt(giftIds[j]));
			if (lgc == null) {
                pi.setCode(ResultInfo.EMPTY_DATA.getCode());
				pi.setMsg("操作失败,请刷新后重试");
				LOG.info("批量兑换失败，未从购物车查询到对应礼品信息。uid:" + uid + " giftId:" + giftIds[j]);
				return pi;
			}

			// 查询礼品信息
			LingbaoGift gift = lingbaoGiftMapper.selectByPrimaryKey(lgc.getGiftId());
			if (null == gift) {
                pi.setCode(ResultInfo.EMPTY_DATA.getCode());
				pi.setMsg("兑换失败");
				LOG.info("批量兑换失败，未查询到礼品信息。礼品id" + lgc.getGiftId());
				return pi;
			}

			LingbaoExchangeInfo info = null;
			// 如果购物车中所需领宝为0，表示抽奖所得 兑换不需要验证,兑换初始信息根据序列号查询获得
			if (lgc.getIntegral() == 0) {
				info = lingbaoExchangeInfoMapper.queryBySerialNumber(lgc.getSerialNumber());
				if (null == info || info.getStatus() != 3) {
                    pi.setCode(ResultInfo.EMPTY_DATA.getCode());
					pi.setMsg("兑换失败");
					LOG.info("未查询到初始化兑换记录（中奖产品）：礼品ID" + gift.getId() + " 用户ID：" + uid);
					return pi;
				}

            } else { // 正常兑换
				PageInfo validInfo = validGift(gift.getId(), lgc.getNum());
				if (validInfo != null) {
					return validInfo;
				}

				info = new LingbaoExchangeInfo();
				// 是否存在特惠
				int cost = gift.getIntegral();
				if (null != gift.getPreferentialIntegral() && gift.getPreferentialIntegral() > 0) {
					cost = gift.getPreferentialIntegral();
				}
				info.setIntegral(cost);
				info.setuId(uid);
				info.setName(gift.getName());
				info.setGiftId(gift.getId());
				info.setType(0);
				info.setNum(lgc.getNum());
				info.setSerialNumber(System.currentTimeMillis() + "");

				// redis数量修改
				redisCardNum++;
				allCost += cost * lgc.getNum();
			}

			if (null != gift.getReceiveWay() && gift.getReceiveWay() == 0) {
				if (!StringUtils.isEmpty(addressID)) {
					info.setAddressId(addressID);
				} else {
                    pi.setCode(ResultInfo.ACTIVITY_NOT_LINE.getCode());
					pi.setMsg("请先选择收货地址");
					return pi;
				}
			}

			info.setStatus(0);
			info.setExchangeTime(new Date());

			int result = 0;
            if (lgc.getIntegral() == 0) { // 抽奖执行更新操作
				result = lingbaoExchangeInfoMapper.updateByPrimaryKeySelective(info);

            } else { // 5.非中奖产品：更新已兑换数量，库存量
				LingbaoGiftWithBLOBs lwb = new LingbaoGiftWithBLOBs();
				lwb.setId(gift.getId());
				lwb.setStore(gift.getStore() - lgc.getNum());
				lwb.setExchangeCount(gift.getExchangeCount() + lgc.getNum());
				lingbaoGiftMapper.updateByPrimaryKeySelective(lwb);

				result = lingbaoExchangeInfoMapper.insert(info);
			}

			if (result > 0) {

				// 6.从购物车删除
				result = lingbaoGiftCartMapper.deleteByPrimaryKey(lgc.getId());
				if (result < 1) {
                    pi.setCode(ResultInfo.SERVER_ERROR.getCode());
					pi.setMsg("兑换失败");
					LOG.info("兑换失败，删除购物车数据失败。");
					return pi;
				}

				// 7.非抽奖礼品，向领宝记录表中插入一条记录
				if (info.getIntegral() > 0) {
					ActivityRecord ar = new ActivityRecord();
					ar.setAmount(-info.getIntegral());
					ar.setName("兑换礼品");
					ar.setContent(info.getName());
					ar.setStatus(0);
					ar.setType(0);
					ar.setuId(uid);
					ar.setUseDate(new Date());
					result = activityRecordMapper.insertSelective(ar);
					if (result < 1) {
                        pi.setCode(ResultInfo.SERVER_ERROR.getCode());
						pi.setMsg("兑换失败");
						LOG.info("兑换失败，保存领宝记录失败。");
						return pi;
					}
				}

			} else {
                pi.setCode(ResultInfo.SERVER_ERROR.getCode());
				pi.setMsg("兑换失败");
				LOG.info("兑换失败，保存领宝兑换记录失败");
				return pi;
			}
		}

		// 查询用户领宝数
		UsersAccount account = usersAccountMapper.selectByUid(uid);
		if (account.getCountLingbao() < allCost) {
            pi.setCode(ResultInfo.LINGBAO_INSUFFICIENT.getCode());
			pi.setMsg("领宝不足,您仅剩" + account.getCountLingbao() + "领宝");
			return pi;
		}

		// 8.扣除用户领宝数量
		account.setCountLingbao(account.getCountLingbao() - allCost);
		int i = usersAccountMapper.updateByPrimaryKeySelective(account);
		if (i < 1) {
            pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("兑换失败");

			LOG.info("兑换失败，更新用户账户领宝失败");
			return pi;
		}

		// 用户现有领宝数量
		pi.setObj(account.getCountLingbao());
		LOG.info("批量兑换成功：用户ID：" + uid);

		// 修改redis中数量限制
		String key = AppCons.GIFT_CART_NUMBER + uid;
		if (redisDao.hasKey(key)) {
			System.out.println("减去购物车reids数量：" + redisCardNum);
			int orgiNum = (Integer) redisDao.get(key);
			redisDao.set(key, orgiNum - redisCardNum);
			redisDao.expire(key, 6, TimeUnit.HOURS);
		}

        pi.setCode(ResultInfo.SUCCESS.getCode());
		pi.setMsg("兑换成功");
		return pi;
	}

	/**
	 * 领宝兑换验证
	 * 
	 * @param account 用户账户
	 * @param gift 礼品信息
	 * @param num 兑换数量
	 * @param addressID 地址id
	 * @return pageinfo
	 */
	private PageInfo validExchangeInfo(UsersAccount account, LingbaoGift gift, Integer num, Integer addressID) {
		PageInfo pi = new PageInfo();
        pi.setCode(ResultInfo.SUCCESS.getCode());

		if (null != account) {
			// 单件礼品所需积分
			int cost = gift.getIntegral();
			if (null != gift.getPreferentialIntegral() && gift.getPreferentialIntegral() > 0) {
				cost = gift.getPreferentialIntegral();
			}
			pi.setObj(cost);

			// 验证用户领宝数量
			int userLingbao = account.getCountLingbao();
			if (userLingbao >= cost * num) {
				// 验证收货地址
                if (null != gift.getReceiveWay() && gift.getReceiveWay() == 0) { // 需要收货地址
					if (!StringUtils.isEmpty(addressID)) {
						LingbaoAddressExample addrExample = new LingbaoAddressExample();
						addrExample.createCriteria().andUIdEqualTo(account.getuId()).andIdEqualTo(addressID)
								.andStatusEqualTo("1");
						int i = (int) lingbaoAddressMapper.countByExample(addrExample);
                        if (i < 1) { // 无效
                            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
							pi.setMsg("该地址无效");
						}
					} else {
                        pi.setCode(ResultInfo.RECEIVING_ADDRESS.getCode());
						pi.setMsg("请选择收货地址");
					}
				}
			} else {
                pi.setCode(ResultInfo.LINGBAO_INSUFFICIENT.getCode());
				pi.setMsg("领宝不足");
			}
		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("获取用户账户信息失败");
		}

		return pi;
	}

    /**
     * 操作兑换数据
     * 
     * @param account 用户账户信息
     * @param gift 礼品信息
     * @param num 数量
     * @param addressID 地址id
     * @param cost 花费
     * @return booelean是否成功
     *
     */
	private boolean exchangeDataOperate(UsersAccount account, LingbaoGift gift, Integer num, Integer addressID,
			Integer cost) {
		boolean exchangeDataOk = true;

		String uid = account.getuId();
		int giftId = gift.getId();

		// 插入一条兑换记录
		LingbaoExchangeInfo info = new LingbaoExchangeInfo();
		info.setuId(uid);
		info.setName(gift.getName());
		info.setGiftId(giftId);
		info.setNum(num);
		info.setIntegral(cost * num);
		if (null != gift.getReceiveWay() && gift.getReceiveWay() == 0) {
			info.setAddressId(addressID);
		}
		info.setSerialNumber(System.currentTimeMillis() + "");
		info.setStatus(0);
		info.setExchangeTime(new Date());
		info.setType(0);

		int dataResult = lingbaoExchangeInfoMapper.insertSelective(info);

		if (dataResult > 0) {
			LOG.info("插入礼品兑换信息成功.\n" + info.toString());
		} else {
			exchangeDataOk = false;
		}

		// 更新礼品表信息
		LingbaoGiftWithBLOBs giftBlob = new LingbaoGiftWithBLOBs();
		giftBlob.setId(gift.getId());
        giftBlob.setStore(gift.getStore() - num); // 更新库存
        giftBlob.setExchangeCount(gift.getExchangeCount() + num); // 更新兑换数量
		dataResult = lingbaoGiftMapper.updateByPrimaryKeySelective(giftBlob);

		if (dataResult > 0) {
			LOG.info("更新礼品信息成功.\n" + giftBlob.toString());
		} else {
			exchangeDataOk = false;
		}

		// 更新用户账户领宝
		account.setCountLingbao(account.getCountLingbao() - (num * cost));
		dataResult = usersAccountMapper.updateByPrimaryKeySelective(account);

		if (dataResult > 0) {
			LOG.info("更新用户账户领宝成功.\n" + giftBlob.toString());
		} else {
			exchangeDataOk = false;
		}

		// 向activity_record表插入一条记录
		ActivityRecord ar = new ActivityRecord();
		ar.setuId(uid);
		ar.setAmount(-info.getIntegral());
		ar.setName("兑换礼品");
		ar.setContent(info.getName() + "x" + num);
		ar.setUseDate(new Date());
		ar.setType(0);
		ar.setStatus(0);
		dataResult = activityRecordMapper.insertSelective(ar);

		if (dataResult > 0) {
			LOG.info("插入领宝记录成功.\n" + giftBlob.toString());
		} else {
			exchangeDataOk = false;
		}

		// 如果购物车存在，删除记录
		LingbaoGiftCartExample gcExample = new LingbaoGiftCartExample();
		gcExample.createCriteria().andUIdEqualTo(uid).andGiftIdEqualTo(giftId);
		List<LingbaoGiftCart> giftCart = lingbaoGiftCartMapper.selectByExample(gcExample);

		if (giftCart != null && giftCart.size() > 0) {
			dataResult = lingbaoGiftCartMapper.deleteByPrimaryKey(giftCart.get(0).getId());

			if (dataResult > 0) {
				LOG.info("删除购物车记录成功.\n" + giftBlob.toString());

				// 非抽奖礼品，修改redis购物车数量
				if (giftCart.get(0).getIntegral() != 0) {
					int cartNum = getGiftCartNum(uid);
					if (cartNum > 0) {
						String gkey = AppCons.GIFT_CART_NUMBER + uid;
						redisDao.set(gkey, cartNum - 1);
					}
				}

			} else {
				exchangeDataOk = false;
			}

		}

		return exchangeDataOk;
	}
}
