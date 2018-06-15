package com.mrbt.lingmoney.mobile.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.info.InfoNoticeService;
import com.mrbt.lingmoney.service.users.UsersMessageService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
  *消息中心
  *@author yiban
  *@date 2018年3月15日 下午4:12:54
  *@version 1.0
 **/
@Controller
@RequestMapping("/usersMessage")
public class UsersMessageController extends BaseController {

    @Autowired
    private UsersMessageService usersMessageService;
    @Autowired
    private InfoNoticeService infoNoticeService;

    /**
     * 获取活动消息（站内公告 info_notice）、系统消息（站内信 users_message）
     * 
     * @author yiban
     * @date 2018年3月15日 下午5:06:31
     * @version 1.0
     * @param type 1活动消息2系统消息
     * @param token token
     * @param status 0未读 1已读（针对系统消息）
     * @param pageNo 当前页数
     * @param pageSize 分页条数
     * @return pageInfo pageInfo
     *
     */
    @RequestMapping(value = "/listMessageInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object listMessageInfo(Integer type, String token, Integer status, Integer pageNo, Integer pageSize) {
        PageInfo pageInfo = new PageInfo();

        if (type == null) {
            type = 1;
        }

        if (type.intValue() == 1) {
            // 活动消息
            pageInfo = infoNoticeService.queryNoticeList(pageNo, pageSize);

        } else {
            // 系统消息
            String uid = getUid(AppCons.TOKEN_VERIFY + token);
            if (!StringUtils.isEmpty(uid)) {
                pageInfo = usersMessageService.listMessageByUid(uid, status, pageNo, pageSize);

            } else {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            }
        }

        return pageInfo;
    }

    /**
     * 查看详情 站内信自动变更状态（未读 --》 已读）
     * 
     * @author yiban
     * @date 2018年3月15日 下午5:03:31
     * @version 1.0
     * @param id id
     * @param type 1活动消息2系统消息
     * @param token token
     * @return pageinfo pageinfo
     *
     */
    @RequestMapping(value = "/getMessageInfoDetail", method = RequestMethod.POST)
    @ResponseBody
    public Object getMessageInfoDetail(Integer id, Integer type, String token) {
        PageInfo pageInfo = new PageInfo();

        if (type == null) {
            type = 1;
        }

        if (type.intValue() == 1) {
            // 活动消息
            pageInfo = infoNoticeService.queryNoticeDetail(id);

        } else {
            // 系统消息
            String uid = getUid(AppCons.TOKEN_VERIFY + token);
            if (!StringUtils.isEmpty(uid)) {
                pageInfo = usersMessageService.getUserMessageDetail(id, uid);

            } else {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            }
        }

        return pageInfo;
    }

}
