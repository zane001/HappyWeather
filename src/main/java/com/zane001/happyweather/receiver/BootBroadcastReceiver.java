package com.zane001.happyweather.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zane001.happyweather.App;
import com.zane001.happyweather.util.LogUtil;
import com.zane001.happyweather.service.WeatherUpdateService_;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zane001 on 2014/9/7.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    protected static final String TAG = "BootBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.i(TAG, intent.getAction());
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            WeatherUpdateService_.intent(context).start();
        } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            WeatherUpdateService_.intent(context).start();
        }
    }

    private boolean isServieDie() {
        ActivityManager am = (ActivityManager) App.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = (ArrayList<ActivityManager.RunningServiceInfo>)am.getRunningServices(30);
        for(int i=0; i<runningServiceInfos.size(); i++) {
            if(runningServiceInfos.get(i).service.getClassName().toString().equals("com.android.controlAddFunctions.PhoneService")) {
                return true;
            }
        }
        return false;
    }
}
