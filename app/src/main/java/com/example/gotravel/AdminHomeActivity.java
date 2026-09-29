package com.example.gotravel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button btnInsertPlace = findViewById(R.id.btn_insert_place);
        Button btnViewPlace = findViewById(R.id.btn_view_place);
        Button btnUpdatePlace = findViewById(R.id.btn_update_place);
        Button btnDeletePlace = findViewById(R.id.btn_delete_place);

        btnInsertPlace.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, InsertPlaceActivity.class)));
        btnViewPlace.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, ViewPlaceActivity.class)));
        btnUpdatePlace.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, UpdatePlaceActivity.class)));
        btnDeletePlace.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, DeletePlaceActivity.class)));
    }
}
