package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import com.hezhiheng.todolist.ToDoListApplication;
import com.hezhiheng.todolist.db.dao.UserDao;
import com.hezhiheng.todolist.db.entity.User;
import com.hezhiheng.todolist.db.roomdatabases.UserDatabase;

public class GetUserFromDB extends AsyncTask<Void, Void, User> {
    private ToDoListApplication toDoListApplication = ToDoListApplication.getInstance();
    private UserDao userDao = UserDatabase.getInstance(toDoListApplication.getApplicationContext()).getUserDao();
    @Override
    protected User doInBackground(Void... voids) {
        return userDao.getUserByName();
    }
}
