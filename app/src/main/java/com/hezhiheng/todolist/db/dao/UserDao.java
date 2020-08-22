package com.hezhiheng.todolist.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hezhiheng.todolist.db.entity.User;

@Dao
public interface UserDao {
    @Query("select * from user limit 0,1")
    User getUserByName();

    @Insert
    void insertOne(User user);
}
