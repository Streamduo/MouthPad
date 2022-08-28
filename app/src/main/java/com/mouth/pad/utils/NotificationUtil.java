package com.mouth.pad.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.mouth.pad.MyApplication;
import com.mouth.pad.R;


/**
 * @ClassName: NotificationUtils
 * @Description: 通知栏工具类
 * @Author: JiangMin
 * @CreateDate: 2021/2/25 15:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/25 15:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NotificationUtil extends ContextWrapper {
    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = MyApplication.Companion.getAppContext().getString(R.string.app_name);
    private Context mContext;

    public NotificationUtil(Context context) {
        super(context);
        this.mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content, Intent intent) {
        if (intent == null) {
            return new Notification.Builder(getApplicationContext(), id)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setAutoCancel(true);
        } else {
            return new Notification.Builder(getApplicationContext(), id)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(getPendingIntent(intent))
                    .setAutoCancel(true);
        }

    }

    private PendingIntent getPendingIntent(Intent intentClick) {
        //如果是一个singletask activity 则需要添加FLAG_ACTIVITY_CLEAR_TOP ，否则启动可能失败
        intentClick.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentClick.addCategory("android.intent.category.LAUNCHER");
        intentClick.setAction("android.intent.action.MAIN");
        return PendingIntent.getActivity(mContext, 0, intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public NotificationCompat.Builder getNotification_25(String title, String content, Intent intent) {
        if (intent == null) {
            return new NotificationCompat.Builder(getApplicationContext(), id)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);
        } else {
            return new NotificationCompat.Builder(getApplicationContext(), id)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(getPendingIntent(intent))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);
        }
    }


    public void sendTitleNotification(String title, String content) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content, null).build();
            getManager().notify((int) System.currentTimeMillis() / 1000, notification);
        } else {
            Notification notification = getNotification_25(title, content, null).build();
            getManager().notify((int) System.currentTimeMillis() / 1000, notification);
        }
    }

}
