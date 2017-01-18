package com.knyamagoudar.tasklist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by knyamagoudar on 1/11/17.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_TASKLIST = "taskList";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASKNAME = "taskName";
    public static final String COLUMN_TASKNOTES = "taskNotes";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_DUEDATE = "dueDate";

    private static final String DATABASE_NAME = "taskList.db";
    private static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_TASKLIST + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TASKNAME
            + " text not null ,"
            + COLUMN_TASKNOTES + " text, "
            + COLUMN_PRIORITY + " text not null, "
            + COLUMN_STATUS + " text not null, "
            + COLUMN_DUEDATE + " text);";

    public MySQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKLIST);
        onCreate(db);
    }
}
