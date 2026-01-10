package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    private EditText inputPrompt;
    private ImageButton btnSend;
    private LinearLayout chatContainer;
    private ScrollView chatScrollView;
    private LinearLayout loadingLayout;

    private DatabaseReference chatRef;
    private String currentUserEmail;

    // ensures animation runs only once
    private boolean isInitialLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Bind UI
        inputPrompt = findViewById(R.id.inputPrompt);
        btnSend = findViewById(R.id.btnSend);
        chatContainer = findViewById(R.id.chatContainer);
        chatScrollView = findViewById(R.id.chatScrollView);
        loadingLayout = findViewById(R.id.loadingLayout);

        // Show loading animation initially
        loadingLayout.setVisibility(View.VISIBLE);

        // Get current user email
        currentUserEmail = Objects.requireNonNull(
                FirebaseAuth.getInstance().getCurrentUser()
        ).getEmail();

        // Firebase reference
        chatRef = FirebaseDatabase.getInstance().getReference("groupChat");

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                // Clear messages ONLY when loading fresh data
                chatContainer.removeAllViews();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    String message = snap.child("message").getValue(String.class);
                    String email = snap.child("email").getValue(String.class);
                    addMessageToContainer(message, email);
                }

                // Hide loader ONLY ONCE
                if (isInitialLoad) {
                    loadingLayout.setVisibility(View.GONE);
                    chatScrollView.setVisibility(View.VISIBLE);
                    isInitialLoad = false;
                }

                chatScrollView.post(() ->
                        chatScrollView.fullScroll(View.FOCUS_DOWN)
                );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(
                        ChatActivity.this,
                        "Chat error: " + error.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // Send button
        btnSend.setOnClickListener(v -> {
            String msg = inputPrompt.getText().toString().trim();

            if (msg.isEmpty()) {
                Toast.makeText(
                        ChatActivity.this,
                        "Please type a message",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            String key = chatRef.push().getKey();
            if (key != null) {
                chatRef.child(key).child("message").setValue(msg);
                chatRef.child(key).child("email").setValue(currentUserEmail);
            }

            inputPrompt.setText("");
        });
    }

    // Adds chat bubbles
    private void addMessageToContainer(String message, String email) {
        if (message == null || email == null) return;

        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextSize(16f);
        textView.setPadding(20, 12, 20, 12);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        int sideMargin = 20;
        int verticalMargin = 12;

        if (email.equals(currentUserEmail)) {
            params.gravity = Gravity.END;
            params.setMargins(sideMargin, verticalMargin, 10, verticalMargin);
            textView.setBackgroundResource(R.drawable.bg_user_message);
            textView.setTextColor(Color.WHITE);
        } else {
            params.gravity = Gravity.START;
            params.setMargins(10, verticalMargin, sideMargin, verticalMargin);
            textView.setBackgroundResource(R.drawable.bg_ai_message);
            textView.setTextColor(Color.BLACK);
        }

        textView.setLayoutParams(params);
        chatContainer.addView(textView);
    }
}
