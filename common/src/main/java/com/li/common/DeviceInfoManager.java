package com.li.common;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.UUID;


public class DeviceInfoManager {


    public static DisplayMetrics getDisPlay(Context context) {
//        DisplayMetrics dm = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return context.getApplicationContext().getResources().getDisplayMetrics();

    }

    /**
     * This will fetch a system property value if it exists
     *
     * @param key {@link String}
     * @return {@link String}
     * @throws IOException {@link IOException} on failure to read process output
     *                     stream
     *                     ro.smartisan.version ->smartisan os
     *                     ro.build.version.emui -> emui
     *                     ro.miui.ui.version.name -> miui
     *                     ro.build.display.id  -> flyme
     */
    private static String getSystemProperty(String key) throws IOException {
        Process process = Runtime.getRuntime().exec("getprop " + key);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        StringBuilder outputBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            outputBuilder.append(line).append("\n");
        }
        bufferedReader.close();
        return outputBuilder.toString().trim();
    }


    public static String getExtOs() {
        String version = null;
        try {
            version = getSystemProperty("ro.smartisan.version");
            if (!TextUtils.isEmpty(version)) {
                return version;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            version = getSystemProperty("ro.build.version.emui");
            if (!TextUtils.isEmpty(version)) {
                return version;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            version = getSystemProperty("ro.miui.ui.version.name");
            if (!TextUtils.isEmpty(version)) {
                return version;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            version = getSystemProperty("ro.build.display.id");
            if (!TextUtils.isEmpty(version) && version.startsWith("flyme")) {
                return version;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return String.format("Android %s", Build.VERSION.RELEASE);

    }


    /**
     * 获取设备ID
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        String deviceId = "0";
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        }
        return deviceId;
    }

    /**
     * sim卡状态
     *
     * @param context
     * @return
     */
    public static int getSimState(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        final int state = tm.getSimState();
        return state;
    }

    /**
     * sim卡IMSI
     *
     * @param context
     * @return
     */
    public static String getSubscriberId(Context context) {
        WeakReference<Context> contextWeak = new WeakReference<Context>(context);
        TelephonyManager tm = (TelephonyManager) contextWeak.get()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    /**
     * 获取AndroidId
     *
     * @return
     */
    public static String getAndroidId(Context context) {
        return Settings.System.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID); // 获取AndroidId
    }

    /**
     * 获取wifi物理地址
     *
     * @return
     */
    public static String getWifiMac(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String wifiMac = info.getMacAddress();
        return wifiMac;
    }

    /**
     * 获取设备的唯一标识
     *
     * @return 唯一标识字符串
     */
    public static String getDeviceUUID(Context context) {

        String uuid = null;
        if (TextUtils.isEmpty(uuid)) {
            uuid = getDeviceId(context);
        }
        if (TextUtils.isEmpty(uuid)) {
            uuid = getWifiMac(context);
        }
        if (TextUtils.isEmpty(uuid)) {
            uuid = getAndroidId(context);
        }
        if (TextUtils.isEmpty(uuid)) {
            uuid = autoGenerateUUID(context);
        }
        return uuid;
    }

    /**
     * 获取一个uuid
     *
     * @return
     */
    public static String autoGenerateUUID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "cacheMap", Context.MODE_PRIVATE);
        String uuid = preferences.getString("uuid", "");
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            preferences.edit().putString("uuid", uuid);
        }
        return uuid;
    }


    /**
     * BASEBAND-VER 基带版本 应用考虑到兼容性 所以暂时不作为区分模拟器和真机的条件
     *
     * @return String 基带版本
     */
    public static String getBaseBand_Ver() {
        String Version = "";
        try {

            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class,
                    String.class});
            Object result = m.invoke(invoker, new Object[]{
                    "gsm.version.baseband", "no message"});
            Version = (String) result;
        } catch (Exception e) {
        }
        return Version;
    }

//    /**
//     * @param context ApplicationContext
//     */
//    public static void initialize(Context context) {
//        mContext = context;
//
//    }

}
