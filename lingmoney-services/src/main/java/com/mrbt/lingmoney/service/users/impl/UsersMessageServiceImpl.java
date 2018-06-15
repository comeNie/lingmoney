package com.mrbt.lingmoney.service.users.impl;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.mapper.UserUnionInfoMapper;
import com.mrbt.lingmoney.mapper.UsersMessageMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.UsersMessage;
import com.mrbt.lingmoney.model.UsersMessageExample;
import com.mrbt.lingmoney.service.users.UsersMessageService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年5月9日 下午5:58:52
 * @version 1.0
 * @description
 **/
@Service
public class UsersMessageServiceImpl implements UsersMessageService {
	@Autowired
	private UsersMessageMapper usersMessageMapper;
	@Autowired
	private UserUnionInfoMapper userUnionInfoMapper;
    @Autowired
    private UsersPropertiesMapper usersPropertiesMapper;

	@Override
	public void packageUserMessageList(ModelMap model, String uid, Integer pageNo) {
		GridPage<UsersMessage> gridPage = new GridPage<UsersMessage>();

		UsersMessageExample umExample = new UsersMessageExample();
		umExample.createCriteria().andUIdEqualTo(uid);
		umExample.setOrderByClause("time desc");
		PageInfo pi = new PageInfo(pageNo, AppCons.DEFAULT_PAGE_SIZE);
		umExample.setLimitStart(pi.getFrom());
		umExample.setLimitEnd(pi.getSize());

		gridPage.setRows(usersMessageMapper.selectByExample(umExample));
		gridPage.setTotal(usersMessageMapper.countByExample(umExample));

		model.addAttribute("gridPage", gridPage);
		model.addAttribute("pageNo", pi.getNowpage());
		model.addAttribute("pageSize", AppCons.DEFAULT_PAGE_SIZE);
		model.addAttribute("totalSize", gridPage.getTotal());
		model.addAttribute("unreadSize", countByUnread(uid));
	}

	@Override
	public Integer countByUnread(String uid) {
		UsersMessageExample umExample = new UsersMessageExample();
		umExample.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(0);
		return (int) usersMessageMapper.countByExample(umExample);
	}

	@Override
	public int checkMessageDetail(ModelMap modelMap, Integer id, String uid) {
		// 根据ID查询信息
		UsersMessage message = usersMessageMapper.selectByPrimaryKey(id);
		int unread = 0;
		// 如果未读,更新该信息状态
		if (message != null && message.getStatus() == 0) {
			message.setStatus(1);
			usersMessageMapper.updateByPrimaryKeySelective(message);
			unread = getUserUnReadSession(uid);
		}

		modelMap.addAttribute("message", message);

		return unread;
	}

	@Override
	public int deleteAndUpdateSession(String ids, String uid) {
		String[] idStr = ids.split("_");
		for (String mid : idStr) {
			usersMessageMapper.deleteByPrimaryKey(NumberUtils.toInt(mid));
		}
		return getUserUnReadSession(uid);
	}

	@Override
	public int updateMessageReadStatus(String ids, String uid, int status) {
		String[] idStr = ids.split("_");
		for (String mid : idStr) {
			UsersMessage usersMessage = usersMessageMapper.selectByPrimaryKey(NumberUtils.toInt(mid));
			if (null != usersMessage) {
				usersMessage.setStatus(status);
				usersMessageMapper.updateByPrimaryKeySelective(usersMessage);
			}
		}

		return getUserUnReadSession(uid);
	}

	/**
	 * 获取用户未读信息条数
	 * 
	 * @param uid 用户id
	 * @return 未读信息条数
	 */
	private int getUserUnReadSession(String uid) {
		// 计算未读站内信,更新session信息
		UsersMessageExample umExample = new UsersMessageExample();
		umExample.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(0);
		int unRead = (int) usersMessageMapper.countByExample(umExample);
		return unRead;
	}

    @Override
    public PageInfo listMessageByUid(String uid, Integer status, Integer pageNo, Integer pageSize) {
        PageInfo pageInfo = new PageInfo(pageNo, pageSize);

        UsersMessageExample umExample = new UsersMessageExample();
        if (!StringUtils.isEmpty(status)) {
            umExample.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(status);
        } else {
            umExample.createCriteria().andUIdEqualTo(uid);
        }
        umExample.setOrderByClause("status desc, time desc");
        umExample.setLimitStart(pageInfo.getFrom());
        umExample.setLimitEnd(pageInfo.getSize());

        pageInfo.setTotal((int) usersMessageMapper.countByExample(umExample));
        if (pageInfo.getTotal() > 0) {
            pageInfo.setRows(usersMessageMapper.selectByExample(umExample));
            pageInfo.setResultInfo(ResultInfo.SUCCESS);

        } else {
            pageInfo.setResultInfo(ResultInfo.NO_DATA);
        }

        return pageInfo;
    }

    @Override
    public PageInfo getUserMessageDetail(Integer id, String uid) {
        PageInfo pageInfo = new PageInfo();
        UsersMessageExample example = new UsersMessageExample();
        example.createCriteria().andUIdEqualTo(uid).andIdEqualTo(id);

        List<UsersMessage> list = usersMessageMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            UsersMessage usersMessage = list.get(0);
            pageInfo.setResultInfo(ResultInfo.SUCCESS);
            pageInfo.setObj(usersMessage);
            // 如果未读 更新状态为已读
            if (usersMessage.getStatus() == 0) {
                UsersMessage record = new UsersMessage();
                record.setId(usersMessage.getId());
                usersMessage.setStatus(1);
                usersMessageMapper.updateByPrimaryKeySelective(record);
            }

        } else {
            pageInfo.setResultInfo(ResultInfo.NO_DATA);
        }

        return pageInfo;
    }

}
