package com.example.tothelistapp;

import java.sql.Time;
import java.time.LocalDateTime;

public class Task {

    private final String taskName;
    private final String dueDate;
    private final String description;
    private boolean finished;

    public Task(String taskName, String dueDate, String description) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.description = description;
        this.finished = false;
    }

    public String getTaskName() { return taskName; }

    public String getDueDate() {


        String tempTime = dueDate.split(" ")[0];
        String tempHour = tempTime.split(":")[0];
        String tempMinute = tempTime.split(":")[1];

        String tempDate = dueDate.split(" ")[1];
        String tempDay = tempDate.split("-")[0];
        String tempMonth = tempDate.split("-")[1];
        String tempYear = tempDate.split("-")[2];

        if (tempHour.length() == 1) tempHour = "0" + tempHour;
        if (tempMinute.length() == 1) tempMinute = "0" + tempMinute;
        if (tempDay.length() == 1) tempDay = "0" + tempDay;
        if (tempMonth.length() == 1) tempMonth = "0" + tempMonth;

        return tempHour + ":" + tempMinute + " " + tempDay + "-" + tempMonth + "-" + tempYear;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getDescription() { return description; }


}
