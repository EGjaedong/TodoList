package com.hezhiheng.todolist.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hezhiheng.todolist.R;
import com.hezhiheng.todolist.viewmodel.UserViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private UserViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        UserViewModel.Factory factory = new UserViewModel.Factory();
        model = new ViewModelProvider(this, factory).get(UserViewModel.class);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_btn)
    void loginClick() {

    }
}
