package com.mrbt.lingmoney.admin.service.oa.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.mongo.MongoSynProductData;
import com.mrbt.lingmoney.admin.mongo.MongoSynTradingData;
import com.mrbt.lingmoney.admin.mongo.MongoSynUserData;
import com.mrbt.lingmoney.admin.mongo.SynDataRecordingTime;
import com.mrbt.lingmoney.admin.service.oa.DatasToMongodbTaskService;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.SynTradingDataMapper;
import com.mrbt.lingmoney.mapper.SynUserDataMapper;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.SynTradingData;
import com.mrbt.lingmoney.model.SynTradingDataExample;
import com.mrbt.lingmoney.model.SynUserData;
import com.mrbt.lingmoney.model.SynUserDataExample;
import com.mrbt.lingmoney.utils.ResultParame;
/**
 * 同步信息
 * 
 * @description
 * @version 1.0
 * @since 2017-011-28
 */

@Service
public class DatasToMongodbTaskServiceImpl implements DatasToMongodbTaskService {
	private static final Logger LOG = LogManager.getLogger(DatasToMongodbTaskServiceImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private SynUserDataMapper synUserDataMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private SynTradingDataMapper synTradingDataMapper;
	
	static Map<String, String> reIsCodeMap = new HashMap<String, String>();
	
	@Override
	public void usersDatasToMongodbTask() {
		LOG.info("同步用户数据到mongodb开始");
		try {
			// 查询上一次同步的时间
			Date date = null;
			SynDataRecordingTime sdrt = mongoTemplate.findOne(new Query(Criteria.where("title").is("syn_user")),
					SynDataRecordingTime.class);
			if (sdrt != null) {
				date = sdrt.getSynTime();
			} else {
				date = new SimpleDateFormat("yyyy-MM-dd").parse("2015-11-01 00:00:00");
			}

			// 查询要同步的用户数据
			SynUserDataExample example = new SynUserDataExample();
			example.createCriteria().andRegDateGreaterThanOrEqualTo(date);
			example.setOrderByClause("reg_date");
			List<SynUserData> listSud = synUserDataMapper.selectByExample(example);

			SynUserDataExample example2 = new SynUserDataExample();

			Date synDate = null;

			for (SynUserData sud : listSud) {
				MongoSynUserData query = mongoTemplate.findOne(new Query(Criteria.where("uId").is(sud.getuId())),
						MongoSynUserData.class);
				if (query != null) {
					continue;
				} else {
					MongoSynUserData istData = new MongoSynUserData();
					String recommenderIsCode = "";

					// 查询推荐人的推荐码
					if (sud.getReferralId() != null && !sud.getReferralId().equals("")) {
						if (reIsCodeMap.containsKey(sud.getReferralId())) {
							recommenderIsCode = reIsCodeMap.get(sud.getReferralId());
						} else {
							example2.clear();
							example2.createCriteria().andUIdEqualTo(sud.getReferralId());
							List<SynUserData> recIsCodeList = synUserDataMapper.selectByExample(example2);
							if (recIsCodeList != null && recIsCodeList.size() > 0) {
								recommenderIsCode = recIsCodeList.get(0).getReferralCode();
							} else {
								LOG.info("验证下数据是不是有问题了");
							}
						}
					}

					copyUserMongoBean(istData, sud, recommenderIsCode); // 复制用户同步的信息
					mongoTemplate.insert(istData);
				}
				synDate = sud.getRegDate();
			}

			if (synDate != null) {
				mongoTemplate.upsert(new Query(Criteria.where("title").is("syn_user")),
						new Update().set("synTime", synDate), SynDataRecordingTime.class);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 复制用户同步的信息
	 * 
	 * @param istData
	 *            istData
	 * @param sud
	 *            sud
	 * @param recommenderIsCode
	 *            recommenderIsCode
	 */
	private void copyUserMongoBean(MongoSynUserData istData, SynUserData sud, String recommenderIsCode) {
		istData.setRecommenderIsCode(recommenderIsCode);
		istData.setuId(sud.getuId());
		istData.setBank(sud.getBank());
		istData.setCertification(sud.getCertification());
		istData.setIdCard(sud.getIdCard());
		istData.setName(sud.getName());
		istData.setReferralCode(sud.getReferralCode());
		istData.setReferralId(sud.getReferralId());
		istData.setRegDate(sud.getRegDate());
		istData.setTelephone(sud.getTelephone());
		istData.setSynDataTime(System.currentTimeMillis());
	}
	

	@Override
	public void tradingDatasToMongodbTask() {
		LOG.info("同步交易数据到mongodb开始");
		try {
			// 查询上一次同步的时间
			Date date = null;
			SynDataRecordingTime sdrt = mongoTemplate.findOne(new Query(Criteria.where("title").is("syn_trading")),
					SynDataRecordingTime.class);
			if (sdrt != null) {
				date = sdrt.getSynTime();
			} else {
				date = new SimpleDateFormat("yyyy-MM-dd").parse("2015-11-01 00:00:00");
			}

			// 创建产品状态查询条件
			List<Integer> proStatus = new ArrayList<Integer>();
			proStatus.add(ResultParame.ResultNumber.TWO.getNumber()); // 产品成立
			proStatus.add(ResultParame.ResultNumber.SEVEN.getNumber()); // 产品成立等待放款

			// 查询产品信息
			ProductExample example = new ProductExample();
			example.createCriteria().andStatusIn(proStatus)
					.andPcIdNotEqualTo(ResultParame.ResultNumber.SEVENTEEN.getNumber()).andEstablishDtIsNotNull()
					.andEstablishDtGreaterThan(date);
			example.setOrderByClause("establish_dt");

			List<Product> listPro = productMapper.selectByExample(example);
			SynTradingDataExample stde = new SynTradingDataExample();

			Date synDate = null;
			for (Product product : listPro) {
				long x = mongoTemplate.count(new Query(Criteria.where("id").is(product.getId())),
						MongoSynProductData.class);
				if (x > 0) {
					continue;
				}

				// 成立产品对应的购买记录
				stde.clear();
				stde.createCriteria().andPIdEqualTo(product.getId());
				
				List<SynTradingData> listStd = synTradingDataMapper.selectByExample(stde);
				
				for (SynTradingData std : listStd) {
					long y = mongoTemplate.count(new Query(Criteria.where("tId").is(std.getId())),
							MongoSynTradingData.class);
					if (y > 0) {
						continue;
					}
					MongoSynTradingData mstd = new MongoSynTradingData();
					copyTradingMongoBean(mstd, std); // 复制交易信息
					mongoTemplate.insert(mstd);
					
					//修补用户数据
					//根据UID查询
					MongoSynUserData msud = mongoTemplate.findOne(new Query(Criteria.where("uId").is(std.getuId()).and("bank").is(0)), MongoSynUserData.class);
					if (msud != null) {
						if (msud.getCertification() == 0) {
							//查询mysql中的用户信息数据
							SynUserDataExample sde = new SynUserDataExample();
							sde.createCriteria().andUIdEqualTo(std.getuId());
							
							List<SynUserData> sudList = synUserDataMapper.selectByExample(sde);
							
							
							if (sudList != null && sudList.size() > 0) {
								SynUserData sud = sudList.get(0);
								
								mongoTemplate.upsert(new Query(Criteria.where("uId").is(std.getuId())), 
										new Update().set("bank", sud.getBank()).set("certification", sud.getCertification())
										.set("name", sud.getName()).set("idCard", sud.getIdCard()), MongoSynUserData.class);
							}
						}
					}
				}

				MongoSynProductData mspd = new MongoSynProductData();
				copyProductMongoBean(mspd, product); // 复制产品信息
				mongoTemplate.insert(mspd);
				
				synDate = product.getEstablishDt();
			}
			if (synDate != null) {
				mongoTemplate.upsert(new Query(Criteria.where("title").is("syn_product")),
						new Update().set("synTime", synDate), SynDataRecordingTime.class);
			}

		} catch (ParseException e) {
			LOG.info("同步交易数据到mongodb出错");
			e.printStackTrace();
		}
		LOG.info("同步交易数据到mongodb结束");
	}

	/**
	 * 复制交易信息
	 * 
	 * @param mstd
	 *            mstd
	 * @param std
	 *            std
	 */
	private void copyTradingMongoBean(MongoSynTradingData mstd, SynTradingData std) {
		mstd.setBizCode(std.getBizCode());
		mstd.setBuyDt(std.getBuyDt());
		mstd.setBuyMoney(std.getBuyMoney());
		mstd.setFinancialMoney(std.getFinancialMoney());
		mstd.setId(std.getId());
		mstd.setInterest(std.getInterest());
		mstd.setMinSellDt(std.getMinSellDt());
		mstd.setOutBizCode(std.getOutBizCode());
		mstd.setPcId(std.getPcId());
		mstd.setpCode(std.getpCode());
		mstd.setPcType(std.getPcType());
		mstd.setpId(std.getpId());
		mstd.setSellDt(std.getSellDt());
		mstd.setSellMoney(std.getSellMoney());
		mstd.setStatus(std.getStatus());
		mstd.setuId(std.getuId() + "");
		mstd.setValueDt(std.getValueDt());
		mstd.setwValueDt(std.getwValueDt());
		mstd.setIdCard(std.getIdCard());
		mstd.setSynDataTime(System.currentTimeMillis());
	}

	/**
	 * 复制产品信息
	 * 
	 * @param mspd
	 *            mspd
	 * @param product
	 *            product
	 */
	private void copyProductMongoBean(MongoSynProductData mspd, Product product) {
		mspd.setBatch(product.getBatch());
		mspd.setBuyLimit(product.getBuyLimit());
		mspd.setCode(product.getCode());
		mspd.setEdDt(product.getEdDt());
		mspd.setEstablishDt(product.getEstablishDt());
		mspd.setfTime(product.getfTime());
		mspd.setfYield(product.getfYield());
		mspd.setId(product.getId());
		mspd.setName(product.getName());
		mspd.setPcId(product.getPcId());
		mspd.setPcName(product.getPcName());
		mspd.setPriorMoney(product.getPriorMoney());
		mspd.setpType(product.getpType());
		mspd.setReachMoney(product.getReachMoney());
		mspd.setReWay(product.getReWay());
		mspd.setStatus(product.getStatus());
		mspd.setStDt(product.getStDt());
		mspd.setValueDt(product.getValueDt());
		mspd.setWaitRate(product.getWaitRate());
		mspd.setSynDataTime(System.currentTimeMillis());
	}

}
