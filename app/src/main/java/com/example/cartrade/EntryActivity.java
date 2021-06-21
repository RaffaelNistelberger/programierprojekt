package com.example.cartrade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EntryActivity extends AppCompatActivity {


    private TextView name;
    private TextView price;
    private TextView first_registration;
    private TextView kilometres;
    private TextView ps;
    private TextView description;
    private TextView location;
    private TextView telNumber;
    private ImageView carImage;
    private String carURL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);



        Intent intent = getIntent();



        name = findViewById(R.id.editText_Name);
        price = findViewById(R.id.editText_Price);
        first_registration = findViewById(R.id.editText_first_registration);
        kilometres = findViewById(R.id.editText_Kilometres);
        ps = findViewById(R.id.editText_ps);
        description = findViewById(R.id.editText_Description);
        telNumber = findViewById(R.id.editText_TelNumber);
        carImage = findViewById(R.id.carImage);
        location = findViewById(R.id.location_TextView);

        name.setText(intent.getStringExtra("Name"));
        price.setText(intent.getStringExtra("Price"));
        first_registration.setText(intent.getStringExtra("First_Registration"));
        kilometres.setText(intent.getStringExtra("Kilometres"));
        ps.setText(intent.getStringExtra("Ps"));
        description.setText(intent.getStringExtra("Description"));
        telNumber.setText(intent.getStringExtra("TelNumber"));
        location.setText(intent.getStringExtra("Location"));


        carURL = intent.getStringExtra("CarURL");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(name.getText().toString());
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        switch (id) {
            case android.R.id.home:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




}

