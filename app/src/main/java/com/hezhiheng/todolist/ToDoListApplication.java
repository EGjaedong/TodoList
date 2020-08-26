package com.hezhiheng.todolist;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class ToDoListApplication extends Application {
    private static ToDoListApplication instance;
    private static SharedPreferences sharedPreferences;

    public static ToDoListApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.user_already_login_file), Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
