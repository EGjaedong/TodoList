package com.hezhiheng.todolist.view;

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
import com.hezhiheng.todolist.viewmodel.UserViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AppCompatActivity {
    private UserViewModel model;
    private String usernamePattern = "^[\\d|a-z|A-Z]{3,12}$";
    private String passwordPattern = "^[\\w|\\W]{6,18}$";

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

    private boolean usernameJudge = false;
    private boolean passwordJudge = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        UserViewModel.Factory factory = new UserViewModel.Factory();
        model = new ViewModelProvider(this, factory).get(UserViewModel.class);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_btn, R.id.btn_error_username, R.id.btn_error_password})
    void loginClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                model.login(editUsername.getText().toString(), editPassword.getText().toString());
                break;
            case R.id.btn_error_username:
                usernameErrorText.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        usernameErrorText.setVisibility(View.GONE);
                    }
                }, 3000);
                break;
            case R.id.btn_error_password:
                passwordErrorText.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        passwordErrorText.setVisibility(View.GONE);
                    }
                }, 3000);
                break;
        }
    }

    @OnTextChanged(R.id.edit_username)
    void judgeUsernameText(CharSequence text) {
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
