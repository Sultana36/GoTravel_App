package com.example.gotravel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<com.example.gotravel.Place> placeList;
    private LayoutInflater inflater;

    public PlaceAdapter(Context context, ArrayList<com.example.gotravel.Place> placeList) {
        this.context = context;
        this.placeList = placeList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return placeList.size();
    }

    @Override
    public Object getItem(int position) {
        return placeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_place, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.iv_place_image);
        TextView nameView = convertView.findViewById(R.id.tv_place_name);
        TextView descriptionView = convertView.findViewById(R.id.tv_place_description);

        com.example.gotravel.Place place = placeList.get(position);

        imageView.setImageBitmap(place.getImage());
        nameView.setText(place.getName());
        descriptionView.setText(place.getDescription());

        return convertView;
    }
}
