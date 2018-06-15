package com.mrbt.lingmoney.mapper;

import java.util.List;

import com.mrbt.lingmoney.model.HxRedPacketVo;
import com.mrbt.lingmoney.utils.PageInfo;

public interface HxRedPacketCustomMapper {
    
	List<HxRedPacketVo> findBycondition(PageInfo pageInfo);
	
	int countBycondition(PageInfo pageInfo);
}