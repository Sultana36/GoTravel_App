package com.example.gotravel;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class UpdatePlaceActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private EditText etPlaceName, etPlaceDescription, etSearchPlace;
    private DatabaseHelper dbHelper;
    private int placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_place);

        imageView = findViewById(R.id.place_image);
        etPlaceName = findViewById(R.id.et_place_name);
        etPlaceDescription = findViewById(R.id.et_place_description);
        etSearchPlace = findViewById(R.id.et_search_place);
        Button btnSearch = findViewById(R.id.btn_search);
        Button btnUpdate = findViewById(R.id.btn_update);
        Button btnSelectImage = findViewById(R.id.btn_select_image);

        dbHelper = new DatabaseHelper(this);

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnSearch.setOnClickListener(v -> {
            String searchQuery = etSearchPlace.getText().toString();
            Cursor cursor = dbHelper.searchPlaceByName(searchQuery);

            if (cursor != null && cursor.moveToFirst()) {
                placeId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_DESCRIPTION));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PLACE_IMAGE));

                etPlaceName.setText(name);
                etPlaceDescription.setText(description);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(UpdatePlaceActivity.this, "Place not found", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(v -> {
            String name = etPlaceName.getText().toString();
            String description = etPlaceDescription.getText().toString();

            if (name.isEmpty() || description.isEmpty() || imageView.getDrawable() == null) {
                Toast.makeText(UpdatePlaceActivity.this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            } else {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] image = byteArrayOutputStream.toByteArray();

                boolean result = dbHelper.updatePlace(placeId, name, description, image);
                if (result) {
                    Toast.makeText(UpdatePlaceActivity.this, "Place updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdatePlaceActivity.this, "Failed to update place", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageView.setImageURI(data.getData());
        }
    }
}

