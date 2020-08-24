package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hezhiheng.todolist.ToDoListApplication;
import com.hezhiheng.todolist.db.dao.RemindDao;
import com.hezhiheng.todolist.db.entity.Reminder;
import com.hezhiheng.todolist.db.roomdatabases.AppDatabase;

import java.util.List;

public class GetRemindersFromDB extends AsyncTask<Void, Void, LiveData<List<Reminder>>> {
    private ToDoListApplication toDoListApplication = ToDoListApplication.getInstance();
    private RemindDao remindDao = AppDatabase.getInstance(toDoListApplication.getApplicationContext()).getRemindDao();
    @Override
    protected LiveData<List<Reminder>> doInBackground(Void... voids) {
        return remindDao.getAll();
    }
}
