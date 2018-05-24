package com.example.patrick.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NewTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_REPLY = "com.example.android.tasklistsql.REPLY";
    public static final String EXTRA_TASK_FREQ = "com.example.android.tasklistsql.FREQUENCY";
    public static final String EXTRA_TASK_SCHED = "com.example.android.tasklistsql.SCHEDULE";
    private EditText mEditTaskView;
    private Spinner mScheduleSpinner;
    private Spinner mFrequencySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        mEditTaskView = findViewById(R.id.new_task_id);
        mScheduleSpinner = findViewById(R.id.schedule_Spinner);
        mScheduleSpinner.setOnItemSelectedListener(this);
        mScheduleSpinner.setAdapter(new ArrayAdapter<TaskSchedule>(this, android.R.layout.simple_spinner_item, TaskSchedule.values()));

        mFrequencySpinner = findViewById(R.id.frequency_Spinner);
        final Button button = findViewById(R.id.button_done_new_task);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent replyIntent = new Intent();
                //TODO: handle if name exists by id in task database. or create unique ids for each task
                if (TextUtils.isEmpty(mEditTaskView.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String task = mEditTaskView.getText().toString();
                    Integer taskFrequency = mFrequencySpinner.getSelectedItemPosition()+1;
                    Integer taskSchedule = mScheduleSpinner.getSelectedItemPosition();
                    replyIntent.putExtra(EXTRA_REPLY,task);
                    replyIntent.putExtra(EXTRA_TASK_FREQ, taskFrequency);
                    replyIntent.putExtra(EXTRA_TASK_SCHED, taskSchedule);

                    setResult(RESULT_OK,replyIntent);
                }
                finish();
            }
        });
    }


    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        long selected = parent.getSelectedItemId();
        if (parent.getId()== R.id.schedule_Spinner)
        {
            TextView textView = (TextView) findViewById(R.id.timePerLabel);
            if (selected == 0) {
                textView.setText(R.string.new_item_timesPerDay);
            } else if (selected == 1) {

                textView.setText(R.string.new_item_timesPerWeek);
            } else if (selected == 2) {
                textView.setText(R.string.new_item_timesPerMonth);
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}
