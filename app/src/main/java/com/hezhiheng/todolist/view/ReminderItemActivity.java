package com.hezhiheng.todolist.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hezhiheng.todolist.R;

import java.time.LocalDate;
import java.time.Month;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderItemActivity extends AppCompatActivity {
    private static final int MONTH_ADD_NUMBER = 1;

    @BindView(R.id.btn_select_date)
    Button btnSelectDate;
    @BindView(R.id.calendar)
    CalendarView calendarView;
    @BindView(R.id.calendar_container)
    FrameLayout calendarContainer;
    @BindView(R.id.remind_title)
    EditText editRemindTitle;
    @BindView(R.id.remind_desc)
    EditText editRemindDesc;
    @BindView(R.id.btn_save_remind)
    ImageButton btnSaveRemind;
    private LocalDate selectDate;

    private boolean showCalender = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_item_layout);
        ButterKnife.bind(this);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectDate = LocalDate.of(year, month + MONTH_ADD_NUMBER, dayOfMonth);
        });
    }

    @OnClick({R.id.btn_select_date, R.id.btn_save_remind})
    void btnClick(Button button) {
        switch (button.getId()) {
            case R.id.btn_select_date:
                if (!showCalender) {
                    calendarContainer.setVisibility(View.VISIBLE);
                    showCalender = true;
                } else {
                    calendarContainer.setVisibility(View.GONE);
                    showCalender = false;
                }
                break;
            case R.id.btn_save_remind:

                break;
        }
    }
}
