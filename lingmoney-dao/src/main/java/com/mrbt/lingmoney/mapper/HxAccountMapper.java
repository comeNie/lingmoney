package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HxAccountMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_account
     *
     * @param example
     */
    long countByExample(HxAccountExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_account
     *
     * @param example
     */
    int deleteByExample(HxAccountExample example);

    /**
     *  根据主键删除数据库的记录,hx_account
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_account
     *
     * @param record
     */
    int insert(HxAccount record);

    /**
     *  动态字段,写入数据库记录,hx_account
     *
     * @param record
     */
    int insertSelective(HxAccount record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_account
     *
     * @param example
     */
    List<HxAccount> selectByExample(HxAccountExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_account
     *
     * @param id
     */
    HxAccount selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_account
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxAccount record, @Param("example") HxAccountExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_account
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxAccount record, @Param("example") HxAccountExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_account
     *
     * @param record
     */
    int updateByPrimaryKeySelective(HxAccount record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_account
     *
     * @param record
     */
    int updateByPrimaryKey(HxAccount record);

    /**
     * 根据uId查询E账户信息
     * @param uId
     * @return
     */
	HxAccount selectByUid(String uId);
}