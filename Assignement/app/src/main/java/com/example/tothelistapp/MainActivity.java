package com.example.tothelistapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

interface OnCheckedChangeListener {
    void onCheckedChanged(boolean isChecked, int position);
}

public class MainActivity extends AppCompatActivity implements OnCheckedChangeListener {

    public static final int BACK = 333;
    public static final int SAVE = 444;
    private RecyclerView taskView;
    private TaskAdapter taskAdapter;
    private TaskDAO taskDAO;
    private Button addTask;
    private TaskList tasks;

    private final ActivityResultLauncher<Intent> addTaskLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onActivityResult(ActivityResult result) {
            System.out.println("here");
            Intent intent = result.getData();

            if (result.getResultCode() == SAVE && intent != null) {
                String name = intent.getStringExtra(getString(R.string.TaskName));
                String description = intent.getStringExtra(getString(R.string.TaskDescription));
                int year = intent.getIntExtra("YEAR",0);
                int month = intent.getIntExtra("MONTH",0);
                int day = intent.getIntExtra("DAY",0);
                int hour = intent.getIntExtra("HOUR",0);
                int minute = intent.getIntExtra("MINUTE",0);
                String dateTime = hour+":"+minute+" "+day+"-"+month+"-"+year;

                tasks.addTask(new Task(name,dateTime,description));
                updateUI();

            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVars();
        setOnclickListeners();
        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        taskDAO.save(MainActivity.this, tasks);
    }

    private void updateUI() {
        taskAdapter = new TaskAdapter(tasks.getTasks(), this);
        taskView.setAdapter(taskAdapter);
    }

    private void initVars() {
        taskDAO = new TaskDAO();
        tasks = taskDAO.load(MainActivity.this);
        taskView = (RecyclerView) findViewById(R.id.task_Recycler_view);
        taskView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(SwipeCallBack).attachToRecyclerView(taskView);
        addTask = (Button) findViewById(R.id.add_Task);
    }

    public void setOnclickListeners(){
        addTask.setOnClickListener(view -> {
            System.out.println("ADD TASK");
            Intent intent = new Intent(this,AddTaskActivity.class);
            addTaskLauncher.launch(intent);
            updateUI();
        });
    }

    @Override
    public void onCheckedChanged(boolean isChecked, int position) {
        System.out.println("Checked");
        tasks.setTaskState(isChecked, position);
    }

    // Adapter
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private final List<Task> taskList;

        private final OnCheckedChangeListener finishTaskCallback;

        public TaskAdapter(List<Task> tasks, OnCheckedChangeListener listener) {
            taskList = tasks;
            finishTaskCallback = listener;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = taskList.get(position);
            holder.bind(task, position);
        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }

        public void checkAsDone(boolean status, int position) {
            finishTaskCallback.onCheckedChanged(status, position);
        }

    }


    // View Holder
    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Task task;
        private int position;

        private TextView titleTextView;
        private TextView dateTextView;
        private TextView descriptionView;
        private Switch taskFinishedView;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);

            titleTextView = (TextView) itemView.findViewById(R.id.Task_Name);
            dateTextView = (TextView) itemView.findViewById(R.id.Task_Date);
            descriptionView = (TextView) itemView.findViewById(R.id.Task_Description);
            taskFinishedView = (Switch) itemView.findViewById(R.id.Task_Completed);

            taskFinishedView.setOnCheckedChangeListener((compoundButton, state) -> {
                System.out.println("\nTask finished: " + state + this.position);
                taskAdapter.checkAsDone(state, this.position);
            });
        }

        public void bind(Task task, int position) {
            this.task = task;
            this.position = position;
            titleTextView.setText(this.task.getTaskName());
            dateTextView.setText(this.task.getDueDate().toString());
            descriptionView.setText(this.task.getDescription());
            taskFinishedView.setChecked(task.isFinished());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), this.task.getTaskName() + " clicked!", Toast.LENGTH_SHORT) .show();
        }
    }

    ItemTouchHelper.SimpleCallback SwipeCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            tasks.remove(viewHolder.getAdapterPosition());
            updateUI();
        }
    };



}