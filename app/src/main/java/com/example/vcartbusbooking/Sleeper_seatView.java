
package com.example.vcartbusbooking;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.atomic.AtomicInteger;

public class Sleeper_seatView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sleeper_seat_view);


        int rows = 6; // 12 rows
        int columns = 4; // 5 columns
        int p = 1;

        // Reference to GridLayout
        GridLayout seatingLayout = findViewById(R.id.seatingLayout_sleeper);
        seatingLayout.setRowCount(rows);
        seatingLayout.setColumnCount(columns);

        // Create ImageView for the driver's seat and position it at the top-right
        ImageView driverSeatImage = new ImageView(this);
        driverSeatImage.setImageResource(R.drawable.imagedriver); // Add your driver's seat image in drawable folder
        GridLayout.LayoutParams driverParams = new GridLayout.LayoutParams();
        driverParams.rowSpec = GridLayout.spec(0, 1); // Top row
        driverParams.columnSpec = GridLayout.spec(columns - 1, 1); // Last column (top-right)
        driverSeatImage.setLayoutParams(driverParams);
        driverParams.width = 100;  // Set the desired width for the driver's seat image
        driverParams.height = 100;
        // Add the driver's seat image to the GridLayout
        seatingLayout.addView(driverSeatImage);
        int seatPrice = 500; // Price per seat
        AtomicInteger selectedSeats = new AtomicInteger(000); // Track selected seats
