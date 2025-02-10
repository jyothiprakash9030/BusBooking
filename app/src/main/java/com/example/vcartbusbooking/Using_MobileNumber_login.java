package com.example.vcartbusbooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Using_MobileNumber_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_using_mobile_number_login);
        Button button=findViewById(R.id.btn_generate_otp);
        button.setOnClickListener(view -> {
            Intent intent=new Intent(Using_MobileNumber_login.this,Using_Email_login.class);
            startActivity(intent);




        });
    }
}