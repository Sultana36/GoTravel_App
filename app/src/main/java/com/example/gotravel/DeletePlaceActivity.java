package com.example.gotravel;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeletePlaceActivity extends AppCompatActivity {

    private EditText etSearchPlace;
    private ImageView imageView;
    private DatabaseHelper dbHelper;
    private int placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_place);

        etSearchPlace = findViewById(R.id.et_search_place);
        imageView = findViewById(R.id.place_image);
        Button btnSearch = findViewById(R.id.btn_search);
        Button btnDelete = findViewById(R.id.btn_delete);

        dbHelper = new DatabaseHelper(this);

        btnSearch.setOnClickListener(v -> {
            String searchQuery = etSearchPlace.getText().toString();
            Cursor cursor = dbHelper.searchPlaceByName(searchQuery);

            if (cursor != null && cursor.moveToFirst()) {
                placeId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_DESCRIPTION));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_IMAGE));

                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(DeletePlaceActivity.this, "Place not found", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            boolean result = dbHelper.deletePlace(placeId);
            if (result) {
                Toast.makeText(DeletePlaceActivity.this, "Place deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DeletePlaceActivity.this, "Failed to delete place", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
