package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import com.hezhiheng.todolist.db.entity.User;

public class GetUserFromDBTask extends AsyncTask<Void, Void, User> implements DatabaseTaskBase {
    @Override
    protected User doInBackground(Void... voids) {
        return userDao.getFirstUser();
    }
}
