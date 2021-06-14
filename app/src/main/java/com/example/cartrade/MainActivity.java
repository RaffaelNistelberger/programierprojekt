package com.example.cartrade;

import androidx.annotation.RequiresApi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.os.Message;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

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
    private int nextIndex;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("cars");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //login();
        loadData();
        //saveData(3,new Car("Test3",1200.10,"1991",110,1000,"Auto3","DE","0650"));

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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
    private void saveData(int index,Car carToAdd){
        myRef.child(index+"").setValue(carToAdd.toMap());
        System.out.println("Saved to Database!");
    }

    private void loadData(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> dataSnapshot1 = dataSnapshot.getChildren();
                Iterator it = dataSnapshot1.iterator();
                while (it.hasNext()){
                    System.out.println((DataSnapshot)it.next());
                    System.out.println(dataSnapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("ERROR");
            }
        });
    }

}