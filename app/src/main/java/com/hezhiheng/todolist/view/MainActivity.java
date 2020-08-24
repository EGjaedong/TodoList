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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_add)
    ImageButton btnAddRemind;
    @BindView(R.id.show_remind)
    TextView showRemindersText;

    private ReminderItemViewModel remindViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ReminderItemViewModel.Factory factory = new ReminderItemViewModel.Factory();
        remindViewModel = new ViewModelProvider(this, factory).get(ReminderItemViewModel.class);
        final Observer<List<Reminder>> remindersObserver = new Observer<List<Reminder>>() {
            @Override
            public void onChanged(List<Reminder> reminders) {
                if (reminders.size()!=0){
                    StringBuilder sb = new StringBuilder();
                    reminders.forEach(reminder -> {
                        sb.append(reminder.toString()).append("\n");
                    });
                    showRemindersText.setText(sb.toString());
                }
            }
        };
        remindViewModel.getReminders().observe(this, remindersObserver);
    }

    @OnClick(R.id.btn_add)
    void btnClick(View view) {
        Intent intent = new Intent(this, ReminderItemActivity.class);
        startActivity(intent);
    }
}