package com.mrbt.lingmoney.test;

import java.io.File;

import com.mrbt.lingmoney.utils.MD5Utils;

public class UpdateUsersProp {
	/**
	 * 生成更新users_properties表中的recommended_level
	 * select id, recommended_level from users_properties where recommended_level is not null limit 0, 10000;
	 * @param args
	 */
	public static void main(String[] args) {
		String fileData = FileOpertion.readFile(new File("F:/aaaa.txt"), "gbk").replaceAll("\n", "");
		String[] datas = fileData.split("\r");
		
		for (int i = 0; i < datas.length; i++) {
			String d = datas[i];
			if(d.equals("")){
				continue;
			}
			String[] ds = d.split("	");
			String id = ds[0];
			
			String level = ds[1];
			String[] levels = level.split(",");
			String getMd5Level = "";
			for (int j = 0; j < levels.length; j++) {
				if(j == levels.length -1){
					getMd5Level = getMd5Level + MD5Utils.MD5(levels[j] + "LINGQIAN").toUpperCase();
				}else{
//					getMd5Level = getMd5Level + MD5Util.getMD5(levels[j] + "LINGQIAN", "UTF-8").toUpperCase() + ",";
					getMd5Level = getMd5Level + MD5Utils.MD5(levels[j] + "LINGQIAN").toUpperCase() + ",";
				}
			}
			String sql = "update users_properties set recommended_level = '" + getMd5Level + "' where id = " + id + ";\n";
			FileOpertion.writerBatchToFile(sql, "gbk", "f:/sql.txt");
		}
		System.out.println("总数据量:" + datas.length);
	}
}
