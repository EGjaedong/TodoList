package com.hezhiheng.todolist.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hezhiheng.todolist.R;
import com.hezhiheng.todolist.enums.UserFindResultEnum;
import com.hezhiheng.todolist.viewmodel.UserViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AppCompatActivity {
    private UserViewModel model;
    private static final int USER_ALREADY_LOGIN_FLAG = 123456;
    private static final int DEFAULT_LOGIN_FLAG = 0;
    private boolean usernameJudge = false;
    private boolean passwordJudge = false;
    private SharedPreferences sharedPreferences;

    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.btn_error_username)
    ImageButton errorUsernameBtn;
    @BindView(R.id.btn_error_password)
    ImageButton errorPasswordBtn;
    @BindView(R.id.login_btn)
    Button btnLogin;
    @BindView(R.id.username_error_text)
    TextView usernameErrorText;
    @BindView(R.id.password_error_text)
    TextView passwordErrorText;
    @BindString(R.string.user_not_found_message)
    String userNotFound;
    @BindString(R.string.password_error_message)
    String passwordError;
    @BindString(R.string.user_already_login_file)
    String userAlreadyLoginFileName;
    @BindString(R.string.user_already_login_key)
    String userAlreadyLoginKey;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        UserViewModel.Factory factory = new UserViewModel.Factory();
        model = new ViewModelProvider(this, factory).get(UserViewModel.class);
        sharedPreferences = getSharedPreferences(userAlreadyLoginFileName, Context.MODE_PRIVATE);
        ButterKnife.bind(this);
        if (judgeUserIsAlreadyLogin()) {
            goMainActivity();
        }
    }

    private boolean judgeUserIsAlreadyLogin() {
        return sharedPreferences.getInt(userAlreadyLoginKey, DEFAULT_LOGIN_FLAG)
                == USER_ALREADY_LOGIN_FLAG;
    }

    @OnClick({R.id.login_btn, R.id.btn_error_username, R.id.btn_error_password})
    void loginClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                UserFindResultEnum loginResult = model.login(editUsername.getText().toString(),
                        editPassword.getText().toString());
                if (loginResult != UserFindResultEnum.OK) {
                    showLoginResult(loginResult);
                } else {
                    setAlreadyLogin();
                    goMainActivity();
                }
                break;
            case R.id.btn_error_username:
                showErrorMessage(usernameErrorText);
                break;
            case R.id.btn_error_password:
                showErrorMessage(passwordErrorText);
                break;
        }
    }

    private void setAlreadyLogin() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(userAlreadyLoginKey, USER_ALREADY_LOGIN_FLAG);
        editor.apply();
    }

    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void showLoginResult(UserFindResultEnum loginResult) {
        switch (loginResult) {
            case USER_NOT_FOUND:
                showToastOfLoginResult(userNotFound);
                break;
            case PASSWORD_ERROR:
                showToastOfLoginResult(passwordError);
        }
    }

    private void showToastOfLoginResult(String loginResultMessage) {
        Toast.makeText(this, loginResultMessage, Toast.LENGTH_SHORT).show();
    }

    private void showErrorMessage(TextView textView) {
        textView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> textView.setVisibility(View.GONE), 3000);
    }

    @OnTextChanged(R.id.edit_username)
    void judgeUsernameText(CharSequence text) {
        String usernamePattern = "^[\\d|a-z|A-Z]{3,12}$";
        usernameJudge = regularJudge(usernamePattern, text.toString());
        if (usernameJudge) {
            hideErrorBtn(errorUsernameBtn);
        } else {
            showErrorBtn(errorUsernameBtn);
        }
        isBtnLoginAvailable();
    }

    @OnTextChanged(R.id.edit_password)
    void judgePasswordText(CharSequence text) {
        String passwordPattern = "^[\\w|\\W]{6,18}$";
        passwordJudge = regularJudge(passwordPattern, text.toString());
        if (passwordJudge) {
            hideErrorBtn(errorPasswordBtn);
        } else {
            showErrorBtn(errorPasswordBtn);
        }
        isBtnLoginAvailable();
    }

    private void isBtnLoginAvailable() {
        if (usernameJudge && passwordJudge) {
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.login_btn_available);
        } else {
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundResource(R.drawable.login_btn_unavailable);
        }
    }

    private void hideErrorBtn(ImageButton button) {
        button.setVisibility(View.GONE);
    }

    private void showErrorBtn(ImageButton button) {
        button.setVisibility(View.VISIBLE);
    }

    private boolean regularJudge(String patternString, String target) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }
}
