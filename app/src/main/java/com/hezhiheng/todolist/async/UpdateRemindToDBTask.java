package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import com.hezhiheng.todolist.db.entity.Reminder;

public class UpdateRemindToDBTask extends AsyncTask<Reminder, Void, Integer> implements DatabaseTaskBase {
    @Override
    protected Integer doInBackground(Reminder... reminders) {
        return remindDao.update(reminders[FIRST_INDEX]);
    }
}
