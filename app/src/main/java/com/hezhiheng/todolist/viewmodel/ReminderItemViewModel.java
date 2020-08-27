package com.hezhiheng.todolist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hezhiheng.todolist.db.entity.Reminder;
import com.hezhiheng.todolist.repository.ReminderRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class ReminderItemViewModel extends ViewModel {
    private static final int REMIND_UPDATE_ERROR = 0;
    private static final int NOT_EXIST_REMIND_ID = 0;

    private ReminderRepository reminderRepository;
    private LiveData<List<Reminder>> mReminders;

    public ReminderItemViewModel(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public LiveData<List<Reminder>> getReminders() {
        if (mReminders == null) {
            mReminders = new MutableLiveData<>();
            loadReminders();
        }
        return mReminders;
    }

    private void loadReminders() {
        try {
            mReminders = reminderRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Reminder getOneById(int id) {
        Reminder reminder = null;
        try {
            reminder = reminderRepository.getOneById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reminder;
    }

    public boolean saveRemind(String title, String desc, LocalDate selectDate,
                              boolean isRemindFinish, boolean isSetSystemRemind) {
        try {
            reminderRepository.save(createRemind(NOT_EXIST_REMIND_ID, title, desc, selectDate, isRemindFinish, isSetSystemRemind));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int updateRemind(Reminder reminder) {
        int remindId = REMIND_UPDATE_ERROR;
        if (reminder != null) {
            try {
                Reminder originalRemind = reminderRepository.getOneById(reminder.getId());
                if (originalRemind != null) {
                    remindId = reminderRepository.updateRemind(reminder);
                }
                loadReminders();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return remindId;
    }

    public int updateRemind(int id, String title, String desc, LocalDate selectDate,
                            boolean isRemindFinish, boolean isSetSystemRemind) {
        return this.updateRemind(createRemind(id, title, desc, selectDate, isRemindFinish, isSetSystemRemind));
    }

    public void deleteRemind(int id) {
        Reminder remindToDelete = getOneById(id);
        reminderRepository.deleteOne(remindToDelete);
        loadReminders();
    }

    public boolean isSetSystemRemind(int id) {
        return getOneById(id).isSystemRemind();
    }

    private Reminder createRemind(int id, String title, String desc, LocalDate selectDate,
                                  boolean isRemindFinish, boolean isSetSystemRemind) {
        ZonedDateTime zonedDateTime = selectDate.atStartOfDay(ZoneId.systemDefault());
        Date date = Date.from(zonedDateTime.toInstant());
        if (id != 0) {
            return new Reminder(id, title, desc, date, isRemindFinish, isSetSystemRemind);
        }
        return new Reminder(title, desc, date, isRemindFinish, isSetSystemRemind);
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
