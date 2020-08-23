package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import com.hezhiheng.todolist.ToDoListApplication;
import com.hezhiheng.todolist.db.dao.RemindDao;
import com.hezhiheng.todolist.db.entity.Reminder;
import com.hezhiheng.todolist.db.roomdatabases.AppDatabase;

public class SaveRemindTask extends AsyncTask<Reminder, Void, Void> {
    private ToDoListApplication toDoListApplication = ToDoListApplication.getInstance();
    private RemindDao remindDao = AppDatabase.
            getInstance(toDoListApplication.getApplicationContext()).getRemindDao();
    @Override
    protected Void doInBackground(Reminder... reminders) {
        remindDao.saveOne(reminders[0]);
        return null;
    }
}
