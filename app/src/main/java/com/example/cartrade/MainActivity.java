package com.example.cartrade;

import androidx.annotation.RequiresApi;

import androidx.annotation.NonNull;
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

import android.os.Message;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private MainAdapter adapter;
    private ArrayList<Car> carList;
    private ListView listView;
    private LinearLayout linearLayout;
    private final static int RQ_PREFERENCES = 1;
    private SharedPreferences prefs;
    private long nextIndex;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("cars");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        //login();
        //prefs = PreferenceManager.getDefaultSharedPreferences(this);

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
                Intent intent = new Intent(this,
                        MySettingsActivity.class);
                startActivityForResult(intent, RQ_PREFERENCES);
                return true;
            case R.id.search_bar:
                //Methode hinzufügen
                return true;
            case R.id.priceInc:
                sortListbyPriceAsc();
                return true;
            case R.id.priceDesc:
                sortListbyPriceDesc();
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


    public void sortListbyPriceDesc(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //carList.sort((o1, o2) -> o2.getPrice() -o1.getPrice());
            bindAdapterToListView(listView);
        }
    }

    public void sortListbyPriceAsc(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //carList.sort((o1, o2) -> o1.getPrice() -o2.getPrice());
            bindAdapterToListView(listView);
        }
    }
    private void saveData(long index,Car carToAdd){
        myRef.child(index+"").setValue(carToAdd.toMap());
        System.out.println("Saved to Database!");
    }

    private void loadData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nextIndex = dataSnapshot.getChildrenCount();
                System.out.println(nextIndex);
                Iterable<DataSnapshot> dataSnapshot1 = dataSnapshot.getChildren();
                Iterator it = dataSnapshot1.iterator();
                while (it.hasNext()){
                    System.out.println((DataSnapshot)it.next());

                }
                //saveData(nextIndex, new Car("Test",12000,"1991",200,120000,"Auto"+nextIndex,"AUT", "0650"));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("ERROR");
            }
        });
    }

    public void add(){

    }

}
