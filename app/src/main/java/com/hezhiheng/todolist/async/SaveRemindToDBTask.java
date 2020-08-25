package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import com.hezhiheng.todolist.db.entity.Reminder;

public class SaveRemindToDBTask extends AsyncTask<Reminder, Void, Void> implements DatabaseTaskBase {
    @Override
    protected Void doInBackground(Reminder... reminders) {
        remindDao.saveOne(reminders[0]);
        return null;
    }
}
