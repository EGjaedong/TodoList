package com.hezhiheng.todolist.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Date;

@Entity(tableName = "reminder")
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String desc;
    @ColumnInfo
    private Date date;
    @ColumnInfo
    private boolean isFinished;
    @ColumnInfo
    private boolean isSystemRemind;

    public Reminder() {
    }

    @Ignore
    public Reminder(String title, String desc, Date date, boolean isFinished, boolean isSystemRemind) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.isFinished = isFinished;
        this.isSystemRemind = isSystemRemind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isSystemRemind() {
        return isSystemRemind;
    }

    public void setSystemRemind(boolean systemRemind) {
        isSystemRemind = systemRemind;
    }

    @NotNull
    @Override
    public String toString() {
        return "Reminder{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", date=" + date +
                '}';
    }
}