//        TextView passengerCountTextView1 = findViewById(R.id.passenger_count1);
//        passengerCountTextView1.setText("0 Passenger(s)|"); // Initial value// Display initial amount
//
//        // Reference to the TextView to display the amount
//        TextView amountTextView = findViewById(R.id.passenger_info);
//        amountTextView.setText("₹ " + selectedSeats.get() * seatPrice);


        // Loop to generate seats dynamically
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // Add a gap (empty space) in the middle column for the aisle
                if (j == 1) {
                    // Add a gap (invisible view) for the aisle
                    View gap = new View(this);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = 50; // Width of the aisle gap
                    params.height = 50; // Height of the aisle gap
                    gap.setLayoutParams(params);
                    seatingLayout.addView(gap);
                } else {
                    // Create a button for the seat
                    Button seatButton = new Button(this);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = 150; // Set seat width
                    params.height = 210; // Set seat height
                    params.setMargins(10, 10, 10, 10); // Add margins between seats
                    seatButton.setLayoutParams(params);

                    // Set seat text based on position
                    String seatText = "s" + p; // Skipping aisle column
                    seatButton.setText(seatText);

                    // Check if the seat is D1, if it is, mark it as the conductor's seat
                    seatButton.setTextSize(12);

                    p++;
                    seatButton.setGravity(Gravity.CENTER);
                    seatButton.setTextColor(Color.BLACK);

                    // Add click listener for seat selection

                    seatButton.setOnClickListener(v -> {
                        // Skip updating for the conductor's seat

                        if (v.isSelected()) {
                            v.setSelected(false);
                            seatButton.setBackgroundColor(Color.parseColor("#DFDFDF")); // Deselect seat
                            selectedSeats.decrementAndGet(); // Decrease seat count
                        } else {
                            v.setSelected(true);
                            seatButton.setBackgroundColor(Color.parseColor("#78CE9C")); // Select seat
                            selectedSeats.incrementAndGet(); // Increase seat count
                        }

                        // Calculate and update total amount
                        int totalAmount = selectedSeats.get() * seatPrice;
//                        amountTextView.setText("₹ " + totalAmount);
//                        passengerCountTextView1.setText(selectedSeats.get() + " Passenger(s) |");
                    });
                    // Add the button to the GridLayout
                    seatingLayout.addView(seatButton);
                }
            }
        }
        Button btnLower = findViewById(R.id.btn_lower);
        Button btnUpper = findViewById(R.id.btn_upper);
        Button btnInfo = findViewById(R.id.btn_info);

        btnLower.setOnClickListener(v -> {
            btnLower.setBackgroundResource(R.drawable.btn_lower_active);
            btnLower.setTextColor(getResources().getColor(R.color.white));

            btnUpper.setBackgroundResource(R.drawable.btn_inactive);
            btnUpper.setTextColor(getResources().getColor(R.color.gray));

            btnInfo.setBackgroundResource(R.drawable.btn_inactive);
            btnInfo.setTextColor(getResources().getColor(R.color.gray));
            Intent intent = new Intent(Sleeper_seatView.this, Bus_Seating_view.class);
            startActivity(intent);
        });

        btnUpper.setOnClickListener(v -> {
            btnUpper.setBackgroundResource(R.drawable.btn_lower_active);
            btnUpper.setTextColor(getResources().getColor(R.color.white));

            btnLower.setBackgroundResource(R.drawable.btn_inactive);
            btnLower.setTextColor(getResources().getColor(R.color.black));

            btnInfo.setBackgroundResource(R.drawable.btn_inactive);
            btnInfo.setTextColor(getResources().getColor(R.color.black));

        });

        btnInfo.setOnClickListener(v -> {
            btnInfo.setBackgroundResource(R.drawable.btn_lower_active);
            btnInfo.setTextColor(getResources().getColor(R.color.white));

            btnLower.setBackgroundResource(R.drawable.btn_inactive);
            btnLower.setTextColor(getResources().getColor(R.color.gray));

            btnUpper.setBackgroundResource(R.drawable.btn_inactive);
            btnUpper.setTextColor(getResources().getColor(R.color.gray));
        });


        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a LinearLayout for the popup
                LinearLayout popupLayout = new LinearLayout(Sleeper_seatView.this);
                popupLayout.setOrientation(LinearLayout.VERTICAL);
                popupLayout.setBackgroundColor(Color.WHITE);
                popupLayout.setPadding(200, 20, 20, 20);
                popupLayout.setElevation(10);

                // Define colors and labels
                int[] colors = {Color.parseColor("#D1C4E9"), Color.parseColor("#F8BBD0"),
                        Color.parseColor("#FFFFFF"), Color.parseColor("#BDBDBD"),
                        Color.parseColor("#C8E6C9")};
                String[] labels = {"CHALLENGED / SENIORS", "WOMEN", "AVAILABLE", "UNAVAILABLE", "SELECTED"};

                // Add rows dynamically
                for (int i = 0; i < labels.length; i++) {
                    LinearLayout row = new LinearLayout(Sleeper_seatView.this);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setGravity(Gravity.CENTER_VERTICAL);
                    row.setPadding(5, 10, 5, 10);

                    // Create colored box
                    View colorBox = new View(Sleeper_seatView.this);
                    LinearLayout.LayoutParams boxParams = new LinearLayout.LayoutParams(40, 40);
                    boxParams.setMargins(10, 0, 20, 0);
                    colorBox.setLayoutParams(boxParams);

                    // Set rounded corner background
                    GradientDrawable shape = new GradientDrawable();
                    shape.setColor(colors[i]);
                    shape.setCornerRadius(5);
                    colorBox.setBackground(shape);
//                    if (i == 2) { // White box (AVAILABLE)
//                        shape.setStroke(1, Color.BLACK); // Black border
//                    }

                    colorBox.setBackground(shape);
                    // Create text label
                    TextView textView = new TextView(Sleeper_seatView.this);
                    textView.setText(labels[i]);
                    textView.setTextSize(14);
                    textView.setTextColor(Color.BLACK);

                    // Add color box & text to row
                    row.addView(colorBox);
                    row.addView(textView);

                    // Add row to popup layout
                    popupLayout.addView(row);
                }

                // Create PopupWindow
                PopupWindow popupWindow = new PopupWindow(popupLayout, 700, 700, true);
                popupWindow.showAsDropDown(btnInfo, 0, 20); // Show below button
            }
        });


    }



}