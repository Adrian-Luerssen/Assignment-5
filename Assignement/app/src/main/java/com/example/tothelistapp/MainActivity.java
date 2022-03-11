package com.example.tothelistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView taskView;
    private TaskAdapter taskAdapter;
    private Button addTask;
    private TaskList tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVars();
        setOnclickListeners();

        tasks.addTask(new Task("PP2",new Time(234428923),"Finish assignment 5"));

        updateUI();


    }

    private void updateUI() {
        taskAdapter = new TaskAdapter(tasks.getTasks());
        taskView.setAdapter(taskAdapter);
    }

    private void initVars() {
        tasks = new TaskList();
        taskView = (RecyclerView) findViewById(R.id.task_Recycler_view);
        taskView.setLayoutManager(new LinearLayoutManager(this));
        addTask = (Button) findViewById(R.id.add_Task);
    }

    public void setOnclickListeners(){
        addTask.setOnClickListener(view -> {
            System.out.println("ADD TASK");
            tasks.addTask(new Task("PP2",new Time(234428923),"Finish assignment 5"));
            updateUI();
        });
    }

    // Adapter
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> taskList;

        public TaskAdapter(List<Task> tasks) {
            taskList = tasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = taskList.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }
    }


    // View Holder
    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Task task;

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

            taskFinishedView.setOnClickListener(view -> {
                System.out.println("here");
                task.setFinished(taskFinishedView.isChecked());
                System.out.println(task.isFinished());
            });
        }

        public void bind(Task task) {
            this.task = task;
            titleTextView.setText(this.task.getTaskName());
            dateTextView.setText(this.task.getDueDate().toString());
            descriptionView.setText(this.task.getDescription());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), this.task.getTaskName() + " clicked!", Toast.LENGTH_SHORT) .show();
        }
    }
}