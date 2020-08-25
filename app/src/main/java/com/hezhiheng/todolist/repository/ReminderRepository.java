package com.hezhiheng.todolist.repository;

import androidx.lifecycle.LiveData;

import com.hezhiheng.todolist.async.GetRemindFromDBTask;
import com.hezhiheng.todolist.async.GetRemindersFromDBTask;
import com.hezhiheng.todolist.async.SaveRemindToDBTask;
import com.hezhiheng.todolist.async.UpdateRemindToDBTask;
import com.hezhiheng.todolist.db.entity.Reminder;

import java.util.List;

public class ReminderRepository {
    public void save(Reminder reminder) throws Exception {
        new SaveRemindToDBTask().execute(reminder).get();
    }

    public LiveData<List<Reminder>> getAll() throws Exception {
        return new GetRemindersFromDBTask().execute().get();
    }

    public int updateRemind(Reminder reminder) throws Exception {
        return new UpdateRemindToDBTask().execute(reminder).get();
    }

    public Reminder getOneById(int id) throws Exception {
        return new GetRemindFromDBTask().execute(id).get();
    }
}
