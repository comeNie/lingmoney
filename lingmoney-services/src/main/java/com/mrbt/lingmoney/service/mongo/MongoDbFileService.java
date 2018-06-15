package com.mrbt.lingmoney.service.mongo;

import java.io.File;

import com.mongodb.gridfs.GridFSDBFile;

/**
 * mongo文件
 *
 */
public interface MongoDbFileService {

    /**
     * 存储文件 
     * @param collectionName 集合名 
     * @param file 文件 
     * @param tId 交易id
     */
    void saveFile(File file, String tId, String collectionName);
	
    /**
     * 取出文件 
     * @param tId 订单ID
     * @return 文件集合
     */
    GridFSDBFile retrieveFileOne(String tId);

    /**
     * 判断合同是否存在
     * @param string
     * @return
     */
	boolean retrieveFileExisting(String string);
}
