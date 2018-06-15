package com.mrbt.lingmoney.service.users;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 *@author syb
 *@date 2017年5月9日 下午5:58:28
 *@version 1.0
 *@description 站内信
 **/
public interface UsersMessageService {

	/**
	 * 查询首页分页数据
	 * @param model 
	 * @param uid 用户id
	 * @param pageNo 分页页数
	 */
	void packageUserMessageList(ModelMap model, String uid, Integer pageNo);

	/**
	 * 查询未读消息记录数
	 * @param uid 用户id
	 * @return integer 未读信息条数
	 */
	Integer countByUnread(String uid);

	/**
	 * 查看消息记录详情,如果未读,更新状态,更新session
	 * @param modelMap 
	 * @param id 消息id
	 * @param uid 用户id
	 * @return int数据库操作结果
	 */
	int checkMessageDetail(ModelMap modelMap, Integer id, String uid);

	/**
	 * 删除消息记录,更新session
	 * @param ids 消息id，多个用英文逗号分隔
	 * @param uid 用户id
	 * @return int数据库操作结果
	 */
	int deleteAndUpdateSession(String ids, String uid);

	/**
	 * 设置站内信状态（已读/未读）,并更新session
	 * @param ids 消息id，多个用英文逗号分隔
	 * @param uid 用户id
	 * @param status 0 未读  1已读
	 * @return int数据库操作结果
	 */
	int updateMessageReadStatus(String ids, String uid, int status);

    /**
     * 查询用户站内信
     * 
     * @author yiban
     * @date 2018年3月15日 下午4:37:21
     * @version 1.0
     * @param uid 用户id
     * @param status 0 未读 1已读
     * @param pageNo 当前页
     * @param pageSize 每页条数
     * @return pageinfo pageinfo
     *
     */
    PageInfo listMessageByUid(String uid, Integer status, Integer pageNo, Integer pageSize);

    /**
     * 获取用户消息详情，如果未读，更新状态为已读（mobile）
     * 
     * @author yiban
     * @date 2018年3月15日 下午4:57:10
     * @version 1.0
     * @param id id
     * @param uid 用户id
     * @return
     *
     */
    PageInfo getUserMessageDetail(Integer id, String uid);

}
