package com.li.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.li.common.NetworkUtil;


/**
 * 网络接收者
 */
public class NetworkReceiver extends BroadcastReceiver {
    private static final String TAG="NetworkReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        int type = NetworkUtil.getInstance().getCurrentNetType(context.getApplicationContext());

        switch (type) {
            case NetworkUtil.STATE_INVALIDE_NETWORK:
                Log.d("TAG","无网络");

                break;

            case NetworkUtil.STATE_WIFI_NETWORK:
                Log.d("TAG","wifi网络");

                break;
            case NetworkUtil.STATE_2G_NETWORK:
                Log.d("TAG","2g网络");


                break;
            case NetworkUtil.STATE_3G_NETWORK:
                Log.d("TAG","3g网络");

                break;

            case NetworkUtil.STATE_4G_NETWORK:
                Log.d("TAG","4g网络");

                break;
        }
    }

}