package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;

public interface UsersRedPacketMapper {
	/**
	 * 根据指定的条件获取数据库记录数,users_red_packet
	 *
	 * @param example
	 */
	int countByExample(UsersRedPacketExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,users_red_packet
	 *
	 * @param example
	 */
	int deleteByExample(UsersRedPacketExample example);

	/**
	 * 根据主键删除数据库的记录,users_red_packet
	 *
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,users_red_packet
	 *
	 * @param record
	 */
	int insert(UsersRedPacket record);

	/**
	 * 动态字段,写入数据库记录,users_red_packet
	 *
	 * @param record
	 */
	int insertSelective(UsersRedPacket record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,users_red_packet
	 *
	 * @param example
	 */
	List<UsersRedPacket> selectByExample(UsersRedPacketExample example);

	/**
	 * 根据指定主键获取一条数据库记录,users_red_packet
	 *
	 * @param id
	 */
	UsersRedPacket selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,users_red_packet
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") UsersRedPacket record,
			@Param("example") UsersRedPacketExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,users_red_packet
	 *
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") UsersRedPacket record, @Param("example") UsersRedPacketExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,users_red_packet
	 *
	 * @param record
	 */
	int updateByPrimaryKeySelective(UsersRedPacket record);

	/**
	 * 根据主键来更新符合条件的数据库记录,users_red_packet
	 *
	 * @param record
	 */
	int updateByPrimaryKey(UsersRedPacket record);

	/**
	 * 获取用户所有符合类型的优惠券集合
	 * 
	 * @param actType
	 * @return
	 */
	List<UsersRedPacket> getCheckRedPacketList(Map<String, Object> map);

	/**
	 * 查询用户是否领取过，未领取过才能领取，不能重复领取
	 * 
	 * @param userId
	 * @param actType
	 * @param rpType
	 * @return
	 */
	List<String> findUserIsGetRedPacket(Map<String, Object> map);

    /**
     * 查询需要推送的红包信息
     * 查询条件：1.用户没有禁止推送， 2.到期时间三天后或者当天，3.友盟推送token不为null
     * @author yiban
     * @date 2018年3月22日 下午1:54:55
     * @version 1.0
     * @return map: ratenum 加息券数量; packetnum 红包数量; days 天数; umentoken 用户友盟token; uid 用户id; deviceType 1android 2 ios
     *
     */
    List<Map<String, Object>> listRedPacketPushMessageInfo();

	/**
	 * 批量插入
	 * 
	 * @param usersRedPacketList list
	 * @return 数据返回
	 */
	int insertBatch(@Param("usersRedPacketList") List<UsersRedPacket> usersRedPacketList);
}