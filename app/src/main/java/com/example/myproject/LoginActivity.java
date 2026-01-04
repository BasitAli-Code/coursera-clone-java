package com.example.myproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signupButton = findViewById(R.id.sgbtn);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button loginButton = findViewById(R.id.loginButton);
        EditText emailField = findViewById(R.id.emailfield);
        EditText passwordField = findViewById(R.id.passwordfield);

        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                String emailKey = email + "_email";
                String passwordKey = email + "_password";

                String savedEmail = sp.getString(emailKey, "");
                String savedPassword = sp.getString(passwordKey, "");

                if (savedEmail.isEmpty() || savedPassword.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "No account found. Please sign up first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.equals(savedEmail) || !password.equals(savedPassword)) {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor spEditor = sp.edit();
                spEditor.putBoolean("isLoggedIn" , true);
                spEditor.apply();

                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
