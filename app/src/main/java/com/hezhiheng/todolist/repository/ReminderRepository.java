package com.hezhiheng.todolist.repository;

import androidx.lifecycle.LiveData;

import com.hezhiheng.todolist.async.GetRemindersFromDB;
import com.hezhiheng.todolist.async.SaveRemindTask;
import com.hezhiheng.todolist.db.entity.Reminder;

import java.util.List;

public class ReminderRepository {
    public void save(Reminder reminder) throws Exception {
        new SaveRemindTask().execute(reminder).get();
    }

    public LiveData<List<Reminder>> getAll() throws Exception {
        return new GetRemindersFromDB().execute().get();
    }
}
