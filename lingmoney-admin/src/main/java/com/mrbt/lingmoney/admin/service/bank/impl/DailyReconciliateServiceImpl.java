package com.mrbt.lingmoney.admin.service.bank.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.DailyReconciliateService;
import com.mrbt.lingmoney.admin.utils.ZipUtil;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.deal.HxDailyReconciliate;
import com.mrbt.lingmoney.bank.deal.HxNewDailyReconciliate;
import com.mrbt.lingmoney.bank.deal.dto.HxDailyReconciliateReqDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.HxDailyDataMapper;
import com.mrbt.lingmoney.mapper.HxDailyReconciliateMapper;
import com.mrbt.lingmoney.model.HxDailyData;
import com.mrbt.lingmoney.model.HxDailyReconciliateExample;
import com.mrbt.lingmoney.model.HxDailyReconciliateExample.Criteria;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 
 * DailyReconciliateServiceImpl
 *
 */
@Service("DailyReconciliateService")
public class DailyReconciliateServiceImpl implements DailyReconciliateService {
	private static final Logger LOGGER = LogManager.getLogger(DailyReconciliateService.class);

	@Autowired
	private HxDailyReconciliateMapper hxDailyReconciliateMapper;

	@Autowired
	private HxDailyReconciliate hxDailyReconciliate;
	
	@Autowired
	private HxDailyDataMapper hxDailyDataMapper;

    @Autowired
    private HxNewDailyReconciliate hxNewDailyReconciliate;
    @Autowired
    private CustomQueryMapper customQueryMapper;

	@Override
	public com.mrbt.lingmoney.model.HxDailyReconciliate request(HxDailyReconciliateReqDto paymentDto) throws Exception {
		LOGGER.info("进入请求日终对账进程...");
		Map<String, Object> resMap = hxDailyReconciliate.requestDaily(paymentDto);
		LOGGER.info("请求结果：");
		// 解析文件
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhhmmss");

		String fileName = "";
		if (StringUtils.isEmpty(resMap.get("FILENAME"))) {
			fileName = "HxDaily_" + resMap.get("CHECKDATE") + "_" + f.format(new Date());
			LOGGER.info("银行返回文件名为空，构造文件名：" + fileName);
		} else {
			fileName = resMap.get("FILENAME").toString();
			LOGGER.info("银行返回文件名：" + fileName);
		}

		if (StringUtils.isEmpty(resMap.get("FILECONTEXT"))) {
			throw new ResponseInfoException("银行返回文件内容为空", ResultParame.ResultInfo.EMPTY_DATA.getCode());
		}
		String base64file = resMap.get("FILECONTEXT").toString();

		String file = dealFile(base64file, fileName);
		
		// 提交文件至数据库
		com.mrbt.lingmoney.model.HxDailyReconciliate daily = new com.mrbt.lingmoney.model.HxDailyReconciliate();
		daily.setCheckDate(new Date());
		daily.setFileName(fileName);
		daily.setFileContent(file);
		String id = UUID.randomUUID().toString().replace("-", "");
		daily.setId(id);
		hxDailyReconciliateMapper.insertSelective(daily);
		LOGGER.info("文件插入至数据库成功，id为:" + id);
		
		
		// 按行分割
		String[] arr = file.split("\n");

		for (int i = 1; i < arr.length; i++) {
			HxDailyData dailyData = new HxDailyData().parseRowData(arr[0], arr[i]);
			// 提交数据至数据库
			hxDailyDataMapper.insertSelective(dailyData);
		}

		
		return daily;
	}

	@Override
	public PageInfo queryList(String startDate, String endDate, PageInfo pageInfo) throws Exception {

		HxDailyReconciliateExample ex = new HxDailyReconciliateExample();
		Criteria c = ex.createCriteria();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (!StringUtils.isEmpty(startDate)) {
			c.andCheckDateGreaterThanOrEqualTo(format.parse(startDate));
		}
		if (!StringUtils.isEmpty(endDate)) {
			c.andCheckDateLessThanOrEqualTo(DateUtils.addDay(format.parse(endDate), 1));
		}

        ex.setOrderByClause("check_date desc");
		ex.setLimitStart(pageInfo.getFrom());
		ex.setLimitEnd(pageInfo.getSize());
		int count = hxDailyReconciliateMapper.countByExample(ex);
		List<com.mrbt.lingmoney.model.HxDailyReconciliate> li = hxDailyReconciliateMapper.selectByExample(ex);
		pageInfo.setRows(li);
		pageInfo.setTotal(count);
		return pageInfo;
	}

