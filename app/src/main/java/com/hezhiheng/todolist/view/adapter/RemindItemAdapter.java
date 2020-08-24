package com.hezhiheng.todolist.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hezhiheng.todolist.R;
import com.hezhiheng.todolist.db.entity.Reminder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class RemindItemAdapter extends RecyclerView.Adapter<RemindItemAdapter.RemindViewHolder> {
    public List<Reminder> remindList;
    public Context mContext;

    public static class RemindViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView dateTextView;

        public RemindViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.remind_title_text_in_main);
            dateTextView = itemView.findViewById(R.id.remind_date_text_in_main);
        }
    }

    public RemindItemAdapter(Context mContext, List<Reminder> remindList) {
        this.mContext = mContext;
        this.remindList = remindList;
    }

    public void setRemindList(List<Reminder> remindList) {
        this.remindList = remindList;
    }

    @NonNull
    @Override
    public RemindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RemindViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.remind_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RemindViewHolder holder, int position) {
        Reminder reminder = remindList.get(position);
        if (reminder != null) {
            holder.titleTextView.setText(reminder.getTitle());
            holder.dateTextView.setText(formatDate(reminder.getDate()));
        }
    }

    private String formatDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime.getMonthValue() + "月" + localDateTime.getDayOfMonth() + "日";
    }

    @Override
    public int getItemCount() {
        return remindList.size();
    }
}
