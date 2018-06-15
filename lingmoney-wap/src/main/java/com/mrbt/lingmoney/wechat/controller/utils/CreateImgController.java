package com.mrbt.lingmoney.wechat.controller.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PropertiesUtil;

import net.sf.json.JSONObject;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年2月6日
 */
@Controller
@RequestMapping(value = "/createImg")
public class CreateImgController {
	@Autowired
	private FtpUtils ftpUtils;
	
	private String indexPic = "image";

	private String IMAGE_PATH = PropertiesUtil.getPropertiesByKey("NEW_YEAR_IMAGE_PATH");

	// public static void main(String[] args) throws Exception {
	// CreateImgController helper = new CreateImgController();
	// WaterMarkConfigure configure = helper.new WaterMarkConfigure();
	// configure.setFont(new Font("楷体", Font.ITALIC, 26));
	// configure.setWaterMarkContent(
	// "领钱儿恭祝狗年吉祥：旺狗贺岁，欢乐祥瑞;旺狗汪汪，事业兴旺;旺狗打滚，财源滚滚;旺狗高跳，吉星高照;旺狗撒欢，如意平安;旺狗祈福，阖家幸福!");
	// configure.setMarkContentColor(new Color(0, 0, 0));
	// configure.setDegree(null);
	// configure.setAlpha(0.5f);
	// configure.setFromName("苏轶博");
	// configure.setToName("小狗崽");
	// helper.getMarkedInputstream(configure);
	// }

