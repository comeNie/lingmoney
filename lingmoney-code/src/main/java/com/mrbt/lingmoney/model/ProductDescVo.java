package com.mrbt.lingmoney.model;

/**
 * 产品详情bean
 *
 * @author lihq
 * @date 2017年6月14日 下午5:57:35
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public class ProductDescVo {
	private String title;
	private String content;

	
	public ProductDescVo(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ProductDescVo [title=" + title + ", content=" + content + "]";
	}

}
