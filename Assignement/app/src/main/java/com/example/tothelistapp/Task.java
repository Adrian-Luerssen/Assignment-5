package com.example.tothelistapp;

import java.sql.Time;

public class Task {
    private String taskName;
    private Time dueDate;
    private boolean finished;

    public Task(String taskName, Time dueDate, boolean finished) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.finished = finished;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Time getDueDate() {
        return dueDate;
    }

    public void setDueDate(Time dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
