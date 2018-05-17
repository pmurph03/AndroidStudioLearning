package com.example.patrick.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

public class TaskCreation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);
        Spinner spinner = (Spinner) findViewById(R.id.frequencySpinner);
        spinner.setOnItemSelectedListener(this);
      //  Spinner perSpinner = (Spinner) findViewById(R.id.timesPerFrequencySpinner);
     //   spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        long selected = parent.getSelectedItemId();
        if (parent.getId()== R.id.frequencySpinner)
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

    public void onDoneButtonClicked(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void onNothingSelected(AdapterView<?> parent) {

    }


}
