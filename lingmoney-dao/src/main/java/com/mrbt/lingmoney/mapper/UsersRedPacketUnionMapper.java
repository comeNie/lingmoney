package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UsersRedPacketUnion;
import com.mrbt.lingmoney.model.UsersRedPacketUnionExample;

import java.util.List;

public interface UsersRedPacketUnionMapper {
    /**
     *  根据指定的条件获取数据库记录数,users_red_packet
     *
     * @param example
     */
    int countByExample(UsersRedPacketUnionExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,users_red_packet
     *
     * @param example
     */
    List<UsersRedPacketUnion> selectByExample(UsersRedPacketUnionExample example);

    /**
     *  根据指定主键获取一条数据库记录,users_red_packet
     *
     * @param id
     */
    UsersRedPacketUnion selectByPrimaryKey(Integer id);
}