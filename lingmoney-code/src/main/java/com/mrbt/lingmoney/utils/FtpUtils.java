package com.mrbt.lingmoney.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * FTP上传工具类
 * @author Administrator
 *
 */
public class FtpUtils {

	/**
	 * 主机
	 */
	private String host;
	/**
	 * 端口
	 */
	private int port;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户密码
	 */
	private String userPwd;
	/**
	 * 客户端
	 */
	private FTPClient ftpClient;
	/**
	 * 访问的url根据路
	 */
	private String url;

	public FtpUtils() {
		ftpClient = new FTPClient();
	}

	/**
	 * 初始化
	 * @throws SocketException	SocketException
	 * @throws IOException	IOException
	 */
	private void init() throws SocketException, IOException {
		ftpClient.connect(host, port);
		ftpClient.login(userName, userPwd);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
		ftpClient.setControlEncoding("UTF-8");
	}

	/**
	 * 创建文件夹
	 * @param dir	文件夹
	 * @return 返回成功/失败
	 */
	private boolean makeDirectory(String dir) {
		boolean flag = true;
		try {
			String[] dirs = dir.split("/");
			StringBuffer pathBuf = new StringBuffer();
			for (String path : dirs) {
				if (StringUtils.isNotBlank(path)) {
					pathBuf.append("/").append(path);
					if (!ftpClient.changeWorkingDirectory(pathBuf.toString())) {
						flag = ftpClient.makeDirectory(pathBuf.toString());
					}
					if (!flag) {
						return flag;
					}
				}
			}
		} catch (Exception e) {
			// log.error(e);
		}
		return flag;
	}

	/**
	 * FTP上传单个文件测试
	 * @param is	流
	 * @param savePath	保存文件路径
	 * @param saveName	保存文件名称
	 */
	public void upload(InputStream is, String savePath, String saveName) {
		try {

			init();
			// 设置上传目录
			boolean flag = ftpClient.changeWorkingDirectory(savePath);
			if (!flag) {
				if (!makeDirectory(savePath)) {
					throw new RuntimeException("创建文件夹失败,未保存文件");
				}
				ftpClient.changeWorkingDirectory("/");
				ftpClient.changeWorkingDirectory(savePath);
			}
			ftpClient.setBufferSize(ResultNumber.ONE_ZERO_TWO_FOUR.getNumber());
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.storeFile(saveName, is);
		} catch (IOException e) {
			// log.error(e);
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				// log.error(e);
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	/**
	 * 上传图片公共方法
	 * @param file MultipartFile
	 * @param path path
	 * @param ftpUtils ftpUtis
	 * @return	返回图片路径
	 */
	public String uploadImages(MultipartFile file, String path, FtpUtils ftpUtils) {
		try {
			if (file != null && file.getSize() > 0) {
				BufferedImage img = ImageIO.read(file.getInputStream());
				if (img != null) {
					String fileName = UUID.randomUUID().toString();
					String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
					if (StringUtils.isNotBlank(type)) {
						fileName += type;
					} else {
						fileName += ".jpg";
					}
					ftpUtils.upload(file.getInputStream(), path, fileName);
					return ftpUtils.getUrl() + path + "/" + fileName;
				} else {
					return "300";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "500";
		}
		return "300";
	}

}
