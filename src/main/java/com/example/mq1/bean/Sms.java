package com.example.mq1.bean;

import java.util.Date;

/**
 * @Classname Sms
 * @Description TODO
 * @Date 4/7/2020 11:05
 * @Created by geoffreyarchie
 */
public class Sms extends Base {
    private static final long serialVersionUID = 1L;
    private String mobile;
    private String content;

    public Sms(String mobile, String content) {
        this.mobile = mobile;
        this.content = content;
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

    public Sms(String mobile, String content, String status, Date createTime, Date modifyDate) {
        this.mobile = mobile;
        this.content = content;
        this.setStatus(status);
        this.setCreateTime(createTime);
        this.setModifyTime(modifyDate);
    }

    public Sms() {

    }
}
