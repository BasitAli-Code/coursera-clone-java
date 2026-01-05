package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        auth = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.lgbtn);
        Button signinButton = findViewById(R.id.createAccountButton);

        EditText nameField = findViewById(R.id.namefield);
        EditText emailField = findViewById(R.id.emailfield);
        EditText passwordField = findViewById(R.id.passwordfield);
        CheckBox checkBoxField = findViewById(R.id.termsCheckBox);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        signinButton.setOnClickListener(v -> {

            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            boolean isTermsAccepted = checkBoxField.isChecked();

            // Validations
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isTermsAccepted) {
                Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SigninActivity.this,
                                        "Account Created successfully",
                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SigninActivity.this,
                                        "Sign up failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });
    }
}
