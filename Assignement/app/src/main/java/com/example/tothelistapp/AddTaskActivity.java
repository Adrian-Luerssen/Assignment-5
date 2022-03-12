package com.example.tothelistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private EditText nameTask;
    private EditText descriptionTask;

    private Button calendar;
    private int day , month , year, hour, minute;

    private Button save;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        initVars();

        calendar.setOnClickListener(view ->  {

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, AddTaskActivity.this,year, month,day);
            datePickerDialog.show();

        });

        back.setOnClickListener(view -> {
            setResult(MainActivity.BACK);
            finish();
        });

        save.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(getString(R.string.TaskName),nameTask.getText().toString());
            intent.putExtra("YEAR",year);
            intent.putExtra("MONTH",month);
            intent.putExtra("DAY",day);
            intent.putExtra("HOUR",hour);
            intent.putExtra("MINUTE",minute);
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
        calendar = (Button) findViewById(R.id.Calendar_Pop);

        Calendar cal = Calendar.getInstance();
        day  = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR);
        minute= cal.get(Calendar.MINUTE);

    }

    @Override
    public void onDateSet(DatePicker view, int yearSelected, int monthSelected, int daySelected) {
        year = yearSelected;
        day = daySelected;
        month = monthSelected+1;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
        hour = hourOfDay;
        minute = minuteOfDay;
        //Toast.makeText(this,"Year: " + myYear + "\n" + "Month: " + myMonth + "\n" + "Day: " + myDay + "\n" + "Hour: " + myHour + "\n" + "Minute: " + myMinute);
    }
}