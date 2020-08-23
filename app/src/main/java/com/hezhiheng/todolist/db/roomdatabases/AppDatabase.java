package com.hezhiheng.todolist.db.roomdatabases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.hezhiheng.todolist.db.converter.DateConverter;
import com.hezhiheng.todolist.db.dao.RemindDao;
import com.hezhiheng.todolist.db.dao.UserDao;
import com.hezhiheng.todolist.db.entity.Reminder;
import com.hezhiheng.todolist.db.entity.User;

@Database(entities = {User.class, Reminder.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    private static final String DATA_BASE_NAME = "do-to-list-db";

    public static AppDatabase getInstance(Context context){
        synchronized (AppDatabase.class){
            if (instance == null){
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATA_BASE_NAME).build();
            }
        }
        return instance;
    }

    public abstract UserDao getUserDao();
    public abstract RemindDao getRemindDao();
}
