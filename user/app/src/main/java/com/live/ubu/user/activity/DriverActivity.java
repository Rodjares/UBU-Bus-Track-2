package com.live.ubu.user.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.live.ubu.user.R;
import com.live.ubu.user.adapter.DriverAdapter;
import com.live.ubu.user.model.Driver;

import java.util.ArrayList;

public class DriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Driver> drivers = new ArrayList<>();
        DriverAdapter adapter = new DriverAdapter(drivers,this);
        RecyclerView recyclerView = findViewById(R.id.list_driver);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);

        String[] name = getResources().getStringArray(R.array.list_name_driver);
        String[] position = getResources().getStringArray(R.array.list_position_driver);
        int[] image = {R.drawable.d_1, R.drawable.d_2, R.drawable.d_3, R.drawable.d_4, R.drawable.d_5, R.drawable.d_6, R.drawable.d_7, R.drawable.d_8, R.drawable.d_9, R.drawable.d_10, R.drawable.d_11,R.drawable.d_12, R.drawable.d_13};
        for (int i = 0; i < name.length; i++) {
            drivers.add(new Driver(name[i], position[i], image[i]));
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(!item.isChecked());
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
