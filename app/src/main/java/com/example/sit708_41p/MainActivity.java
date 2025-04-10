package com.example.sit708_41p;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, TaskListActivity.class);
        startActivity(intent);
        finish();
    }
}