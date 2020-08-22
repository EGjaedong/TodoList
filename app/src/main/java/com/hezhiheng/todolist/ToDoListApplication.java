package com.hezhiheng.todolist;

import android.app.Application;

public class ToDoListApplication extends Application {
    private static ToDoListApplication instance;
    public static ToDoListApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
