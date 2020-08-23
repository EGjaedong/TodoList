package com.hezhiheng.todolist.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hezhiheng.todolist.R;

import java.time.LocalDate;

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
    @BindView(R.id.btn_back)
    ImageButton btnBack;

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

    @OnClick({R.id.btn_select_date, R.id.btn_save_remind, R.id.btn_back})
    void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_select_date:
                if (!showCalender) {
                    calendarChangeVisibility(true);
                } else {
                    calendarChangeVisibility(false);
                }
                break;
            case R.id.btn_save_remind:
                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    void calendarChangeVisibility(Boolean toVisibility) {
        if (toVisibility){
            calendarContainer.setVisibility(View.VISIBLE);
            showCalender = true;
        }else {
            calendarContainer.setVisibility(View.GONE);
            showCalender = false;
        }
    }
}
