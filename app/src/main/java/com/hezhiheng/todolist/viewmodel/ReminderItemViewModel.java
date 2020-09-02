package com.hezhiheng.todolist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hezhiheng.todolist.db.entity.Reminder;
import com.hezhiheng.todolist.repository.ReminderRepository;

import java.util.List;

public class ReminderItemViewModel extends ViewModel {
    private static final int REMIND_UPDATE_ERROR = 0;

    private ReminderRepository reminderRepository;
    private LiveData<List<Reminder>> mReminders;

    public ReminderItemViewModel(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public LiveData<List<Reminder>> getAllReminders() {
        if (mReminders == null) {
            mReminders = new MutableLiveData<>();
            loadAllReminders();
        }
        return mReminders;
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

    public boolean saveOne(Reminder reminder) {
        try {
            reminderRepository.saveOne(reminder);
            loadAllReminders();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int updateOne(Reminder reminder) {
        int remindId = REMIND_UPDATE_ERROR;
        if (reminder != null) {
            try {
                Reminder originalRemind = reminderRepository.getOneById(reminder.getId());
                if (originalRemind != null) {
                    remindId = reminderRepository.updateOne(reminder);
                }
                loadAllReminders();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return remindId;
    }

    public void deleteOne(int id) {
        Reminder remindToDelete = getOneById(id);
        reminderRepository.deleteOne(remindToDelete);
        loadAllReminders();
    }

    public boolean isSetSystemRemind(int id) {
        return getOneById(id).isSystemRemind();
    }

    private void loadAllReminders() {
        try {
            mReminders = reminderRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
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
