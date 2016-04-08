package com.njucm.cmdh.app.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.MainActivity;

import java.util.concurrent.TimeUnit;

/**
 * Created by Mesogene on 4/3/15.
 */
public class PopService extends Service {

    public final int NOTIFICATION_ID = 0X1123;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                while(true){
//                    try {
//                        TimeUnit.SECONDS.sleep(6);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
//                    Notification notify = new Notification();
//                    notify.icon = R.drawable.ic_launcher;
//                    notify.tickerText = "干燥！";
//                    notify.when = System.currentTimeMillis();
//                    notify.defaults = Notification.DEFAULT_SOUND;
//                    notify.defaults = Notification.DEFAULT_ALL;
//                    notify.setLatestEventInfo(getApplicationContext(), "监控节点A 田块1", "点击查看", pi);
//                    NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.notify(NOTIFICATION_ID, notify);
//                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
