package com.example.cartrade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private EditText name;
    private EditText price;
    private EditText first_registration;
    private EditText kilometres;
    private EditText ps;
    private EditText description;
    private EditText location;
    private EditText telNumber;
    private Button upload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = findViewById(R.id.editText_Name);
        price = findViewById(R.id.editText_Price);
        first_registration = findViewById(R.id.editText_first_registration);
        kilometres = findViewById(R.id.editText_Kilometres);
        ps = findViewById(R.id.editText_ps);
        description = findViewById(R.id.editText_Description);
        telNumber = findViewById(R.id.editText_TelNumber);
        upload = findViewById(R.id.button_Upload);
        location = findViewById(R.id.editText_Location);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAlert();
            }
        });

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
