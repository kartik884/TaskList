package com.knyamagoudar.tasklist.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.knyamagoudar.tasklist.utils.MySQLiteHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by knyamagoudar on 1/11/17.
 */

public class TaskListDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TASKNAME,
            MySQLiteHelper.COLUMN_DUEDATE,
            MySQLiteHelper.COLUMN_TASKNOTES,
            MySQLiteHelper.COLUMN_PRIORITY,
            MySQLiteHelper.COLUMN_STATUS };

    public TaskListDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public TaskList createTaskList(TaskList taskList){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TASKNAME,taskList.getTaskName());
        values.put(MySQLiteHelper.COLUMN_TASKNOTES,taskList.getTaskNotes());
        values.put(MySQLiteHelper.COLUMN_PRIORITY,taskList.getPriority());
        values.put(MySQLiteHelper.COLUMN_STATUS,taskList.getStatus());

        System.out.println("Status before: "+taskList.getStatus());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(taskList.getDueDate());
        values.put(MySQLiteHelper.COLUMN_DUEDATE,date);

        long insertId = database.insert(MySQLiteHelper.TABLE_TASKLIST,null,values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKLIST,allColumns,
                MySQLiteHelper.COLUMN_ID + "=" + insertId,null,null,null,null);

        cursor.moveToFirst();
        TaskList newTaskList = cursorToTaskList(cursor);

        System.out.println("Status: "+newTaskList.getStatus());
        cursor.close();
        return taskList;
    }

    public void deleteTaskList(TaskList taskList) {
        long id = taskList.getId();
        System.out.println("Task deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_TASKLIST, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public void updateTaskList(TaskList taskList) {
        long id = taskList.getId();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TASKNAME,taskList.getTaskName());
        values.put(MySQLiteHelper.COLUMN_TASKNOTES,taskList.getTaskNotes());
        values.put(MySQLiteHelper.COLUMN_PRIORITY,taskList.getPriority());
        values.put(MySQLiteHelper.COLUMN_STATUS,taskList.getStatus());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(taskList.getDueDate());
        values.put(MySQLiteHelper.COLUMN_DUEDATE,date);

        System.out.println("Task updated with id: " + id);
        System.out.println("Task Name" + taskList.getTaskName());

        long insertId = database.update(MySQLiteHelper.TABLE_TASKLIST,values, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);

        System.out.println("Insert Id" + insertId);
    }

    public ArrayList<TaskList> getAllTasks() {
        ArrayList<TaskList> taskLists = new ArrayList<TaskList>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKLIST,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TaskList taskList = cursorToTaskList(cursor);
            taskLists.add(taskList);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return taskLists;
    }



    private TaskList cursorToTaskList(Cursor cursor) {


        try {
            TaskList taskList = new TaskList();
            taskList.setId(cursor.getLong(0));
            taskList.setTaskName(cursor.getString(1));

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            taskList.setDueDate((Date) formatter.parse(cursor.getString(2)));
            taskList.setTaskNotes(cursor.getString(3));
            taskList.setPriority(cursor.getString(4));
            taskList.setStatus(cursor.getString(5));

            //taskList.setPriority(cursor.);
            return taskList;

        } catch (Exception e) {
            return null;
        }

    }

}
