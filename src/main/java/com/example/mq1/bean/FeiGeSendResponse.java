package com.example.mq1.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FeiGeSendResponse extends Bean {


    private static final long serialVersionUID = 1L;
    @JsonProperty("SendId")
    private String sendId;
    @JsonProperty("InvalidCount")
    private Integer invalidCount;
    @JsonProperty("SuccessCount")
    private Integer successCount;
    @JsonProperty("BlackCount")
    private Integer blackCount;
    @JsonProperty("Code")
    private Integer code;
    @JsonProperty("Message")
    private String message;

    @JsonIgnore
    public String getSendId() {
        return sendId;
    }
    @JsonIgnore
    public void setSendId(String sendId) {
        this.sendId = sendId;
    }
    @JsonIgnore
    public Integer getInvalidCount() {
        return invalidCount;
    }
    @JsonIgnore
    public void setInvalidCount(Integer invalidCount) {
        this.invalidCount = invalidCount;
    }
    @JsonIgnore
    public Integer getSuccessCount() {
        return successCount;
    }
    @JsonIgnore
    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }
    @JsonIgnore
    public Integer getBlackCount() {
        return blackCount;
    }
    @JsonIgnore
    public void setBlackCount(Integer blackCount) {
        this.blackCount = blackCount;
    }
    @JsonIgnore
    public Integer getCode() {
        return code;
    }
    @JsonIgnore
    public void setCode(Integer code) {
        this.code = code;
    }
    @JsonIgnore
    public String getMessage() {
        return message;
    }
    @JsonIgnore
    public void setMessage(String message) {
        this.message = message;
    }
}
