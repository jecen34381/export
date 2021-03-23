package com.example.mq1.util;

import com.example.mq1.bean.Bean;

import java.util.List;

public class FeigeSmsRequest extends Bean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String to;
    private String appId;
    private String templateId;
    private List<String> datas;
    private String content;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getTemplateId() {
        return templateId;
    }
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    public List<String> getDatas() {
        return datas;
    }
    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
