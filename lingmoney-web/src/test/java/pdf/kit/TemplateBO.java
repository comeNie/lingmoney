package pdf.kit;

import lombok.Data;

import java.util.List;

/**
 * Created by fgm on 2017/4/17.
 */
@Data
public class TemplateBO {

	private String lenderId;
	private String lenderName;//姓名
	private String borrowingAmount;//借款金额
	private String borrowingTerm;//借款期限
	private String interestTate;//年利率
	private String borrowingStarts;//借款开始日期
	private String maturityDate;//借款终止日期
	private String monthEndRepaymentDay;//月截止还款日
	private String totalRepayment;//总还款本息
	private String templateName;
	private String freeMarkerUrl;
	private String ITEXTUrl;
	private String JFreeChartUrl;
	private List<String> scores;
	private String imageUrl;
	private String picUrl;
	
	public String getLenderId() {
		return lenderId;
	}
	public void setLenderId(String lenderId) {
		this.lenderId = lenderId;
	}
	public String getLenderName() {
		return lenderName;
	}
	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}
	public String getBorrowingAmount() {
		return borrowingAmount;
	}
	public void setBorrowingAmount(String borrowingAmount) {
		this.borrowingAmount = borrowingAmount;
	}
	public String getBorrowingTerm() {
		return borrowingTerm;
	}
	public void setBorrowingTerm(String borrowingTerm) {
		this.borrowingTerm = borrowingTerm;
	}
	public String getInterestTate() {
		return interestTate;
	}
	public void setInterestTate(String interestTate) {
		this.interestTate = interestTate;
	}
	public String getBorrowingStarts() {
		return borrowingStarts;
	}
	public void setBorrowingStarts(String borrowingStarts) {
		this.borrowingStarts = borrowingStarts;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getMonthEndRepaymentDay() {
		return monthEndRepaymentDay;
	}
	public void setMonthEndRepaymentDay(String monthEndRepaymentDay) {
		this.monthEndRepaymentDay = monthEndRepaymentDay;
	}
	public String getTotalRepayment() {
		return totalRepayment;
	}
	public void setTotalRepayment(String totalRepayment) {
		this.totalRepayment = totalRepayment;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getFreeMarkerUrl() {
		return freeMarkerUrl;
	}
	public void setFreeMarkerUrl(String freeMarkerUrl) {
		this.freeMarkerUrl = freeMarkerUrl;
	}
	public String getITEXTUrl() {
		return ITEXTUrl;
	}
	public void setITEXTUrl(String iTEXTUrl) {
		ITEXTUrl = iTEXTUrl;
	}
	public String getJFreeChartUrl() {
		return JFreeChartUrl;
	}
	public void setJFreeChartUrl(String jFreeChartUrl) {
		JFreeChartUrl = jFreeChartUrl;
	}
	public List<String> getScores() {
		return scores;
	}
	public void setScores(List<String> scores) {
		this.scores = scores;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
