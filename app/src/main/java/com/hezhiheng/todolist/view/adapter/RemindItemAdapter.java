package com.hezhiheng.todolist.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hezhiheng.todolist.R;
import com.hezhiheng.todolist.db.entity.Reminder;
import com.hezhiheng.todolist.utils.DateFormatUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class RemindItemAdapter extends RecyclerView.Adapter<RemindItemAdapter.RemindViewHolder> {
    private static final int REMOVE_ALL_FLAG = 0;

    private List<Reminder> remindList;
    private Context mContext;
    private CheckItemListener mCheckItemListener;
    public OnItemClickListener mOnItemClickListener;

    public RemindItemAdapter(Context mContext, List<Reminder> remindList, CheckItemListener checkItemListener) {
        this.mContext = mContext;
        this.remindList = remindList;
        this.mCheckItemListener = checkItemListener;
    }

    static class RemindViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView dateTextView;
        public CheckBox itemCheckBox;

        public RemindViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.remind_title_text_in_main);
            dateTextView = itemView.findViewById(R.id.remind_date_text_in_main);
            itemCheckBox = itemView.findViewById(R.id.finish_check_box_in_list);
        }
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
            if (reminder.isFinished()) {
                setTitleTextViewFinish(holder);
            } else {
                setTitleTextViewUnFinish(holder);
            }
            holder.dateTextView.setText(formatDate(reminder.getDate()));
            holder.itemCheckBox.setChecked(reminder.isFinished());
            setItemCheckBoxOnClickListener(holder, reminder);
            setItemViewOnClickListener(holder, position);
        }
    }

    private void setTitleTextViewUnFinish(@NonNull RemindViewHolder holder) {
        holder.titleTextView.getPaint().setFlags(REMOVE_ALL_FLAG);
        holder.titleTextView.setTextColor(mContext.getResources().getColor(R.color.item_title_color, null));
    }

    private void setTitleTextViewFinish(@NonNull RemindViewHolder holder) {
        holder.titleTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.titleTextView.setTextColor(mContext.getResources().getColor(R.color.finished_remind_title_text_color, null));
    }

    @Override
    public int getItemCount() {
        return remindList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private void setItemViewOnClickListener(@NonNull RemindViewHolder holder, int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.
                    onItemClick(position));
        }
    }

    private void setItemCheckBoxOnClickListener(@NonNull RemindViewHolder holder, Reminder reminder) {
        holder.itemCheckBox.setOnClickListener(v -> {
            reminder.setFinished(!reminder.isFinished());
            holder.itemCheckBox.setChecked(reminder.isFinished());
            if (null != mCheckItemListener) {
                mCheckItemListener.itemChecked(reminder, holder.itemCheckBox.isChecked());
            }
            notifyDataSetChanged();
        });
    }

    private String formatDate(Date date) {
        LocalDateTime localDateTime = DateFormatUtil.transDateToLocalDateTime(date);
        return localDateTime.getMonthValue() + "月" + localDateTime.getDayOfMonth() + "日";
    }

    public interface CheckItemListener {
        void itemChecked(Reminder reminder, boolean isChecked);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
