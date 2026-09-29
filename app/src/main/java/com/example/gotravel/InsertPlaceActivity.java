package com.example.gotravel;

import android.content.Intent;
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
import java.io.IOException;
import java.io.InputStream;

public class InsertPlaceActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private EditText etPlaceName, etPlaceDescription;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_place);

        imageView = findViewById(R.id.place_image);
        etPlaceName = findViewById(R.id.et_place_name);
        etPlaceDescription = findViewById(R.id.et_place_description);
        Button btnInsert = findViewById(R.id.btn_insert);
        Button btnSelectImage = findViewById(R.id.btn_select_image);

        dbHelper = new DatabaseHelper(this);

        btnSelectImage.setOnClickListener(v -> {
            openGallery();
        });

        btnInsert.setOnClickListener(v -> {
            insertPlace();
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void insertPlace() {
        String name = etPlaceName.getText().toString();
        String description = etPlaceDescription.getText().toString();

        if (name.isEmpty() || description.isEmpty() || imageView.getDrawable() == null) {
            Toast.makeText(InsertPlaceActivity.this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
        } else {
            byte[] image = getByteArrayFromImageView(imageView);

            boolean result = dbHelper.insertPlace(name, description, image);
            if (result) {
                Toast.makeText(InsertPlaceActivity.this, "Place inserted successfully", Toast.LENGTH_SHORT).show();
                // Clear fields after successful insertion
                etPlaceName.setText("");
                etPlaceDescription.setText("");
                imageView.setImageResource(0);
            } else {
                Toast.makeText(InsertPlaceActivity.this, "Failed to insert place", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getByteArrayFromImageView(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}