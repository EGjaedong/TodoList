package com.hezhiheng.todolist.repository;

import androidx.lifecycle.LiveData;

import com.hezhiheng.todolist.async.DeleteRemindFromDBTask;
import com.hezhiheng.todolist.async.GetRemindFromDBTask;
import com.hezhiheng.todolist.async.GetRemindersFromDBTask;
import com.hezhiheng.todolist.async.SaveRemindToDBTask;
import com.hezhiheng.todolist.async.UpdateRemindToDBTask;
import com.hezhiheng.todolist.db.entity.Reminder;

import java.util.List;

public class ReminderRepository {
    public void saveOne(Reminder reminder) {
        new SaveRemindToDBTask().execute(reminder);
    }

    public LiveData<List<Reminder>> getAll() throws Exception {
        return new GetRemindersFromDBTask().execute().get();
    }

    public int updateOne(Reminder reminder) throws Exception {
        return new UpdateRemindToDBTask().execute(reminder).get();
    }

    public Reminder getOneById(int id) throws Exception {
        return new GetRemindFromDBTask().execute(id).get();
    }

    public void deleteOne(Reminder reminder) {
        new DeleteRemindFromDBTask().execute(reminder);
    }
}
