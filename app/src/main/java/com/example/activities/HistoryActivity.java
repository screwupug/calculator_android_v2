package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.calculator.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import adapters.HistoryAdapter;
import database.DBHelper;
import fragments.DeleteDialogFragment;

public class HistoryActivity extends AppCompatActivity implements DeleteDialogFragment.ConfirmationListener {

    private RecyclerView historyList;
    private HistoryAdapter adapter;
    private List<String> expressions, results;
    private Toolbar toolbar;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyList = findViewById(R.id.rv_history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        toolbar = findViewById(R.id.history_toolbar);
        historyList.setLayoutManager(layoutManager);
        historyList.setHasFixedSize(false);
        db = new DBHelper(this);
        expressions = new ArrayList<>();
        results = new ArrayList<>();
        adapter = new HistoryAdapter(this, expressions, results);
        historyList.setAdapter(adapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        displayData();
    }

    // Menu initialization
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_delete_option, menu);
        return true;
    }

    // Menu handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Cursor cursor = db.getDataFromExpressions();
        Intent intent = new Intent(this, MainActivity.class);
        if (item.getItemId() == R.id.action_delete) {
            // if DB is empty - no actions
            if (cursor.getCount() != 0) {
                showDialog(item.getActionView());
            }
        }
        if (item.getItemId() == android.R.id.home) {
            startActivity(intent);
        }
        return true;
    }

    // Method that fills lists from DB
    private void displayData() {
        Cursor cursor = db.getDataFromExpressions();
        // if DB is empty - no actions
        if (cursor.getCount() == 0) {
            return;
        }
        while (cursor.moveToNext()) {
            expressions.add(cursor.getString(0));
            results.add(cursor.getString(1));
        }
        db.close();
    }

    // DeleteDialogFragment initialization
    public void showDialog(View view) {
        BottomSheetDialogFragment fragment = new DeleteDialogFragment();
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }


    //Dialog button listener
    @Override
    public void confirmationButtonClicked(DeleteDialogFragment fragment) {
        db.deleteExpressionsBase();
        db.close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Dialog button listener
    @Override
    public void cancelButtonClicked(DeleteDialogFragment fragment) {
        fragment.dismiss();
    }
}