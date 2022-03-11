package com.example.tothelistapp;

import java.sql.Time;

public class Task {
    private String taskName;
    private Time dueDate;
    private String description;
    private boolean finished;

    public Task(String taskName, Time dueDate, String description) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.description = description;
    }

    public String getTaskName() { return taskName; }

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

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
