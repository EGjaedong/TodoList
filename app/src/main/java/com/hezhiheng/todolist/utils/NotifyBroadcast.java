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
            String title = intent.getStringExtra(AlarmService.TITLE_KEY);
            String desc = intent.getStringExtra(AlarmService.DESC_KEY);
            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder builder = notificationHelper.getNotification(title, desc);
            builder.build();
            notificationHelper.notify(NOTIFICATION_ID, builder);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setClass(context, AlarmService.class);
            context.stopService(intent);
        }
    }
}
