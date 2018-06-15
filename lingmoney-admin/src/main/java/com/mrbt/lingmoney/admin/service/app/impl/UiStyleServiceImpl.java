package com.mrbt.lingmoney.admin.service.app.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.app.UiStyleService;
import com.mrbt.lingmoney.mapper.UiIconStyleMapper;
import com.mrbt.lingmoney.model.UiIconStyle;
import com.mrbt.lingmoney.model.UiIconStyleExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 *
 *@author syb
 *@date 2017年9月13日 下午3:05:01
 *@version 1.0
 **/
@Service
public class UiStyleServiceImpl implements UiStyleService {

	@Autowired
	private UiIconStyleMapper uiIconStyleMapper;
	@Autowired
	private FtpUtils ftpUtils;

	@Override
	public PageInfo listUiStyle(Integer page, Integer row, String batchNo, Integer status, String desc) {
		PageInfo pi = new PageInfo(page, row);

		UiIconStyleExample example = new UiIconStyleExample();
		UiIconStyleExample.Criteria criteria = example.createCriteria();
		if (!StringUtils.isEmpty(batchNo)) {
			criteria.andBatchNumberEqualTo(batchNo);
		}
		if (!StringUtils.isEmpty(status)) {
			criteria.andStatusEqualTo(status);
		}
		if (!StringUtils.isEmpty(desc)) {
			criteria.andDescriptionLike("%" + desc + "%");
		}

		example.setOrderByClause("id, group_name");
		example.setLimitStart(pi.getFrom());
		example.setLimitEnd(pi.getSize());

		List<UiIconStyle> list = uiIconStyleMapper.selectByExample(example);
		pi.setRows(list);
		pi.setTotal(0);

		if (list != null && list.size() > 0) {
			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg(ResultInfo.SUCCESS.getMsg());

			int total = uiIconStyleMapper.countByExample(example);
			pi.setTotal(total);

		} else {
			pi.setCode(ResultInfo.NO_DATA.getCode());
			pi.setMsg(ResultInfo.NO_DATA.getMsg());
		}

		return pi;
	}

	@Override
	public PageInfo saveOrUpdate(UiIconStyle uiIconStyle, MultipartFile file) {
		PageInfo pi = new PageInfo();

		if (uiIconStyle == null) {
			pi.setCode(ResultInfo.PARAMETER_ERROR.getCode());
			pi.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			return pi;
		}

		if (file != null) {
			String imgUrl = ftpUtils.uploadImages(file, "appUiImage", ftpUtils);
			if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
				uiIconStyle.setUrl(imgUrl);
			}
		}

		int result = 0;
		if (StringUtils.isEmpty(uiIconStyle.getId())) {
			if (!uiKeyIsOk(uiIconStyle)) {
				pi.setCode(ResultInfo.MODIFY_REJECT.getCode());
				pi.setMsg(ResultInfo.MODIFY_REJECT.getMsg());
				return pi;
			}
			result = uiIconStyleMapper.insertSelective(uiIconStyle);

		} else {
			// 修改时，如果当前key值不等于原key值，需要校验
			if (uiIconStyle.getUiKey() != null) {
				UiIconStyle orgiStyle = uiIconStyleMapper.selectByPrimaryKey(uiIconStyle.getId());
				if (orgiStyle.getUiKey() != null && !orgiStyle.getUiKey().equals(uiIconStyle.getUiKey())) {
					if (!uiKeyIsOk(uiIconStyle)) {
						pi.setCode(ResultInfo.MODIFY_REJECT.getCode());
						pi.setMsg(ResultInfo.MODIFY_REJECT.getMsg());
						return pi;
					}
				}
			}

			result = uiIconStyleMapper.updateByPrimaryKeySelective(uiIconStyle);
		}

		if (result > 0) {
			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg(ResultInfo.SUCCESS.getMsg());
		} else {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pi;
	}

	@Override
	public PageInfo batchUpdateStatus(String ids, Integer status) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(ids)) {
			pi.setCode(ResultInfo.PARAMETER_ERROR.getCode());
			pi.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			return pi;
		}

		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			UiIconStyle record = new UiIconStyle();
			record.setId(Integer.parseInt(idArray[i]));
			record.setStatus(status);
			uiIconStyleMapper.updateByPrimaryKeySelective(record);
		}

		pi.setCode(ResultInfo.SUCCESS.getCode());
		pi.setMsg(ResultInfo.SUCCESS.getMsg());

		return pi;
	}

    /**
     * 验证key值唯一性
     * 
     * @author yiban
     * @date 2017年12月6日 上午11:08:20
     * @version 1.0
     * @param uiIconStyle	uiIconStyle
     * @return true 唯一 false 不唯一
     *
     */
    private boolean uiKeyIsOk(UiIconStyle uiIconStyle) {
        if (uiIconStyle.getUiKey() != null) {
            UiIconStyleExample example = new UiIconStyleExample();
            example.createCriteria().andUiKeyEqualTo(uiIconStyle.getUiKey());

            int count = uiIconStyleMapper.countByExample(example);

            if (count > 0) {
                return false;
            }
        }

        return true;
    }
}
