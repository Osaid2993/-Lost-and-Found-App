package com.osaid.lostandfound;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import com.osaid.lostandfound.data.DatabaseHelper;
import com.osaid.lostandfound.model.Item;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        int itemId = getIntent().getIntExtra("item_id", -1);
        if (itemId == -1) {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Item item = dbHelper.getItemById(itemId);
        if (item == null) {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ImageView detailImage = findViewById(R.id.detailImage);
        TextView detailName = findViewById(R.id.detailName);
        TextView detailPostType = findViewById(R.id.detailPostType);
        TextView detailCategory = findViewById(R.id.detailCategory);
        TextView detailTimestamp = findViewById(R.id.detailTimestamp);
        TextView detailDescription = findViewById(R.id.detailDescription);
        TextView detailDate = findViewById(R.id.detailDate);
        TextView detailLocation = findViewById(R.id.detailLocation);
        TextView detailPhone = findViewById(R.id.detailPhone);
        MaterialButton btnRemove = findViewById(R.id.btnRemove);

        detailName.setText(item.getName());
        detailPostType.setText("Type: " + item.getPostType());
        detailCategory.setText("Category: " + item.getCategory());
        detailTimestamp.setText("Posted: " + item.getTimestamp());
        detailDescription.setText(item.getDescription());
        detailDate.setText("Date: " + item.getDate());
        detailLocation.setText("Location: " + item.getLocation());
        detailPhone.setText("Phone: " + item.getPhone());

        if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
            detailImage.setImageURI(Uri.fromFile(new java.io.File(item.getImagePath())));
        }

        btnRemove.setOnClickListener(v -> {
            dbHelper.deleteItem(item.getId());
            Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}