package com.hezhiheng.todolist.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;

public class AlarmService extends IntentService {
    public static final String TITLE_KEY = "title";
    public static final String DESC_KEY = "desc";
    public static final String TIME_KEY = "time";

    public AlarmService() {
        super("Alarm service");
    }

    public AlarmService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String title = "";
        String desc = "";
        LocalDateTime localDateTime = null;
        if (intent != null) {
            title = intent.getStringExtra(TITLE_KEY);
            desc = intent.getStringExtra(DESC_KEY);
            localDateTime = LocalDateTime.parse(intent.getStringExtra(TIME_KEY));
        }
        if (localDateTime != null) {
            new AlarmUtil().setAlarm(title, desc, localDateTime);
        }
    }
}
