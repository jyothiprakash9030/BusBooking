package com.example.vcartbusbooking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class Bus_Seating_view extends AppCompatActivity {
    TextView bus_seatingtoadress, bus_seatingfromadress, bus_seatingvisibledate,TotalAmount,bus_name;

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bus_seating_view);
        bus_name=findViewById(R.id.bus_name);
        bus_seatingtoadress = findViewById(R.id.bus_seatingtoadress);
        bus_seatingfromadress = findViewById(R.id.bus_seatingfromadress);
        bus_seatingvisibledate = findViewById(R.id.bus_seatingvisibledate);
        TotalAmount=findViewById(R.id.TotalAmount);

        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String date = getIntent().getStringExtra("date");

        String travelsname=getIntent().getStringExtra("busName");

//////////////










//        ///////////////////////
        bus_seatingtoadress.setText(from);
        bus_seatingfromadress.setText(to);
        bus_seatingvisibledate.setText(date);

        bus_name.setText(travelsname);


        // Define number of rows and columns
        int rows = 8; // Total rows
        int columns = 5; // Total columns
        int p = 0;

        // Reference to GridLayout
        GridLayout seatingLayout = findViewById(R.id.seatingLayout);
        seatingLayout.setRowCount(rows);
        seatingLayout.setColumnCount(columns);

        // Create ImageView for the driver's seat and position it at the top-right
        ImageView driverSeatImage = new ImageView(this);
        driverSeatImage.setImageResource(R.drawable.imagedriver); // Add your driver's seat image in drawable folder
        GridLayout.LayoutParams driverParams = new GridLayout.LayoutParams();
        driverParams.rowSpec = GridLayout.spec(0, 1); // Top row
        driverParams.columnSpec = GridLayout.spec(columns - 1, 1); // Last column (top-right)
        driverSeatImage.setLayoutParams(driverParams);
        driverParams.width = 50;  // Set the desired width for the driver's seat image
        driverParams.height = 100;
        seatingLayout.addView(driverSeatImage);

        // Initial amount (default for one seat)
        int seatPrice = 500; // Price per seat
        AtomicInteger selectedSeats = new AtomicInteger(0); // Track selected seats
//        TextView passengerCountTextView = findViewById(R.id.passenger_count);
//        passengerCountTextView.setText(" Passenger(s)|"); // Initial value
        // Reference to the TextView to display the amount
