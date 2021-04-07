package com.example.mq1.bean;

import java.util.Date;

public class SMSPayload extends Bean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String code; // 编号
	private String type; // 类型 0:验证码 1:通知  2:营销
	private String mobile; // 手机号
	private String content; // 内容
	private Date createTime; // 创建时间


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
