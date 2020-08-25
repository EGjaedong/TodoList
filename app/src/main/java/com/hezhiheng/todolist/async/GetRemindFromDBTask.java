package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import com.hezhiheng.todolist.db.entity.Reminder;

public class GetRemindFromDBTask extends AsyncTask<Integer, Void, Reminder> implements DatabaseTaskBase {
    @Override
    protected Reminder doInBackground(Integer... integers) {
        return remindDao.getOneById(integers[0]);
    }
}
