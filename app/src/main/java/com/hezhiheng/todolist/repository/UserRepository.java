package com.hezhiheng.todolist.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hezhiheng.todolist.async.GetResponseTask;
import com.hezhiheng.todolist.async.GetUserFromDBTask;
import com.hezhiheng.todolist.async.SaveUserToDBTask;
import com.hezhiheng.todolist.db.entity.User;

import java.util.concurrent.ExecutionException;

public class UserRepository {
    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public UserRepository() {
    }

    public LiveData<User> getUser() {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        User user = findUserFromDB();
        if (user == null) {
            user = getUserFromService();
        }
        userLiveData.setValue(user);
        return userLiveData;
    }

    private User getUserFromService() {
        User user = null;
        try {
            String response = new GetResponseTask().execute().get();
            user = gson.fromJson(response, User.class);
            new SaveUserToDBTask().execute(user);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User findUserFromDB() {
        User user = null;
        try {
            user = new GetUserFromDBTask().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
}
