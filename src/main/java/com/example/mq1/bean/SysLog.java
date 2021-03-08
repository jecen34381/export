package com.example.mq1.bean;

public class SysLog{
	private static final long serialVersionUID = 1L;
	private String url;
	private Integer sysUserId;
	private String requestId;
	private String request;
	private String response;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
