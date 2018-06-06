package com.example.patrick.myfirstapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.examble.myfirstapp.MESSAGE";
    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;

    private TaskViewModel mTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TaskListAdapter adapter = new TaskListAdapter(this);
        recyclerView.setAdapter(adapter);

        //associate view model with view model provider
        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        //add an observer that observes the LiveData in the view model
        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>(){
           //on changed fires when the data changes and the activity is in the foreground.
            @Override
           public void onChanged(@Nullable final List<Task> tasks){
               adapter.setTasks(tasks);
           }
        });
    }

    //if activity returns with RESULT_OK, insert the word into the database by calling insert()
    //method of TaskViewModel
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
        {
            TaskSchedule taskSchedule = TypeTransmogrifiers.fromInteger(data.getIntExtra(NewTaskActivity.EXTRA_TASK_SCHED,0));
            Task task = new Task(data.getStringExtra(NewTaskActivity.EXTRA_REPLY),
                    data.getIntExtra(NewTaskActivity.EXTRA_TASK_FREQ,1),
                    taskSchedule);
            mTaskViewModel.insert(task);
        } else{
            Toast.makeText(getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onFABClick(View view)
    {
        Intent intent = new Intent(MainActivity.this,NewTaskActivity.class);
        startActivityForResult(intent, NEW_TASK_ACTIVITY_REQUEST_CODE);
    }

    public void onFABCompleteClick(View view)
    {
        mTaskViewModel.insert(new Task("Name",1,TaskSchedule.Daily));
        Log.d("TEST","Clicked Complete");
        String parentName = view.getParent().toString();
        Log.d("TEST",parentName);
        TextView taskName = findViewById(R.id.taskNameView);
        Log.d("TEST",taskName.getText().toString());

        ViewParent parent = view.getParent();
        if (parent==null)
        {
            Log.d("TEST","parent is null");
        }
        else
        {

        }
        if (view!=null)
        {
          //  if (view.get)
           // if (view.getId() )
        }
    }

    public void onFABFailClick(View view)
    {
        Log.d("TEST", "Clicked Fail");
        String parentName = view.getParent().toString();
        Log.d("TEST",parentName);
        TextView taskName = findViewById(R.id.taskNameView);
        Log.d("TEST",taskName.getText().toString());
    }




    //**Called when user taps the send button*/
  /*  public void sendMessage(View view){
        //do something when send message button clicked.
        Intent intent = new Intent(this,DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
*/
/*    public void addNewItem(View view){
        Intent intent = new Intent(this, TaskCreation.class);
        startActivity(intent);
    }*/
}
