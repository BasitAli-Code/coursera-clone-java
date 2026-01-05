package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        Button signupButton = findViewById(R.id.sgbtn);
        Button loginButton = findViewById(R.id.loginButton);
        EditText emailField = findViewById(R.id.emailfield);
        EditText passwordField = findViewById(R.id.passwordfield);

        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
            startActivity(intent);
            finish();
        });

        loginButton.setOnClickListener(v -> {

            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                SharedPreferences.Editor spEditor = sp.edit();
                                spEditor.putBoolean("isLoggedIn", true);
                                spEditor.apply();

                                Toast.makeText(LoginActivity.this,
                                        "Login Successfully",
                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this,
                                        "Login failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });
    }
}
