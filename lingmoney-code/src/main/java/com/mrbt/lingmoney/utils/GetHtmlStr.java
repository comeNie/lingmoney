package com.mrbt.lingmoney.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mrbt.lingmoney.model.Trading;

public class GetHtmlStr {
	public static List<String> getImgSrc(String content) {

		List<String> list = new ArrayList<String>();
		// 目前img标签标示有3种表达式
		// <img alt="" src="1.jpg"/> <img alt="" src="1.jpg"></img> <img alt=""
		// src="1.jpg">
		// 开始匹配content中的<img />标签
		Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
		Matcher m_img = p_img.matcher(content);
		boolean result_img = m_img.find();
		if (result_img) {
			while (result_img) {
				// 获取到匹配的<img />标签中的内容
				String str_img = m_img.group(2);

				// 开始匹配<img />标签中的src
				Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
				Matcher m_src = p_src.matcher(str_img);
				if (m_src.find()) {
					String str_src = m_src.group(3);
					list.add(str_src);
				}
				// 结束匹配<img />标签中的src

				// 匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
				result_img = m_img.find();
			}
		}
		return list;
	}
	
	/**
	 * 支付页面
	 * 
	 * @author yiban
	 * @date 2018年1月7日 上午9:58:25
	 * @version 1.0
	 * @param pid 产品id
	 * @param pcId 产品类型id
	 * @param financialMoney 理财金额
	 * @param infoId 交易信息id
	 * @param bizCode 交易码
	 * @param tId 交易id
	 * @return
	 *
	 */
    public static String getHtmlStrForPay(Integer pid, Integer pcId, BigDecimal financialMoney, Integer infoId,
            String bizCode, Integer tId) {
        System.out.println("跳转支付的url:" + AppCons.PURCHASE_ACTIVE_URL_JD);
        StringBuffer buffer = new StringBuffer();

        buffer.append("<html>");
        buffer.append("<head>");
        buffer.append("<script>");
        buffer.append("function subimt(){");
        buffer.append("document.getElementById('submitForm').submit();");
        buffer.append("}");
        buffer.append("</script>");
        buffer.append("</head>");
        buffer.append("<body onload =\"subimt()\">");
        buffer.append("<form id =\"submitForm\" method=\"post\" action='" + AppCons.PURCHASE_ACTIVE_URL_JD + "?id="
                + bizCode + "_" + pcId + "'>");
        //订单号
        buffer.append("<input type=\"hidden\" name=\"dizNumber\" value='" + bizCode + "'/>");
        //购买金额
        buffer.append("<input type=\"hidden\" name=\"financialMoney\" value='" + financialMoney + "'/>");
        //t_id
        buffer.append("<input type=\"hidden\" name=\"tId\" value='" + tId + "'/>");
        //info_id
        buffer.append("<input type=\"hidden\" name=\"infoId\" value='" + infoId + "'/>");
        //p_id
        buffer.append("<input type=\"hidden\" name=\"pId\" value='" + pid + "'/>");
        buffer.append("</form> ");
        buffer.append("</body>");
        buffer.append("</html>");

        return buffer.toString();
	}

	// static String toAddBankCardResultUrl =
	// "http://localhost:8081/bindBankCardInterface/bind.html";
	public static String getHtmlToAddBankCardResult(String url, String uid, String paymentClientText) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html>");
		buffer.append("<head>");
		buffer.append("<script>");
		buffer.append("function subimt(){");
		buffer.append("document.getElementById('submitForm').submit();");
		buffer.append("}");
		buffer.append("</script>");
		buffer.append("</head>");
		buffer.append("<body onload =\"subimt()\">");
		buffer.append("<form id =\"submitForm\" method=\"post\" action='" + url + "'>");
		buffer.append("<input type=\"hidden\" name=\"uid\" value='" + uid + "'/>");
		buffer.append("<input type=\"hidden\" name=\"paymentClientText\" value='" + paymentClientText + "'/>");
		buffer.append("</form> ");
		buffer.append("</body>");
		buffer.append("</html>");
		return buffer.toString();
	}

	public static String getHtmlStrNew(Trading trading) {

		System.out.println("跳转支付的url:" + AppCons.PURCHASE_ACTIVE_URL_JD);
		StringBuffer buffer = new StringBuffer();

		buffer.append("<html>");
		buffer.append("<head>");
		buffer.append("<script>");
		buffer.append("function subimt(){");
		buffer.append("document.getElementById('submitForm').submit();");
		buffer.append("}");
		buffer.append("</script>");
		buffer.append("</head>");
		buffer.append("<body onload =\"subimt()\">");
		buffer.append("<form id =\"submitForm\" method=\"post\" action='" + AppCons.PURCHASE_ACTIVE_URL_JD + "?id="
				+ trading.getBizCode() + "_" + trading.getPcId() + "'>");
		// 订单号
		buffer.append("<input type=\"hidden\" name=\"dizNumber\" value='" + trading.getBizCode() + "'/>");
		// 购买金额
		buffer.append("<input type=\"hidden\" name=\"financialMoney\" value='"
				+ trading.getFixInfo().getFinancialMoney() + "'/>");
		// t_id
		buffer.append("<input type=\"hidden\" name=\"tId\" value='" + trading.getId() + "'/>");
		// info_id
		buffer.append("<input type=\"hidden\" name=\"infoId\" value='" + trading.getFixInfo().getId() + "'/>");
		// p_id
		buffer.append("<input type=\"hidden\" name=\"pId\" value='" + trading.getpId() + "'/>");
		buffer.append("</form> ");
		buffer.append("</body>");
		buffer.append("</html>");

		return buffer.toString();
	}

}
