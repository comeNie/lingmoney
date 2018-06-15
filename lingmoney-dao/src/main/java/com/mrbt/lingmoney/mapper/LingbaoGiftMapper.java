package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.LingbaoGift;
import com.mrbt.lingmoney.model.LingbaoGiftExample;
import com.mrbt.lingmoney.model.LingbaoGiftWithBLOBs;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LingbaoGiftMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingbao_gift
     *
     * @param example
     */
    long countByExample(LingbaoGiftExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingbao_gift
     *
     * @param example
     */
    int deleteByExample(LingbaoGiftExample example);

    /**
     *  根据主键删除数据库的记录,lingbao_gift
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,lingbao_gift
     *
     * @param record
     */
    int insert(LingbaoGiftWithBLOBs record);

    /**
     *  动态字段,写入数据库记录,lingbao_gift
     *
     * @param record
     */
    int insertSelective(LingbaoGiftWithBLOBs record);

    /**
     * ,lingbao_gift
     *
     * @param example
     */
    List<LingbaoGiftWithBLOBs> selectByExampleWithBLOBs(LingbaoGiftExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingbao_gift
     *
     * @param example
     */
    List<LingbaoGift> selectByExample(LingbaoGiftExample example);

    /**
     *  根据指定主键获取一条数据库记录,lingbao_gift
     *
     * @param id
     */
    LingbaoGiftWithBLOBs selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingbao_gift
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingbaoGiftWithBLOBs record, @Param("example") LingbaoGiftExample example);

    /**
     * ,lingbao_gift
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") LingbaoGiftWithBLOBs record, @Param("example") LingbaoGiftExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingbao_gift
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingbaoGift record, @Param("example") LingbaoGiftExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,lingbao_gift
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LingbaoGiftWithBLOBs record);

    /**
     * ,lingbao_gift
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(LingbaoGiftWithBLOBs record);

    /**
     *  根据主键来更新符合条件的数据库记录,lingbao_gift
     *
     * @param record
     */
    int updateByPrimaryKey(LingbaoGift record);
    
    /**
     * 根据条件查询（只查询页面展示用字段）
     * @param example
     * @return
     */
    List<Map<String, Object>> selectViewColumnByExample(LingbaoGiftExample example);

    /**
     * 根据ID查询指定字段数据
     * @param id
     * @return
     */
	LingbaoGift selectColumnsById(int id);

	/**
	 * 根据条件查询礼品
	 * @param typeId 类别id
	 * @param isRecommend 是否推荐
	 * @return
	 */
	List<LingbaoGift> queryGiftListWithCondition(Map<String,Object> map);
	
	/**
	 * 我的领地--更多页面--》筛选查询
	 * @param params
	 * @return
	 */
	List<LingbaoGift> selectGiftByTypeRange(Map<String, Object> params);

	int countGiftByTypeRange(Map<String, Object> params);
	
	void insertBeach(List<LingbaoGift> list);
	
	List<LingbaoGift> selectByExampleVo(LingbaoGiftExample example);
}