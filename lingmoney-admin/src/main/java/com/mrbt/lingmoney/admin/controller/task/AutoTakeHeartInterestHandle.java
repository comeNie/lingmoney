package com.mrbt.lingmoney.admin.controller.task;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.base.CommonMethodService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.admin.service.takeHeart.TakeHeartInterestService;
import com.mrbt.lingmoney.admin.utils.DateUtils;
import com.mrbt.lingmoney.model.TakeheartCategory;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.utils.ProductUtils;

/**
 * 随心取计息
 *
 */
@Component("AutoTakeHeartInterestHandle")
public class AutoTakeHeartInterestHandle {

    private static final Logger LOGGER = LogManager.getLogger(AutoTakeHeartInterestHandle.class);

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CommonMethodService commonMethodService;
    @Autowired
    private TakeHeartInterestService takeHeartInterestService;

    public void handle() {

        //查看是否有新发布利率，如果有结息
        List<TakeheartCategory> list = takeHeartInterestService.findTakeheartCategory();
        LOGGER.info("随心取计息开始.");

        if (list.size() > 0) {
            // 跑批开始标记到redis，不允许交易
            takeHeartInterestService.saveRedisTakeHeart();

            // 获取所有随心取交易记录
            List<Trading> takeheartTradingList = takeHeartInterestService.findTakeheartTrading(391);

            this.interestOperation(takeheartTradingList);

            for (int i = 0; i < list.size(); i++) {
                TakeheartCategory takeheartCategory = list.get(i);
                //将原利息类别更新为作废，现利息类别记录更新为已使用
                boolean b = takeHeartInterestService.updateTakeheartCategoryStatus(takeheartCategory.getId(),
                        takeheartCategory.getName());

                if (b) {
                    LOGGER.info("更改利率类别状态成功:cid=" + takeheartCategory.getId());
                } else {
                    LOGGER.info("更改利率类别状态失败:cid=" + takeheartCategory.getId());

                }
            }
            // 每日跑批结束标记到redis，允许交易
            takeHeartInterestService.deleteRedisTakeHeart();

            LOGGER.info("随心取计息结束.");
            System.out.println("随心取计息结束.");
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int day = calendar.get(5);

            int startDate = ProductUtils.getIntContent("takeHeart_start_date");

            if (day == startDate) {
                LOGGER.info("随心取计息开始,22日结息.");
                System.out.println("随心取计息开始,22日结息.");

                // 跑批开始标记到redis，不允许交易
                takeHeartInterestService.saveRedisTakeHeart();

                // 获取所有随心取交易记录
                List<Trading> takeheartTradingList = takeHeartInterestService.findTakeheartValueDtTrading();

                this.interestOperation(takeheartTradingList);

                // 每日跑批结束标记到redis，允许交易
                takeHeartInterestService.deleteRedisTakeHeart();

                LOGGER.info("随心取计息结束,22日结息.");
                System.out.println("随心取计息结束,22日结息.");

            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
        }

        LOGGER.info("随心取计息结束.");
    }

    /**
     * 计息操作
     */
    public void interestOperation(List<Trading> takeheartTradingList) {

        BigDecimal interest = null; // 当日利息

        try {
            if (takeheartTradingList == null || takeheartTradingList.size() == 0) {

                LOGGER.info("随心取没有交易记录.");
                return;
            }

            for (int i = 0; i < takeheartTradingList.size(); i++) {

                try {
                    Trading trading = takeheartTradingList.get(i);

                    // 获取用户的随心取redis记录，查看是否有正在处理的交易
                    boolean b = takeHeartInterestService.getRedisTakeHeart(trading.getuId());

                    //redis中没有交易记录
                    if (b) {

                        //Date date = DateUtils.addDay(new Date(),-1);

                        Date reteDate = DateUtils.getTradeDate(new Date(), commonMethodService.findHoliday());

                        //System.out.println("------------reteDate:"+reteDate);

                        //获取购买产品以来的总时长，用于计算利息
                        int day = DateUtils.dateDiff(trading.getValueDt(), reteDate);

                        Map<String, Object> fixRateParams = new HashMap<String, Object>();
                        fixRateParams.put("rDay", day);
                        fixRateParams.put("financialMoney", trading.getFinancialMoney());
                        //获取随心取利率											
                        Map<String, Object> takeheartFixRate = takeHeartInterestService.findTakeheartFixRate(fixRateParams);

                        if (takeheartFixRate == null) {
                            LOGGER.info("未找到匹配的随心取利率记录.");

                        } else {
                            //获取计息的时长
                            int interestDay = DateUtils.dateDiff(trading.getLastValueDt(), reteDate);

                            System.out.println("------计息天数-------interestDay:" + interestDay);

                            if (interestDay > 0) {
                                interest = ProductUtils.countInterest(trading.getFinancialMoney(), interestDay,
                                        (BigDecimal) takeheartFixRate.get("rate"));
                                takeHeartInterestService.takeheartInterest(trading, interest, reteDate);

                                LOGGER.info("tid:" + trading.getId() + "uid:" + trading.getuId() + "结息完成，" + "计息天数为:"
                                        + interestDay + ",计息累计天数为:" + day + ",利率为:" + takeheartFixRate.get("rate")
                                        + "结算金额为:" + trading.getFinancialMoney() + ",利息为:" + interest);

                            } else {

                                LOGGER.info("距离上次结息时长为0,tid:" + trading.getId() + "uid:" + trading.getuId());

                            }

                        }
                    } else {
                        LOGGER.info("redis中有正在处理的交易记录,tid:" + trading.getId() + "uid:" + trading.getuId());
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
