package com.hezhiheng.todolist.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hezhiheng.todolist.db.entity.User;
import com.hezhiheng.todolist.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();

    public LiveData<User> getUser(String userName){
        return userRepository.getUser(userName);
    }
}
