package com.example.cartrade;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainAdapter extends BaseAdapter implements Filterable {
    private List<Car> carList = new ArrayList<>();
    private int layout_ID;
    private Context context;
    private LayoutInflater inflater;

    public MainAdapter(Context context, int layout_adapter_ID, List<Car> carList) {
        this.carList = carList;
        this.layout_ID = layout_adapter_ID;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return carList.size();
    }

    @Override
    public Object getItem(int position) {
        return carList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = (convertView == null) ? inflater.inflate(this.layout_ID, null) : convertView;

        Car c = carList.get(position);
        ((TextView) listItem.findViewById(R.id.carName)).setText(c.getName());
        ((TextView) listItem.findViewById(R.id.price)).setText(c.getPrice()  + "€");
        ((TextView) listItem.findViewById(R.id.ps)).setText(c.getPs()  + " PS");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("imgs/"+c.getCarURL());
        ImageView imageView = listItem.findViewById(R.id.imageView);

        //Glide.with(context.getApplicationContext()).load(storageRef).into(imageView);
        storageRef.getBytes(8388608).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(bitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Fehler");
            }
        });
        return listItem;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = carList.size();
                    filterResults.values = carList;

                } else {
                    List<Car> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (Car itemsModel : carList) {
                        if (itemsModel.getName().contains(searchStr)) {
                            resultsModel.add(itemsModel);
                            filterResults.count = resultsModel.size();
                            filterResults.values = resultsModel;
                        }
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                carList = (List<Car>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}

