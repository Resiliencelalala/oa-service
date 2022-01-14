package com.langsin.oa.utils;

import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**短信
 * @author wyy
 * @date 2020/12/2 16:05
 */
public class SendSms {
    public static void main(String[] args) throws Exception {
        String tel = "18953165872";
        String msg = "验证码123456，该验证码3分钟有效，请勿泄露。";
        String code = "12w456";
        //sendSms(tel,msg);
        sendSmsTempCode(code,tel);
    }
    /**
     * 模板短信 (验证码)
     * @param code 验证码
     * @param tel 手机号
     * @return
     */
    public static Boolean sendSmsTempCode(String code,String tel) {
        String smsAccount = "18753149251";
        String key = "c7362921050c4282976ec3a2a5d8874a";
        String tem = DateUtils.todaySerial2();
        String tempKey = new String(Base64.encodeBase64("{code}".getBytes()));
        String msgs = new String(Base64.encodeBase64(code.getBytes()));
        String sign = getMD5String(smsAccount + tem + key + msgs).toUpperCase();
        if (sign.length() != 32) {
            sign = 0 + sign;
        }
        ArrayList<Map<String, String>> rsl = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Action", "sendsmstemp");
        map.put("UserId", smsAccount);
        map.put("MsgID", null);
        map.put("Mobile", tel);
        map.put("TempID", "168");//模板id
        map.put("SmsSign", "鲤赞星球");//短信签名
        map.put("TempKey", tempKey);
        map.put("TempValue", msgs);
        map.put("ExtCode", null);
        map.put("SendTime", "");
        map.put("TimeStamp", tem);
        map.put("Sign", sign);
        rsl.add(map);
        Gson gson = new Gson();
        String json = gson.toJson(rsl).replace("[", "").replace("]", "");
        System.out.println(json);
        String re = HttpRequestMsm.sendPost("http://smsapimy.cnnhodigit.com/api/SendSmsTemp", json);
        if (re.contains("Status")) {
            String n = re.substring(re.indexOf("Status") + 9, re.indexOf("Status") + 10);
            if ("0".equals(n)) {
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param tel 到信人
     * @param msg 发送内容
     * @return
     */
    public static Boolean sendSms(String tel, String msg) {
        String smsAccount = "18753149251";
        String key = "c7362921050c4282976ec3a2a5d8874a";
        String tem = DateUtils.todaySerial2();
        String msgs = new String(Base64.encodeBase64(msg.getBytes()));
        String sign = getMD5String(smsAccount + tem + key + msgs).toUpperCase();
        if (sign.length() != 32) {
            sign = 0 + sign;
        }
        ArrayList rsl = new ArrayList();
        Map map = new HashMap();
        map.put("Action", "sendsms");
        map.put("UserId", smsAccount);
        map.put("MsgID", null);
        map.put("Mobile", tel);
        map.put("Message", msgs);
        map.put("ExtCode", null);
        map.put("IsFlash", "0");
        map.put("SendTime", "");
        map.put("TimeStamp", tem);
        map.put("Sign", sign);
        rsl.add(map);
        Gson gson = new Gson();
        String json = gson.toJson(rsl).replace("[", "").replace("]", "");
        System.out.println(json);
        String re = HttpRequestMsm.sendPost("http://smsapimy.cnnhodigit.com/api/SendSms", json);
        if (re.contains("Status")) {
            String n = re.substring(re.indexOf("Status") + 9, re.indexOf("Status") + 10);
            if ("0".equals(n)) {
                return true;
            }
        }
        return false;
    }
    /**
     * md5加密
     *
     * @param str
     * @return
     */
    public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            // 一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
