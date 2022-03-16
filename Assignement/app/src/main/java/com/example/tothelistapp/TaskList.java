package com.example.tothelistapp;

import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTaskState(boolean state, int position){
        tasks.get(position).setFinished(state);
    }

    public void remove(int position) {
        tasks.remove(position);
    }
}
