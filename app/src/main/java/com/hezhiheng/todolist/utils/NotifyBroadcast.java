package com.hezhiheng.todolist.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotifyBroadcast extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(AlarmUtil.INTENT_ALARM_ACTION)) {
            String title = intent.getStringExtra(AlarmUtil.TITLE_KEY);
            String desc = intent.getStringExtra(AlarmUtil.DESC_KEY);
            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder builder = notificationHelper.getNotificationBuilder(title, desc);
            builder.build();
            notificationHelper.notify(NOTIFICATION_ID, builder);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setClass(context, AlarmUtil.class);
            context.stopService(intent);
        }
    }
}
