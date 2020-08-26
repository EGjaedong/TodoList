package com.hezhiheng.todolist.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotifyBroadcast extends BroadcastReceiver {
    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast action", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onReceive: 1");
        String action = intent.getAction();
        if (action.equals(intent.getAction())) {
            Log.e("--------", action);
            String title = intent.getStringExtra("title");
            String desc = intent.getStringExtra("desc");
            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder builder = notificationHelper.getNotification(title, desc);
            builder.build();
            notificationHelper.notify(1, builder);
            Toast.makeText(context, "Broadcast action", Toast.LENGTH_SHORT).show();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setClass(context, AlarmService.class);
            context.stopService(intent);
        }
    }
}
