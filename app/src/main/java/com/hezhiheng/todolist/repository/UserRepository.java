package com.hezhiheng.todolist.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.hezhiheng.todolist.async.GetResponseTask;
import com.hezhiheng.todolist.async.GetUserFromDB;
import com.hezhiheng.todolist.async.SaveUserTask;
import com.hezhiheng.todolist.db.entity.User;

import java.util.concurrent.ExecutionException;

public class UserRepository {
    private Gson gson = new Gson();
    private SaveUserTask saveUserTask = new SaveUserTask();
    private GetUserFromDB getUserFromDB = new GetUserFromDB();

    public UserRepository() {
    }

    public LiveData<User> getUser(String userName) {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        User user = findUserFromDB(userName);
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
            saveUserTask.execute(user);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User findUserFromDB(String userName) {
        User user = null;
        try {
            user = getUserFromDB.execute(userName).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
}
