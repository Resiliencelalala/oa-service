package com.langsin.oa.utils;

import com.langsin.oa.dto.UserBackendDto;
import org.apache.shiro.subject.Subject;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title
 * Created by SY on 2019/11/22.
 */
public class CurrencyUtil {
//    public static Object convertBean(Object object){
//        object.getClass().;
//        return object;
//    }

    public static UserBackendDto getUserBackendDto() {
        Subject subject = ShiroUtils.getSubject();
        return (UserBackendDto) subject.getPrincipal();
    }

    public static Boolean scopeCompare(BigDecimal num) {
        BigDecimal checkMax = BigDecimal.valueOf(999999999.99);

        if (num.compareTo(BigDecimal.ZERO) == -1) {
            return true;
        }
        if (num.compareTo(checkMax) == 1) {
            return true;
        }
        return false;
    }

    //alice_start
    public static Boolean scopeCompareInt(String num) {
        Pattern pattern = Pattern.compile("^\\d(\\d{0,9})?$");
        Matcher matcher = pattern.matcher(num);
        return matcher.matches();

    }
    //alice_end

    public static Boolean scopeCompare(String num) {
//        Pattern pattern = Pattern.compile("^\\d(\\d{0,9})+(.[0-9]{0,4})?$");
        Pattern pattern = Pattern.compile("^\\d{0,9}(\\.[0-9]{0,4})?$");
        Matcher matcher = pattern.matcher(num);
        return matcher.matches();
    }

    public static Boolean scopeCompare1(String num) {
//        Pattern pattern = Pattern.compile("^\\d(\\d{0,9})+(.[0-9]{0,2})?$");
        Pattern pattern = Pattern.compile("^\\d{0,9}(\\.[0-9]{0,2})?$");
        Matcher matcher = pattern.matcher(num);
        return matcher.matches();
    }

    public static String remove0(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");
            s = s.replaceAll("[.]$", "");
        }
        return s;
    }

    public static void main(String[] args) {
        String s = "32400.60";
        if (s.indexOf(".") > 0) {
            s= s.replaceAll("0+?$", "");//去掉多余的0
            System.out.println(s);
        }
    }
}

