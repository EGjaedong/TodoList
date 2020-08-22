package com.hezhiheng.todolist.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hezhiheng.todolist.async.GetResponseTask;
import com.hezhiheng.todolist.async.GetUserFromDB;
import com.hezhiheng.todolist.async.SaveUserTask;
import com.hezhiheng.todolist.db.entity.User;

import java.util.concurrent.ExecutionException;

public class UserRepository {
    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    public UserRepository() {
    }

    public LiveData<User> getUser() {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        User user = findUserFromDB();
        if (user == null){
            user = getUserFromService();
        }
        userLiveData.setValue(user);
        return userLiveData;
    }

    private User getUserFromService() {
        User user = null;
        GetResponseTask getResponseTask = new GetResponseTask();
        try {
            String response = getResponseTask.execute().get();
            user = gson.fromJson(response, User.class);
            SaveUserTask saveUserTask = new SaveUserTask();
            saveUserTask.execute(user);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User findUserFromDB() {
        User user = null;
        try {
            GetUserFromDB getUserFromDB = new GetUserFromDB();
            user = getUserFromDB.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
}
