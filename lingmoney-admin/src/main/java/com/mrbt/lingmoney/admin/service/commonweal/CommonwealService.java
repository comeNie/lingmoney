package com.mrbt.lingmoney.admin.service.commonweal;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.PublicBenefitActivities;
import com.mrbt.lingmoney.model.PublicBenefitActivitiesWithBLOBs;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

public interface CommonwealService {

	GridPage<PublicBenefitActivitiesWithBLOBs> listGrid(PageInfo pageInfo);

	int saveAndUpdate(MultipartFile file1, PublicBenefitActivitiesWithBLOBs vo);

	int del(Integer id);

	int push(Integer id);

	int fulfil(Integer id);

}
