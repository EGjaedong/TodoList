package com.hezhiheng.todolist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hezhiheng.todolist.db.entity.User;
import com.hezhiheng.todolist.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<User> mUserLiveData = new MutableLiveData<>();
    private UserRepository userRepository;

    public UserViewModel(UserRepository repository){
        userRepository = repository;
    }

    public void getUser(String userName){
        mUserLiveData.setValue(userRepository.getUser(userName).getValue());
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final UserRepository mRepository;
        public Factory(){
            mRepository = UserRepository.getInstance();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserViewModel(mRepository);
        }
    }
}
