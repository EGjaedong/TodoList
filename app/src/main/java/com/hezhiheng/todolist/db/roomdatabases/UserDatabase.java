package com.hezhiheng.todolist.db.roomdatabases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hezhiheng.todolist.db.dao.UserDao;
import com.hezhiheng.todolist.db.entity.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;
    private static final String DATA_NAME = "do-to-list-db";

    public static UserDatabase getInstance(Context context){
        synchronized (UserDatabase.class){
            if (instance == null){
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase.class, DATA_NAME).build();
            }
        }
        return instance;
    }

    public abstract UserDao getUserDao();
}
