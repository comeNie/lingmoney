package com.mrbt.lingmoney.web.controller.users;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mrbt.lingmoney.model.UsersAsk;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsresDegree;
import com.mrbt.lingmoney.service.users.UsersAskService;
import com.mrbt.lingmoney.service.users.UsersPropertiesService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.users.UsresDegreeService;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.web.vo.users.UsersAskVo;

/**
 * 你问我答
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/usersAsk")
public class UsersAskController {
	private static final Logger LOG = LogManager.getLogger(UsersAskController.class);
	@Autowired
	private UsersAskService usersAskService;
	@Autowired
	private UsersPropertiesService usersPropertiesService;
	@Autowired
	private UsresDegreeService usresDegreeService;
	@Autowired
	private UsersService usersService;

	/**
	 * 你问我答列表
	 * 
	 * @param model
	 *            数据包装
	 * @param title
	 *            标题
	 * @param pageNo
	 *            当前页数
	 * @param status
	 *            状态
	 * @param type
	 *            类型
	 * @param hot
	 *            是否热门问题
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/list")
	public String list(ModelMap model, String title, Integer pageNo, Integer status, Integer type, Integer hot,
			HttpServletRequest request) {
		try {
			GridPage<UsersAsk> gridPage = usersAskService.packageListInfo(model, title, pageNo, status, type, hot);
			GridPage<UsersAskVo> viewGrid = new GridPage<UsersAskVo>();
			viewGrid.setTotal(gridPage.getTotal());

			if (gridPage.getRows().size() > 0) {
				List<UsersAskVo> rows = new ArrayList<UsersAskVo>();
				for (int i = 0; i < gridPage.getRows().size(); i++) {
					UsersAsk usersAsk = gridPage.getRows().get(i);
					String uId = usersAsk.getuId();
					UsersProperties usersProperties = usersPropertiesService.queryByUid(uId);
					UsersAskVo usersAskVo = new UsersAskVo();
					if (usersProperties != null) {
						int level = usersProperties.getLevel();
						UsresDegree usersDegree = usresDegreeService.queryById(level);
						usersAskVo.setDegreeName(usersDegree.getName());
						usersAskVo.setNickName(usersProperties.getNickName());

					} else {
						usersAskVo.setDegreeName("--");
						usersAskVo.setNickName("--");
					}

					usersAskVo.setId(usersAsk.getId());
					usersAskVo.setContent(usersAsk.getContent());
					usersAskVo.setKeyWord(usersAsk.getKeyword());
					usersAskVo.setTitle(usersAsk.getTitle());
					rows.add(usersAskVo);
				}

				viewGrid.setRows(rows);
			}

			model.addAttribute("gridPage", viewGrid);
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

		} catch (Exception e) {
			LOG.error("查询问答数据失败" + e);
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

		return "helpcenter/qAnda";
	}

	/**
	 * 查询问答详情
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @param id
	 *            问答id
	 * @return 返回信息
	 */
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String deatils(ModelMap model, HttpServletRequest request, Integer id) {
		if (!StringUtils.isEmpty(id)) {
			try {
				usersAskService.packageDetailInfo(model, id);
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

			} catch (Exception e) {
				LOG.error("查询问答详情错误" + e);
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

			return "helpcenter/qAndaDetail";

		} else {
			return String.valueOf(ResultParame.ResultInfo.NOT_FOUND.getCode());
		}
	}

	/**
	 * 你问我答，提交问题
	 * 
	 * @param newTitle
	 *            新标题
	 * @param newContent
	 *            新内容
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping(value = "/newAsk")
	public String newAsk(String newTitle, String newContent, HttpServletRequest request) {
		String uid = CommonMethodUtil.getUidBySession(request);

		if (!StringUtils.isEmpty(uid)) {
			try {
				usersAskService.save(uid, newTitle, newContent);

			} catch (Exception e) {
				LOG.error("你问我答，提交问题失败" + e);
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			LOG.info("用户提交问题失败，登录超时。" + uid);
			return "users/login";
		}

		return "redirect:/usersAsk/list.html";
	}

}
