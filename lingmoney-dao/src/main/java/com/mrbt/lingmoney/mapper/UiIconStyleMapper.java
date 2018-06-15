package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UiIconStyle;
import com.mrbt.lingmoney.model.UiIconStyleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UiIconStyleMapper {
    /**
     *  根据指定的条件获取数据库记录数,ui_icon_style
     *
     * @param example
     */
    int countByExample(UiIconStyleExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,ui_icon_style
     *
     * @param example
     */
    int deleteByExample(UiIconStyleExample example);

    /**
     *  根据主键删除数据库的记录,ui_icon_style
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,ui_icon_style
     *
     * @param record
     */
    int insert(UiIconStyle record);

    /**
     *  动态字段,写入数据库记录,ui_icon_style
     *
     * @param record
     */
    int insertSelective(UiIconStyle record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,ui_icon_style
     *
     * @param example
     */
    List<UiIconStyle> selectByExample(UiIconStyleExample example);

    /**
     *  根据指定主键获取一条数据库记录,ui_icon_style
     *
     * @param id
     */
    UiIconStyle selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,ui_icon_style
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UiIconStyle record, @Param("example") UiIconStyleExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,ui_icon_style
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UiIconStyle record, @Param("example") UiIconStyleExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,ui_icon_style
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UiIconStyle record);

    /**
     *  根据主键来更新符合条件的数据库记录,ui_icon_style
     *
     * @param record
     */
    int updateByPrimaryKey(UiIconStyle record);
}