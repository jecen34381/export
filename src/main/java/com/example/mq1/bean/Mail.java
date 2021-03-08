package com.example.mq1.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
public class Mail implements Serializable {


    public String mailFrom;
    public String mailTo;

    public boolean sendHtml;
    public String body;

    public Mail() {
    }

    public Mail(String mailFrom, String mailTo, boolean sendHtml, String body) {
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.sendHtml = sendHtml;
        this.body = body;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public boolean isSendHtml() {
        return sendHtml;
    }

    public void setSendHtml(boolean sendHtml) {
        this.sendHtml = sendHtml;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "mailFrom='" + mailFrom + '\'' +
                ", mailTo='" + mailTo + '\'' +
                ", sendHtml=" + sendHtml +
                ", body='" + body + '\'' +
                '}';
    }
}
