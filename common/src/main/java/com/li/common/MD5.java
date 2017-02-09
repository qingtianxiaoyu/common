package com.li.common;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Finance on 2016/2/19.
 */
public class MD5 {
    private static String defaultMD5 = "00000000000000000000000000000";

    public static String getMD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (content != null) {
                digest.update(content.getBytes());
            }
            return getHashString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return defaultMD5;
        }
    }

    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }

    public static String getMD5Whit16Bit(String content) {
        String md532bit = getMD5(content);
        if (TextUtils.isEmpty(md532bit)) {
            return defaultMD5.substring(8, 24);
        } else {
            return md532bit.substring(8, 24);

        }


    }
}
