package com.hezhiheng.todolist.repository;

import com.hezhiheng.todolist.async.SaveRemindTask;
import com.hezhiheng.todolist.db.entity.Reminder;

public class ReminderRepository {
    public void save(Reminder reminder) throws Exception {
        new SaveRemindTask().execute(reminder).get();
    }
}
