package com.mrbt.lingmoney.web.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mrbt.lingmoney.mapper.AreaDomainMapper;
import com.mrbt.lingmoney.model.AreaDomain;
import com.mrbt.lingmoney.model.AreaDomainExample;
import com.mrbt.lingmoney.utils.IpToAddressUtils;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * @author yiban sun
 * @date 2016年11月8日 上午10:23:58
 * @version 1.0
 * @description 判断登录IP地址的拦截器
 * @since
 **/
public class IpInterceptor implements HandlerInterceptor {
	Logger log = LogManager.getLogger(IpInterceptor.class);
	@Autowired
	private AreaDomainMapper areaDomainMapper;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object param) throws Exception {
		HttpSession session = request.getSession();
		try {
			// session没有才查询
			if (null == session.getAttribute("AREADOMAIN")) {
				log.info("获取完整URL：" + request.getRequestURI().toString());

				String url = "";
				// 获取地址栏URL http://www.lingmoney.cn
				if (null != request.getRequestURL()) {
					url = request.getRequestURL().toString();
					// 通过:进行分割，获取中段地址
					if (StringUtils.isNotBlank(url)) {
						String[] urlname = url.split(":");
						if (null != urlname && urlname.length > 1) {
							url = urlname[1].replace("//", "");
						}
					}
				}

				log.info("获取截取URL：" + url);

				// 根据url查询是否存在此地址
				AreaDomainExample adExample = new AreaDomainExample();
				adExample.createCriteria().andDomainEqualTo(url);

				List<AreaDomain> list = areaDomainMapper.selectByExample(adExample);
				if (null != list && list.size() > 0) {
					session.setAttribute("AREADOMAIN", list.get(0));

				} else { // 如果没数据，根据IP自动获取地址
					String ip = IpToAddressUtils.getIpAddr(request);
					String address = IpToAddressUtils.getIpProvinceAndCity(ip,
							PropertiesUtil.getPropertiesByKey("baidu_api_key"));

					log.info("查询URL为空，根据IP获取。 ip:" + ip + "; address:" + address);

					// 查询默认地区
					adExample = new AreaDomainExample();
					adExample.createCriteria().andBdCityCodeEqualTo(
							PropertiesUtil.getPropertiesByKey("defaultAreaCityCode"));
					AreaDomain addefault = areaDomainMapper.selectByExample(adExample).get(0);

					// 如果根据IP查询不到对应地址，使用默认地址
					if (StringUtils.isNotBlank(address)) {
						adExample = new AreaDomainExample();
						adExample.createCriteria()
								.andBdCityCodeEqualTo(address.split("_")[ResultParame.ResultNumber.TWO.getNumber()]);
						AreaDomain ad = areaDomainMapper.selectByExample(adExample).get(0);
						if (null == ad) {
							session.setAttribute("AREADOMAIN", addefault);
						} else {
							session.setAttribute("AREADOMAIN", ad);
						}

					} else {
						session.setAttribute("AREADOMAIN", addefault);
					}
				}

			}
		} catch (Exception e) {
			log.error("获取用户地址位置信息失败，系统错误。 \n" + e.getMessage());
			e.printStackTrace();
		}

		return true;
	}

}
