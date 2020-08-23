package com.hezhiheng.todolist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hezhiheng.todolist.db.entity.Reminder;
import com.hezhiheng.todolist.repository.ReminderRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class ReminderItemViewModel extends ViewModel {
    private ReminderRepository reminderRepository;

    public ReminderItemViewModel(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public boolean saveRemind(String title, String desc, LocalDate selectDate,
                          boolean isRemindFinish, boolean isSetSystemRemind) {
        ZonedDateTime zonedDateTime = selectDate.atStartOfDay(ZoneId.systemDefault());
        Date date = Date.from(zonedDateTime.toInstant());
        Reminder reminder = new Reminder(title, desc, date, isRemindFinish, isSetSystemRemind);
        try {
            reminderRepository.save(reminder);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final ReminderRepository mRepository;

        public Factory() {
            mRepository = new ReminderRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ReminderItemViewModel(mRepository);
        }
    }
}
