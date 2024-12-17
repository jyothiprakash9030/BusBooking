package com.example.vcartbusbooking;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BusDetailsActivity extends AppCompatActivity {
    TextView textViewBusDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        textViewBusDetails = findViewById(R.id.textViewBusDetails);

        // Get data from the intent
        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String date = getIntent().getStringExtra("date");

        // Display user input (later replace this with fetched data from a server)
        String details = "Buses from " + from + " to " + to + " on " + date;
        textViewBusDetails.setText(details);
    }
}
