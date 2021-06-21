package com.example.cartrade;

import androidx.annotation.RequiresApi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.content.ClipData;
import android.net.Uri;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.os.Message;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

public class MainActivity extends AppCompatActivity {

    private MainAdapter adapter;
    private ArrayList<Car> carList;
    private ListView listView;
    private LinearLayout linearLayout;
    private final static int RQ_PREFERENCES = 1;
    private SharedPreferences prefs;
    public static long nextIndex;
    private final int ADD_ACTIVITY_REQUEST_CODE = 187;
    public boolean darkModeBool;
    public SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private static final int RQ_ACCESS_FINE_LOCATION = 456;
    private boolean isGpsAllowed = false;
    private String GROUP_KEY_NOTIFICATION = "NotificationGroupKey";


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("cars");

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        login();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceChangeListener = this::preferenceChanged;
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        createNotificationChannel();


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "getString(R.string.channel_name)";
            String description = "getString(R. string . channel_description )";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("3", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    public void showNewCarNotification(View view, int position) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        boolean test = prefs.getBoolean("notification_pref", true);
        if(test) {
            Intent intent = new Intent(this, CarInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentCarInfoActivity(position), 0);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification notification = new Notification.Builder(this, "3")
                        .setSmallIcon(android.R.drawable.star_big_on)
                        .setColor(Color.YELLOW)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText("New Car")
                        .setStyle(new Notification.BigTextStyle()
                                .bigText("Created new Car"))
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setGroup(GROUP_KEY_NOTIFICATION)
                        .build();

                notificationManager.notify(222, notification);
            }


        } else {
            notificationManager.cancel(222);
        }


