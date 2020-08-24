package com.hezhiheng.todolist.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import com.hezhiheng.todolist.R;
import com.hezhiheng.todolist.viewmodel.ReminderItemViewModel;

import java.time.LocalDate;

import butterknife.BindColor;
import butterknife.BindString;
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
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.finish_check_box)
    CheckBox finishRemindCheck;
    @BindView(R.id.remind_switch)
    SwitchCompat systemRemindSwitch;
    @BindString(R.string.year)
    String yearString;
    @BindString(R.string.month)
    String monthString;
    @BindString(R.string.day)
    String dayString;
    @BindColor(R.color.btn_date_text_color)
    int btnDateTextColor;

    private ReminderItemViewModel remindViewModel;
    private boolean showCalender = false;
    private LocalDate selectDate = null;
    private String title;
    private String desc;
    private boolean isRemindFinish;
    private boolean isSetSystemRemind = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_item_layout);
        ButterKnife.bind(this);
        ReminderItemViewModel.Factory factory = new ReminderItemViewModel.Factory();
        remindViewModel = new ViewModelProvider(this, factory).get(ReminderItemViewModel.class);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectDate = LocalDate.of(year, month + MONTH_ADD_NUMBER, dayOfMonth);
            btnSelectDate.setText(selectDate.toString());
            btnSelectDate.setTextColor(btnDateTextColor);
        });
        calendarContainer.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                calendarContainer.setVisibility(View.GONE);
            }
        });
        systemRemindSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isSetSystemRemind = !isSetSystemRemind;
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
                getRemindData();
                if (title != null && selectDate != null) {
                    if (remindViewModel.saveRemind(title, desc, selectDate, isRemindFinish, isSetSystemRemind)) {
                        this.finish();
                    }
                }
                break;
        }
    }

    void calendarChangeVisibility(Boolean toVisibility) {
        if (toVisibility) {
            calendarContainer.setVisibility(View.VISIBLE);
            showCalender = true;
        } else {
            calendarContainer.setVisibility(View.GONE);
            showCalender = false;
        }
    }

    void getRemindData() {
        title = editRemindTitle.getText().toString();
        desc = editRemindDesc.getText().toString();
        isRemindFinish = finishRemindCheck.isChecked();
    }
}
