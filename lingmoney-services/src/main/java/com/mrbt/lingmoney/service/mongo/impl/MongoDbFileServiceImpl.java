package com.mrbt.lingmoney.service.mongo.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.mrbt.lingmoney.mongo.ContractFileInfo;
import com.mrbt.lingmoney.service.mongo.MongoDbFileService;

/**
 * mongo文件
 *
 */
@Service
public class MongoDbFileServiceImpl implements MongoDbFileService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	 
	@Override
    public void saveFile(File file, String id, String collectionName) {
		 try {
            DB db = mongoTemplate.getDb();
            // 存储fs的根节点
            GridFS gridFS = new GridFS(db, collectionName);
            GridFSInputFile gfs = gridFS.createFile(file);
            gfs.put("id", id);
            gfs.put("filename", id + gfs.getFilename().substring(gfs.getFilename().lastIndexOf(".")));
            gfs.put("contentType", gfs.getFilename().substring(gfs.getFilename().lastIndexOf(".")));
            gfs.save();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("存储文件时发生错误！！！");
        }
	}

	@Override
	public GridFSDBFile retrieveFileOne(String id) {
		 try {
            DB db = mongoTemplate.getDb();
            // 获取fs的根节点
            GridFS gridFS = new GridFS(db);
            GridFSDBFile dbfile = gridFS.findOne(new BasicDBObject("id",  id));
            if (dbfile != null) {
                return dbfile;
            }
	    } catch (Exception e) {
	        // TODO: handle exception
	    }
	    return null;
	}

	@Override
	public boolean retrieveFileExisting(String id) {
		ContractFileInfo cif = mongoTemplate.findOne(new Query(Criteria.where("fileName").is(id)), ContractFileInfo.class);
		if (cif != null) {
			return true;
		}
		return false;
	}

}
