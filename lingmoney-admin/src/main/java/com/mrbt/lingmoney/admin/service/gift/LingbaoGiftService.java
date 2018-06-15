package com.mrbt.lingmoney.admin.service.gift;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.LingbaoGiftExample;
import com.mrbt.lingmoney.model.LingbaoGiftWithBLOBs;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author Administrator
 *
 */
public interface LingbaoGiftService {

	/**
	 * 根据主键查询我的领地礼品
	 * 
	 * @param id
	 *            主键
	 * @return 我的领地礼品
	 */
	LingbaoGiftWithBLOBs findById(int id);

	/**
	 * 删除我的领地礼品
	 * 
	 * @param id
	 *            主键
	 */
	void delete(int id);

	/**
	 * 保存我的领地礼品
	 * 
	 * @param vo
	 *            我的领地礼品
	 */
	void save(LingbaoGiftWithBLOBs vo);

	/**
	 * 更新我的领地礼品,字段选择修改
	 * 
	 * @param vo
	 *            我的领地礼品
	 */
	void updateByPrimaryKeySelective(LingbaoGiftWithBLOBs vo);

	/**
	 * 更新我的领地礼品,字段全部修改
	 * 
	 * @param vo
	 *            我的领地礼品
	 */
	void updateByPrimaryKey(LingbaoGiftWithBLOBs vo);

	/**
	 * 导入excel
	 * 
	 * @author 罗鑫
	 * @param excelFile
	 *            文件流
	 * @param fileName
	 *            文件名
	 * @param userName
	 *            操作人
	 * @throws Exception
	 *             异常
	 */
	void importExcel(InputStream excelFile, String fileName,
			String userName) throws Exception;

	/**
	 * 根据条件帮助类查询
	 * 
	 * @param example
	 *            条件帮助类
	 * @return 分页实体类
	 */
	PageInfo getList(LingbaoGiftExample example);

	/**
	 * 更改礼品上下架状态
	 * 
	 * @param id
	 *            主键
	 * @param operateName
	 *            操作人姓名
	 * @return 是否更改成功
	 */
	boolean changeShelfStatus(Integer id, String operateName);

	/**
	 * 更改礼品是否最新状态
	 * 
	 * @param id
	 *            主键
	 * @param operateName
	 *            操作人姓名
	 * @return 是否更改成功
	 */
	boolean changeNewStatus(Integer id, String operateName);

	/**
	 * 更改礼品是否为推荐礼品
	 * 
	 * @param id
	 *            主键
	 * @param operateName
	 *            操作人姓名
	 * @return 是否更改成功
	 */
	boolean changeRecommend(Integer id, String operateName);

	/**
	 * 上传图片至FTP
	 * 
	 * @param file
	 *            文件
	 * @return 文件上传路径
	 */
	String uploadPicture(MultipartFile file);
}
