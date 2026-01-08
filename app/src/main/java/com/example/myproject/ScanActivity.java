package com.example.myproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

public class ScanActivity extends AppCompatActivity {

    Button btnScan;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        btnScan = findViewById(R.id.btnScan);
        txtResult = findViewById(R.id.txtResult);

        btnScan.setOnClickListener(v -> startQRScanner());
    }

    private void startQRScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Point camera at a QR code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setCaptureActivity(CaptureAct.class);
        qrLauncher.launch(options);
    }

    private final androidx.activity.result.ActivityResultLauncher<ScanOptions> qrLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    txtResult.setText(result.getContents());
                } else {
                    Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
                }
            });
}
