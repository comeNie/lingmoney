package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.HxRedPacketExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface HxRedPacketMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_red_packet
     *
     * @param example
     */
    long countByExample(HxRedPacketExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_red_packet
     *
     * @param example
     */
    int deleteByExample(HxRedPacketExample example);

    /**
     *  根据主键删除数据库的记录,hx_red_packet
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_red_packet
     *
     * @param record
     */
    int insert(HxRedPacket record);

    /**
     *  动态字段,写入数据库记录,hx_red_packet
     *
     * @param record
     */
    int insertSelective(HxRedPacket record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_red_packet
     *
     * @param example
     */
    List<HxRedPacket> selectByExample(HxRedPacketExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_red_packet
     *
     * @param id
     */
    HxRedPacket selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_red_packet
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxRedPacket record, @Param("example") HxRedPacketExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_red_packet
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxRedPacket record, @Param("example") HxRedPacketExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_red_packet
     *
     * @param record
     */
    int updateByPrimaryKeySelective(HxRedPacket record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_red_packet
     *
     * @param record
     */
    int updateByPrimaryKey(HxRedPacket record);

    /**
	 * 根据指定条件查找红包对象
	 * 
	 * @param map
	 * @return
	 */
	List<HxRedPacket> selectRedPacketByParams(Map<String, Object> map);
	
	/**
	 * 获取指定活动红包集合
	 * 
	 * @param actType
	 * @return
	 */
	List<Map<String, Object>> getHxRedPacketList(Map<String, Object> params);

	/**
	 * 产品成立送红包
	 *
	 * @author suyibo 2017年10月31日
	 * @param productId
	 *            产品id
	 */
	List<Map<String, Object>> selectUsersUsedRedPacketInfoList(Map<String, Object> params);
}