package com.test.response;

import java.util.List;

public class AppResponse {

	private Integer code;

	private String message;

	private Object data;

	private List<?> dataList;

	private Integer currentPage;

	private Integer totalPage;

	private Integer numPerPage;

	private Integer totalCount;

	private Boolean hasNext;

	public AppResponse(int code, String message, Object data, List<?> dataList) {
		this.code = code;
		this.message = message;
		this.data = data;
		this.dataList = dataList;
	}

	public static AppResponse okData(Object data) {
		AppResponse a = new AppResponse();
		a.data = data;
		a.code = 200;
		a.message = "success";
		return a;
	}

	public static AppResponse okList(List<?> dataList) {
		AppResponse a = new AppResponse();
		a.dataList = dataList;
		a.code = 200;
		a.message = "success";
		return a;
	}

	public static AppResponse okList(int currentPage, int numPerPage,
			int totalCount, List<?> dataList) {
		AppResponse a = okList(dataList);
		a.currentPage = currentPage;
		a.totalCount = totalCount;
		a.numPerPage = numPerPage;
		a.totalPage = (totalCount + numPerPage - 1) / numPerPage;
		a.hasNext = currentPage < a.totalPage;
		return a;
	}

	public AppResponse() {
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

}
