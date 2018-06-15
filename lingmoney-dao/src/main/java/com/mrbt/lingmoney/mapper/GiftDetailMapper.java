package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.GiftDetail;
import com.mrbt.lingmoney.model.GiftDetailExample;
import com.mrbt.lingmoney.model.admin.GiftDetailVo;
import com.mrbt.lingmoney.utils.PageInfo;

public interface GiftDetailMapper {
    /**
     *  根据指定的条件获取数据库记录数,gift_detail
     *
     * @param example
     */
    long countByExample(GiftDetailExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,gift_detail
     *
     * @param example
     */
    int deleteByExample(GiftDetailExample example);

    /**
     *  根据主键删除数据库的记录,gift_detail
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,gift_detail
     *
     * @param record
     */
    int insert(GiftDetail record);

    /**
     *  动态字段,写入数据库记录,gift_detail
     *
     * @param record
     */
    int insertSelective(GiftDetail record);

    /**
     * ,gift_detail
     *
     * @param example
     */
    List<GiftDetail> selectByExampleWithBLOBs(GiftDetailExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,gift_detail
     *
     * @param example
     */
    List<GiftDetail> selectByExample(GiftDetailExample example);

    /**
     *  根据指定主键获取一条数据库记录,gift_detail
     *
     * @param id
     */
    GiftDetail selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,gift_detail
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") GiftDetail record, @Param("example") GiftDetailExample example);

    /**
     * ,gift_detail
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") GiftDetail record, @Param("example") GiftDetailExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,gift_detail
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") GiftDetail record, @Param("example") GiftDetailExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,gift_detail
     *
     * @param record
     */
    int updateByPrimaryKeySelective(GiftDetail record);

    /**
     * ,gift_detail
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(GiftDetail record);

    /**
     *  根据主键来更新符合条件的数据库记录,gift_detail
     *
     * @param record
     */
    int updateByPrimaryKey(GiftDetail record);

    /**
     * 根据用户ID查询用户礼品
     * @param uid
     * @return
     */
	List<Map<String,Object>> selectGiftDetailByUid(Map<String, Object> params);
	int selectGiftDetailByUidCount(String uid);

	List<GiftDetailVo> findByCondition(PageInfo pageInfo);

	int findCountByCondition(PageInfo pageInfo);
}