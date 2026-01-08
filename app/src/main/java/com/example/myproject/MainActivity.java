package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            String title = item.getTitle().toString();

            if (title.equals("Home")) {
                loadFragment(new HomeFragment());
            } else if (title.equals("Courses")) {
               Intent i = new Intent(MainActivity.this , ProductActivity.class);
               startActivity(i);
            } else if (title.equals("Settings")) {
                loadFragment(new SettingFragment());
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });


        loadFragment(new HomeFragment());

        ImageButton qrBtn = findViewById(R.id.qr_button);
        qrBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            startActivity(intent);
        });

        ImageButton homeBtn = findViewById(R.id.home_button);
        homeBtn.setOnClickListener(v -> loadFragment(new HomeFragment()));

        ImageButton settingBtn = findViewById(R.id.setting_button);
        settingBtn.setOnClickListener(v -> loadFragment(new SettingFragment()));
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
