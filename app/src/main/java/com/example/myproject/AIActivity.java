package com.example.myproject;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AIActivity extends AppCompatActivity {

    private LinearLayout chatContainer;
    private ScrollView chatScrollView;
    private EditText inputPrompt;
    private ImageButton btnSend;

    private final String API_KEY = "AIzaSyAElYRgp1Epm1Try5rjcoqFD-tXduCTK1g";

    // Typing indicator
    private TextView typingIndicator;
    private Handler typingHandler;
    private Runnable typingRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chat);

        chatContainer = findViewById(R.id.chatContainer);
        chatScrollView = findViewById(R.id.chatScrollView);
        inputPrompt = findViewById(R.id.inputPrompt);
        btnSend = findViewById(R.id.btnSend);

        // Scroll to bottom automatically when keyboard opens or layout changes
        chatScrollView.getViewTreeObserver().addOnGlobalLayoutListener(() ->
                chatScrollView.post(() -> chatScrollView.fullScroll(View.FOCUS_DOWN))
        );

        btnSend.setOnClickListener(v -> {
            String userMessage = inputPrompt.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                addMessage(userMessage, true);  // User message
                inputPrompt.setText("");
                showTypingIndicator();          // Show typing animation
                callGeminiAPI(userMessage);     // AI response
            }
        });
    }

    // Add message dynamically to chat
    private void addMessage(String message, boolean isUser) {
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextColor(isUser ? Color.WHITE : Color.BLACK);
        textView.setBackgroundResource(isUser ? R.drawable.bg_user_message : R.drawable.bg_ai_message);
        textView.setPadding(24, 16, 24, 16);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        int sideMargin = 20;      // space from opposite side
        int topBottomMargin = 16; // vertical spacing
        params.gravity = isUser ? Gravity.END : Gravity.START;

        if (isUser) {
            params.setMargins(sideMargin, topBottomMargin, 10, topBottomMargin);
        } else {
            params.setMargins(10, topBottomMargin, sideMargin, topBottomMargin);
        }

        textView.setLayoutParams(params);
        textView.setMaxWidth(800); // max width for chat bubble

        chatContainer.addView(textView);
        chatScrollView.post(() -> chatScrollView.fullScroll(View.FOCUS_DOWN));
    }

    // Show "AI is typing..." indicator with animated dots
    private void showTypingIndicator() {
        if (typingIndicator != null) return; // already showing

        typingIndicator = new TextView(this);
        typingIndicator.setText("AI is typing");
        typingIndicator.setTextColor(Color.BLACK);
        typingIndicator.setBackgroundResource(R.drawable.bg_ai_message);
        typingIndicator.setPadding(24, 16, 24, 16);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.START;
        params.setMargins(10, 16, 20, 16);
        typingIndicator.setLayoutParams(params);

        chatContainer.addView(typingIndicator);
        chatScrollView.post(() -> chatScrollView.fullScroll(View.FOCUS_DOWN));

        // Animate dots
        typingHandler = new Handler();
        typingRunnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                String dots = "";
                for (int i = 0; i < count; i++) dots += ".";
                typingIndicator.setText("AI is typing" + dots);
                count = (count + 1) % 4; // 0..3 dots
                typingHandler.postDelayed(this, 500); // every 0.5 sec
            }
        };
        typingHandler.post(typingRunnable);
    }

    // Remove typing indicator
    private void hideTypingIndicator() {
        if (typingHandler != null && typingRunnable != null) {
            typingHandler.removeCallbacks(typingRunnable);
        }
        if (typingIndicator != null) {
            chatContainer.removeView(typingIndicator);
            typingIndicator = null;
        }
    }

    // Call Gemini API using OkHttp
    private void callGeminiAPI(String prompt) {
        OkHttpClient client = new OkHttpClient();

        try {
            JSONObject textPart = new JSONObject();
            textPart.put("text", prompt);

            JSONArray partsArray = new JSONArray();
            partsArray.put(textPart);

            JSONObject contentObject = new JSONObject();
            contentObject.put("parts", partsArray);

            JSONArray contentsArray = new JSONArray();
            contentsArray.put(contentObject);

            JSONObject requestBodyJson = new JSONObject();
            requestBodyJson.put("contents", contentsArray);

            RequestBody body = RequestBody.create(
                    requestBodyJson.toString(),
                    MediaType.get("application/json")
            );

            Request request = new Request.Builder()
                    .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        hideTypingIndicator();
                        addMessage("Network error: " + e.getMessage(), false);
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(() -> hideTypingIndicator());

                    if (response.body() == null) {
                        runOnUiThread(() -> addMessage("Empty response from API", false));
                        return;
                    }

                    String responseData = response.body().string();
                    Log.d("AIActivity", "Raw response: " + responseData);

                    try {
                        JSONObject json = new JSONObject(responseData);

                        if (json.has("error")) {
                            String errorMsg = json.getJSONObject("error").getString("message");
                            runOnUiThread(() -> addMessage("API Error: " + errorMsg, false));
                            return;
                        }

                        JSONArray candidates = json.getJSONArray("candidates");
                        JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
                        JSONArray parts = content.getJSONArray("parts");
                        String reply = parts.getJSONObject(0).getString("text");

                        runOnUiThread(() -> addMessage(reply, false));

                    } catch (Exception e) {
                        runOnUiThread(() -> addMessage("Parsing error: " + e.getMessage(), false));
                    }
                }
            });

        } catch (Exception e) {
            hideTypingIndicator();
            addMessage("Error creating request: " + e.getMessage(), false);
        }
    }
}
