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
        name = findViewById(R.id.name_TextView);
        price = findViewById(R.id.price_TextView);
        first_registration = findViewById(R.id.firstRegistration_TextView);
        kilometres = findViewById(R.id.kilometres_TextView);
        ps = findViewById(R.id.ps_TextView);
        description = findViewById(R.id.description_TextView);
        telNumber = findViewById(R.id.telNumber_TextView);
        carImage = findViewById(R.id.carImage);
        location = findViewById(R.id.location_TextView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name.setText(bundle.getString("Name"));
        price.setText(bundle.getString("Price"));
        first_registration.setText(bundle.getString("First_Registration"));
        kilometres.setText(bundle.getString("Kilometres"));
        ps.setText(bundle.getString("Ps"));
        description.setText(bundle.getString("Description"));
        telNumber.setText(bundle.getString("TelNumber"));
        location.setText(bundle.getString("Location"));


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
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




}

