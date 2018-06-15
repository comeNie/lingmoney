package demo.utils;

import java.util.HashMap;
import java.util.Map;

import com.enjoysign.sdk.pdf.draw.ImageUtil;

public class MakeStamp {

	static Map<String, String> map = new HashMap<String, String>();

	static {
		// map.put("北京睿博众盈科技发展有限公司", "91110109099023041C");
		// map.put("众合胜（天津）资产管理有限公司", "91120118MA06R31399");
		// map.put("北京洮丰涌顺投资咨询有限公司", "91110107053626664L");
		// map.put("北京万德众归管理咨询有限公司", "91110112MA008AKC3T");
		// map.put("北京往圣学教育咨询有限公司", "91110112MA009CFB4C");
		// map.put("北京东方胜隆投资管理有限公司", "911101050962715179");
		// map.put("假日联合国际旅游有限公司", "91110106051426335K");
		// map.put("中嵩众筹（北京）资本管理有限公司", "91110105MA001FRPXJ");
		// map.put("北京睿博创立投资管理中心（有限合伙）", "911101093067498072");
		// map.put("北京睿博永嘉投资管理中心（有限合伙）", "91110109399187902E");
		// map.put("北京睿博永享投资管理中心（有限合伙）", "91110109399187689B");
		// map.put("北京睿博永泰投资管理中心（有限合伙）", "91110105074191778A");
		// map.put("北京睿博永诚投资管理中心（有限合伙）", "911101093991878140");
		// map.put("北京睿博永安投资管理中心（有限合伙）", "91110109097317724U");
		// map.put("北京睿博永合投资管理中心（有限合伙）", "911101093991879889");
		// map.put("北京睿博恒昌投资管理中心（有限合伙）", "911101093067498740");
		// map.put("北京睿博恒达投资管理中心（有限合伙）", "91110105074191823B");
		// map.put("北京睿博恒星投资管理中心（有限合伙）", "9111010533034464XP");
		// map.put("北京睿博创世投资管理中心（有限合伙）", "911101053304030481");
		// map.put("北京聚融创富资产管理中心（有限合伙）", "91110108579022582Y");
		// map.put("北京易首尊信息咨询中心（有限合伙）", "91110112MA0052RF42");
		// map.put("北京顺天途信息咨询中心（有限合伙）", "91110112MA006ERG05");
		// map.put("北京道本行信息咨询中心（有限合伙）", "91110112MA006UTJ1X");
		// map.put("北京天行众泽信息咨询中心（有限合伙）", "91110112MA0072X6XC");
		// map.put("北京承巽行信息咨询中心（有限合伙）", "91110105MA00B3TK3A");
		// map.put("北京睿博创世投资管理中心（有限合伙）", "911101053304030000");
		// map.put("北京睿博恒昌投资管理中心（有限合伙）", "911101093067498000");
		// map.put("北京睿博恒星投资管理中心（有限合伙）", "9111010533034464XP");
		// map.put("霍尔果斯和尘同光咨询服务有限公司", "91654004MA77QHWT5A");
		// map.put("天一多闻（北京）网络科技有限公司", "91110105MA00ABWJ0D");
		// map.put("兰州思域文化传播有限公司", "9162010239769189X3");
		// map.put("睿博财富（北京）投资管理有限公司", "91110109092435713C");
		// map.put("兰州思域文化传播有限公司", "9162010239769189X3");
		// map.put("北京迪联商贸有限责任公司", "91110108102110454H");
		// map.put("青岛化鲲容海资产管理有限公司", "91370202MA3DM84B1L");
		// map.put("青岛睿博鹏展财务咨询有限公司", "91370202MA3BYQEF0J");
		// map.put("苏州睿博鹏展财务咨询有限公司", "91320594MA1MW8MU04");
		// map.put("青岛易天极教育咨询服务有限公司", "91370202MA3F4W9Y0K");
//		map.put("睿博（天津）财务咨询有限公司", "91120110091583618C");
//		map.put("西安睿博财务咨询管理有限公司", "91610113333624639P");
//		map.put("睿博财富（北京）投资管理有限公司杭州分公司", "91330103MA27WNAB8T");
//		map.put("北京盛益恒兴商贸有限公司", "91110105335562216U");
//		map.put("北京博禺一品商贸有限公司", "91110102MA0023583N");
//		map.put("北京茶行天下商贸有限公司", "91110102MA001BHR18");
//		map.put("万泰福源（北京）商贸有限公司", "911101087351214602");
//		map.put("北京卓越恒达企业管理有限公司", "91110106318351142M");
//		map.put("北京星概念文化传播有限公司", "9111010872269442XU");

	}

	public static void main(String[] args) throws Exception {

		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + entry.getValue());

			// 默认生成电子合同专用章 参数为：公司名称，直径，字体( 1,为微软雅黑，2，为黑体，3，为宋体)
			byte[] simpleStampBytes = ImageUtil
					.makeCircularStamp(entry.getKey(), 500, 3);
			ImageUtil.saveImgFile(simpleStampBytes,
					"d:/" + entry.getValue() + ".png");
		}

		// // 默认生成电子合同专用章 参数为：公司名称，直径，字体( 1,为微软雅黑，2，为黑体，3，为宋体)
		// byte[] simpleStampBytes =
		// ImageUtil.makeCircularStamp("北京承巽行信息咨询中心（有限合伙）", 500, 3);
		// ImageUtil.saveImgFile(simpleStampBytes, "d:/北京承巽行信息咨询中心（有限合伙）.png");

		// 默认生成电子合同专用章 参数为：公司名称，直径，字体( 1,为微软雅黑，2，为黑体，3，为宋体)
		// byte[] simpleStampBytes =
		// ImageUtil.makeCircularStamp("北京承巽行信息咨询中心（有限合伙）", 500, 3);
		// ImageUtil.saveImgFile(simpleStampBytes, "d:/北京承巽行信息咨询中心（有限合伙）.png");

		// // 自定义印章名称 参数为：公司名称，图章名称，直径，字体( 1,为微软雅黑，2，为黑体，3，为宋体)
		// byte[] complextStampBytes =
		// ImageUtil.makeCircularStamp("北京市签玺科技有限公司",
		// "电子财务专用章", 600, 2);
		// ImageUtil.saveImgFile(complextStampBytes, "d:/complextStamp.png");
		//
		// // 生成个人章，Remington最大8个字 参数为：人名，图章高度，字体( 1,为微软雅黑，2，为黑体，3，为宋体)
		// byte[] personStampBytes = ImageUtil.makeSquareStamp("喻龙", 200, 1);
		// ImageUtil.saveImgFile(personStampBytes, "d:/personStamp.png");

	}

}
