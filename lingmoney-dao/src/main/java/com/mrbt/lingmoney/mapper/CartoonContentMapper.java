package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.CartoonContent;
import com.mrbt.lingmoney.model.CartoonContentExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CartoonContentMapper {
    /**
     *  根据指定的条件获取数据库记录数,cartoon_content
     *
     * @param example
     */
    long countByExample(CartoonContentExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,cartoon_content
     *
     * @param example
     */
    int deleteByExample(CartoonContentExample example);

    /**
     *  根据主键删除数据库的记录,cartoon_content
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,cartoon_content
     *
     * @param record
     */
    int insert(CartoonContent record);

    /**
     *  动态字段,写入数据库记录,cartoon_content
     *
     * @param record
     */
    int insertSelective(CartoonContent record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,cartoon_content
     *
     * @param example
     */
    List<CartoonContent> selectByExample(CartoonContentExample example);

    /**
     *  根据指定主键获取一条数据库记录,cartoon_content
     *
     * @param id
     */
    CartoonContent selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,cartoon_content
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") CartoonContent record, @Param("example") CartoonContentExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,cartoon_content
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") CartoonContent record, @Param("example") CartoonContentExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,cartoon_content
     *
     * @param record
     */
    int updateByPrimaryKeySelective(CartoonContent record);

    /**
     *  根据主键来更新符合条件的数据库记录,cartoon_content
     *
     * @param record
     */
    int updateByPrimaryKey(CartoonContent record);

    /**
     * 根据类型id查询所有相关漫画id
     * @param cid
     * @return
     */
	List<Integer> listCartoonContentId(Integer cid);
}