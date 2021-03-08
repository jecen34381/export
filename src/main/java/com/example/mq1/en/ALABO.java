package com.example.mq1.en;

public enum ALABO {
    one("零","0","ling"),
    two("一","0","ling"),
    three("二","0","ling"),
    fore("三","0","ling"),
    five("四","0","ling"),
    fix("五","0","ling"),
    seven("六","0","ling"),
    eight("七","0","ling"),
    nine("八","0","ling"),
    ten("九","0","ling");

    String chinese;
    String aLaBo;
    String pinYin;

    ALABO(String chinese, String aLaBo, String pinYin) {
        this.chinese = chinese;
        this.aLaBo = aLaBo;
        this.pinYin = pinYin;
    }

    public void setChinese(String chinese){this.chinese = chinese;}
    public String getChinese(){ return this.chinese; }

    public void setaLaBo(String aLaBo){this.aLaBo = aLaBo;}
    public String getaLaBo(){return this.aLaBo;}

    public void setPinYin(String pinYin){this.pinYin = pinYin;}
    public String getPinYin(){return this.pinYin;}
}
