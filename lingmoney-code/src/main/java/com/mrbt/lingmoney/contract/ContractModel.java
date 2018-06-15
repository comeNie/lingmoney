package com.mrbt.lingmoney.contract;

public class ContractModel {
	
	/**
	 * 合同类型，1，居间服务方。2，借贷方，3，出借方
	 */
	private int chapterType;
	
	/**
	 * PDF源文件
	 */
	private String sourceFile;
	
	/**
	 * 签章位置关键字
	 */
	private String keyword;
	
	/**
	 * 关键字所在页数
	 */
	private int signOnPage;
	
	/**
	 * 签名者名称
	 */
	private String signerName;
	
	/**
	 * 签署合同标题
	 */
	private String reason;
	
	/**
	 * 签章的序列号头，用产品名称代替
	 */
	private String serialNoHeard;
	
	/**
	 * 签章图片
	 */
	private String signaturePicture;
	
	/**
	 * 第三平台业务订单号
	 */
	private String orderId;
	
	/**
	 * CA证书类型，包括：0=平台证书，1=企业长期证书，2=企业临时证书，3=个人长期证书，4=个人临时证书
	 */
	private int caType;
	
	/**
	 * 签署人证件类型 0=居民身份证,1=护照,8=企业营业执照
	 */
	private String cardType;
	
	/**
	 * 签署人证件号, 如果是企业为：企业信用代码，如果是个人，为个人身份证号
	 */
	private String cardNumber;
	
	/**
	 * 签署人真实姓名
	 */
	private String realName;
	
	/**
	 * EMAIL, 如果为企业，必须填写
	 */
	private String email;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 签章后的pdf存放地址
	 */
	private String signedPdfBytes;
	
	/**
	 * 通讯调试开关，请在生产环境关闭
	 */
	private boolean debug;

	

	/**
	 * @return the chapterType
	 */
	public int getChapterType() {
		return chapterType;
	}



	/**
	 * @param chapterType the chapterType to set
	 */
	public void setChapterType(int chapterType) {
		this.chapterType = chapterType;
	}



	/**
	 * @return the sourceFile
	 */
	public String getSourceFile() {
		return sourceFile;
	}



	/**
	 * @param sourceFile the sourceFile to set
	 */
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}



	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}



	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}



	/**
	 * @return the signOnPage
	 */
	public int getSignOnPage() {
		return signOnPage;
	}



	/**
	 * @param signOnPage the signOnPage to set
	 */
	public void setSignOnPage(int signOnPage) {
		this.signOnPage = signOnPage;
	}



	/**
	 * @return the signerName
	 */
	public String getSignerName() {
		return signerName;
	}



	/**
	 * @param signerName the signerName to set
	 */
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}



	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}



	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}



	/**
	 * @return the serialNoHeard
	 */
	public String getSerialNoHeard() {
		return serialNoHeard;
	}



	/**
	 * @param serialNoHeard the serialNoHeard to set
	 */
	public void setSerialNoHeard(String serialNoHeard) {
		this.serialNoHeard = serialNoHeard;
	}



	/**
	 * @return the signaturePicture
	 */
	public String getSignaturePicture() {
		return signaturePicture;
	}



	/**
	 * @param signaturePicture the signaturePicture to set
	 */
	public void setSignaturePicture(String signaturePicture) {
		this.signaturePicture = signaturePicture;
	}



	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}



	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	/**
	 * @return the caType
	 */
	public int getCaType() {
		return caType;
	}



	/**
	 * @param caType the caType to set
	 */
	public void setCaType(int caType) {
		this.caType = caType;
	}



	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}



	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}



	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}



	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}



	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}



	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}



	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}



	/**
	 * @return the signedPdfBytes
	 */
	public String getSignedPdfBytes() {
		return signedPdfBytes;
	}



	/**
	 * @param signedPdfBytes the signedPdfBytes to set
	 */
	public void setSignedPdfBytes(String signedPdfBytes) {
		this.signedPdfBytes = signedPdfBytes;
	}



	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}



	/**
	 * @param debug the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	public String toString() {
		return "ContractModel [chapterType=" + chapterType + ", sourceFile="
				+ sourceFile + ", keyword=" + keyword + ", signOnPage="
				+ signOnPage + ", signerName=" + signerName + ", reason="
				+ reason + ", serialNoHeard=" + serialNoHeard
				+ ", signaturePicture=" + signaturePicture + ", orderId="
				+ orderId + ", caType=" + caType + ", cardType=" + cardType
				+ ", cardNumber=" + cardNumber + ", realName=" + realName
				+ ", email=" + email + ", phone=" + phone + ", signedPdfBytes="
				+ signedPdfBytes + ", debug=" + debug + "]";
	}
	
}
