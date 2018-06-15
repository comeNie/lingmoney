package com.mrbt.lingmoney.service.bank.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.deal.HxSinglePayment;
import com.mrbt.lingmoney.bank.utils.dto.ResponseBodyDto;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.HxPaymentMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.HxPayment;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxPaymentExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.service.bank.PaymentCommonService;
import com.mrbt.lingmoney.service.bank.SinglePaymentService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 单笔还款
 */
@Service
public class SinglePaymentServiceImpl implements SinglePaymentService {
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private HxSinglePayment hxSinglePayment;
	@Autowired
	private HxPaymentMapper hxPaymentMapper;
	@Autowired
	private RedisSet redisSet;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private PaymentCommonService paymentCommonService;

	private static final Logger LOGGER = LogManager.getLogger(SinglePaymentService.class);

	@Override
	public String receivePaymentResult(Document document) throws Exception {
		ResponseBodyDto response = new ResponseBodyDto();

		// 接收结果
		String channelFlow = document.selectSingleNode("//OLDREQSEQNO").getText();
	
		if (StringUtils.isEmpty(channelFlow)) {
            throw new PageInfoException("异步应答报文原还款流水号为空", ResultInfo.EMPTY_DATA.getCode());
		}
		
		// 将还款信息加入redis队里，等待提交收益明细
		redisSet.setListElement("PAYMENTINFOQUEUE", channelFlow);
		LOGGER.info("流水号为" + channelFlow + "的还款操作，其收益明细提交已push到redis队列，请等待提交计划执行");


		// 更新状态
		HxPayment hxPayment = new HxPayment();
		hxPayment.setStatus(1);
		hxPayment.setPaymentDate(new Date());
		HxPaymentExample ex = new HxPaymentExample();
		ex.createCriteria().andChannelFlowEqualTo(channelFlow);
		hxPaymentMapper.updateByExampleSelective(hxPayment, ex);

		// 返回状态

		response.setOLDREQSEQNO(channelFlow);

		// 更新借款人数据
		successOperation(channelFlow);

		return hxSinglePayment.responseAsync(response);
	}

    /**
     * 还款操作成功后进行的数据库操作
     * 
     * @param channelFlow 流水号
     * @throws Exception 
     *
     */
	private void successOperation(String channelFlow) throws Exception {
		HxPaymentBidBorrowUnionInfo paymentRes = verifyService.verifyPaymentBorrowInfo(channelFlow);
        // 如果是个人用户，扣除借款人账户余额
        if (!StringUtils.isEmpty(paymentRes.getuId())) {
            String uid = paymentRes.getuId();
            UsersAccountExample ex = new UsersAccountExample();
            ex.createCriteria().andUIdEqualTo(uid);

            List<UsersAccount> resLi = usersAccountMapper.selectByExample(ex);
            if (resLi == null || resLi.size() == 0) {
                throw new PageInfoException("用户账户信息不存在", ResultInfo.EMPTY_DATA.getCode());
            }
            UsersAccount userAcc = resLi.get(0);
            BigDecimal loanAmount = paymentRes.getAmount();
            UsersAccount userAccNew = new UsersAccount();
            userAccNew.setBalance(userAcc.getBalance().subtract(loanAmount));
            // 还款成功后，还款金额与手续费会冻结在借款人账户中，直到 P2P商户提交还款收益明细成功后才会扣除
            userAccNew.setFrozenMoney(userAcc.getFrozenMoney().add(loanAmount));
            usersAccountMapper.updateByExampleSelective(userAccNew, ex);
        }

		// 修改产品状态 13 还款成功
		paymentCommonService.changeProductStatus(paymentRes.getpId(), AppCons.PRODUCT_STATUS_REPAYMENT_SUCCESS);

	}

}
