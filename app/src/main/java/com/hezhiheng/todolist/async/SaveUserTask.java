package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import com.hezhiheng.todolist.ToDoListApplication;
import com.hezhiheng.todolist.db.dao.UserDao;
import com.hezhiheng.todolist.db.entity.User;
import com.hezhiheng.todolist.db.roomdatabases.AppDatabase;

public class SaveUserTask extends AsyncTask<User, Void, Void> {
    private static final int FIRST_INDEX = 0;
    private ToDoListApplication toDoListApplication = ToDoListApplication.getInstance();
    private UserDao userDao = AppDatabase.getInstance(toDoListApplication.getApplicationContext()).getUserDao();

    @Override
    protected Void doInBackground(User... users) {
        userDao.insertOne(users[FIRST_INDEX]);
        return null;
    }
}