	/**
	 * 获得加水印后的流（01）
	 * 
	 * @param src
	 *            原图流
	 * @param configure
	 *            水印配置
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getMarkedInputstream")
	public @ResponseBody Object getMarkedInputstream(String toName, String fromName, String waterMarkContent)
			throws IOException {
		JSONObject resJson = new JSONObject();
		FileInputStream input = null;
		InputStream src = null;
		Random rd = new Random(); // 创建一个Random类对象实例
		int s = rd.nextInt(2) + 1; // 生成1-2之间的随机数，rd.nextInt(3)表示生成0-2之间
		if (s == 1) {
			src = new FileInputStream(IMAGE_PATH + "image1.jpg");
		} else {
			src = new FileInputStream(IMAGE_PATH + "image2.jpg");
		}

		WaterMarkConfigure configure = new WaterMarkConfigure();
		configure.setToName(toName);
		configure.setFromName(fromName);
		configure.setWaterMarkContent(waterMarkContent);
		configure.setFont(new Font("楷体", Font.ITALIC, 26));
		configure.setMarkContentColor(new Color(0, 0, 0));
		configure.setDegree(null);
		configure.setAlpha(0.5f);
		try {
			Image srcImg = ImageIO.read(src);
			int srcImgWidth = srcImg.getWidth(null);
			int srcImgHeight = srcImg.getHeight(null);
			// 加水印
			BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
			g.setColor(configure.getMarkContentColor()); // 根据图片的背景设置水印颜色
			g.setFont(configure.getFont());

			Integer x = configure.getX();
			Integer y = configure.getY();

			// 水印默认位置，正中
			if (x == null) {
				x = srcImgWidth / 2 - getWatermarkLength(configure.getWaterMarkContent().substring(0, 10), g) / 2;
			}
			if (y == null) {
				y = srcImgHeight / 2;
			}

			// 设置旋转度数
			if (configure.getDegree() != null) {
				g.rotate(Math.toRadians(configure.getDegree()), (double) bufImg.getWidth() / 2,
						(double) bufImg.getHeight() / 2);
			}

			// 设置透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, configure.getAlpha()));
			g.drawString("致亲爱的:" + configure.getToName(), x - 50, y - 180);
			if (configure.getWaterMarkContent().length() <= 12) {
				g.drawString(configure.getWaterMarkContent().substring(0, configure.getWaterMarkContent().length()),
						x,
						y - 135);
			} else if (configure.getWaterMarkContent().length() > 12
					&& configure.getWaterMarkContent().length() <= 26) {
				g.drawString(configure.getWaterMarkContent().substring(0, 12), x, y - 135);
				g.drawString(configure.getWaterMarkContent().substring(12, configure.getWaterMarkContent().length()),
						x - 50, y - 90);
			} else if (configure.getWaterMarkContent().length() > 26
					&& configure.getWaterMarkContent().length() <= 40) {
				g.drawString(configure.getWaterMarkContent().substring(0, 12), x, y - 135);
				g.drawString(configure.getWaterMarkContent().substring(12, 26), x - 50, y - 90);
				g.drawString(configure.getWaterMarkContent().substring(26, configure.getWaterMarkContent().length()),
						x - 50,
						y - 45);
			} else if (configure.getWaterMarkContent().length() > 40
					&& configure.getWaterMarkContent().length() <= 54) {
				g.drawString(configure.getWaterMarkContent().substring(0, 12), x, y - 135);
				g.drawString(configure.getWaterMarkContent().substring(12, 26), x - 50, y - 90);
				g.drawString(configure.getWaterMarkContent().substring(26, 40), x - 50, y - 45);
				g.drawString(configure.getWaterMarkContent().substring(40, configure.getWaterMarkContent().length()),
						x - 50, y);
			} else if (configure.getWaterMarkContent().length() > 54
					&& configure.getWaterMarkContent().length() <= 68) {
				g.drawString(configure.getWaterMarkContent().substring(0, 12), x, y - 135);
				g.drawString(configure.getWaterMarkContent().substring(12, 26), x - 50, y - 90);
				g.drawString(configure.getWaterMarkContent().substring(26, 40), x - 50, y - 45);
				g.drawString(configure.getWaterMarkContent().substring(40, 54), x - 50, y);
				g.drawString(configure.getWaterMarkContent().substring(54, configure.getWaterMarkContent().length()),
						x - 50, y + 45);
			} else if (configure.getWaterMarkContent().length() > 68
					&& configure.getWaterMarkContent().length() <= 82) {
				g.drawString(configure.getWaterMarkContent().substring(0, 12), x, y - 135);
				g.drawString(configure.getWaterMarkContent().substring(12, 26), x - 50, y - 90);
				g.drawString(configure.getWaterMarkContent().substring(26, 40), x - 50, y - 45);
				g.drawString(configure.getWaterMarkContent().substring(40, 54), x - 50, y);
				g.drawString(configure.getWaterMarkContent().substring(54, 68), x - 50, y + 45);
				g.drawString(configure.getWaterMarkContent().substring(68, configure.getWaterMarkContent().length()),
						x - 50, y + 90);
			} else {
				g.drawString(configure.getWaterMarkContent().substring(0, 13), x - 15, y - 135);
				g.drawString(configure.getWaterMarkContent().substring(13, 28), x - 65, y - 90);
				g.drawString(configure.getWaterMarkContent().substring(28, 43), x - 65, y - 45);
				g.drawString(configure.getWaterMarkContent().substring(43, 58), x - 65, y);
				g.drawString(configure.getWaterMarkContent().substring(58, 73), x - 65, y + 45);
				g.drawString(configure.getWaterMarkContent().substring(73, 88), x - 65, y + 90);
				g.drawString(configure.getWaterMarkContent().substring(88, configure.getWaterMarkContent().length()),
						x - 65, y + 135);
			}
			g.drawString("爱你的:" + configure.getFromName(), x + 150, y + 180);

			g.dispose();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bufImg, "jpg", os);
			String filename = System.currentTimeMillis() + "Img.jpg";
			IOUtils.copy(new ByteArrayInputStream(os.toByteArray()), new FileOutputStream(IMAGE_PATH + filename));

			// 延迟两秒，将生成的图片上传至ftp服务器
			try {
				Thread.currentThread();
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			File file = new File(IMAGE_PATH + filename);
			input = new FileInputStream(file);
			MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));

			if (multipartFile.getSize() > 0) {
				BufferedImage img = ImageIO.read(multipartFile.getInputStream());
				if (img != null) {
					String fileName = UUID.randomUUID().toString();
					String type = multipartFile.getOriginalFilename()
							.substring(multipartFile.getOriginalFilename().indexOf("."));
					if (StringUtils.isNotBlank(type)) {
						fileName += type;
					} else {

						fileName += ".jpg";
					}
					ftpUtils.upload(multipartFile.getInputStream(), indexPic, fileName);

					String url = ftpUtils.getUrl() + indexPic + "/" + fileName;

					resJson.put("code", 200);
					resJson.put("url", url);

					// 删除本地磁盘图片图片
					input.close();
					file.delete();

					return resJson;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != input) {
				input.close();
			}
		}
		return null;
	}

	/**
	 * 获取水印文字总长度
	 */
	private int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}

	public class WaterMarkConfigure {
		private String waterMarkContent;// 水印文字
		private Font font;// 水印字体
		private Color markContentColor;// 水印颜色
		private Double degree;
		private Float alpha;
		private Integer x;
		private Integer y;
		private String fromName;// 发送人名称
		private String toName;// 接收人名称

		public Integer getX() {
			return x;
		}

		public void setX(Integer x) {
			this.x = x;
		}

		public Integer getY() {
			return y;
		}

		public void setY(Integer y) {
			this.y = y;
		}

		public Float getAlpha() {
			return alpha;
		}

		public void setAlpha(Float alpha) {
			this.alpha = alpha;
		}

		public String getWaterMarkContent() {
			return waterMarkContent;
		}

		public void setWaterMarkContent(String waterMarkContent) {
			this.waterMarkContent = waterMarkContent;
		}

		public Font getFont() {
			return font;
		}

		public void setFont(Font font) {
			this.font = font;
		}

		public Color getMarkContentColor() {
			return markContentColor;
		}

		public void setMarkContentColor(Color markContentColor) {
			this.markContentColor = markContentColor;
		}

		public Double getDegree() {
			return degree;
		}

		public void setDegree(Double degree) {
			this.degree = degree;
		}

		public String getFromName() {
			return fromName;
		}

		public void setFromName(String fromName) {
			this.fromName = fromName;
		}

		public String getToName() {
			return toName;
		}

		public void setToName(String toName) {
			this.toName = toName;
		}

	}
}
