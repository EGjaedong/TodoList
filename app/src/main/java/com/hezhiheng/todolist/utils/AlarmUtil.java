package com.hezhiheng.todolist.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.hezhiheng.todolist.ToDoListApplication;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class AlarmUtil {
    public static final String INTENT_ALARM_ACTION = "com.hezhiheng.todolist.RECEIVER";
    private static final int REQUEST_CODE = 0;
    private static final int FLAGS = 0;
    private final Context mContext = ToDoListApplication.getInstance().getApplicationContext();
    private final String packageName = mContext.getPackageName();
    private final String cls = packageName + ".utils.NotifyBroadcast";

    public void setAlarm(final String title, final String desc, final LocalDateTime localDateTime) {
        Intent intent = new Intent();
        intent.setAction(INTENT_ALARM_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setComponent(new ComponentName(packageName, cls));
        }
        intent.putExtra(AlarmService.TITLE_KEY, title);
        intent.putExtra(AlarmService.DESC_KEY, desc);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, REQUEST_CODE, intent, FLAGS);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli(), pendingIntent);
        }
    }
}
