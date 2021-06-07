package com.example.cartrade;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainAdapter adapter;
    private ArrayList<Car> carList;
    private ListView listView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login();
    }

    public void login(){
        carList = new ArrayList<>();
        linearLayout = findViewById(R.id.linearLayout);
        listView = findViewById(R.id.listView);
        //loadCarsIntoList
        bindAdapterToListView(listView);
    }


    public void bindAdapterToListView(ListView listView) {
        adapter = new MainAdapter(this, R.layout.activity_adapter, carList);

        listView.setAdapter(adapter);
    }


}