package com.hezhiheng.todolist.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hezhiheng.todolist.db.entity.User;

@Dao
public interface UserDao {
    @Query("select * from user where name = :name")
    User getUserByName(String name);

    @Insert
    void insertOne(User user);
}
