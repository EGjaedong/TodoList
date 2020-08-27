package com.hezhiheng.todolist.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.hezhiheng.todolist.ToDoListApplication;
import com.hezhiheng.todolist.db.entity.Reminder;
import com.hezhiheng.todolist.utils.AlarmUtil;
import com.hezhiheng.todolist.viewmodel.ReminderItemViewModel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderItemActivity extends AppCompatActivity {
    private static final int MONTH_ADD_NUMBER = 1;
    private static final int DEFAULT_REMIND_ID = 0;
    private static final int TARGET_HOUR = 6;
    private static final int TARGET_MINUTE = 0;
    private static final int TARGET_SECOND = 0;
    private final String packageName = "com.hezhiheng.todolist";
    private final String serviceAction = packageName + ".AlarmService";

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
    SwitchCompat remindSwitch;
    @BindString(R.string.year)
    String yearString;
    @BindString(R.string.month)
    String monthString;
    @BindString(R.string.day)
    String dayString;
    @BindColor(R.color.btn_date_text_color)
    int btnDateTextColor;
    @BindView(R.id.btn_remove)
    ImageButton btnRemove;
    @BindString(R.string.channel_id)
    String channelId;
    @BindString(R.string.remind_id_intent_key)
    String remindIdIntentKey;

    private ReminderItemViewModel remindViewModel;
    private boolean showCalender = false;
    private LocalDate selectDate = null;
    private String title;
    private String desc;
    private boolean isRemindFinish;
    private boolean isSetSystemRemind = false;
    private boolean remindIsExist = false;
    private int remindIdIfIsExist = 0;
    private Context appContext = ToDoListApplication.getInstance().getApplicationContext();
    private AlarmUtil alarmUtil = AlarmUtil.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_item_layout);
        ButterKnife.bind(this);
        ReminderItemViewModel.Factory factory = new ReminderItemViewModel.Factory();
        remindViewModel = new ViewModelProvider(this, factory).get(ReminderItemViewModel.class);

        showRemindIfExisted();
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectDate = LocalDate.of(year, month + MONTH_ADD_NUMBER, dayOfMonth);
            showDateAtButton(selectDate);
            calendarContainer.setVisibility(View.GONE);
        });
        calendarContainer.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                calendarContainer.setVisibility(View.GONE);
            }
        });
        remindSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isSetSystemRemind = isChecked;
        });
    }

    private void showDateAtButton(LocalDate selectDate) {
        btnSelectDate.setText(formatDate(selectDate));
        btnSelectDate.setTextColor(btnDateTextColor);
    }

    private void showRemindIfExisted() {
        Intent intent = getIntent();
        if (intent != null) {
            remindIdIfIsExist = intent.getIntExtra(remindIdIntentKey, DEFAULT_REMIND_ID);
            if (remindIdIfIsExist > 0) {
                Reminder remind = remindViewModel.getOneById(remindIdIfIsExist);
                if (remind != null) {
                    showRemindItem(remind);
                    btnRemove.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void showRemindItem(Reminder reminder) {
        LocalDate localDate = convertDateToLocalDate(reminder.getDate());
        showDateAtButton(localDate);
        this.selectDate = localDate;
        finishRemindCheck.setChecked(reminder.isFinished());
        remindSwitch.setChecked(reminder.isSystemRemind());
        editRemindTitle.setText(reminder.getTitle());
        editRemindDesc.setText(reminder.getDesc());
        this.remindIsExist = true;
    }

    private LocalDate convertDateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime.toLocalDate();
    }

    private String formatDate(LocalDate localDate) {
        int year = localDate.getYear();
        int monthValue = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();
        return year + yearString + monthValue +
                monthString + dayOfMonth + dayString;
    }

    @OnClick({R.id.btn_select_date, R.id.btn_save_remind, R.id.btn_back, R.id.btn_remove})
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
                if (title != null && !title.equals("") && selectDate != null) {
                    if (!remindIsExist) {
                        if (remindViewModel.saveRemind(title, desc, selectDate, isRemindFinish, isSetSystemRemind)) {
                            setNotification(isSetSystemRemind);
                            this.finish();
                        }
                    } else {
                        int updateRemindId = remindViewModel.updateRemind(remindIdIfIsExist, title, desc, selectDate, isRemindFinish, isSetSystemRemind);
                        setNotification(isSetSystemRemind);
                        if (updateRemindId != 0) {
                            this.finish();
                        }
                    }
                }
                break;
            case R.id.btn_remove:
                deleteRemindFromDB();
                this.finish();
                break;
        }
    }

    void deleteRemindFromDB() {
        if (remindIdIfIsExist != 0) {
            if (remindViewModel.isSetSystemRemind(remindIdIfIsExist)) {
                cancelNotification();
            }
            remindViewModel.deleteRemind(remindIdIfIsExist);
        }
    }

    private void cancelNotification() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = LocalDateTime.of(selectDate.getYear(), selectDate.getMonth(),
                selectDate.getDayOfMonth(), TARGET_HOUR, TARGET_MINUTE, TARGET_SECOND);
        if (now.isBefore(LocalDateTime.from(targetTime))) {
            alarmUtil.cancelAlarm(remindIdIfIsExist);
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
        this.title = editRemindTitle.getText().toString();
        this.desc = editRemindDesc.getText().toString();
        this.isRemindFinish = finishRemindCheck.isChecked();
    }

    private void setNotification(boolean isSetRemind) {
        if (!isSetRemind) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = LocalDateTime.of(selectDate.getYear(), selectDate.getMonth(),
                selectDate.getDayOfMonth(), TARGET_HOUR, TARGET_MINUTE, TARGET_SECOND);
        if (now.isAfter(targetTime)) {
            return;
        }
        alarmUtil.setAlarm(title, desc, targetTime, remindIdIfIsExist);
    }
}
