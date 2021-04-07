package com.example.mq1.en;

public enum SMSType {
    C0("0", "验证码"),
    C1("1", "通知"),
    C2("2", "营销");

    private String code;
    private String name;

    private SMSType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(String code) {
        for (SMSType gender : SMSType.values()) {
            if (gender.getCode().equals(code)) {
                return gender.name;
            }
        }
        return null;
    }

    public static SMSType get(String code) {
        for (SMSType value : SMSType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
