package com.my.lockscreen;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * 继承DeviceAdminReceiver的广播
 * <p>
 * Created by YJH on 2016/10/28 14:53.
 */
public class AdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        Log.i("AdminReceiver", "接收到广播~");
        if (ACTION_DEVICE_ADMIN_ENABLED.equals(intent.getAction())) {
            Log.i("AdminReceiver", "onEnabled");
        }
    }
}
