package com.mrbt.lingmoney.service.info;

import net.sf.json.JSONObject;

/**
  *
  *@author yiban
  *@date 2018年1月12日 下午10:10:55
  *@version 1.0
 **/
public interface InfoInterfaceService {
    /**
     * 获取验证码
     * 
     * @author yiban
     * @date 2018年1月12日 下午10:11:44
     * @version 1.0
     * @param telephone 手机号
     * @return
     *
     */
    JSONObject getValidCode(String telephone);
}
