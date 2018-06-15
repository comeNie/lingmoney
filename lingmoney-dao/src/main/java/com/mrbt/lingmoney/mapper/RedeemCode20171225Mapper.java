package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.RedeemCode20171225;
import com.mrbt.lingmoney.model.RedeemCode20171225Example;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RedeemCode20171225Mapper {
    /**
     *  根据指定的条件获取数据库记录数,redeem_code_20171225
     *
     * @param example
     */
    long countByExample(RedeemCode20171225Example example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,redeem_code_20171225
     *
     * @param example
     */
    int deleteByExample(RedeemCode20171225Example example);

    /**
     *  根据主键删除数据库的记录,redeem_code_20171225
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,redeem_code_20171225
     *
     * @param record
     */
    int insert(RedeemCode20171225 record);

    /**
     *  动态字段,写入数据库记录,redeem_code_20171225
     *
     * @param record
     */
    int insertSelective(RedeemCode20171225 record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,redeem_code_20171225
     *
     * @param example
     */
    List<RedeemCode20171225> selectByExample(RedeemCode20171225Example example);

    /**
     *  根据指定主键获取一条数据库记录,redeem_code_20171225
     *
     * @param id
     */
    RedeemCode20171225 selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,redeem_code_20171225
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") RedeemCode20171225 record, @Param("example") RedeemCode20171225Example example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,redeem_code_20171225
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") RedeemCode20171225 record, @Param("example") RedeemCode20171225Example example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,redeem_code_20171225
     *
     * @param record
     */
    int updateByPrimaryKeySelective(RedeemCode20171225 record);

    /**
     *  根据主键来更新符合条件的数据库记录,redeem_code_20171225
     *
     * @param record
     */
    int updateByPrimaryKey(RedeemCode20171225 record);
    
    /**
     * 增加领宝个数
     * @param id
     * @param snum
     */
	void appendLingbao(Map<String, Object> map);
}