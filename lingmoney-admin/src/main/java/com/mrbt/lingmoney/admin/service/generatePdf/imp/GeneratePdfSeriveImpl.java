package com.mrbt.lingmoney.admin.service.generatePdf.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.mrbt.lingmoney.admin.mongo.SynDataRecordingTime;
import com.mrbt.lingmoney.admin.service.generatePdf.GeneratePdfSerive;
import com.mrbt.lingmoney.admin.utils.DateFormatUtils;
import com.mrbt.lingmoney.contract.SignByKeywordDemo;
import com.mrbt.lingmoney.mapper.ContractBorrowerInfoMapper;
import com.mrbt.lingmoney.mapper.ContractProductInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.model.ContractBorrowerInfo;
import com.mrbt.lingmoney.model.ContractBorrowerInfoExample;
import com.mrbt.lingmoney.model.ContractProductInfo;
import com.mrbt.lingmoney.model.ContractProductInfoExample;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.pdfProperties.TemplateBO;
import com.mrbt.lingmoney.mongo.ContractFileInfo;
import com.mrbt.lingmoney.utils.PdfUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 生成pdf
 */
@Service
public class GeneratePdfSeriveImpl implements GeneratePdfSerive {

	private static final Logger LOG = LogManager.getLogger(GeneratePdfSeriveImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private ContractProductInfoMapper contractProductInfoMapper;
	
	@Autowired
	private ContractBorrowerInfoMapper contractBorrowerInfoMapper;
	
	static Map<String, ContractBorrowerInfo> borrowerInfoMap = new HashMap<String, ContractBorrowerInfo>();
	
	@Autowired
	private TradingMapper tradingmapper;
	
	
	@Override
	public void generate() {
		// 获取上一次执行时间
		Date establishDt = getLastQueryDate(); 
		// 根据缓存时间查询产品信息
		ContractProductInfoExample cpie = new ContractProductInfoExample();
		cpie.createCriteria().andEstablishDtGreaterThan(establishDt);
		
		List<ContractProductInfo> cpiList = contractProductInfoMapper.selectByExample(cpie);
		LOG.info("生成PDF计划任务查询出来的产品数量为:" + cpiList.size());
		
		for (ContractProductInfo cpi : cpiList) {
			//查询借款人信息
			ContractBorrowerInfo cbi = queyrContractBorrowerInfo(cpi);
			if (cbi == null) {
				LOG.info("查询到借款人为空：" + cpi.getpCode());
			} else {
//				//验证合同是否已经生成
				String id = cpi.getTid() + cpi.getuId(); // 存储文件名称tId+uId
				ContractFileInfo cif = mongoTemplate.findOne(new Query(Criteria.where("fileName").is(id)), ContractFileInfo.class);
				if (cif != null) {
					LOG.info("合同文件:" + id + "已经生成完，请不要重复生成");
					continue;
				}
				
				try {
					String outFile = getContractFile(cpi, cbi);
					
					if (outFile != null && !outFile.equals("")) {
						//写入到mongodb中
						File file = new File(outFile);
						
						if (file.exists()) {
							this.saveFile(file, id, "fs");
							mongoTemplate.upsert(new Query(Criteria.where("title").is("syn_pdf")), new Update().set("synTime", cpi.getEstablishDt()), SynDataRecordingTime.class);
							
							ContractFileInfo cFileInfo = new ContractFileInfo();
							cFileInfo.setFileName(id);
							cFileInfo.setUploadTime(System.currentTimeMillis());
							mongoTemplate.insert(cFileInfo);
							file.delete();
						} else {
							LOG.info("生成合同计划任务出问题了,合同文件不存在");
						}
					} else {
						LOG.info("生成合同计划任务出问题了");
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	private String getContractFile(ContractProductInfo cpi, ContractBorrowerInfo cbi) throws InterruptedException {
		//通过模板生成PDF
		String sourcePdf = getSourcePdf(cpi, cbi);
		if (sourcePdf != null && !sourcePdf.equals("")) {
			//三次签名，第一次区间服务商签名，第二次，借款人签名，第三次投标人签名     生成三方合同，签章
			return SignByKeywordDemo.contractOfgeneration(cpi, cbi, sourcePdf);
		}
		return null;
	}


	/**
	 * 通过模板生成PDF文件
	 * @param cpi	成立的产品的购买记录
	 * @param cbi	借款人信息
	 * @return
	 */
	private String getSourcePdf(ContractProductInfo cpi, ContractBorrowerInfo cbi) {
		TemplateBO templateBO = new TemplateBO();
		templateBO.setContractNumber(cpi.getBizCode() + "-" + System.currentTimeMillis());
		templateBO.setLenderName(cpi.getName() + ""); // 设置姓名
		templateBO.setBorrowingAmount(cpi.getBuyMoney() + "");
		templateBO.setBorrowingTerm(cpi.getfTime() + "");
		templateBO.setInterestTate(cpi.getfYield() + "");
		templateBO.setBorrowingStarts(new SimpleDateFormat("yyyy-MM-dd").format(cpi.getValueDt()) + "");
		templateBO.setMaturityDate(new SimpleDateFormat("yyyy-MM-dd").format(cpi.getMinSellDt()) + "");
		templateBO.setMonthEndRepaymentDay("");
		templateBO.setTotalRepayment(cpi.getBuyMoney().add(cpi.getInterest()) + "");
		templateBO.setServiceFee("");
		templateBO.setBorrower(cbi.getEnterpriseName());
		return PdfUtils.createPDF(templateBO,  cpi.getpId() + "_" + cpi.getTid() + "_" + cpi.getTelephone() + "_hello.pdf");
	}


	private ContractBorrowerInfo queyrContractBorrowerInfo(ContractProductInfo cpi) {
		ContractBorrowerInfo cbi = new ContractBorrowerInfo(); 
		
		if (cpi.getPlatformUserNo() != null && cpi.getPlatformUserNo().equals(107)) {
			cbi.setAllIdNumber("91110107053626664L");//营业执照号，身份证
			cbi.setBwIdtype("2020");
			cbi.setEmail("malan@wdzggroup.com");
			cbi.setEnterpriseName("北京洮丰涌顺投资咨询有限公司");
		} else {
			if (borrowerInfoMap.containsKey(cpi.getpCode())) {
				cbi = borrowerInfoMap.get(cpi.getpCode());
			} else {
				ContractBorrowerInfoExample cbiexample = new ContractBorrowerInfoExample();
				cbiexample.createCriteria().andLoanNoEqualTo(cpi.getpCode());
				List<ContractBorrowerInfo> cbiList = contractBorrowerInfoMapper.selectByExample(cbiexample);
				if (cbiList != null && cbiList.size() > 0) {
					cbi = cbiList.get(0);
					borrowerInfoMap.put(cpi.getpCode(), cbi);
				}
			}
		}
		return cbi;
	}


	/**
	 * 查询上一次查询的时间
	 * @return
	 */
	private Date getLastQueryDate() {
		Date establishDt = null;
		try {
			SynDataRecordingTime sdrt = mongoTemplate.findOne(new Query(Criteria.where("title").is("syn_pdf")), SynDataRecordingTime.class);
			if (sdrt != null) {
				establishDt = sdrt.getSynTime();
			} else {
				establishDt = DateFormatUtils.parseString("2018-01-17 00:00:00");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return establishDt;
	}

	/**
	 * 存储文件
	 * 
	 * @param collectionName
	 *            集合名
	 * @param file
	 *            文件
	 * @param id
	 *            id
	 */
	public void saveFile(File file, String id, String collectionName) {
		try {
			DB db = mongoTemplate.getDb();
			// 存储fs的根节点
			GridFS gridFS = new GridFS(db, collectionName);
			GridFSInputFile gfs = gridFS.createFile(file);
			gfs.put("id", id);
			gfs.put("filename", id + gfs.getFilename().substring(gfs.getFilename().lastIndexOf(".")));
			gfs.put("contentType", gfs.getFilename().substring(gfs.getFilename().lastIndexOf(".")));
			gfs.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取出文件
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	public GridFSDBFile retrieveFileOne(String id) {
		try {
			DB db = mongoTemplate.getDb();
			// 获取fs的根节点
			GridFS gridFS = new GridFS(db);
			GridFSDBFile dbfile = gridFS.findOne(new BasicDBObject("id", id));
			if (dbfile != null) {
				return dbfile;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}


	/**
	 * 导出合同到本地
	 */
	@Override
	public void exportContract(String[] pCodes, String headfolder) {
		for (int i = 0; i < pCodes.length; i++) {
			//查询p_code的所有交易信息
			TradingExample example = new TradingExample();
			example.createCriteria().andStatusEqualTo(ResultNumber.ONE.getNumber()).andPCodeEqualTo(pCodes[i]);
			
			List<Trading> tradings = tradingmapper.selectByExample(example);
			for(Trading trading : tradings) {
				String fileName = trading.getId() + trading.getuId();
				GridFSDBFile gFsdbFile = retrieveFileOne(trading.getId() + trading.getuId());
				if (gFsdbFile != null) {  
					
					String folder = headfolder + pCodes[i] + "/";
					File folders = new File(folder);
					if (!folders.exists()) {
						folders.mkdirs();
					}
	                
					File file = new File(folder + fileName + ".pdf");
					
					try {
						FileOutputStream fos = new FileOutputStream(file);
						gFsdbFile.writeTo(fos);
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
	            }  
			}
			
		}
		
	}
}
