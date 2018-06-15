package com.mrbt.lingmoney.service.insura;

import net.sf.json.JSONObject;

public interface HuanongInsuraService {

	/**
	 * 通过产品ID查询借款人类型
	 * @param pId
	 * @return
	 */
	JSONObject queryProductBorrowerType(Integer pId);

}
