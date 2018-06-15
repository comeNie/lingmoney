package com.mrbt.lingmoney.service.bank;

import java.util.Map;

import org.dom4j.Document;

import com.mrbt.lingmoney.bank.tiedcard.dto.HxTiedCardReqDto;
import com.mrbt.lingmoney.bank.utils.dto.PageResponseDto;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 华兴E账户-绑卡服务接口
 * 
 * @author YJQ
 *
 */
public interface BankCardService {

	/**
	 * 绑卡
	 * 
	 * @author YJQ 2017年6月5日
	 * @param req 请求信息
	 * @param key key
	 * @throws Exception 
	 * @return pageresponsedto
	 */
	PageResponseDto TiedBankCard(HxTiedCardReqDto req, String uId) throws Exception;

	/**
	 * 接收绑卡异步应答结果
	 * 
	 * @author YJQ 2017年6月5日
	 * @param document 报文
	 * @return 应答报文
	 * @throws Exception 
	 */
	String receiveTiedCardResult(Document document) throws Exception;

	/**
	 * 查询用户开卡状态
	 * @author YJQ  2017年6月28日
	 * @param key 
	 * @param channelFlow 开卡流水号
	 * @return 查询结果map
	 * @throws Exception 
	 */
	Map<String, Object> queryTiedCardResult(String key, String channelFlow) throws Exception;

	/**
	 * 查询用户开卡状态
	 * @author YJQ  2017年7月28日
	 * @param uId 用户id
	 * @return 查询结果map
	 * @throws Exception 
	 */
    Map<String, Object> queryTiedCardResult(String uId) throws Exception;

    /**
     * 更改用户绑定卡（个人）
     * 
     * @author yiban
     * @date 2018年3月13日 上午10:51:17
     * @version 1.0
     * @param clientType
     * @param appId
     * @param uId
     * @return
     *
     */
    PageInfo changePersonalBindCard(Integer clientType, String appId, String uId);

    /**
     * 查询绑卡信息
     * 
     * @author yiban
     * @date 2018年3月13日 上午11:58:39
     * @version 1.0
     * @param uid 用户id
     * @param currentPage 当前页码
     * @param pageNumber 每页条数
     * @param logGroup 日志头
     * @return pageinfo pageinfo
     *
     */
    PageInfo queryBindCardInfo(String uid, String currentPage, String pageNumber, String logGroup);

}
