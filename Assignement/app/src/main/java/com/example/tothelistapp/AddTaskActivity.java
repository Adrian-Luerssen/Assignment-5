package com.example.tothelistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Time;

public class AddTaskActivity extends AppCompatActivity {
    private EditText nameTask;
    private EditText descriptionTask;
    private Button save;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        initVars();

        back.setOnClickListener(view -> {
            setResult(MainActivity.BACK);
            finish();
        });

        save.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(getString(R.string.TaskName),nameTask.getText().toString());
            intent.putExtra(getString(R.string.TaskDescription),descriptionTask.getText().toString());
            setResult(MainActivity.SAVE,intent);
            finish();
        });
    }

    private void initVars() {
        nameTask = (EditText) findViewById(R.id.Task_Name_input);
        descriptionTask = (EditText) findViewById(R.id.Task_Description_input);
        save = (Button) findViewById(R.id.Save_Task_Info);
        back= (Button) findViewById(R.id.Back);
    }
}