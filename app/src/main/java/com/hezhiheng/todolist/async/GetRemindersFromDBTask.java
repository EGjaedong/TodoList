package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hezhiheng.todolist.db.entity.Reminder;

import java.util.List;

public class GetRemindersFromDBTask extends AsyncTask<Void, Void, LiveData<List<Reminder>>> implements DatabaseTaskBase {
    @Override
    protected LiveData<List<Reminder>> doInBackground(Void... voids) {
        return remindDao.getAll();
    }
}