	// 计划任务 每日下午六点自动请求
	@Override
	public void antoRequestDaily() throws Exception {
		try {
			request(new HxDailyReconciliateReqDto());
		} catch (ResponseInfoException resE) {
			LOGGER.info("提交失败，错误码" + resE.getCode() + ":" + resE.getMessage());
		} catch (PageInfoException pageE) {
			LOGGER.info("提交失败，错误码" + pageE.getCode() + ":" + pageE.getMessage());

		} catch (Exception e) {
			LOGGER.info("日终对账异常：" + e.toString()); // 抛出堆栈信息
			e.printStackTrace();
		}
	}

	/**
	 * 解压文件
	 * 
	 * @author YJQ 2017年7月10日
	 * @param fileBase64
	 *            fileBase64
	 * @param fileName
	 *            fileName
	 * @return 数据返回
	 * @throws Exception
	 *             Exception
	 */
	private static String dealFile(String fileBase64, String fileName) throws Exception {

		SimpleDateFormat f = new SimpleDateFormat("yyyyMM");

		String targetPath = PropertiesUtil.getPropertiesByKey("DAILY_FILE_SAVE_URL") + f.format(new Date()) + "/";
		File filePath = new File(targetPath + fileName);
		if (!filePath.exists()) {
			filePath.getParentFile().mkdir();
		}
		
		// 保存文件至本地路径
		byte[] buffer = DatatypeConverter.parseBase64Binary(fileBase64);
		FileOutputStream out = new FileOutputStream(targetPath + fileName);
		out.write(buffer);
		out.close();
		LOGGER.info("zip文件保存至本地成功，url为" + targetPath + fileName);

		// 解压
		String path = ZipUtil.unZip(targetPath + fileName);

		// 提取文件

		FileInputStream in = new FileInputStream(path);

		// size 为字串的长度 ，这里一次性读完

		int size = in.available();

		byte[] unZiBuffer = new byte[size];

		in.read(unZiBuffer);

		in.close();

		String str = new String(unZiBuffer, "GB2312");

		// 删除zip文件
		filePath.delete();
		return str;
	}

