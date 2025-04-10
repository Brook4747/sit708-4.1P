package com.example.sit708_41p;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView textTitle, textDescription, textDueDate;
    private Button btnEdit, btnDelete;

    private TaskViewModel taskViewModel;
    private Task task;
    private int taskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        textTitle = findViewById(R.id.textDetailTitle);
        textDescription = findViewById(R.id.textDetailDescription);
        textDueDate = findViewById(R.id.textDetailDueDate);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        if (getIntent().hasExtra("task_id")) {
            taskId = getIntent().getIntExtra("task_id", -1);

            taskViewModel.getTaskById(taskId).observe(this, t -> {
                if (t != null) {
                    task = t;
                    textTitle.setText(t.getTitle());
                    textDescription.setText(t.getDescription());
                    textDueDate.setText(t.getDueDate());
                } else {
                    Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }


        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(TaskDetailActivity.this, AddEditTaskActivity.class);
            intent.putExtra("task_id", taskId);
            startActivity(intent);
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(TaskDetailActivity.this)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        taskViewModel.delete(task);
                        Toast.makeText(TaskDetailActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}

