package com.mrbt.lingmoney.testng;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mrbt.lingmoney.admin.service.generatePdf.GeneratePdfSerive;

public class AutoGeneratePdfTaskTestng extends SpringTestNG {
	
	@Autowired
	private GeneratePdfSerive generatePdfSerive;
	
	/**
	 * 初始化
	 */
	@BeforeClass
	public void setUp() {
		LOGGER.info("初始化");
	}
	
	/**
	 * 执行测试
	 */
	@Test
	public void testAdd2() {
//		generatePdfSerive.generate();
		
		String pCode = "24001903012487,24001903012489,65002203012494,24001903012505,65002203012509,65002203012514,64002003012370,24001903012381,24001903012384,24001903012376,24001903012387,24001903012486,24001903012496,24001903012499,24001903012506,24001903012513,24001903012516,24001903012519,";
		String[] pCodes = pCode.split(",");
		String headfolder = "E:/导出合同20180608/";
		generatePdfSerive.exportContract(pCodes, headfolder);
	}

}
