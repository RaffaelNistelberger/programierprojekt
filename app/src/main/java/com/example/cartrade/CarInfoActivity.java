package com.example.cartrade;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CarInfoActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_car_info);
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
        price.setText(bundle.getString("Price")+"€");
        first_registration.setText(bundle.getString("First_Registration"));
        kilometres.setText(bundle.getString("Kilometres")+"km");
        ps.setText(bundle.getString("Ps")+" PS");
        description.setText(bundle.getString("Description"));
        telNumber.setText(bundle.getString("TelNumber"));
        location.setText(bundle.getString("Location"));
        carURL = intent.getStringExtra("CarURL");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(name.getText().toString());
        actionBar.setDisplayHomeAsUpEnabled(true);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("imgs/"+carURL);
        //Glide.with(context.getApplicationContext()).load(storageRef).into(imageView);
        storageRef.getBytes(8388608).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                carImage.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Fehler");
            }
        });

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

