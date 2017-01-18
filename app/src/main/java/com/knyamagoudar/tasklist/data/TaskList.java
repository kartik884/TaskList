package com.knyamagoudar.tasklist.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by knyamagoudar on 1/11/17.
 */

public class TaskList implements Serializable{

    private long id;
    private String taskName;
    private Date dueDate;
    private String taskNotes;
    private String priority;
    private String status;

    public long getId() {
        return id;

    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return taskName;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getTaskNotes() {
        return taskNotes;
    }

    public void setTaskNotes(String taskNotes) {
        this.taskNotes = taskNotes;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
