package com.mrbt.lingmoney.mobile.controller.users;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.QRCodeUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/** 
 * @author  suyibo 
 * @date 创建时间：2017年11月23日
 */
@Controller
@RequestMapping("/testUserRecommend")
public class TestUserRecommendAction {

	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	/**
	 *  @param request req
	 *  @exception  Exception  Exception
	 */
	@RequestMapping("/show")
	public void list(HttpServletRequest request) throws Exception {
		// 获取UsersProperties对象中所有的uid
		List<String> uidList = usersPropertiesMapper.findAll();
		if (!uidList.isEmpty()) {
			for (String uid : uidList) {
				UsersProperties up = usersPropertiesMapper.selectByuId(uid);
				try {
					String path = "E://Workspaces//Eclipse//lingmoney//lingmoney-web//src//main//webapp";
					String text = AppCons.COMPANY_URL + "?referralTel=" + up.getReferralCode();
					String format = "gif";
					String fileName = "referralCode" + uid + ".gif";
					BufferedImage buffer = QRCodeUtil.encodeBufferedImage(text, path + "/resource/images/lingqian.gif",
							false);
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					ImageIO.write(buffer, format, os);
					InputStream is = new ByteArrayInputStream(os.toByteArray());
					save(is, uid, fileName);

				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
			}
		}

	}
	
	
	/**
	 * 保存文件
	 * @param is is
	 * @param uid uid
	 * @param saveName saveName
	 */
	private static void save(InputStream is, String uid, String saveName) {
		byte[] data = new byte[ResultNumber.ONE_ZERO_TWO_FOUR.getNumber()];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			// http://static.lingmoney.com.cn/TUPAN/50/referralCode50.gif
			File file = new File("Z:\\TUPAN\\" + uid + "");
			if (!file.exists()) {
				file.mkdirs(); // 创建文件夹
			}
			fileOutputStream = new FileOutputStream("Z:\\TUPAN\\" + uid + "\\" + saveName + "");
			while ((len = is.read(data)) != ResultNumber.MINUS_ONE.getNumber()) {
				fileOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
