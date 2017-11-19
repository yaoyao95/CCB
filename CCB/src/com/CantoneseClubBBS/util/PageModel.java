package com.CantoneseClubBBS.util;

/**
 * 分页实体
 * 
 * @author Aoi
 * @email Aoihoshino@gmail.com
 * @date 2017年4月14日
 * @version 1.0
 */
public class PageModel {
	/** 定义默认一页显示的记录条数 */
	public final static int PAGE_SIZE = 5;
	/** 当前页码 */
	private int pageIndex;
	/** 每页显示的记录数 */
	private int pageSize;
	/** 总记录条数 */
	private int recordCount;

	/** setter and getter method */
	public int getPageIndex() {
		return pageIndex <= 1 ? 1 : pageIndex;
	}

	public void setPageIndex(double pageIndex) {
		this.pageIndex = (int) pageIndex;
	}

	public int getPageSize() {
		return pageSize <= 0 ? PAGE_SIZE : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	/** limit的第一个问号,即该页第一行 */
	public int getStartRow() {
		return (this.getPageIndex() - 1) * this.getPageSize();
	}

}