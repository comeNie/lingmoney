package com.mrbt.lingmoney.mobile.controller.pdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.gridfs.GridFSDBFile;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.service.mongo.MongoDbFileService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ResultBean;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultUtils;

/**
 * 
 * 获取pdf文件路径
 *
 */
@Controller
@RequestMapping("/tradContract")
public class GetPdfPathController {
	private static final Logger LOG = LogManager.getLogger(GetPdfPathController.class);
	@Autowired
	private MongoDbFileService mongoDbFileService;
	
	@Autowired
	private RedisDao redisDao;
	
	/**
	 * 获取pdf文件路径,兼容无法正常显示合同的接口
	 * 
	 * @param request
	 *            请求
	 * @param tId
	 *            交易id
	 * @return 返回信息
	 */
	@RequestMapping("/getPdfPath")
	private @ResponseBody Object getPdfPath(HttpServletRequest request, String tId, String token) {
        JSONObject jsonObject = new JSONObject();
		String uId = (String) redisDao.get(AppCons.TOKEN_VERIFY + token);
		boolean gridFSDBFile = mongoDbFileService.retrieveFileExisting(tId + uId);
		
		if (gridFSDBFile) {
            jsonObject.put("code", String.valueOf(ResultParame.ResultInfo.SUCCESS.getCode()));
            jsonObject.put("msg", String.valueOf(ResultParame.ResultInfo.SUCCESS.getMsg()));
		} else {
            jsonObject.put("code", String.valueOf(ResultParame.ResultInfo.CONTRACT_NO.getCode()));
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
	 */
	@RequestMapping("/queryPdfPath")
	private @ResponseBody Object queryPdfPath(HttpServletRequest request, String tId, String token) {
        JSONObject jsonObject = new JSONObject();
		String uId = (String) redisDao.get(AppCons.TOKEN_VERIFY + token);
		boolean gridFSDBFile = mongoDbFileService.retrieveFileExisting(tId + uId);
		
		if (gridFSDBFile) {
            jsonObject.put("code", String.valueOf(ResultParame.ResultInfo.SUCCESS.getCode()));
            jsonObject.put("msg", String.valueOf(ResultParame.ResultInfo.SUCCESS.getMsg()));
		} else {
            jsonObject.put("code", String.valueOf(ResultParame.ResultInfo.CONTRACT_NO.getCode()));
            jsonObject.put("msg", String.valueOf(ResultParame.ResultInfo.CONTRACT_NO.getMsg()));
		}
        return new ResultBean(ResultUtils.getEncodeResult(jsonObject));
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
	@RequestMapping("/pdfPath")
	private  void getPdfPathIO(HttpServletRequest request, HttpServletResponse response, String tId, String token) {
		LOG.info("获取生成的合同：/pdf/PdfPath:" + tId + mongoDbFileService);
		String uId = (String) redisDao.get(AppCons.TOKEN_VERIFY + token);
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
