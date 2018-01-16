package com.gk.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by JDRY-SJM on 2018/1/16.
 */

public class ConnectionChangeReceiver extends BroadcastReceiver {
    private static final String TAG = ConnectionChangeReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "网络状态改变");
        boolean success = false;
        //获得网络连接服务
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        //获取wifi连接状态
        NetworkInfo.State state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        //判断是否正在使用wifi网络
        if (state == NetworkInfo.State.CONNECTED) {
            success = true;
        }
        //获取GPRS状态
        state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        //判断是否在使用GPRS网络
        if (state == NetworkInfo.State.CONNECTED) {
            success = true;
        }
        //如果没有连接成功
        if (!success) {
            Toast.makeText(context, "当前网络无连接", Toast.LENGTH_SHORT).show();
        }
    }
}
