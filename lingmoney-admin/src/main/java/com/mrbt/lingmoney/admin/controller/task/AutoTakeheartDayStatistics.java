package com.mrbt.lingmoney.admin.controller.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.mapper.TakeheartUserStatisticsMapper;

/**
  *随心取当日用户库存统计
  *@author yiban
  *@date 2018年1月10日 上午9:08:25
  *@version 1.0
 **/
@Component("autoTakeheartDayStatistics")
public class AutoTakeheartDayStatistics {
    private static final Logger LOG = LogManager.getLogger(AutoTakeheartDayStatistics.class);

    @Autowired
    private TakeheartUserStatisticsMapper takeheartUserStatisticsMapper;

    public void takeheartDayStatistics() {
        int result = takeheartUserStatisticsMapper.dayStatistics();

        LOG.info("随心取当日库存统计完毕， 共{}条数据", result);
    }

    public static void main(String[] args) {
        new AutoTakeheartDayStatistics().takeheartDayStatistics();
    }

}
