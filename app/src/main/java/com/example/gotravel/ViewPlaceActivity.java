package com.example.gotravel;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewPlaceActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper dbHelper;
    private PlaceAdapter placeAdapter;
    private ArrayList<Place> placeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);

        listView = findViewById(R.id.list_view);
        dbHelper = new DatabaseHelper(this);
        placeList = new ArrayList<>();

        Cursor cursor = dbHelper.getAllPlaces();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_DESCRIPTION));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_IMAGE));

                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                placeList.add(new Place(id, name, description, bitmap));
            }
            cursor.close();
        }

        placeAdapter = new PlaceAdapter(this, placeList);
        listView.setAdapter(placeAdapter);
    }
}
