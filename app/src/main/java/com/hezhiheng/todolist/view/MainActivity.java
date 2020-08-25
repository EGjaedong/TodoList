package com.hezhiheng.todolist.view;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hezhiheng.todolist.R;
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

    private ReminderItemViewModel remindViewModel;

    @BindDimen(R.dimen.recycler_view_item_space)
    int itemSpace;

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
            reminderList.sort(Comparator.comparing(Reminder::getDate));
            RemindItemAdapter remindItemAdapter = new RemindItemAdapter(this, reminderList, this);
            remindListContainer.setAdapter(remindItemAdapter);
            remindListContainer.setLayoutManager(new LinearLayoutManager(this));
            remindListContainer.addItemDecoration(new SpacesItemDecoration(itemSpace));
            textRemindCount.setText(getString(R.string.remind_count, reminderList.size()));
        }
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

    @OnClick(R.id.btn_add)
    void btnClick(View view) {
        Intent intent = new Intent(this, ReminderItemActivity.class);
        startActivity(intent);
    }
}