package com.hezhiheng.todolist.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.hezhiheng.todolist.db.dao.UserDao;
import com.hezhiheng.todolist.db.entity.User;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserRepository {
    private static final String USER_URL = "https://twc-android-bootcamp.github.io/fake-data/data/user.json";

    private UserDao userDao;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Request request = new Request.Builder().url(USER_URL).build();
    Call call = okHttpClient.newCall(request);

    public LiveData<User> getUser(String userName) {
        refreshUser(userName);
        return getUser(userName);
    }

    private void refreshUser(String userName){
        MutableLiveData<User> result = new MutableLiveData<>();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                result.setValue(null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        final String responseString = response.body().string();
                        User user = new Gson().fromJson(responseString, User.class);
                        result.setValue(user);
                    }
                }
            }
        });
        LiveData<User> userFromDB = userDao.getUser(userName);
        if (result.getValue() != null && !result.getValue().equals(userFromDB.getValue())){
            userDao.updateUser(result.getValue());
        }
    }
}
