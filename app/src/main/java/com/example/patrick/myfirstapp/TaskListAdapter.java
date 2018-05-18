package com.example.patrick.myfirstapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>{


    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskItemView;
        private final TextView freqItemView;
        private final TextView schedItemView;
        private TaskViewHolder(View itemView) {
            super(itemView);
            taskItemView = itemView.findViewById(R.id.textView);
            freqItemView = itemView.findViewById(R.id.freqView);
            schedItemView = itemView.findViewById(R.id.schedView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Task> mTasks; //cached copy of tasks.

    TaskListAdapter(Context context){mInflater = LayoutInflater.from(context);}

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TaskViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position)
    {
        if (mTasks !=null)
        {
            Task currentTask = mTasks.get(position);
            holder.taskItemView.setText(currentTask.getTask());
            holder.freqItemView.setText(currentTask.getFrequency().toString());
            holder.schedItemView.setText(currentTask.getTaskSchedule().toString());
        }
        else {
                //cover case of no data being ready yet
                holder.taskItemView.setText("No tasks.");
        }
    }

    void setTasks(List<Task> tasks)
    {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTasks != null) {
            return mTasks.size();
        }
        else return 0;
    }
}
