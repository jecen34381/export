package com.example.mq1.bean;

public class RuleResult {

    private boolean accordWith;
    private String message;

    public RuleResult(boolean accordWith) {
        this.accordWith = accordWith;
    }

    public RuleResult(boolean accordWith, String message) {
        this.accordWith = accordWith;
        this.message = message;
    }

    public boolean isAccordWith() {
        return accordWith;
    }

    public void setAccordWith(boolean accordWith) {
        this.accordWith = accordWith;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
