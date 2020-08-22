package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import com.hezhiheng.todolist.ToDoListApplication;
import com.hezhiheng.todolist.db.dao.UserDao;
import com.hezhiheng.todolist.db.entity.User;
import com.hezhiheng.todolist.db.roomdatabases.UserDatabase;

public class GetUserFromDB extends AsyncTask<String, Void, User> {
    private static final int FIRST_INDEX = 0;
    private ToDoListApplication toDoListApplication = ToDoListApplication.getInstance();
    private UserDao userDao = UserDatabase.getInstance(toDoListApplication.getApplicationContext()).getUserDao();
    @Override
    protected User doInBackground(String... strings) {
        return userDao.getUserByName(strings[FIRST_INDEX]);
    }
}
