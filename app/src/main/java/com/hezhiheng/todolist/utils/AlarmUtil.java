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
import java.util.HashMap;
import java.util.Map;

public class AlarmUtil {
    public static final String INTENT_ALARM_ACTION = "com.hezhiheng.todolist.RECEIVER";
    public static final String TITLE_KEY = "title";
    public static final String DESC_KEY = "desc";

    private static AlarmUtil instance;
    private final Context mContext;
    private final String packageName;
    private final String cls;
    private AlarmManager alarmManager;
    private Map<Integer, PendingIntent> pendingIntentMap;

    private AlarmUtil() {
        mContext = ToDoListApplication.getInstance().getApplicationContext();
        packageName = mContext.getPackageName();
        cls = packageName + ".utils.NotifyBroadcast";
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        pendingIntentMap = new HashMap<>();
    }

    public static AlarmUtil getInstance() {
        synchronized (AlarmUtil.class) {
            if (instance == null) {
                instance = new AlarmUtil();
            }
        }
        return instance;
    }

    public void setAlarm(final String title, final String desc,
                         final LocalDateTime localDateTime, final int requestCode) {
        Intent intent = new Intent(mContext, NotifyBroadcast.class);
        intent.setAction(INTENT_ALARM_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setComponent(new ComponentName(packageName, cls));
        }
        intent.putExtra(TITLE_KEY, title);
        intent.putExtra(DESC_KEY, desc);
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        if (alarmManager != null) {
            pendingIntentMap.put(requestCode, pendingIntent);
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli(), pendingIntent);
        }
    }

    public void cancelAlarm(int cancelAlarmId) {
        PendingIntent pendingIntent = pendingIntentMap.get(cancelAlarmId);
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntentMap.remove(cancelAlarmId);
        }
    }
}
