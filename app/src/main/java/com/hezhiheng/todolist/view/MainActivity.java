package com.hezhiheng.todolist.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hezhiheng.todolist.R;
import com.hezhiheng.todolist.ToDoListApplication;
import com.hezhiheng.todolist.db.entity.Reminder;
import com.hezhiheng.todolist.utils.AlarmUtil;
import com.hezhiheng.todolist.view.adapter.RemindItemAdapter;
import com.hezhiheng.todolist.viewmodel.ReminderItemViewModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private ReminderItemViewModel remindViewModel;
    private boolean isFirstShow = true;
    private SharedPreferences sharedPreferences;
    private static final int TARGET_HOUR = 6;
    private static final int TARGET_MINUTE = 0;
    private static final int TARGET_SECOND = 0;
    private AlarmUtil alarmUtil;
    private List<Reminder> reminderList;

    @BindView(R.id.btn_add)
    ImageButton btnAddRemind;
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.text_month)
    TextView textMonth;
    @BindView(R.id.remind_list_container)
    RecyclerView remindListContainer;
    @BindView(R.id.text_remind_count)
    TextView textRemindCount;
    @BindDimen(R.dimen.recycler_view_item_space)
    int itemSpace;
    @BindString(R.string.channel_id)
    String channelId;
    @BindString(R.string.remind_id_intent_key)
    String remindIdIntentKey;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindString(R.string.user_already_login_key)
    String userAlreadyLoginKey;

    private RemindItemAdapter remindItemAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(itemSpace);

    private static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {
            outRect.bottom = space;
        }
    }

    private RemindItemAdapter.OnItemClickListener itemClickListener = (view, position) -> {
        Reminder reminder = reminderList.get(position);
        if (view.getId() == R.id.finish_check_box_in_list) {
            reminder.setFinished(!reminder.isFinished());
            remindViewModel.updateOne(reminder);
            remindViewModel.sortReminderList(reminderList);
            cancelNotification(reminder);
            remindItemAdapter.notifyDataSetChanged();
        } else {
            Intent intent = new Intent(MainActivity.this, ReminderItemActivity.class);
            intent.putExtra(remindIdIntentKey, reminder.getId());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedPreferences = ToDoListApplication.getSharedPreferences();

        remindViewModel = new ViewModelProvider(this, new ReminderItemViewModel.Factory()).get(ReminderItemViewModel.class);

        alarmUtil = AlarmUtil.getInstance();

        showDate();
        reminderList = remindViewModel.getAllReminders().getValue();
        setObserver();
    }

    private void setObserver() {
        final Observer<List<Reminder>> remindersObserver = reminders -> {
            if (reminders.size() != 0) {
                reminderList = reminders;
                if (isFirstShow) {
                    showRemindList(reminders);
                } else {
                    updateReminderListShow();
                }
            }
        };
        remindViewModel.getAllReminders().observe(this, remindersObserver);
    }

    private void updateReminderListShow() {
        remindViewModel.sortReminderList(reminderList);
        remindItemAdapter.setRemindList(reminderList);
        remindItemAdapter.notifyDataSetChanged();
    }

    private void showRemindList(List<Reminder> reminderList) {
        if (reminderList != null && reminderList.size() != 0) {
            remindViewModel.sortReminderList(reminderList);
            remindItemAdapter = new RemindItemAdapter(this, reminderList);
            // TODO: 2020/9/2 setOnItemClickListener
            // setItemClickListener(remindItemAdapter);
            remindItemAdapter.setOnItemClickListener(itemClickListener);
            remindListContainer.setAdapter(remindItemAdapter);
            remindListContainer.setLayoutManager(linearLayoutManager);
            if (isFirstShow) {
                remindListContainer.addItemDecoration(spacesItemDecoration);
                isFirstShow = false;
            }
            textRemindCount.setText(getString(R.string.remind_count, reminderList.size()));
        }
    }

    private void showDate() {
        LocalDate localDate = LocalDate.now();
        Month month = localDate.getMonth();
        int dayOfMonth = localDate.getDayOfMonth();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)).append(", ").append(dayOfMonth);
        switch (dayOfMonth) {
            case 1:
                stringBuilder.append("st");
                break;
            case 2:
                stringBuilder.append("nd");
                break;
            case 3:
                stringBuilder.append("rd");
                break;
            default:
                stringBuilder.append("th");
                break;
        }
        textDate.setText(stringBuilder.toString());
        textMonth.setText(month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
    }

    private void cancelNotification(Reminder reminder) {
        if (reminder.isSystemRemind()) {
            LocalDateTime now = LocalDateTime.now();
            LocalDate localDate = reminder.getDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDateTime targetTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(),
                    localDate.getDayOfMonth(), TARGET_HOUR, TARGET_MINUTE, TARGET_SECOND);
            if (now.isBefore(LocalDateTime.from(targetTime))) {
                alarmUtil.cancelAlarm(reminder.getId());
            }
        }
    }


    @OnClick({R.id.btn_add, R.id.btn_more, R.id.btn_logout})
    void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Intent intent = new Intent(this, ReminderItemActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_more:
                btnLogout.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}