//        TextView amountTextView = findViewById(R.id.passenger_info);
//        amountTextView.setText("₹ " + selectedSeats.get() * seatPrice); // Display initial amount
        TextView count_seat=findViewById(R.id.TotalAmount);

        count_seat.setText(" ");




        // Loop to generate seats dynamically
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j == 2 && i != rows - 1) {
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
                    params.width = 100; // Seat width
                    params.height = 130; // Seat height
                    params.setMargins(10, 10, 10, 10); // Margins between seats
                    seatButton.setLayoutParams(params);

                    // Set seat text based on position
                    String seatText = "D" + p;

                    seatButton.setText(seatText);

                    // Check if the seat is D1, mark it as the conductor's seat
                    if (p == 0) {
                        seatButton.setText("C"); // Conductor's seat
                        seatButton.setBackgroundColor(Color.parseColor("#FFDD00")); // Yellow color for conductor
                    }
                    else if (p == 19) {

                        seatButton.setBackgroundResource(R.drawable.women_seat_bg); // Yellow color for conductor
                    }
                    else if (p == 2) {

                        seatButton.setBackgroundResource(R.drawable.challenged_or_senior_seat_bg); // Yellow color for conductor
                    }
//                    else if (p == 22) {
//                        seatButton.setBackgroundColor(Color.parseColor("#78CE9C"));
//                       // Yellow color for conductor
//                    }
                    else if (p == 13||p==14||p==26||p==33||p==6||p==12||p==24||p==21) {

                        seatButton.setBackgroundResource(R.drawable.seat_booked_bg); // Yellow color for conductor
                    }



                    else {
                        seatButton.setBackgroundColor(Color.parseColor("#F8FDFF")); // Default color for other seats
                    }

                    p++;
                    seatButton.setGravity(Gravity.CENTER);
                    seatButton.setTextColor(Color.BLACK);

                    seatButton.setOnClickListener(v -> {
                        // Skip updating for the conductor's seat
                        if ("C".equals(seatButton.getText().toString())) {
                            Toast.makeText(Bus_Seating_view.this, "Conductor's seat cannot be selected!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (v.isSelected()) {
                            v.setSelected(false);
                            seatButton.setBackgroundColor(Color.parseColor("#F8FDFF")); // Deselect seat
                            selectedSeats.decrementAndGet(); // Decrease seat count
                        } else {
                            v.setSelected(true);
                            seatButton.setBackgroundColor(Color.parseColor("#78CE9C")); // Select seat
                            selectedSeats.incrementAndGet(); // Increase seat count
                        }

                        // Calculate and update total amount
                        int totalAmount = selectedSeats.get() * seatPrice;

//                        amountTextView.setText("₹ " + totalAmount);
//                        passengerCountTextView.setText(selectedSeats.get() + " Passenger(s)|");
//                        TotalAmount.setText("₹ " + totalAmount);
                        count_seat.setText("₹ " + totalAmount);
                    });


                    // Add the button to the GridLayout
                    seatingLayout.addView(seatButton);
                }
            }
        }

        // Button logic for lower, upper, and info sections
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
        });

        btnUpper.setOnClickListener(v -> {
            btnUpper.setBackgroundResource(R.drawable.btn_lower_active);
            btnUpper.setTextColor(getResources().getColor(R.color.white));

            btnLower.setBackgroundResource(R.drawable.btn_inactive);
            btnLower.setTextColor(getResources().getColor(R.color.gray));

            btnInfo.setBackgroundResource(R.drawable.btn_inactive);
            btnInfo.setTextColor(getResources().getColor(R.color.gray));
            Intent intent = new Intent(Bus_Seating_view.this, Sleeper_seatView.class);
            startActivity(intent);
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
                LinearLayout popupLayout = new LinearLayout(Bus_Seating_view.this);
                popupLayout.setOrientation(LinearLayout.VERTICAL);
                popupLayout.setBackgroundColor(Color.WHITE);
                popupLayout.setPadding(20, 20, 20, 20);
                popupLayout.setElevation(10);

                // Define colors and labels
                int[] colors = {Color.parseColor("#D1C4E9"), Color.parseColor("#F8BBD0"),
                        Color.parseColor("#FFFFFF"), Color.parseColor("#BDBDBD"),
                        Color.parseColor("#C8E6C9")};
                String[] labels = {"CHALLENGED / SENIORS", "WOMEN", "AVAILABLE", "UNAVAILABLE", "SELECTED"};

                // Add rows dynamically
                for (int i = 0; i < labels.length; i++) {
                    LinearLayout row = new LinearLayout(Bus_Seating_view.this);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setGravity(Gravity.CENTER_VERTICAL);
                    row.setPadding(5, 10, 5, 10);

                    // Create colored box
                    View colorBox = new View(Bus_Seating_view.this);
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
                    TextView textView = new TextView(Bus_Seating_view.this);
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












        /////////////////////////////////////
        String startTime=getIntent().getStringExtra("startTime");
        String endTime=getIntent().getStringExtra("endTime");
        Button proceedbutton=findViewById(R.id.proceed_button);
        proceedbutton.setOnClickListener(view -> {
            Intent i1=new Intent(Bus_Seating_view.this, Droping_point.class);

//
            i1.putExtra("busName", travelsname);
            i1.putExtra("date", date);
            i1.putExtra("startTime", startTime);
            i1.putExtra("endTime", endTime);
            i1.putExtra("to",to);
            i1.putExtra("from",from);
            startActivity(i1);

        });
    }

}
