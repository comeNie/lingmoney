package com.mrbt.lingmoney.utils;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 分页标签处理类
 */
public class PagerTag extends TagSupport {
	private static final long serialVersionUID = 5729832874890369508L;
	private String url; // 请求URI
	private int pageSize = 2; // 每页要显示的记录数
	private int pageNo = 1; // 当前页号
	private int recordCount; // 总记录数
	private String fromName;
	private int falg = 1;

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {

		int pageCount = 0;
		try {
			pageCount = (recordCount + pageSize - 1) / pageSize; // 计算总页数

		} catch (Exception e) {

		}

		if (fromName == null) {
			fromName = "qFromPage";
		}

		// 拼写要输出到页面的HTML文本
		StringBuilder sb = new StringBuilder();

		sb.append("<style type=\"text/css\">");
		sb.append(".pagination {padding: 5px;font-size:12px;text-align:right}");
		sb.append(
				".pagination a, .pagination a:link, .pagination a:visited {padding:3px 8px;margin:0 5px;border:1px solid #ddd;text-decoration:none;color:#434343;}");
		sb.append(".pagination a:hover, .pagination a:active {background:#204c6f;color: #fff;text-decoration: none;}");
		sb.append(
				".pagination span.current {padding:3px 8px;margin:0 5px;background:#204c6f;font-weight: bold;color: #fff;}");
		sb.append(".pagination span.disabled {padding: 3px 8px;margin:0 5px;border: 1px solid #eee; color: #ddd;}");
		sb.append("</style>\r\n");
		sb.append("<div id=\"pages\" class=\"pagination\">\r\n");
		if (recordCount == 0) {
			sb.append("<strong style=\"display:block;text-align:center\">没有可显示的项目</strong>\r\n");
		} else {
			// 页号越界处理
			if (pageNo > pageCount) {
				pageNo = pageCount;
			}
			if (pageNo < 1) {
				pageNo = 1;
			}

			String result_url = this.url;
			if (falg == 1) {
				int pos = result_url.indexOf('?');
				if (pos > 0) {
					result_url = result_url.substring(0, pos);
				}
			}

			sb.append("<form method=\"post\" action=\"").append(result_url).append("\" name=\"" + fromName + "\">\r\n");

			// 获取请求中的所有参数
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

			Enumeration<String> enumeration = request.getParameterNames();
			String name = null; // 参数名
			String value = null; // 参数值
			// 把请求中的所有参数当作隐藏表单域
			while (enumeration.hasMoreElements()) {
				name = enumeration.nextElement();
				value = request.getParameter(name);
				// 去除页号
				if (name.equals("pageNo")) {
					if (null != value && !"".equals(value)) {
						pageNo = Integer.parseInt(value);
					}
					continue;
				}

				if (falg == 1) {
					sb.append("<input type=\"hidden\" name=\"").append(name).append("\" value=\"").append(value)
							.append("\"/>\r\n");
				}
			}

			// 把当前页号设置成请求参数
			sb.append("<input type=\"hidden\" name=\"").append("pageNo").append("\" value=\"").append(pageNo)
					.append("\"/>\r\n");

			// 输出统计数据
			sb.append("&nbsp;共<strong>").append(recordCount).append("</strong>项").append(",<strong>").append(pageCount)
					.append("</strong>页:&nbsp;\r\n");

			// 上一页处理
			if (pageNo == 1) {
				sb.append("<span class=\"disabled\">&laquo;&nbsp;上一页").append("</span>\r\n");
			} else {
				sb.append("<a href=\"javascript:turnOverPage(").append((pageNo - 1))
						.append(")\">&laquo;&nbsp;上一页</a>\r\n");
			}

			// 如果前面页数过多,显示"..."
			int start = 1;
			if (this.pageNo > 4) {
				start = this.pageNo - 1;
				sb.append("<a href=\"javascript:turnOverPage(1)\">1</a>\r\n");
				sb.append("<a href=\"javascript:turnOverPage(2)\">2</a>\r\n");
				sb.append("&hellip;\r\n");
			}
			// 显示当前页附近的页
			int end = this.pageNo + 1;
			if (end > pageCount) {
				end = pageCount;
			}
			for (int i = start; i <= end; i++) {
				if (pageNo == i) { // 当前页号不需要超链接
					sb.append("<span class=\"current\">").append(i).append("</span>\r\n");
				} else {
					sb.append("<a href=\"javascript:turnOverPage(").append(i).append(")\">").append(i)
							.append("</a>\r\n");
				}
			}
			// 如果后面页数过多,显示"..."
			if (end < pageCount - 2) {
				sb.append("&hellip;\r\n");
			}
			if (end < pageCount - 1) {
				sb.append("<a href=\"javascript:turnOverPage(").append(pageCount - 1).append(")\">")
						.append(pageCount - 1).append("</a>\r\n");
			}
			if (end < pageCount) {
				sb.append("<a href=\"javascript:turnOverPage(").append(pageCount).append(")\">").append(pageCount)
						.append("</a>\r\n");
			}

			// 下一页处理
			if (pageNo == pageCount) {
				sb.append("<span class=\"disabled\">下一页&nbsp;&raquo;").append("</span>\r\n");
			} else {
				sb.append("<a href=\"javascript:turnOverPage(").append((pageNo + 1))
						.append(")\">下一页&nbsp;&raquo;</a>\r\n");
			}
			sb.append("</form>\r\n");

			// sb.append("<script language=\"javascript\">\r\n");
			// sb.append(" $(document).ready(function() {\r\n");
			// sb.append("
			// $('body').scrollTop($('body')[0].scrollHeight);\r\n");
			// sb.append(" });\r\n");
			// sb.append("</script>\r\n");

			// 生成提交表单的JS
			sb.append("<script language=\"javascript\">\r\n");
			sb.append("  function turnOverPage(no){\r\n");
			sb.append("    if(no>").append(pageCount).append("){");
			sb.append("      no=").append(pageCount).append(";}\r\n");
			sb.append("    if(no<1){no=1;}\r\n");
			sb.append("    document." + fromName + ".pageNo.value=no;\r\n");
			sb.append("    document." + fromName + ".submit();\r\n");
			sb.append("  }\r\n");
			sb.append("</script>\r\n");
		}
		sb.append("</div>\r\n");

		// 把生成的HTML输出到响应中
		try {
			pageContext.getOut().println(sb.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY; // 本标签主体为空,所以直接跳过主体
	}

	public int getFalg() {
		return falg;
	}

	public void setFalg(int falg) {
		this.falg = falg;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
}