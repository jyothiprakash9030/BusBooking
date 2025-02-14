package com.example.vcartbusbooking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Using_MobileNumber_login extends AppCompatActivity {

    private EditText editTextMobile;
    private Button buttonLogin;
    private static final String API_URL = "https://api-629-bis.vilvabusiness.com/api/request-otp";
    private static final String AUTH_TOKEN = "Bearer YOUR_ACCESS_TOKEN";  // 🔴 Replace with a valid token

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_mobile_number_login);

        editTextMobile = findViewById(R.id.et_phone_number);
        buttonLogin = findViewById(R.id.btn_generate_otp);

        buttonLogin.setOnClickListener(v -> {
            String mobile = editTextMobile.getText().toString().trim();

            if (mobile.isEmpty() || mobile.length() < 10) {
                editTextMobile.setError("Enter a valid mobile number");
                return;
            }

            sendOtpRequest(mobile);
        });
    }

    private void sendOtpRequest(String mobile) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/json");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", mobile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(mediaType, jsonObject.toString());

                Request request = new Request.Builder()
                        .url("https://api-629-bis.vilvabusiness.com/api/request-otp")
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN")  // Replace with actual token
                        //.addHeader("x-api-key", "YOUR_API_KEY")  // Uncomment if your API needs an API key instead
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String responseBody = response.body() != null ? response.body().string() : "No response body";

                    if (response.code() == 401) {  // If 401 Unauthorized, log the issue
                        Log.e("API Error", "Unauthorized: Invalid Token or API Key");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Using_MobileNumber_login.this, "Invalid Token! Please check.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (!response.isSuccessful()) {
                        Log.e("API Error", "Request Failed: " + response.code() + " - " + responseBody);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Using_MobileNumber_login.this, "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Log.d("API Response", "Success: " + responseBody);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Using_MobileNumber_login.this, "OTP Sent Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Using_MobileNumber_login.this, "Network Error!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

}
