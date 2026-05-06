package com.osaid.lostandfound;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnCreate = findViewById(R.id.btnCreateAdvert);
        MaterialButton btnShow = findViewById(R.id.btnShowItems);
        MaterialButton btnMap = findViewById(R.id.btnShowMap);

        btnCreate.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CreateAdvertActivity.class));
        });

        btnShow.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ListItemsActivity.class));
        });

        btnMap.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MapActivity.class));
        });
    }
}