package com.mrbt.lingmoney.admin.service.takeHeart.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.exception.DataUnCompleteException;
import com.mrbt.lingmoney.admin.service.takeHeart.TakeHeartInterestService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.ProductParamMapper;
import com.mrbt.lingmoney.mapper.TakeheartCategoryMapper;
import com.mrbt.lingmoney.mapper.TakeheartFixRateMapper;
import com.mrbt.lingmoney.mapper.TakeheartTransactionFlowMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.TakeheartCategory;
import com.mrbt.lingmoney.model.TakeheartCategoryExample;
import com.mrbt.lingmoney.model.TakeheartTransactionFlow;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

@Service
public class TakeHeartInterestServiceImp implements TakeHeartInterestService {
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private TakeheartCategoryMapper takeheartCategoryMapper;
    @Autowired
    private TradingMapper tradingMapper;
    @Autowired
    private ProductParamMapper productParamMapper;
    @Autowired
    private TakeheartFixRateMapper takeheartFixRateMapper;
    @Autowired
    private TakeheartTransactionFlowMapper takeheartTransactionTlowMapper;
    @Autowired
    private UsersAccountMapper usersAccountMapper;
    @Autowired
    private AccountFlowMapper accountFlowMapper;

    /**
     * 获取利息是否有变动
     * @return
     */
    @Override
    public List<TakeheartCategory> findTakeheartCategory() {
        TakeheartCategoryExample example = new TakeheartCategoryExample();
        example.createCriteria().andStatusEqualTo(1).andReleaseDateEqualTo(new Date());
        return takeheartCategoryMapper.selectByExample(example);
    }

    /**
     * 每日跑批开始记录到redis
     * 
     * @return
     */
    @Override
    public boolean saveRedisTakeHeart() {
        boolean b = true;
        String key = AppCons.TAKE_HEART + "interest";
        redisDao.set(key, 0);
        redisDao.expire(key, 1, TimeUnit.HOURS);
        return b;
    }

    /**
     * 每日跑批结束删除redis
     * 
     * @return
     */
    @Override
    public boolean deleteRedisTakeHeart() {

        boolean b = true;

        String key = AppCons.TAKE_HEART + "interest";

        b = redisDao.delete(key);

        return b;

    }

    /**
     * 获取需要结息的记录
     */
    @Override
    public List<Trading> findTakeheartTrading(int pid) {
        TradingExample example = new TradingExample();
        example.createCriteria().andPIdEqualTo(pid).andStatusEqualTo(1);
        return tradingMapper.selectByExample(example);
    }

    /**
     * 更改利息类别状态
     * @return
     */
    @Override
    public boolean updateTakeheartCategoryStatus(int cid, String name) {

        TakeheartCategoryExample example = new TakeheartCategoryExample();
        example.createCriteria().andNameEqualTo(name).andIdNotEqualTo(cid);
        TakeheartCategory takeheartCategory = new TakeheartCategory();
        takeheartCategory.setStatus(ResultNumber.THREE.getNumber());

        int result = takeheartCategoryMapper.updateByExampleSelective(takeheartCategory, example);
        if (result > ResultNumber.ZERO.getNumber()) {
            takeheartCategory.setStatus(ResultNumber.TWO.getNumber());
            example.clear();
            example.createCriteria().andIdEqualTo(cid);
            takeheartCategoryMapper.updateByExampleSelective(takeheartCategory, example);
            return true;
        }
        return false;
    }

    /**
     * 获取>224天的随心取记录
     */
    @Override
    public List<Trading> findTakeheartValueDtTrading() {
        return tradingMapper.findTakeheartValueDtTrading();
    }

    /**
     * 获取用户的随心取redis记录，查看是否有正在处理的交易
     * 
     * @return
     */
    @Override
    public boolean getRedisTakeHeart(String uid) {
        boolean b = true;
        String key = AppCons.TAKE_HEART + uid;
        String value = (String) redisDao.get(key);
        if (value != null) {
            b = false;
        }
        return b;
    }

    /**
     * 获取随心取利率
     * 
     * @return
     */
    @Override
    public Map<String, Object> findTakeheartFixRate(Map<String, Object> fixRateParams) {
        return takeheartFixRateMapper.getTakeHeartFixRate(fixRateParams);
    }

    /**
     * 计息操作
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void takeheartInterest(Trading trading, BigDecimal interest, Date reteDate) {
        // 插入计息流水记录
        TakeheartTransactionFlow record = new TakeheartTransactionFlow();
        record.setuId(trading.getuId());
        record.setpId(trading.getpId());
        record.settId(trading.getId());
        record.setType(2);
        record.setState(1);
        record.setOperateTime(new Date());
        record.setInterest(interest);
        int result = takeheartTransactionTlowMapper.insertSelective(record);
        if (result < 1) {
            throw new DataUnCompleteException("保存随心取计息流水错误");
        }

        //更新trading收益和总金额
        Trading td = new Trading();
        td.setId(trading.getId());
        td.setFinancialMoney(trading.getFinancialMoney().add(interest));
        td.setInterest(interest);
        td.setAllInterest(trading.getAllInterest().add(interest));
        td.setLastValueDt(reteDate);
        result = tradingMapper.updateByPrimaryKeySelective(td);
        if (result < 1) {
            throw new DataUnCompleteException("更新trading收益和总金额错误");
        }

        // 更新账户总额加上付息金额
        UsersAccountExample uaExample = new UsersAccountExample();
        uaExample.createCriteria().andUIdEqualTo(trading.getuId());
        UsersAccount us = usersAccountMapper.selectByUid(trading.getuId());
        UsersAccount ua = new UsersAccount();
        ua.setTotalAsset(us.getTotalAsset().add(interest));
        ua.setTotalFinance(us.getTotalFinance().add(interest));
        ua.setIncome(us.getIncome().add(interest));
        result = usersAccountMapper.updateByExampleSelective(ua, uaExample);
        if (result < 1) {
            throw new DataUnCompleteException("更新账户总额加上付息金额错误");
        }

        //插入交易流水表付息记录
        AccountFlow af = new AccountFlow();
        af.settId(trading.getId());
        af.setaId(us.getId());
        af.setOperateTime(new Date());
        af.setMoney(interest);
        af.setStatus(AppCons.ACCOUNT_FLOW_FINISH);
        af.setNote("随心取付息");
        af.setType(AppCons.ACCOUNT_FLOW_TYPE_FINANCIAL);
        af.setBalance(us.getBalance());
        af.setFrozenMoney(us.getFrozenMoney());
        af.setPlatform(0);
        result = accountFlowMapper.insertSelective(af);
        if (result < 1) {
            throw new DataUnCompleteException("插入交易流水表付息记录错误");
        }
    }

}
