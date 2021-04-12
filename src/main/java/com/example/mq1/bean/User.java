package com.example.mq1.bean;

import java.util.Date;

/**
 * 用户信息
 * 
 * @author Admin
 *
 */
public class User extends Base {
	private static final long serialVersionUID = 1L;
	private String userName;// 用户名
	private String password;// 密码
	private String name;// 姓名
	private String gender;// 性别
	private String mobile;// 手机号
	private String nation;// 民族
	private Date birthday;// 出生日期
	private String avatar; //头像
	private String address;// 户籍所在地
	private String idNumber;// 身份证号
	private String qq;// QQ
	private String wechat;// 微信号
	private String email;// 邮箱
	private Integer provinceId;// 省
	private Integer cityId;// 市
	private Integer projectId;// 课程
	private Integer sourceAccountId;// 来源账号
	private String comment;// 备注
	private String catalog;// 咨询状态
	private Date communicationTime;// 沟通时间
	private String communicationStage;// 沟通状态
	private Date communicationNextDateTime;// 下次沟通时间
	private String stage;// 认领状态
	private String client;// 终端
	private Date stockTime;// 回库时间
	private String url;// url
	private String source;// 乐语
	private String ad;// 乐语

	public String getUsername() {
		return userName = (userName != null ? userName.trim() : null);
	}

	public void setUserName(String userName) {
		this.userName = (userName != null ? userName.trim() : null);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile = (mobile != null ? mobile.trim() : null);
	}

	public void setMobile(String mobile) {
		this.mobile = (mobile != null ? mobile.trim() : null);
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getUserName() {
		return userName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public Date getCommunicationTime() {
		return communicationTime;
	}

	public void setCommunicationTime(Date communicationTime) {
		this.communicationTime = communicationTime;
	}

	public String getCommunicationStage() {
		return communicationStage;
	}

	public void setCommunicationStage(String communicationStage) {
		this.communicationStage = communicationStage;
	}

	public Date getCommunicationNextDateTime() {
		return communicationNextDateTime;
	}

	public void setCommunicationNextDateTime(Date communicationNextDateTime) {
		this.communicationNextDateTime = communicationNextDateTime;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Integer getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(Integer sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public Date getStockTime() {
		return stockTime;
	}

	public void setStockTime(Date stockTime) {
		this.stockTime = stockTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
