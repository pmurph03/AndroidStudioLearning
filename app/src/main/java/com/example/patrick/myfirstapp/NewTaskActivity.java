package com.example.patrick.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewTaskActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.tasklistsql.REPLY";
    public static final String EXTRA_TASK_FREQ = "com.example.android.tasklistsql.FREQUENCY";
    public static final String EXTRA_TASK_SCHED = "com.example.android.tasklistsql.SCHEDULE";
    private EditText mEditTaskView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        mEditTaskView = findViewById(R.id.edit_task);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditTaskView.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String task = mEditTaskView.getText().toString();
                    //GET INTEGER FREQ
                    Integer taskFrequency = 0;
                    Integer taskSchedule = 1;
                    replyIntent.putExtra(EXTRA_REPLY,task);
                    replyIntent.putExtra(EXTRA_TASK_FREQ, taskFrequency);
                    replyIntent.putExtra(EXTRA_TASK_SCHED, taskSchedule);

                    setResult(RESULT_OK,replyIntent);
                }
                finish();
            }
        });
    }
}
