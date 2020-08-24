package com.hezhiheng.todolist.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hezhiheng.todolist.R;
import com.hezhiheng.todolist.db.entity.Reminder;
import com.hezhiheng.todolist.viewmodel.ReminderItemViewModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_add)
    ImageButton btnAddRemind;
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.text_month)
    TextView textMonth;

    private ReminderItemViewModel remindViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showDate();

        ReminderItemViewModel.Factory factory = new ReminderItemViewModel.Factory();
        remindViewModel = new ViewModelProvider(this, factory).get(ReminderItemViewModel.class);
        final Observer<List<Reminder>> remindersObserver = reminders -> {
            if (reminders.size() != 0) {
                StringBuilder sb = new StringBuilder();
                reminders.forEach(reminder -> {
                    sb.append(reminder.toString()).append("\n");
                });
                // TODO: 2020/8/24 add remindList show
            }
        };
        remindViewModel.getReminders().observe(this, remindersObserver);
    }

    private void showDate() {
        LocalDate localDate = LocalDate.now();
        Month month = localDate.getMonth();
        int dayOfMonth = localDate.getDayOfMonth();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        String dayText = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ", "
                + dayOfMonth + "th";
        textDate.setText(dayText);
        textMonth.setText(month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
    }

    @OnClick(R.id.btn_add)
    void btnClick(View view) {
        Intent intent = new Intent(this, ReminderItemActivity.class);
        startActivity(intent);
    }
}