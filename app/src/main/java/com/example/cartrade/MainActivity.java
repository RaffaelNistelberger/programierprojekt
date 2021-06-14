package com.example.cartrade;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                //Methode hinzufügen
                return true;
            case R.id.settings:
                //Methode hinzufügen
                return true;
            case R.id.search_bar:
                //Methode hinzufügen
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void sortListbyPriceDesc(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            carList.sort((o1, o2) -> o2.getPrice() -o1.getPrice());
            bindAdapterToListView(listView);
        }
    }

    public void sortListbyPriceAsc(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            carList.sort((o1, o2) -> o1.getPrice() -o2.getPrice());
            bindAdapterToListView(listView);
        }
    }
}