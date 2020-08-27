package com.hezhiheng.todolist.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.hezhiheng.todolist.R;
import com.hezhiheng.todolist.ToDoListApplication;
import com.hezhiheng.todolist.view.MainActivity;

import java.time.LocalDateTime;

public class NotificationHelper extends ContextWrapper {
    private NotificationManager mNotificationManager;
    private Context mContext = ToDoListApplication.getInstance().getApplicationContext();

    private static final String CHANNEL_NAME = "Default Channel";
    private static final String CHANNEL_DESCRIPTION = "Default notify desc";
    private static final int REQUEST_CODE = 0;

    private String channelId;

    public NotificationHelper(Context base) {
        super(base);
        channelId = getString(R.string.channel_id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mNotificationChannel = new NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationChannel.setDescription(CHANNEL_DESCRIPTION);
            getNotificationManager().createNotificationChannel(mNotificationChannel);
        }
    }

    public NotificationCompat.Builder getNotificationBuilder(String title, String desc) {
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(this, channelId);
        } else {
            builder = new NotificationCompat.Builder(this);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
        builder.setContentTitle(title);
        builder.setContentText(desc);
        builder.setSmallIcon(R.mipmap.check_selected);
        builder.setAutoCancel(true);

        Intent notifyIntent = new Intent(mContext, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(mContext, REQUEST_CODE,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(notifyPendingIntent);
        return builder;
    }

    public void notify(int id, NotificationCompat.Builder builder) {
        if (getNotificationManager() != null) {
            getNotificationManager().notify(id, builder.build());
        }
    }

    private NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }
}
