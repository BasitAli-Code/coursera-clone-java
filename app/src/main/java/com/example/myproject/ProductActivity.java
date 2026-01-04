package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel("Intro to Programming", "Learn basics of coding with C, Java, and Python." , R.drawable.intro_programming));
        products.add(new ProductModel("Data Science", "Learn to analyze and visualize data." , R.drawable.data_science));
        products.add(new ProductModel("Machine Learning", "Build models and understand AI." , R.drawable.machine_learning));
        products.add(new ProductModel("Web Development", "Learn HTML, CSS, and JavaScript." , R.drawable.web_development));
        products.add(new ProductModel("Mobile App Development", "Make Android apps with Java." , R.drawable.mobile_app));
        products.add(new ProductModel("Cyber Security", "Protect systems and networks." , R.drawable.cyber_security));
        products.add(new ProductModel("AI for Everyone", "Understand Artificial Intelligence." , R.drawable.ai));
        products.add(new ProductModel("Database Design", "Learn SQL and ER diagrams." , R.drawable.database));
        products.add(new ProductModel("Cloud Computing", "Understand AWS, Azure, and Google Cloud." , R.drawable.cloud));
        products.add(new ProductModel("Digital Marketing", "SEO, ads, and content strategy." , R.drawable.digital));

        ProductAdapter adapter = new ProductAdapter(products, product -> {
            Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);
            intent.putExtra("title", product.getTitle());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("imageResId", product.getImageResId());

            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}
