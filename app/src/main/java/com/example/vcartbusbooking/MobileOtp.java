package com.example.vcartbusbooking;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MobileOtp extends AppCompatActivity {

    private EditText[] otpInputs = new EditText[6];
    private Button verifyOtpButton;
    private String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp);

        otpInputs[0] = findViewById(R.id.inputotp1);
        otpInputs[1] = findViewById(R.id.inputotp2);
        otpInputs[2] = findViewById(R.id.inputotp3);
        otpInputs[3] = findViewById(R.id.inputotp4);
        otpInputs[4] = findViewById(R.id.inputotp5);
        otpInputs[5] = findViewById(R.id.inputotp6);
        verifyOtpButton = findViewById(R.id.sign);

        mobileNumber = getIntent().getStringExtra("mobile");

        setupOtpInputs();

        verifyOtpButton.setOnClickListener(v -> validateOtp());
    }

    private void setupOtpInputs() {
        for (int i = 0; i < otpInputs.length; i++) {
            final int index = i;
            otpInputs[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty() && index < otpInputs.length - 1) {
                        otpInputs[index + 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void validateOtp() {
        StringBuilder otpBuilder = new StringBuilder();
        for (EditText otpInput : otpInputs) {
            otpBuilder.append(otpInput.getText().toString());
        }

        String otp = otpBuilder.toString();

        if (otp.length() < 6) {
            Toast.makeText(this, "Enter complete OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        verifyOtp(otp);
    }

    private void verifyOtp(String otp) {
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("otp", otp);
                jsonObject.put("mobile", mobileNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
            Request request = new Request.Builder()
                    .url("https://api-629-bis.vilvabusiness.com/api/verify-otp")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String responseBody = response.body() != null ? response.body().string() : "No response";

                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(MobileOtp.this, "OTP Verified!", Toast.LENGTH_SHORT).show();
                        goToHomePage();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(MobileOtp.this, "Invalid OTP!", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MobileOtp.this, "Network Error!", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void goToHomePage() {
        Intent intent = new Intent(MobileOtp.this, SourceAndDestination.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
