package com.hezhiheng.todolist.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hezhiheng.todolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderItemActivity extends AppCompatActivity {
    @BindView(R.id.btn_select_date)
    Button btnSelectDate;
    @BindView(R.id.date_selector)
    CalendarView calendarView;
    @BindView(R.id.calendar_container)
    FrameLayout calendarContainer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_item_layout);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_select_date})
    void btnClick(View view) {
        calendarContainer.setVisibility(View.VISIBLE);
    }
}
