package com.li.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.li.common.NetworkUtil;
import com.li.watchdog.Logger;


/**
 * 网络接收者
 */
public class NetworkReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        int type = NetworkUtil.getInstance().getCurrentNetType(context.getApplicationContext());

        switch (type) {
            case NetworkUtil.STATE_INVALIDE_NETWORK:
                Logger.d("无网络");

                break;

            case NetworkUtil.STATE_WIFI_NETWORK:
                Logger.d("wifi网络");

                break;
            case NetworkUtil.STATE_2G_NETWORK:
                Logger.d("2g网络");


                break;
            case NetworkUtil.STATE_3G_NETWORK:
                Logger.d("3g网络");

                break;

            case NetworkUtil.STATE_4G_NETWORK:
                Logger.d("4g网络");

                break;
        }
    }

}