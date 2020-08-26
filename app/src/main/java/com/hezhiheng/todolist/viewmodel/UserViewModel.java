package com.hezhiheng.todolist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hezhiheng.todolist.db.entity.User;
import com.hezhiheng.todolist.enums.UserFindResultEnum;
import com.hezhiheng.todolist.repository.UserRepository;
import com.hezhiheng.todolist.utils.MD5Util;

public class UserViewModel extends ViewModel {
    private LiveData<User> mUserLiveData;
    private UserRepository userRepository;

    public UserViewModel(UserRepository repository) {
        userRepository = repository;
        getUser();
    }

    public void getUser() {
        mUserLiveData = userRepository.getUser();
    }

    public UserFindResultEnum login(String username, String password) {
        User user = mUserLiveData.getValue();
        if (user != null) {
            if (!user.getName().equals(username)){
                return UserFindResultEnum.USER_NOT_FOUND;
            }
            if (user.getName().equals(username) && user.getPassword().equals(MD5Util.md5(password))){
                return UserFindResultEnum.OK;
            }else {
                return UserFindResultEnum.PASSWORD_ERROR;
            }
        }
        return UserFindResultEnum.USER_NOT_FOUND;
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
