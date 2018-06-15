package com.mrbt.lingmoney.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 翻页的结果类
 * 
 * @author lzp
 *
 * @param <T>
 */
public class GridPage<T> {

	public List<T> rows = new ArrayList<T>();
	public long total;

	public GridPage() {

	}

	public GridPage(List<T> rows, int total) {
		super();
		this.rows = rows;
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
