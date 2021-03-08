package com.example.mq1.util;

import com.example.mq1.en.ALABO;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 把阿拉伯数字转换成中文的工具类
 */
@Component
public class ConvertALaBoToChinese {

    public String doConvert(String nums){

        String[] numArray = nums.split("");
        List<String> result = new ArrayList<>();
        Arrays.asList(numArray).stream().forEach(num -> {
            result.add(Character.isDigit(num.toCharArray()[0]) ? convertNum(num) : num);
        });
        String reustlStr = StringUtils.join(result).replace(",", "");

        return reustlStr;
    }

    public String convertNum(String num){
        switch (num){
            case "1" :return ALABO.one.getChinese();
            case "2" :return ALABO.two.getChinese();
            case "3" :return ALABO.three.getChinese();
            case "4" :return ALABO.fore.getChinese();
            case "5" :return ALABO.five.getChinese();
            case "6" :return ALABO.fix.getChinese();
            case "7" :return ALABO.seven.getChinese();
            case "8" :return ALABO.eight.getChinese();
            case "9" :return ALABO.nine.getChinese();
            default: return "";
        }

    }
}
