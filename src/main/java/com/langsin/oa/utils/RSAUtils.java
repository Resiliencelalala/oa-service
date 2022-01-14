package com.langsin.oa.utils;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密工具类
 */
public class RSAUtils {
 
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";


 
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
     
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
     
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
     
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
 
    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


    /**
     * <P>
     * 私钥解密
     * </p>
     * 
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
 

 
    /**
     * <p>
     * 公钥加密
     * </p>
     * 
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
 

    /**
     * <p>
     * 获取私钥
     * </p>
     * 
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }
 
    /**
     * <p>
     * 获取公钥
     * </p>
     * 
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }


    public static void main(String[] args) throws Exception {
        String content = "123456";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCB0QMnEMYE+Cm2mOQaLOFa+Et9H8hEGnmyiZWVpD4/XZM12M63siilKKvLcLMkjcBZDR9dNiMyvZV7bmbaIE1VLjfKVib5WClv5ZLJoC5VzE+w3ox11rLQSDxU6A3bj76Ac2fzwwBTWdfx0MYMkxMXVbCVlccmUibloAnyFFHQyQIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIHRAycQxgT4KbaY5Bos4Vr4S30fyEQaebKJlZWkPj9dkzXYzreyKKUoq8twsySNwFkNH102IzK9lXtuZtogTVUuN8pWJvlYKW/lksmgLlXMT7DejHXWstBIPFToDduPvoBzZ/PDAFNZ1/HQxgyTExdVsJWVxyZSJuWgCfIUUdDJAgMBAAECgYAYeRHSFIxwNWSPwuUFtj5JyxNYJFy50g/tQ5jKnCsMARUWiqf144VcFX3FnZp4Iq0HXLzz8pfSIgGDQn3rUzmgt2wcwNlgL0PX6FfAWHIRHsk3ax5b+C9YaiABcxF2vvjrV+/fAn5P5qLSLZRNX7868dVXzsGKziT2UimLViiRPQJBAMO+6H9Oge4z9HNbpSDZnjhmwtQu0uUzi65+wbD8naLbaXPxDPPHNhxUsbf2HJWqMMz3nWnN4HKGzsDrOT9UQwcCQQCpxsQwcA5OljTZGVpoXT3+Z9Eriy2SbPWgImi8oe+dNBmR+UEkharyp+NIOy/epfL3CCDtOakBl0C5Xsy6zUmvAkAh0QdlhvCXcE5Y54o9hJr673XVJkm04xTMjoVzyBCLpBAxwYEVVGMavd7+AvypNn+7yUNelfyijHuHhJudPzddAkEAlBNQug6B6/kGTvPV7oCkuWdDAH97V+LAuNJKF3wFyU7PnomPT5Nl7g6QJUe6EK8comjsjvtjdIe/Y+5TEfpK8QJAcA5vvWR6lAqEwvM19VaVXE8j46Wzml7GcSbvU34uCxXIx3n1J2rjvXJfr8wyhbqzd4FukHrmicZHmGDUUwXJZA==";
        byte[] data = content.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        String result = Base64Utils.encode(encodedData);
        System.out.println("加密后content: " + result);


        String decryptBody;
        StringBuilder json = new StringBuilder();
        content = result.replaceAll(" ", "+");

        if (!StringUtils.isEmpty(content)) {
            String[] contents = content.split("\\|");
            for (int k = 0; k < contents.length; k++) {
                String value = contents[k];
                value = new String(RSAUtils.decryptByPrivateKey(Base64Utils.decode(value), privateKey), "UTF-8");
                json.append(value);
            }
        }
        decryptBody = json.toString();
        System.out.println("解密后content: " + decryptBody);
      /*  Map<String, Object> map = genKeyPair();
        String  publicKey = getPublicKey(map);
        String privateKey = getPrivateKey(map);
        System.out.println("公钥: " + publicKey+"\n");
        System.out.println("私钥: " + privateKey+"\n");*/
    }
}
