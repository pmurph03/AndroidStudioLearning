package com.example.patrick.myfirstapp;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        private final TextView dateItemView;
        private final TextView completionItemView;
        private final FloatingActionButton completionButton;
        private final FloatingActionButton failButton;

      //  private final
        private TaskViewHolder(View itemView) {
            super(itemView);
            taskItemView = itemView.findViewById(R.id.taskNameView);
            freqItemView = itemView.findViewById(R.id.freqView);
            schedItemView = itemView.findViewById(R.id.schedView);
            dateItemView = itemView.findViewById(R.id.dateView);
            completionItemView = itemView.findViewById(R.id.completionView);
            completionButton = itemView.findViewById(R.id.completeCheck);
            failButton = itemView.findViewById(R.id.failCheck);


        }
    }

    private final LayoutInflater mInflater;
    private List<Task> mTasks; //cached copy of tasks.

    //CALLBACK
    MyCallBack myCallBack;
    public interface MyCallBack{
        void listenerMethod(String taskName, Boolean isComplete);
    }

    //CONSTRUCTOR
    TaskListAdapter(Context context, MyCallBack myCallBack)
    {
        Log.d("TEST:" , "TASK LIST ADAPTER CREATED");
        this.myCallBack = myCallBack;
        mInflater = LayoutInflater.from(context);
    }

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
            final Task currentTask = mTasks.get(position);
            holder.taskItemView.setText(currentTask.getTask());


            holder.freqItemView.setText(currentTask.getFrequency().toString());
            holder.schedItemView.setText(currentTask.getTaskSchedule().toString());
            holder.dateItemView.setText(currentTask.getDateCreated().toString());
            holder.completionItemView.setText(currentTask.getCompletions().toString());

            holder.freqItemView.setVisibility(View.GONE);
            holder.schedItemView.setVisibility(View.GONE);
            holder.dateItemView.setVisibility(View.GONE);
         //   holder.completionItemView.setVisibility(View.GONE);
            //TODO: check if task already completed for the day/replace failure with success

            holder.completionButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view){
                   myCallBack.listenerMethod(currentTask.getTask(),true);
                   notifyDataSetChanged();
               }
            });
            holder.failButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    myCallBack.listenerMethod(currentTask.getTask(),false);
                    notifyDataSetChanged();
                }
            });



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
