package com.hezhiheng.todolist.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.hezhiheng.todolist.ToDoListApplication;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class AlarmUtil {
    private static final int REQUEST_CODE = 0;
    private static final int FLAGS = 0;
    public static final String INTENT_ALARM_LOG = "com.hezhiheng.todolist.RECEIVER";
    private static final String TAG = "com.hezhiheng.todolist";
    private final Context mContext = ToDoListApplication.getInstance().getApplicationContext();

    public void setAlarm(final String title, final String desc, final LocalDateTime localDateTime) {
        Intent intent = new Intent();
        intent.setAction(INTENT_ALARM_LOG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setComponent(new ComponentName("com.hezhiheng.todolist", "com.hezhiheng.todolist.utils.NotifyBroadcast"));
        }
        intent.putExtra("title", title);
        intent.putExtra("desc", desc);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, REQUEST_CODE, intent, FLAGS);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli(), pendingIntent);
            Log.e(TAG, localDateTime.toString());
        }else {
            Log.e(TAG, "not found");
        }
    }
}
