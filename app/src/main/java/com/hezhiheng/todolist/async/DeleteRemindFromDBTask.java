package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import com.hezhiheng.todolist.db.entity.Reminder;

public class DeleteRemindFromDBTask extends AsyncTask<Reminder, Void, Void> implements DatabaseTaskBase {
    @Override
    protected Void doInBackground(Reminder... reminders) {
        remindDao.deleteOne(reminders[FIRST_INDEX]);
        return null;
    }
}
