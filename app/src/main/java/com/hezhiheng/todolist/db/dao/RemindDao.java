package com.hezhiheng.todolist.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.hezhiheng.todolist.db.entity.Reminder;

@Dao
public interface RemindDao {
    @Insert
    void saveOne(Reminder reminder);
}
