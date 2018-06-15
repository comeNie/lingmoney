package com.mrbt.lingmoney.admin.mongo;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * syn_data_recording_time
 * @author Administrator
 *
 */
@Document(collection = "syn_data_recording_time")
public class SynDataRecordingTime {
	
	private String id;
	
	private String title;
	
	private Date synTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getSynTime() {
		return synTime;
	}

	public void setSynTime(Date synTime) {
		this.synTime = synTime;
	}
}
