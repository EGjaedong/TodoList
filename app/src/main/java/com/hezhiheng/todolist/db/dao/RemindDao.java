package com.hezhiheng.todolist.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hezhiheng.todolist.db.entity.Reminder;

import java.util.List;

@Dao
public interface RemindDao {
    @Query("select * from reminder")
    LiveData<List<Reminder>> getAll();

    @Insert
    void saveOne(Reminder reminder);
}
