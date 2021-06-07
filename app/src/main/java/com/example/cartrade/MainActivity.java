package com.example.cartrade;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainAdapter adapter;
    private ArrayList<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carList = new ArrayList<>();
    }


    public void bindAdapterToListView(ListView listView) {
        adapter = new MainAdapter(this, R.layout.activity_adapter, carList);

        listView.setAdapter(adapter);
    }
}