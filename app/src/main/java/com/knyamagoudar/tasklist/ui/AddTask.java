package com.knyamagoudar.tasklist.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.knyamagoudar.tasklist.R;
import com.knyamagoudar.tasklist.data.TaskList;
import com.knyamagoudar.tasklist.data.TaskListDataSource;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTask extends Activity {

    TaskListDataSource taskListDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_add_task);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_header);


        Spinner spinner = (Spinner) findViewById(R.id.spPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Spinner statusSpinner = (Spinner) findViewById(R.id.spStatus);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        taskListDataSource = new TaskListDataSource(this);
        taskListDataSource.open();
    }

    public void onBackBtn(View v){
        this.finish();
    }

    public void onSaveTask(View v){
        EditText etTaskName = (EditText) findViewById(R.id.etTaskName);
        String taskName = etTaskName.getText().toString();

        DatePicker datePicker = (DatePicker) findViewById(R.id.dpDueDate);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        EditText etTaskNotes = (EditText) findViewById(R.id.etTaskNotes);
        String taskNotes = etTaskNotes.getText().toString();

        Spinner spPriority=(Spinner) findViewById(R.id.spPriority);
        String prority = spPriority.getSelectedItem().toString();

        Spinner spStatus=(Spinner) findViewById(R.id.spStatus);
        String status = spStatus.getSelectedItem().toString();

        System.out.println("Status add task: "+ status);

        Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);

        TaskList taskList = new TaskList();
        taskList.setTaskName(taskName);
        taskList.setTaskNotes(taskNotes);
        taskList.setPriority(prority);
        taskList.setStatus(status);
        taskList.setDueDate(date);

        taskListDataSource.createTaskList(taskList);
        this.finish();

    }

    public void onCancelAction(View v){
        this.finish();
    }
}
