package com.example.cartrade;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainAdapter adapter;
    private ArrayList<Car> carList;
    private ListView listView;
    private LinearLayout linearLayout;
    private final static int RQ_PREFERENCES = 1;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

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
                Intent intent = new Intent(this,
                        MySettingsActivity.class);
                startActivityForResult(intent, RQ_PREFERENCES);
                return true;
            case R.id.search_bar:
                //Methode hinzufügen
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void login() {
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

    public void showPrefs(View view) {
        String backgroundColor = prefs.getString(getString(R.string.Darkmode_pref), "#FFFFFF");
        view.setBackgroundColor(Color.parseColor(backgroundColor));
    }

    private void preferenceChanged(SharedPreferences sharedPrefs, String key) {
        if (key.equals("Darkmode_pref")) {
            String sValue = sharedPrefs.getString(key, "");
            Toast.makeText(this, key + " new Background: " + sValue, Toast.LENGTH_LONG).show();
            recreate();
        }
    }
}
