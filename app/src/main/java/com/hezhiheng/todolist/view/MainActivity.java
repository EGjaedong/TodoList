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
import com.hezhiheng.todolist.view.adapter.RemindItemAdapter;
import com.hezhiheng.todolist.viewmodel.ReminderItemViewModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements RemindItemAdapter.CheckItemListener {
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

    private ReminderItemViewModel remindViewModel;
    private boolean isFirstShow = true;
    private SharedPreferences sharedPreferences;


    private Comparator<Reminder> reminderComparator = (o1, o2) -> {
        if (o1.isFinished() && !o2.isFinished()) {
            return 1;
        } else if (!o1.isFinished() && o2.isFinished()) {
            return -1;
        } else {
            if (o1.getDate().before(o2.getDate())) {
                return -1;
            } else if (o1.getDate().after(o2.getDate())) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    private

    static class SpacesItemDecoration extends RecyclerView.ItemDecoration {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedPreferences = ToDoListApplication.getSharedPreferences();

        ReminderItemViewModel.Factory factory = new ReminderItemViewModel.Factory();
        remindViewModel = new ViewModelProvider(this, factory).get(ReminderItemViewModel.class);

        showDate();
        List<Reminder> reminderList = remindViewModel.getReminders().getValue();
        showRemindList(reminderList);
        setObserver();
    }

    private void setObserver() {
        final Observer<List<Reminder>> remindersObserver = reminders -> {
            if (reminders.size() != 0) {
                showRemindList(reminders);
            }
        };
        remindViewModel.getReminders().observe(this, remindersObserver);
    }

    private void showRemindList(List<Reminder> reminderList) {
        if (reminderList != null && reminderList.size() != 0) {
            reminderList.sort(reminderComparator);
            RemindItemAdapter remindItemAdapter = new RemindItemAdapter(this,
                    reminderList, this);
            setItemClickListener(remindItemAdapter);
            remindListContainer.setAdapter(remindItemAdapter);
            remindListContainer.setLayoutManager(new LinearLayoutManager(this));
            if (isFirstShow) {
                remindListContainer.addItemDecoration(new SpacesItemDecoration(itemSpace));
                isFirstShow = false;
            }
            textRemindCount.setText(getString(R.string.remind_count, reminderList.size()));
        }
    }

    private void setItemClickListener(RemindItemAdapter remindItemAdapter) {
        remindItemAdapter.setOnItemClickListener((view, position) -> {
            TextView idTextView = (TextView) view;
            String idString = idTextView.getText().toString();
            Intent intent = new Intent(MainActivity.this, ReminderItemActivity.class);
            intent.putExtra(remindIdIntentKey, Integer.parseInt(idString));
            startActivity(intent);
        });
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

    @Override
    public void itemChecked(Reminder reminder, boolean isChecked) {
        remindViewModel.updateRemind(reminder);
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