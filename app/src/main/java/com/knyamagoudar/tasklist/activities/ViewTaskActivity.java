package com.knyamagoudar.tasklist.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.knyamagoudar.tasklist.R;
import com.knyamagoudar.tasklist.fragments.EditFragment;
import com.knyamagoudar.tasklist.models.TaskList;
import com.knyamagoudar.tasklist.models.TaskListDataSource;
import com.knyamagoudar.tasklist.interfaces.CustomInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by knyamagoudar on 1/15/17.
 */

public class ViewTaskActivity extends AppCompatActivity implements CustomInterface {

    TextView tvTaskName,tvDueDate,tvTaskNotes,tvPriority,tvStatus;
    TaskListDataSource taskListDataSource;
    TaskList t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        t = (TaskList) getIntent().getSerializableExtra("Task");
        refreshView();
    }

    public void refreshView(){

        taskListDataSource = new TaskListDataSource(this);
        taskListDataSource.open();

        tvTaskName = (TextView) findViewById(R.id.tvViewTaskName);
        tvTaskName.setText(t.getTaskName());

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        tvDueDate = (TextView) findViewById(R.id.tvViewDueDate);
        tvDueDate.setText(formatter.format(t.getDueDate()));

        tvTaskNotes = (TextView) findViewById(R.id.tvViewTaskNotes);
        tvTaskNotes.setText(t.getTaskNotes());

        tvPriority = (TextView) findViewById(R.id.tvViewPriority);
        tvPriority.setText(t.getPriority());

        tvStatus = (TextView) findViewById(R.id.tvViewStatus);
        tvStatus.setText(t.getStatus());
    }


    public void onBackBtn(View v){
        this.finish();
    }

    public void onCancelFragment(View view){
        getFragmentManager().popBackStack();
    }

    public void onEditTask(View v){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        EditFragment editFragment = EditFragment.newInstance(t,this);
        //editFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        editFragment.show(fm,"fragment_edit");

    }

    public void onCancelAction(View v){
        System.out.println(t.getTaskName());
        taskListDataSource.deleteTaskList(t);
        this.finish();
    }

    @Override
    public void saveEditTaskData(TaskList task) {
        t= null;
        t = task;
        taskListDataSource.updateTaskList(task);
        refreshView();
    }
}