        if (prefs.getBoolean("notification_pref", true)) {
            Notification summaryNotification = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                summaryNotification = new Notification.Builder(this, "3")
                        .setContentTitle("2 new messages")
                        .setSmallIcon(android.R.drawable.star_big_on)
                        .setColor(Color.YELLOW)
                        .setContentTitle(getString(R.string.app_name))
                        .setStyle(new Notification.BigTextStyle()
                                .bigText("Das ist ein Text"))
                        .setWhen(System.currentTimeMillis())
                        .setGroup(GROUP_KEY_NOTIFICATION)
                        .setGroupSummary(true)
                        .build();
            }
            notificationManager.notify(333, summaryNotification);
        } else {
            notificationManager.cancel(333);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder builder = new
                    Notification.Builder(this, "3")
                    .setSmallIcon(android.R.drawable.star_big_on)
                    .setColor(Color.YELLOW)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("This is just a small Notification")
                    .setWhen(System.currentTimeMillis());
        }
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                add();
                return true;
            case R.id.settings:
                Intent intent = new Intent(this,
                        MySettingsActivity.class);
                startActivityForResult(intent, 1);

                return true;
            case R.id.search_bar:
                searchTerm(item);
                return true;
            case R.id.priceInc:
                sortListbyPriceAsc();
                return true;
            case R.id.priceDesc:
                sortListbyPriceDesc();
                return true;
            case R.id.registrationInc:
                sortListbyRegistrationAsc();
                return true;
            case R.id.registrationDesc:
                sortListbyRegistrationDesc();
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

    public void searchTerm(MenuItem menuItem) {
        LinearLayout ll = new LinearLayout(this);
        EditText et = new EditText(this);
        et.setId(R.id.search_term_input);
        et.setHint("Enter search term");
        ll.addView(et);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(ll).setTitle("Search")
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("ok", (dialog, which) -> {
                    if (et.getText() != null) {
                        List<Car> searchList = new ArrayList<>();
                        for (Car car : carList) {
                            if (car.getName().toLowerCase().contains(et.getText().toString().toLowerCase())) {
                                searchList.add(car);
                            }
                        }
                        adapter = new MainAdapter(getApplicationContext(), R.layout.activity_adapter, searchList);
                        listView.setAdapter(adapter);
                    }
                    dialog.dismiss();
                }).show();
    }

    public void login() {
        carList = new ArrayList<>();
        linearLayout = findViewById(R.id.linearLayout);
        listView = findViewById(R.id.listView);
        //loadCarsIntoList
        bindAdapterToListView(listView);
        itemOnClickListener();
        checkPermissionGPS();
    }


    public void bindAdapterToListView(ListView listView) {
        adapter = new MainAdapter(this, R.layout.activity_adapter, carList);

        listView.setAdapter(adapter);
    }

    private void preferenceChanged(SharedPreferences sharedPrefs, String key) {
        if (key.equals("darkmode_pref")) {
            darkModeBool = prefs.getBoolean("darkmode_pref", false);
            if (darkModeBool) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        if (key.equals("notification_pref")) {
            boolean sValue = sharedPrefs.getBoolean(key, true);
            Toast.makeText(this, key + " are " + sValue, Toast.LENGTH_LONG).show();
        }
    }

    public void sortListbyPriceDesc() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            carList.sort((o1, o2) -> (int) (o2.getPrice() - o1.getPrice()));
            bindAdapterToListView(listView);
        }
    }

    public void sortListbyPriceAsc() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            carList.sort((o1, o2) -> (int) (o1.getPrice() - o2.getPrice()));
            bindAdapterToListView(listView);
        }
    }

    public void sortListbyRegistrationDesc() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            carList.sort(new Comparator<Car>() {
                @Override
                public int compare(Car o1, Car o2) {
                    String[] tmp1 = o1.getFirst_registration().split("/");
                    String[] tmp2 = o2.getFirst_registration().split("/");
                    return Integer.parseInt(tmp1[1]) - Integer.parseInt(tmp2[1]);
                }
            });
            bindAdapterToListView(listView);
        }
    }

    public void sortListbyRegistrationAsc() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            carList.sort(new Comparator<Car>() {
                @Override
                public int compare(Car o1, Car o2) {
                    String[] tmp1 = o1.getFirst_registration().split("/");
                    String[] tmp2 = o2.getFirst_registration().split("/");
                    return Integer.parseInt(tmp2[1]) - Integer.parseInt(tmp1[1]);
                }
            });
            bindAdapterToListView(listView);
        }
    }


    private void saveData(long index, Car carToAdd) {
        myRef.child(index + "").setValue(carToAdd.toMap());
        System.out.println("Saved to Database!");
    }

    private void loadData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                carList.clear();
                nextIndex = dataSnapshot.getChildrenCount();
                System.out.println(nextIndex);
                Iterable<DataSnapshot> dataSnapshot1 = dataSnapshot.getChildren();
                Iterator it = dataSnapshot1.iterator();
                while (it.hasNext()) {
                    DataSnapshot ds = ((DataSnapshot) it.next());
                    System.out.println(ds);

                    String name = ds.child("name").getValue().toString();
                    double price = Double.parseDouble(ds.child("price").getValue().toString());
                    String first_registration = ds.child("first_registration").getValue().toString();
                    int ps = Integer.parseInt(ds.child("ps").getValue().toString());
                    int kilometres = Integer.parseInt(ds.child("kilometres").getValue().toString());
                    String description = ds.child("description").getValue().toString();
                    String location = ds.child("location").getValue().toString();
                    String telNumber = ds.child("telNumber").getValue().toString();
                    String carURL = ds.child("carURL").getValue().toString();


                    carList.add(new Car(name, price, first_registration, ps, kilometres, description, location, telNumber, carURL));
                    bindAdapterToListView(listView);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("ERROR");
            }
        });
    }

    public void add() {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        intent.putExtra("isGpsAllowed", this.isGpsAllowed);
        startActivityForResult(intent, ADD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //returns here when AddActivity upload is finished
        if (resultCode == Activity.RESULT_OK) {
            Car tmp = new Car(data.getStringExtra("Name"), Integer.parseInt(data.getStringExtra("Price")), data.getStringExtra("First_Registration"), Integer.parseInt(data.getStringExtra("Ps")), Integer.parseInt(data.getStringExtra("Kilometres")), data.getStringExtra("Description"), data.getStringExtra("Location"), data.getStringExtra("TelNumber"),  nextIndex+".jpg");
            saveData(nextIndex, tmp);
        }
        if (resultCode == Activity.RESULT_CANCELED) {

        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }


    }



    private void checkPermissionGPS() {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    RQ_ACCESS_FINE_LOCATION);
        } else {
            isGpsAllowed = true;
        }
    }


    public Intent intentCarInfoActivity(int position){

        Intent intent = new Intent(this, CarInfoActivity.class);
        intent.putExtra("Name", carList.get(position).getName());
        intent.putExtra("Price", String.valueOf(carList.get(position).getPrice()));
        intent.putExtra("First_Registration", carList.get(position).getFirst_registration());
        intent.putExtra("Kilometres", String.valueOf(carList.get(position).getKilometres()));
        intent.putExtra("Ps", String.valueOf(carList.get(position).getPs()));
        intent.putExtra("Description", carList.get(position).getDescription());
        intent.putExtra("TelNumber", carList.get(position).getTelNumber());
        intent.putExtra("Location", carList.get(position).getLocation());
        intent.putExtra("CarURL", carList.get(position).getCarURL());


        return intent;
    }

    public void itemOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = intentCarInfoActivity(position);
                startActivity(intent);
            }
        });

    }
}
