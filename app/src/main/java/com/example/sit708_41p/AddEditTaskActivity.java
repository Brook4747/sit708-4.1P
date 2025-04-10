package com.example.sit708_41p;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddEditTaskActivity extends AppCompatActivity {

    private EditText editTitle, editDescription, editDueDate;
    private Button btnSave;

    private TaskViewModel taskViewModel;
    private int taskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        editDueDate = findViewById(R.id.editDueDate);
        btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> finish());


        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        if (getIntent().hasExtra("task_id")) {
            taskId = getIntent().getIntExtra("task_id", -1);

            taskViewModel.getTaskById(taskId).observe(this, task -> {
                if (task != null) {
                    editTitle.setText(task.getTitle());
                    editDescription.setText(task.getDescription());
                    editDueDate.setText(task.getDueDate());
                }
            });
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String dueDate = editDueDate.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(dueDate)) {
            Toast.makeText(this, "Title and due date are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        Task task = new Task(title, description, dueDate);
        if (taskId != -1) {
            task.setId(taskId);
            taskViewModel.update(task);
            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
        } else {
            taskViewModel.insert(task);
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}

