package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        ImageView courseImage = findViewById(R.id.courseImage); // NEW
        TextView courseName = findViewById(R.id.courseName);
        TextView courseDescription = findViewById(R.id.courseDescription);
        Button enrollButton = findViewById(R.id.enrollButton);

        String name = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        int imageResId = getIntent().getIntExtra("imageResId", R.drawable.ai); // default image

        if (name == null) name = "Course Name";
        if (description == null) description = "Course Description";

        courseName.setText(name);
        courseDescription.setText(description);
        courseImage.setImageResource(imageResId);

        enrollButton.setOnClickListener(v -> {
            Toast.makeText(ProductDetailActivity.this, "Enrolled successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ProductDetailActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }
}