    @Override
    public PageInfo newDailyReconciliateReqeust(String oprType, String logGroup) {
        PageInfo pageInfo = new PageInfo();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        // 对账日期默认为昨天
        String fileDate = com.mrbt.lingmoney.admin.utils.DateUtils.sf.format(cal.getTime());
        String fileContext = "";
        String fileCount = "1";// 大小默认为1
        String fileIndex = "1";// 序号默认为1

        String fileName = null;
        if (oprType.equals("U")) {
            fileName = "GHB_" + HxBaseData.MERCHANTID + "_P2P_TCK_" + oprType + "_" + fileDate.replace("-", "") + "_"
                    + fileIndex;
            // 生成上传文件
            fileContext = createUploadFile(fileDate, fileName + ".txt");
        }

        //日期查询格式为yyyy-MM-dd 银行需要格式为yyyyMMdd
        fileDate = fileDate.replace("-", "");
        Document document = hxNewDailyReconciliate.requestHxNewDailyReconciliate(oprType, fileDate, fileContext, fileCount,
                fileIndex, fileName + ".zip", logGroup);
        if (document != null) {
            Map<String, Object> resultMap = HxBaseData.xmlToMap(document);
            String errorCode = (String) resultMap.get("errorCode");
            if (errorCode.equals("0")) {
                String status = (String) resultMap.get("STATUS");
                if ("S".equals(status)) {
                    pageInfo.setCode(ResultInfo.SUCCESS.getCode());
                    pageInfo.setMsg("上送文件成功");

                    // 成功
                    if (oprType.equals("D")) { // 只有下载的时候才需要解析文件
                        pageInfo.setMsg("下载文件成功");
                        // 解析文件
                        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhhmmss");

                        if (StringUtils.isEmpty(resultMap.get("FILENAME"))) {
                            fileName = "HxDaily_" + resultMap.get("CHECKDATE") + "_" + f.format(new Date());
                            LOGGER.info("银行返回文件名为空，构造文件名：" + fileName);
                        } else {
                            fileName = resultMap.get("FILENAME").toString();
                            LOGGER.info("银行返回文件名：" + fileName);
                        }

                        if (StringUtils.isEmpty(resultMap.get("FILECONTEXT"))) {
                            pageInfo.setResultInfo(ResultInfo.EMPTY_DATA);
                            return pageInfo;
                        }
                        String base64file = resultMap.get("FILECONTEXT").toString();

                        try {
                            String file = dealFile(base64file, fileName);
                            // 提交文件至数据库
                            com.mrbt.lingmoney.model.HxDailyReconciliate daily = new com.mrbt.lingmoney.model.HxDailyReconciliate();
                            daily.setCheckDate(new Date());
                            daily.setFileName(fileName);
                            daily.setFileContent(file);
                            String id = UUID.randomUUID().toString().replace("-", "");
                            daily.setId(id);
                            hxDailyReconciliateMapper.insertSelective(daily);
                            LOGGER.info("文件插入至数据库成功，id为:" + id);

                            // 按行分割
                            String[] arr = file.split("\n");

                            for (int i = 1; i < arr.length; i++) {
                                HxDailyData dailyData = new HxDailyData().parseRowData(arr[0], arr[i]);
                                // 提交数据至数据库
                                hxDailyDataMapper.insertSelective(dailyData);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else if ("F".equals(status)) {
                    // 失败
                    String dealErrMsg = (String) resultMap.get("DEALERRMSG");
                    String dealErrCode = (String) resultMap.get("DEALERRCODE");
                    pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
                    pageInfo.setMsg(dealErrCode + ":" + dealErrMsg);
                }

            } else {
                pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
                pageInfo.setMsg(errorCode + resultMap.get("errorMsg"));
            }

        } else {
            pageInfo.setResultInfo(ResultInfo.NO_DATA);
        }

        return pageInfo;
    }

    private String createUploadFile(String fileDate, String fileName) {
        BufferedWriter finalBw = null;
        BufferedWriter bw = null;

        try {
            // 上传的临时文件，因为文件格式需要，先创建一个临时文件，写入内容。然后将文件内容复制到正式文件
            File tempFile = new File("temporyFile.txt");
            System.out.println("文件路径为：" + tempFile.getAbsolutePath());
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "GBK"));

            List<Map<String, Object>> listBidding = customQueryMapper.listDailyReconciliateBidding(fileDate);
            List<Map<String, Object>> listAccountFlow = customQueryMapper.listDailyReconciliateAccountFlow(fileDate);
            List<Map<String, Object>> listBankLendding = customQueryMapper.listDailyReconciliateBankLendding(fileDate);
            List<Map<String, Object>> listBiddingCancel = customQueryMapper
                    .listDailyReconciliateBiddingCancel(fileDate);
            List<Map<String, Object>> listBiddingLoss = customQueryMapper.listDailyReconciliateBiddingLoss(fileDate);
            List<Map<String, Object>> listRePayment = customQueryMapper.listDailyReconciliateRePayment(fileDate);

            int successCount = 0; // 成功总笔数
            if (listBidding != null && listBidding.size() > 0) {
                for (Map<String, Object> map : listBidding) {
                    String line = createFileLine(map);
                    bw.write(line);
                    bw.newLine();
                    Integer state = (Integer) map.get("state"); //状态
                    if (state != null && state.intValue() != 12 && state.intValue() != 5) {
                        successCount++;
                    }
                }
            }

            if (listAccountFlow != null && listAccountFlow.size() > 0) {
                for (Map<String, Object> map : listAccountFlow) {
                    String line = createFileLine(map);
                    bw.write(line);
                    bw.newLine();
                    Integer state = (Integer) map.get("state"); //状态
                    if (state != null && state.intValue() == 1) {
                        successCount++;
                    }
                }
            }

            if (listBankLendding != null && listBankLendding.size() > 0) {
                for (Map<String, Object> map : listBankLendding) {
                    String line = createFileLine(map);
                    bw.write(line);
                    bw.newLine();
                    Integer state = (Integer) map.get("state"); //状态
                    if (state != null && state.intValue() == 1) {
                        successCount++;
                    }
                }
            }

            if (listBiddingCancel != null && listBiddingCancel.size() > 0) {
                for (Map<String, Object> map : listBiddingCancel) {
                    String line = createFileLine(map);
                    bw.write(line);
                    bw.newLine();
                    Integer state = (Integer) map.get("state"); //状态
                    if (state != null && state.intValue() == 1) {
                        successCount++;
                    }
                }
            }

            if (listBiddingLoss != null && listBiddingLoss.size() > 0) {
                for (Map<String, Object> map : listBiddingLoss) {
                    String line = createFileLine(map);
                    bw.write(line);
                    bw.newLine();
                    Integer state = (Integer) map.get("state"); //状态
                    if (state != null && state.intValue() == 1) {
                        successCount++;
                    }
                }
            }

            if (listRePayment != null && listRePayment.size() > 0) {
                for (Map<String, Object> map : listRePayment) {
                    String line = createFileLine(map);
                    bw.write(line);
                    bw.newLine();
                    Integer state = (Integer) map.get("state"); //状态
                    if (state != null && state.intValue() == 1) {
                        successCount++;
                    }
                }
            }
            bw.flush();

            String firstLine = HxBaseData.MERCHANTID + "|" + fileDate.replace("-", "") + "|" + successCount;
            // 正式上传的.txt类型文件
            File file = new File(fileName);
            System.out.println(file.getAbsolutePath());
            finalBw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
            finalBw.write(firstLine);
            finalBw.newLine();

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tempFile), "GBK"));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                finalBw.write(readLine);
                finalBw.newLine();
            }
            finalBw.flush();
            // 正式上传的zip文件
            File zipFile = ZipUtil.toZip(file.getAbsolutePath(), new FileOutputStream(new File(file.getAbsolutePath()
                    .replace(".txt", ".zip"))), true);
            String textContent = encodeBase64(zipFile);

