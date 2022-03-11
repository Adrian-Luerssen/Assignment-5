package com.example.tothelistapp;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
