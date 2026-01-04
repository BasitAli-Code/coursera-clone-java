package com.example.myproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Button loginButton = findViewById(R.id.lgbtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button signinButton = findViewById(R.id.createAccountButton);
        EditText nameField = findViewById(R.id.namefield);
        EditText emailField = findViewById(R.id.emailfield);

        EditText passwordField = findViewById(R.id.passwordfield);
        CheckBox checkBoxField = findViewById(R.id.termsCheckBox);

        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();
                boolean isTermsAccepted = checkBoxField.isChecked();

                // Validations
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SigninActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(SigninActivity.this, "Password should be atleast 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SigninActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sp.contains(email + "_email")) {
                    Toast.makeText(SigninActivity.this, "User with this email already exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isTermsAccepted) {
                    Toast.makeText(SigninActivity.this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor spEditor = sp.edit();

                String nameKey = name + "_name";
                String emailKey = email + "_email";
                String password_key = email + "_password";

                spEditor.putString(nameKey, name);
                spEditor.putString(emailKey, email);
                spEditor.putString(password_key, password);
                spEditor.apply();

                Toast.makeText(SigninActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
