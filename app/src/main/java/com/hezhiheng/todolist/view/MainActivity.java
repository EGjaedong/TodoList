package com.hezhiheng.todolist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hezhiheng.todolist.R;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}