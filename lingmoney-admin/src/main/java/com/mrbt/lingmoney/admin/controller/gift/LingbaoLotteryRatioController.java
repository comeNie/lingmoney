package com.mrbt.lingmoney.admin.controller.gift;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.gift.LingbaoLotteryRatioService;
import com.mrbt.lingmoney.model.LingbaoLotteryRatio;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 我的领地抽奖比例
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("/gift/lingbaoLotteryRatio")
public class LingbaoLotteryRatioController {

	private Logger log = MyUtils.getLogger(LingbaoLotteryRatioController.class);

	@Autowired
	private LingbaoLotteryRatioService lingbaoLotteryRatioService;

	/**
	 * 更改我的领地抽奖比例状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			boolean flag = lingbaoLotteryRatioService.changeStatus(id);
			if (flag) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地抽奖比例，更改我的领地抽奖比例状态，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 添加或修改我的领地抽奖比例
	 * 
	 * @param vo
	 *            我的领地抽奖比例
	 * @return 分页实体类
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(LingbaoLotteryRatio vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo.getId() == null || vo.getId() <= 0) {
				vo.setStatus(0);
				lingbaoLotteryRatioService.save(vo);
			} else {
				lingbaoLotteryRatioService.updateByPrimaryKeySelective(vo);
				LingbaoLotteryRatio record = lingbaoLotteryRatioService.findById(vo.getId());
				//如果选择的是礼品，则设置文字为空
				if (vo.getGiftId() != null) {
					record.setWord(null);
				} else { //如果未选择礼品，则设置礼品为空，设置文字为传递来的文字
					record.setGiftId(null);
					record.setWord(vo.getWord());
				}
				lingbaoLotteryRatioService.updateByPrimaryKey(record);
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地抽奖比例，添加或修改我的领地抽奖比例，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询我的领地抽奖比例列表
	 * 
	 * @param vo
	 *            我的领地抽奖比例
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(LingbaoLotteryRatio vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		Map<String, Object> condition = new HashMap<String, Object>();
		try {
			if (vo != null) {
				// 活动id
				if (vo.getTypeId() != null) {
					condition.put("typeId", vo.getTypeId());
				}
				// 礼品id
				if (vo.getGiftId() != null) {
					condition.put("giftId", vo.getGiftId());
				}
				// 状态
				if (vo.getStatus() != null) {
					condition.put("status", vo.getStatus());
				}
			}
			pageInfo.setCondition(condition);
			pageInfo = lingbaoLotteryRatioService.getList(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地抽奖比例，查询我的领地抽奖比例列表，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据主键删除我的领地抽奖比例
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				lingbaoLotteryRatioService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地抽奖比例，根据主键删除我的领地抽奖比例，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

}
