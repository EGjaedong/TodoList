package com.hezhiheng.todolist.async;

import com.hezhiheng.todolist.ToDoListApplication;
import com.hezhiheng.todolist.db.dao.RemindDao;
import com.hezhiheng.todolist.db.dao.UserDao;
import com.hezhiheng.todolist.db.roomdatabases.AppDatabase;

public interface DatabaseTaskBase {
    int FIRST_INDEX = 0;

    ToDoListApplication toDoListApplication = ToDoListApplication.getInstance();
    RemindDao remindDao = AppDatabase.
            getInstance(toDoListApplication.getApplicationContext()).getRemindDao();
    UserDao userDao = AppDatabase.
            getInstance(toDoListApplication.getApplicationContext()).getUserDao();
}
