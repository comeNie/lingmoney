package com.mrbt.lingmoney.admin.service.trading.impl;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.smsSend.SmsService;
import com.mrbt.lingmoney.admin.service.trading.RedeemFailFlowService;
import com.mrbt.lingmoney.admin.service.trading.TradingService;
import com.mrbt.lingmoney.admin.vo.trading.PayVo;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.RedeemFailFlowMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.RedeemFailFlow;
import com.mrbt.lingmoney.model.RedeemFailFlowExample;
import com.mrbt.lingmoney.model.SellResult;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.schedule.SellTrading;
import com.mrbt.lingmoney.model.trading.SmsMessage;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.pay.jd.reback.RebackResultVo;
import com.mrbt.pay.jd.reback.ReturnByJd;

/**
 * 
 * 企业管理--》赎回失败流水
 *
 */
@Service
public class RedeemFailFlowServiceImpl implements RedeemFailFlowService {
	private static final Logger LOGGER = LogManager.getLogger(RedeemFailFlowServiceImpl.class);

	@Autowired
	private RedeemFailFlowMapper redeemFailFlowMapper;
	@Autowired
    private TradingMapper tradingMapper;
    @Autowired
	private ReturnByJd returnByJd;
    @Autowired
    private TradingService tradingService;
    @Autowired
    private CustomQueryMapper customQueryMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private SmsService smsService;
    @Autowired
    private ProductCustomerMapper productCustomerMapper;
	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return ProductRedeemFailFlowExample辅助类
	 */
	@Override
	public RedeemFailFlowExample createRedeemFailFlowExample(RedeemFailFlow vo) {
		RedeemFailFlowExample example = new RedeemFailFlowExample();
		RedeemFailFlowExample.Criteria cri = example.createCriteria();
		cri.andStatusEqualTo(ResultParame.ResultNumber.EIGHTEEN.getNumber());
		return example;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	@Override
	public void delete(int id) {
		redeemFailFlowMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 查找，根据ProductRedeemFailFlow
	 * 
	 * @param vo
	 *            ProductRedeemFailFlow的实例
	 * @param offset
	 *            起始位置
	 * @param limit
	 *            记录条数
	 * @return ProductRedeemFailFlow的集合
	 */
	@Override
	public List<RedeemFailFlow> list(RedeemFailFlow vo, int offset, int limit) {
		RedeemFailFlowExample example = createRedeemFailFlowExample(vo);
		example.setLimitStart(offset);
		example.setLimitEnd(limit);
		return redeemFailFlowMapper.selectByExample(example);
	}

	@Override
	public RedeemFailFlow findByPk(int id) {
		return redeemFailFlowMapper.selectByPrimaryKey(id);
	}

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            ProductRedeemFailFlow实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<ProductRedeemFailFlow>
	 */
	@Override
	public GridPage<RedeemFailFlow> listGrid(RedeemFailFlow vo, RowBounds page) {
		RedeemFailFlowExample example = createRedeemFailFlowExample(vo);
		example.setLimitStart(page.getOffset());
		example.setLimitEnd(page.getLimit());

		GridPage<RedeemFailFlow> result = new GridPage<RedeemFailFlow>();
		result.setRows(redeemFailFlowMapper.selectByExample(example));
		result.setTotal(redeemFailFlowMapper.countByExample(example));
		return result;
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 */
	@Transactional
	@Override
	public void save(RedeemFailFlow vo) {
		redeemFailFlowMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Transactional
	@Override
	public void update(RedeemFailFlow vo) {
		redeemFailFlowMapper.updateByPrimaryKeySelective(vo);
	}

	/**
	 * 赎回
	 *
	 * @param id
	 *            id
	 * @return 数据返回
	 *
	 */
    @Override
    public PageInfo redeem(Integer id) {
        PageInfo pi = new PageInfo();
        
        if (StringUtils.isEmpty(id)) {
			pi.setCode(ResultParame.ResultNumber.ONE_ZERO_ZERO_ONE.getNumber());
            pi.setMsg("参数缺失");
            return pi;
        }
        
        RedeemFailFlow redeemFailFlow = redeemFailFlowMapper.selectByPrimaryKey(id);
        if (redeemFailFlow == null) {
			pi.setCode(ResultParame.ResultNumber.ONE_ZERO_ZERO_THREE.getNumber());
            pi.setMsg("未查到有效数据");
            return pi;
        }
        
        int tid = redeemFailFlow.gettId();
        String uid = redeemFailFlow.getuId();
        BigDecimal buyMoney = redeemFailFlow.getBuyMoney();
        
		LOGGER.info("用户id：{}，赎回金额：{}，交易id：{}", uid, buyMoney.toString(), tid);

        RebackResultVo vo = returnByJd.accountQuery();
        if (vo.isOk()) {

            int bal = vo.getAvail_amount().compareTo(buyMoney);

			if (bal != ResultParame.ResultNumber.ONE.getNumber()) {
				pi.setCode(ResultParame.ResultNumber.FOUR_HUNDRED.getNumber());
                pi.setMsg("平台账户余额不足");
                return pi;
            }

        } else {
			pi.setCode(ResultParame.ResultNumber.FOUR_HUNDRED.getNumber());
            pi.setMsg(vo.getResultInfo());
            return pi;
        }
        
        Trading trading = tradingMapper.selectByPrimaryKey(tid);
        boolean isTakeheartProduct = false;
        if (trading.getpId().intValue() == ProductUtils.getIntContent("takeHeart_pID")) {
            isTakeheartProduct = true;
            // 判读是否可以卖出
            String key = PayVo.takeheart + uid;
            if (!tradingService.checkTakeHeart(key)) {
				pi.setCode(ResultParame.ResultNumber.FOUR_HUNDRED.getNumber());
                pi.setMsg("赎回失败，随心取有未处理完记录");
                return pi;

            } else {
                tradingService.setTakeHeart(key);
            }
        }

        boolean sellSuccess = false;
        if (isTakeheartProduct) {
            SellResult sellResult = tradingService.sellProduct(uid, tid, buyMoney.doubleValue(), 1, "secondRedeem");
            if (sellResult.getFlag() == 0) {
                sellSuccess = true;
            }
            
        } else {
            SellTrading sellTrading = customQueryMapper.querySellTradingInfo(tid);
            if (sellTrading == null) {
				pi.setCode(ResultParame.ResultNumber.ONE_ZERO_ZERO_THREE.getNumber());
                pi.setMsg("交易不存在");
                return pi;
            }

            if (sellTrading.getStatus() != AppCons.BUY_OK) {
				pi.setCode(ResultParame.ResultNumber.FOUR_HUNDRED.getNumber());
                pi.setMsg("不是已经买入的产品");
                return pi;
            }

            if (!sellTrading.getUid().equals(uid)) {
				pi.setCode(ResultParame.ResultNumber.ONE_ZERO_ZERO_THREE.getNumber());
                pi.setMsg("赎回用户错误，非本用户");
                return pi;
            }

            sellTrading.setSellDt(new Date());
            sellTrading.setStatus(AppCons.SELL_STATUS);
            Common.buildOutBizCode(sellTrading);
            int i = customQueryMapper.updateTradingInfoAftersellWenYing(sellTrading);
			if (i > 0) {
                sellSuccess = true;
            }
        }
        
        if (trading.getpId().intValue() == ProductUtils.getIntContent("takeHeart_pID")) {
            // 判读是否可以购买
            String key = PayVo.takeheart + uid;
            tradingService.delTakeHeart(key);
        }

        if (sellSuccess) {
			pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
            pi.setMsg("赎回成功");
            try {
                Users user = usersMapper.selectByPrimaryKey(uid);
                Product pro = productCustomerMapper.selectByCode(redeemFailFlow.getpCode());
                String content = AppCons.sell_content;
                content = MessageFormat.format(content, user.getLoginAccount(),
                        DateUtils.getDateStr(new Date(), DateUtils.sft), pro.getName(), trading.getSellMoney());

                // 发送短信修改为放入redis统一发送
                SmsMessage message = new SmsMessage();
                message.setTelephone(user.getTelephone());
                message.setContent(content);
                smsService.saveSmsMessage(message);

            } catch (Exception e) {
				LOGGER.error("发送赎回成功短信失败，用户id{}，交易id：{}，错误信息：{}", uid, tid, e.toString());
                e.printStackTrace();
            }

        } else {
			pi.setCode(ResultParame.ResultInfo.MODIFY_REJECT.getCode());
            pi.setMsg("赎回失败");
        }

        return pi;
    }

}
