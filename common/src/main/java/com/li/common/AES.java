package com.li.common;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by li on 2016/3/30.
 */
public class AES {
    private static String defaultKey = "3be580f52a1abb7c";
    private static String defaultVi = "0102030405060708";


    public static String decryption(String data) throws Exception {
        return decryption(data, null);

    }

    public static String decryption(String data, String key) throws Exception {
        byte[] mKey;
        if (!TextUtils.isEmpty(key)) {

            mKey = key.length() >= 16 ? key.getBytes() : MD5.getMD5Whit16Bit(key).getBytes();
        } else {
            mKey = defaultKey.getBytes();
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(mKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
//        IvParameterSpec iv = new IvParameterSpec(defaultVi
//                .getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] encrypted = Hex.decodeHex(data.toCharArray());
        byte[] original = cipher.doFinal(encrypted);
        String originalString = new String(original);
        return originalString;
    }

    public static String encryption(String data) throws Exception {
        return encryption(data, null);
    }

    public static String encryption(String data, String key) throws Exception {
        byte[] mKey;
        if (!TextUtils.isEmpty(key)) {
            mKey = key.length() >= 16 ? key.getBytes() : MD5.getMD5Whit16Bit(key).getBytes();
        } else {
            mKey = defaultKey.getBytes();
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(mKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");//"算法/模式/补码方式"
//        IvParameterSpec iv = new IvParameterSpec(defaultVi.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return String.valueOf(Hex.encodeHex(encrypted, true));
    }

}
