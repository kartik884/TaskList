package com.knyamagoudar.tasklist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.knyamagoudar.tasklist.R;
import com.knyamagoudar.tasklist.data.TaskList;
import com.knyamagoudar.tasklist.data.TaskListDataSource;
import com.knyamagoudar.tasklist.util.TaskAdapter;


import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayList<TaskList> items;
    TaskAdapter taskAdapter;
    ListView lvItems;
    private TaskListDataSource taskListDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_header);
        lvItems = (ListView) findViewById(R.id.lvItems);
        taskListDataSource = new TaskListDataSource(this);
        taskListDataSource.open();

        setUpListViewListner();
    }

    public void addItemActicty(View v){
        Intent intent = new Intent(this,AddTask.class);
        startActivity(intent);
    }

    public void viewItemActivity(View v,TaskList t){
        Intent intent = new Intent(this,ViewTask.class);
        intent.putExtra("Task", t);
        startActivity(intent);
    }

    /*public void onAddItem(View v){

        EditText et = (EditText) findViewById(R.id.etNewItem);
        String itemText = et.getText().toString();
        TaskList t = taskListDataSource.createTaskList(itemText);
        itemsAdapter.add(t);
        et.setText("");
    }*/


    @Override
    protected void onResume() {
        super.onResume();

        items = taskListDataSource.getAllTasks();
        taskAdapter = new TaskAdapter(this,items);
        lvItems.setAdapter(taskAdapter);
    }

    public void setUpListViewListner(){

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TaskList t = (TaskList) parent.getItemAtPosition(position);
                        System.out.println("Task Name: "+t.getTaskName());
                        System.out.println("Priority: "+t.getPriority());
                        System.out.println("due date: "+t.getDueDate());
                        System.out.println("status: "+t.getStatus());
                        viewItemActivity(view,t);
                    }
                }
        );

        lvItems.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener(){
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter, View item,
                                                      int pos, long id){

                    taskListDataSource.deleteTaskList(items.get(pos));
                    items.remove(pos);
                    taskAdapter.notifyDataSetChanged();
                    return true;
                }
            });



        }
}
