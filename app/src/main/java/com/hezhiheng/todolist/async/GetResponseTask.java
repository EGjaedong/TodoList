package com.hezhiheng.todolist.async;

import android.os.AsyncTask;

import com.hezhiheng.todolist.api.RequestUser;

public class GetResponseTask extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... voids) {
        return new RequestUser().getUserFromWeb();
    }
}
