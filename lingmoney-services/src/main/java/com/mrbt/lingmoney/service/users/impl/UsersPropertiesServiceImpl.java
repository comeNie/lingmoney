package com.mrbt.lingmoney.service.users.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.mapper.UserUnionInfoMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.UserUnionInfo;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.service.users.UsersPropertiesService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年5月9日 上午9:19:24
 * @version 1.0
 * @description
 **/
@Service
public class UsersPropertiesServiceImpl implements UsersPropertiesService {
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private UserUnionInfoMapper unionInfoMapper;

	@Override
	public UsersProperties queryByUid(String uid) {
		return usersPropertiesMapper.selectByuId(uid);
	}

	@Override
	public PageInfo update(UsersProperties usersProperties, String uid) {
		PageInfo pi = new PageInfo();
		UsersProperties up = usersPropertiesMapper.selectByuId(uid);
		if (up != null) {
			// 如果有email，验证email
			String email = usersProperties.getEmail();
			if (StringUtils.isNotBlank(email)) {
				UsersPropertiesExample example = new UsersPropertiesExample();
				example.createCriteria().andEmailEqualTo(email);
				int result = (int) usersPropertiesMapper.countByExample(example);
				if (result > 0) {
                    pi.setCode(ResultInfo.SIGN_SUCCESS.getCode());
					pi.setMsg("该email已存在");
					return pi;
				}
			}
			// 更新
			usersProperties.setId(up.getId());
			int i = usersPropertiesMapper.updateByPrimaryKeySelective(usersProperties);
			if (i > 0) {
				UserUnionInfo userinfo = unionInfoMapper.selectByUid(uid);
				if (userinfo == null) {
                    pi.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
					pi.setMsg("用户信息为空");
					return pi;
				}
                pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg("更新成功");
				// 刷新session
				pi.setObj(userinfo);
			} else {
                pi.setCode(ResultInfo.SIGN_NOT_SUCCESS.getCode());
				pi.setMsg("更新失败");
			}
		} else {
            pi.setCode(ResultInfo.MODIFY_REJECT.getCode());
			pi.setMsg("信息有误,请刷新后重试");
		}
		return pi;
	}
}
