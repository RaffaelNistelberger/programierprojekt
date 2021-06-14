package com.example.cartrade;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
    private final int ADD_ACTIVITY_REQUEST_CODE = 187;

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
                add();
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

    public void add(){
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivityForResult(intent, ADD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //returns here when AddActivity upload is finished
        if(requestCode == ADD_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Car tmp = new Car(data.getStringExtra("Name"), Integer.parseInt(data.getStringExtra("Price")), data.getStringExtra("First_registration"), Integer.parseInt(data.getStringExtra("Ps")), Integer.parseInt(data.getStringExtra("Kilometres")), data.getStringExtra("Descripotion"), data.getStringExtra("Location"), data.getStringExtra("TelNumber"));
                carList.add(tmp);
                bindAdapterToListView(listView);
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

}