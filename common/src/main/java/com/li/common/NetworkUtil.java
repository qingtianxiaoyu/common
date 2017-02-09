package com.li.common;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.li.watchdog.Logger;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class NetworkUtil {
    public static final int STATE_INVALIDE_NETWORK = -1; // 无网络
    public static final int STATE_UNKNOWN_NEWORK = 0; // 未知网络
    public static final int STATE_MOBILE_NETWORK = 1; // 移动网络
    public static final int STATE_WIFI_NETWORK = 2; // wifi网络
    public static final int STATE_ETHERNET_NETWORK = 3; // 以太网
    public static final int STATE_2G_NETWORK = 4;// 2G网络
    public static final int STATE_3G_NETWORK = 5;// 3G网络
    public static final int STATE_4G_NETWORK = 6;// 4G网络

    private static int networkType;
    private static boolean isAvailable = true;

    private static NetworkUtil networkUtil;

    //返回联网状态
    public static boolean isNetworking() {
        //TODO： check network statues
        return isAvailable;
    }

    /**
     * 获取网络状态
     *
     * @param context
     * @return
     */
    public static int getNetWorkState(Context context) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        ConnectivityManager connMgr = (ConnectivityManager) contextWeakReference.get()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (null == networkInfo) {
            networkType = STATE_INVALIDE_NETWORK;
            isAvailable = false;
            return networkType;
        } else {
            isAvailable = true;
        }

        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE
                || nType == ConnectivityManager.TYPE_MOBILE_DUN
                || nType == ConnectivityManager.TYPE_WIMAX) {
            networkType = STATE_MOBILE_NETWORK;
            int subType = networkInfo.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA
                    || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                networkType = STATE_2G_NETWORK;
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS
                    || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                networkType = STATE_3G_NETWORK;
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                networkType = STATE_4G_NETWORK;
            }

        } else if (nType == ConnectivityManager.TYPE_WIFI) {

            networkType = STATE_WIFI_NETWORK;

        } else if (nType == ConnectivityManager.TYPE_ETHERNET) {
            networkType = STATE_ETHERNET_NETWORK;
        } else {
            networkType = STATE_UNKNOWN_NEWORK;
        }
        return networkType;

    }

    /**
     * 获取运营商名字
     *
     * @param context
     * @return
     */
    public static String getOperatorName(Context context) {
        String ProvidersName = "";
        WeakReference<Context> contentReference = new WeakReference<>(
                context);
        TelephonyManager telephonyManager = (TelephonyManager) contentReference
                .get().getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = telephonyManager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        Logger.d("IMSI = " + IMSI);
        if (TextUtils.isEmpty(IMSI)) {
            return ProvidersName;
        }

        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "电信";
        } else {
            ProvidersName = "其他";
        }
        return ProvidersName;

    }

    public static NetworkUtil getInstance() {
        if (networkUtil == null) {
            networkUtil = new NetworkUtil();
        }
        return networkUtil;
    }


    public int getCurrentNetType(Context context) {
        SoftReference<Context> contextReference = new SoftReference<Context>(
                context);
        int type = STATE_INVALIDE_NETWORK;
        ConnectivityManager cm = (ConnectivityManager) contextReference.get()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = STATE_INVALIDE_NETWORK;
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = STATE_WIFI_NETWORK;
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            type = TelephonyManager.NETWORK_TYPE_GPRS;
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA
                    || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = STATE_2G_NETWORK;
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS
                    || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = STATE_3G_NETWORK;
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = STATE_4G_NETWORK;
            }
        }
        return type;
    }
}
