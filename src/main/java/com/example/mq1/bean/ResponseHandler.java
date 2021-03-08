package com.example.mq1.bean;

public interface ResponseHandler {
	public final String OK = "0";
	public final String ERROR = "1";
	public final String SUCCESS = "操作成功";
	public final String FAILURE = "操作失败";
	public final String FORBIDDEN = "无权限";
	public final String TOKEN_TIMEOUT = "1001";
	public final String TOKEN_TIMEOUT_MESSAGE = "用户令牌过期";
	public final String LOGOUT = "1002";
	public final String LOGOUT_MESSAGE = "请重新登录";
}
