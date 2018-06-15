package com.mrbt.lingmoney.admin.service.generatePdf;

/**
 * 生成pdf
 */
public interface GeneratePdfSerive {
	
	/**
	*生成pdf  
	*/
	void generate();

	/**
	 * 导出合同到本地
	 * @param pCodes 
	 * @param folder 
	 */
	void exportContract(String[] pCodes, String headfolder);
}
