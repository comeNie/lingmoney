package com.mrbt.lingmoney.service.mongo;

import java.io.File;

import com.mongodb.gridfs.GridFSDBFile;

/**
 * 上传文件到MONGODB
 * 
 * @author Administrator
 *
 */
public interface MongoDbFileService {

	/**
	 * 
	 * @param file	文件
	 * @param tId	交易ID
	 * @param collectionName	文件名称
	 */
	void saveFile(File file, String tId, String collectionName);

	
	/**
	 * 
	 * @param tId	交易ID
	 * @return	return
	 */
	GridFSDBFile retrieveFileOne(String tId);
}
