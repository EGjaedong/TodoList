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
    private static final String PASSWORD_CIPHER_TEXT = "e10adc3949ba59abbe56e057f20f883e";
    private static final String ORIGINAL_PASSWORD = "123456";
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
        try {
            String response = new GetResponseTask().execute().get();
            user = gson.fromJson(response, User.class);
            if (PASSWORD_CIPHER_TEXT.equals(user.getPassword())){
                user.setPassword(ORIGINAL_PASSWORD);
            }
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
