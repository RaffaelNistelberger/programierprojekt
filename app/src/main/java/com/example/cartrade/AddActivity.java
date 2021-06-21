package com.example.cartrade;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class AddActivity extends AppCompatActivity {

    private EditText name;
    private EditText price;
    private EditText first_registration;
    private EditText kilometres;
    private EditText ps;
    private EditText description;
    private TextView location;
    private EditText telNumber;
    private Button upload;
    private Button pickPhoto;
    private final int PICK_PHOTO_FOR_AVATAR = 888;
    private ImageView carImage;
    private final int ACCESS_FINE_STORAGE = 123;
    private static final int RQ_ACCESS_FINE_LOCATION = 456;
    private String carImageString;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private LocationManager locationManager;
    private boolean isGpsAllowed;
    private LocationListener locationListener;
    private double lat;
    private double lon;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Intent intent = getIntent();
        isGpsAllowed = intent.getBooleanExtra("isGpsAllowed", false);

        linearLayout = findViewById(R.id.linearLayout);
        name = findViewById(R.id.editText_Name);
        price = findViewById(R.id.editText_Price);
        first_registration = findViewById(R.id.editText_first_registration);
        kilometres = findViewById(R.id.editText_Kilometres);
        ps = findViewById(R.id.editText_ps);
        description = findViewById(R.id.editText_Description);
        telNumber = findViewById(R.id.editText_TelNumber);
        upload = findViewById(R.id.button_Upload);
        carImage = findViewById(R.id.carImage);


        location = findViewById(R.id.textView_location);
        btnClickUpdateCoordinates(linearLayout);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAlert();
            }
        });

        pickPhoto = findViewById(R.id.pickPhoto);
        pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionExternalStorage();

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                carImage.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] upload_data = baos.toByteArray();

                UploadTask uploadTask = storageRef.child("imgs").child(MainActivity.nextIndex+".jpg").putBytes(upload_data);

                System.out.println("Fertig");
                //carImageString =bitMapToString(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("MissingPermission    qasw3e")
    public void btnClickUpdateCoordinates(View view) {

        if (isGpsAllowed) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(
                    LocationManager.GPS_PROVIDER);
            displayLocation(location);
        }
    }



    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
    private void gpsGranted() {
        isGpsAllowed = true;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
    }

    private void checkPermissionGPS() {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    RQ_ACCESS_FINE_LOCATION);
        } else {
            gpsGranted();
        }
    }

    private void displayLocation(Location location) {
        lat = location == null ? -1 :
                location.getLatitude();
        lon = location == null ? -1 :
                location.getLongitude();
        //mLongitude.setText(String.format("%.4f", lon));
        //mLatitude.setText(String.format(".4f", lat));

        LocationTask lTask = new LocationTask(this);
        String locationString = "No location found";
        try {
            String[] tmp = lTask.execute().get().split("\"");

            for (int i = 0; i < tmp.length; i++) {
                if (tmp[i].equals("city")) {
                    locationString = tmp[i + 2];
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.location.setText(locationString);
    }

//    public String bitMapToString(Bitmap bitmap){
//        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
//        byte [] b=baos.toByteArray();
//        String temp= Base64.encodeToString(b, Base64.DEFAULT);
//        return temp;
//    }



    private void checkPermissionExternalStorage() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        String permission2 = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission2)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    ACCESS_FINE_STORAGE);
        } else {
            pickImage();
        }
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    public void returnIntent(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Name", name.getText().toString());
        returnIntent.putExtra("Price", price.getText().toString());
        returnIntent.putExtra("First_Registration", first_registration.getText().toString());
        returnIntent.putExtra("Kilometres", kilometres.getText().toString());
        returnIntent.putExtra("Ps", ps.getText().toString());
        returnIntent.putExtra("Description", description.getText().toString());
        returnIntent.putExtra("TelNumber", telNumber.getText().toString());
        returnIntent.putExtra("Location", location.getText().toString());
        returnIntent.putExtra("CarImageString", carImageString);

        setResult(Activity.RESULT_OK, returnIntent);
    }


    public void uploadAlert(){
        final AlertDialog alert = new AlertDialog.Builder(
                new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Wallpaper))
                .create();
        alert.setTitle("Alert");
        alert.setMessage("Want to upload?");
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);

        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        returnIntent();
                        alert.dismiss();
                        finish();
                    }
                });

        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                        finish();
                    }
                });
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
            }
        });

        alert.show();
    }
}
