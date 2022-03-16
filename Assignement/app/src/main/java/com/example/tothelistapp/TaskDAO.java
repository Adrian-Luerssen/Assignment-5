package com.example.tothelistapp;

import android.content.Context;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class TaskDAO {

    private final Gson gson;

    private static final String FILENAME = "tasks.json";

    public TaskDAO () {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void save(Context context, TaskList taskList) {

        File file = new File(context.getFilesDir(), FILENAME);
        OutputStreamWriter outputStreamWriter;

        try {

            context.deleteFile(FILENAME);

            outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE));

            outputStreamWriter.write(gson.toJson(taskList));

            outputStreamWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public TaskList load(Context context) {

        File file = new File(context.getFilesDir(), FILENAME);
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        TaskList taskList = new TaskList();

        try {

            if(file.exists()) {

                inputStreamReader = new InputStreamReader(context.openFileInput(FILENAME));
                bufferedReader = new BufferedReader(inputStreamReader);

                taskList = gson.fromJson(bufferedReader, TaskList.class);

                //System.out.println(taskList.getTasks().get(0).getTaskName());

                bufferedReader.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return taskList;

    }



}
