package com.hezhiheng.todolist.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotifyBroadcast extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(AlarmUtil.INTENT_ALARM_ACTION)) {
            String title = intent.getStringExtra(AlarmUtil.TITLE_KEY);
            String desc = intent.getStringExtra(AlarmUtil.DESC_KEY);
            NotificationHelper notificationHelper = new NotificationHelper(context);
            notificationHelper.sendNotify(NOTIFICATION_ID, title, desc);
        }
    }
}
