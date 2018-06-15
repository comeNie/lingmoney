package com.mrbt.lingmoney.wap.vo.info;
/**
 * 
 * @author ruochen.yu
 *
 */
public class InfoClientBannerVo implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7160929631894030653L;

	private Integer id; 

	private String name; //图片名称 
 
    private String url;  //图片url
	 
	private String defultPath; //图片路径

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDefultPath() {
		return defultPath;
	}

	public void setDefultPath(String defultPath) {
		this.defultPath = defultPath;
	}


}