            // 完成后将文件删除
            //            zipFile.delete();
            //            file.delete();
            //            tempFile.delete();

            System.out.println("base64加密后zip：" + textContent);
            return textContent;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (finalBw != null) {
                try {
                    finalBw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    String createFileLine(Map<String, Object> map) {
        String acNo = (String) map.get("acNo"); // 账号
        BigDecimal money = (BigDecimal) map.get("money"); // 金额
        Long type = (Long) map.get("type"); // 类型
        String bizCode = (String) map.get("bizCode"); // 交易码
        
        String moneyStr = "";
        if (money != null) {
            moneyStr = new DecimalFormat("#,###.##").format(money);
        }
        String typeStr = "";
        if (type != null) {
            typeStr = type.toString();
        }
        
        if (StringUtils.isEmpty(acNo)) {
            acNo = "";
        }
        if (StringUtils.isEmpty(bizCode)) {
            bizCode = "";
        }
        if (StringUtils.isEmpty(acNo)) {
            acNo = "";
        }
        
        return typeStr + "|" + bizCode + "|" + "|" + acNo + "|" + moneyStr;
    }

    private String encodeBase64(File file) throws Exception {
        File f = file;
        byte[] arr = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            arr = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
        return Base64.encodeBase64String(arr);
    }

    public static void main(String[] args) {
        try {
            File file = ZipUtil.toZip("F:/GHB_LQE_P2P_TCK_U_20180327_1.txt", new FileOutputStream(new File(
                    "F:/GHB_LQE_P2P_TCK_U_20180327_1.zip")), true);
            String txt = new DailyReconciliateServiceImpl().encodeBase64(file);
            System.out.println(txt);
        } catch (FileNotFoundException | RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
