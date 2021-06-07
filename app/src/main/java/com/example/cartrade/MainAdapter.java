package com.example.cartrade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainAdapter extends BaseAdapter {
    private List<Car> carList = new ArrayList<>();
    private int layout_ID;
    private Context context;
    private LayoutInflater inflater;

    public MainAdapter(Context context, int layout_adapter_ID, ArrayList<Car> carList) {
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
        ((TextView) listItem.findViewById(R.id.price)).setText(c.getPrice());
        return listItem;
    }
}
