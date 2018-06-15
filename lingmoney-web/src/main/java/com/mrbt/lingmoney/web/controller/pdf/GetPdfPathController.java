package com.mrbt.lingmoney.web.controller.pdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.gridfs.GridFSDBFile;
import com.mrbt.lingmoney.service.mongo.MongoDbFileService;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;

import net.sf.json.JSONObject;

/**
 * 
 * 获取pdf文件路径
 *
 */
@Controller
@RequestMapping("/pdf")
public class GetPdfPathController {
	private static final Logger LOG = LogManager.getLogger(GetPdfPathController.class);
	@Autowired
	private MongoDbFileService mongoDbFileService;
	
	/**
	 * 获取pdf文件路径
	 * 
	 * @param request
	 *            请求
	 * @param tId
	 *            交易id
	 * @return 返回信息
	 */
	@RequestMapping("/getPdfPath")
	private @ResponseBody Object getPdfPath(HttpServletRequest request, String tId) {
		JSONObject jsonObject = new JSONObject();
		String uId = (String) request.getSession().getAttribute("uid");
		GridFSDBFile gridFSDBFile = mongoDbFileService.retrieveFileOne(tId + uId);
		if (gridFSDBFile != null) {
			jsonObject.put("code",  String.valueOf(ResultParame.ResultInfo.SUCCESS.getCode()));
			jsonObject.put("msg", String.valueOf(ResultParame.ResultInfo.SUCCESS.getMsg()));
			jsonObject.put("path", PropertiesUtil.getPropertiesByKey("PDF_HTTP_PATH") + gridFSDBFile.getFilename());
		} else {
			jsonObject.put("code",  String.valueOf(ResultParame.ResultInfo.CONTRACT_NO.getCode()));
			jsonObject.put("msg", String.valueOf(ResultParame.ResultInfo.CONTRACT_NO.getMsg()));
		}
		return jsonObject;
	}
	/**
	 * 获取pdf文件路径
	 * 
	 * @param request
	 *            请求
	 * @param tId
	 *            交易id
	 * @return 返回信息
	 * @throws IOException 
	 */
	@RequestMapping("/PdfPath")
	private  void getPdfPathIO(HttpServletRequest request, HttpServletResponse response, String tId) {
		LOG.info("获取生成的合同：/pdf/PdfPath:" + tId);
		String uId = (String) request.getSession().getAttribute("uid");
		GridFSDBFile gridFSDBFile = mongoDbFileService.retrieveFileOne(tId + uId);
		if (gridFSDBFile != null) {
			OutputStream out = null;
			BufferedOutputStream bos=null;
			BufferedInputStream bis=null;
			String fileName=gridFSDBFile.getFilename();
			response.setHeader("Content-type", "application/pdf");
			response.setHeader("Content-Disposition", "inline;fileName="+fileName);
			try {
				int i;
				out=response.getOutputStream();
				bos=new BufferedOutputStream(out);
				byte [] by=new byte[300*1024];
				bis = new BufferedInputStream(gridFSDBFile.getInputStream());
				while((i=bis.read(by))!=-1){
					bos.write(by,0,i);
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					bis.close();
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
	}
}
