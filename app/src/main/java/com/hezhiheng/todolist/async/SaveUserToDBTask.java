package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import com.hezhiheng.todolist.db.entity.User;

public class SaveUserToDBTask extends AsyncTask<User, Void, Void> implements DatabaseTaskBase {
    @Override
    protected Void doInBackground(User... users) {
        userDao.insertOne(users[FIRST_INDEX]);
        return null;
    }
}
