package com.example.gotravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class FindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        findViewById(R.id.textView10).setOnClickListener(v -> startActivity(new Intent(FindActivity.this, ViewPlaceActivity.class)));

        findViewById(R.id.popularItem1).setOnClickListener(v -> startActivity(new Intent(FindActivity.this, Pop1Activity.class)));
        findViewById(R.id.popularItem2).setOnClickListener(v -> startActivity(new Intent(FindActivity.this, Pop2Activity.class)));
        findViewById(R.id.popularItem3).setOnClickListener(v -> startActivity(new Intent(FindActivity.this, Pop3Activity.class)));
        findViewById(R.id.popularItem4).setOnClickListener(v -> startActivity(new Intent(FindActivity.this, Pop4Activity.class)));
        findViewById(R.id.popularItem5).setOnClickListener(v -> startActivity(new Intent(FindActivity.this, Pop5Activity.class)));

        findViewById(R.id.recommendedItem1).setOnClickListener(v -> startActivity(new Intent(FindActivity.this, Rec1Activity.class)));
        findViewById(R.id.recommendedItem2).setOnClickListener(v -> startActivity(new Intent(FindActivity.this, Rec2Activity.class)));
        findViewById(R.id.recommendedItem3).setOnClickListener(v -> startActivity(new Intent(FindActivity.this, Rec3Activity.class)));
        findViewById(R.id.recommendedItem4).setOnClickListener(v -> startActivity(new Intent(FindActivity.this, Rec4Activity.class)));
        findViewById(R.id.recommendedItem5).setOnClickListener(v -> startActivity(new Intent(FindActivity.this, Rec5Activity.class)));
    }
}
