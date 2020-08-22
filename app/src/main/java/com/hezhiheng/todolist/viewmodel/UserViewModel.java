package com.hezhiheng.todolist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hezhiheng.todolist.db.entity.User;
import com.hezhiheng.todolist.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private LiveData<User> mUserLiveData;
    private UserRepository userRepository;

    public UserViewModel(UserRepository repository) {
        userRepository = repository;
    }

    public void getUser(String userName) {
        mUserLiveData = userRepository.getUser(userName);
    }

    public boolean login(String username, String password) {
        getUser(username);
        User user = mUserLiveData.getValue();
        if (user != null) {
            return user.getName().equals(username) && user.getPassword().equals(password);
        }
        return false;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final UserRepository mRepository;

        public Factory() {
            mRepository = new UserRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserViewModel(mRepository);
        }
    }
}
