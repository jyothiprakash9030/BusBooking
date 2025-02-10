package com.example.vcartbusbooking;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

public class Passenger_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_passenger_details);
        EditText nameEditText = findViewById(R.id.passenger_name);
        EditText ageEditText = findViewById(R.id.passenger_age);
        Spinner genderSpinner = findViewById(R.id.passenger_gender);
        EditText emailEditText = findViewById(R.id.passenger_email);
        EditText whatsappEditText = findViewById(R.id.passenger_whatsapp);
        CheckBox notificationsCheckBox = findViewById(R.id.checkbox_notifications);
        EditText couponEditText = findViewById(R.id.coupon_code);
        Button applyCouponButton = findViewById(R.id.apply_coupon);
        CheckBox termsCheckBox = findViewById(R.id.checkbox_terms);
        Button proceedPayButton = findViewById(R.id.proceed_pay);

        // Apply Coupon Button Logic
        applyCouponButton.setOnClickListener(v -> {
            String coupon = couponEditText.getText().toString();
            if (coupon.isEmpty()) {
                Toast.makeText(this, "Enter a valid coupon code", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Coupon applied: " + coupon, Toast.LENGTH_SHORT).show();
            }
        });

        // Proceed Pay Button Logic
        proceedPayButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String age = ageEditText.getText().toString();
            String gender = genderSpinner.getSelectedItem().toString();
            String email = emailEditText.getText().toString();
            String whatsapp = whatsappEditText.getText().toString();
            boolean termsAccepted = termsCheckBox.isChecked();

            if (name.isEmpty() || age.isEmpty() || email.isEmpty() || whatsapp.isEmpty() || !termsAccepted) {
                Toast.makeText(this, "Please fill all details and accept terms & conditions", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Proceeding with payment...", Toast.LENGTH_SHORT).show();
                // Add your payment integration logic here
            }
        });



    }
}