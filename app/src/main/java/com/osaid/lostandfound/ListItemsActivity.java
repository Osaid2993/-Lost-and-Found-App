package com.osaid.lostandfound;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import com.osaid.lostandfound.adapter.ItemAdapter;
import com.osaid.lostandfound.data.DatabaseHelper;
import com.osaid.lostandfound.model.Item;

import java.util.List;

public class ListItemsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ItemAdapter adapter;
    private TextView textEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        dbHelper = new DatabaseHelper(this);
        textEmpty = findViewById(R.id.textEmpty);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewItems);
        ChipGroup chipGroup = findViewById(R.id.chipGroupFilter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Item> items = dbHelper.getAllItems();
        adapter = new ItemAdapter(this, items);
        recyclerView.setAdapter(adapter);

        updateEmptyState(items);

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                loadItems(null);
                return;
            }

            Chip selectedChip = findViewById(checkedIds.get(0));
            if (selectedChip == null) return;

            String category = selectedChip.getText().toString();
            if (category.equals("All")) {
                loadItems(null);
            } else {
                loadItems(category);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChipGroup chipGroup = findViewById(R.id.chipGroupFilter);
        Chip chipAll = findViewById(R.id.chipAll);
        chipAll.setChecked(true);
        loadItems(null);
    }

    private void loadItems(String category) {
        List<Item> items;
        if (category == null) {
            items = dbHelper.getAllItems();
        } else {
            items = dbHelper.getItemsByCategory(category);
        }
        adapter.updateItems(items);
        updateEmptyState(items);
    }

    private void updateEmptyState(List<Item> items) {
        if (items.isEmpty()) {
            textEmpty.setVisibility(View.VISIBLE);
        } else {
            textEmpty.setVisibility(View.GONE);
        }
    }
}