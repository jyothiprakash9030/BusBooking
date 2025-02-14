package com.example.vcartbusbooking;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
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




        HorizontalScrollView scrollView = findViewById(R.id.horizontalScrollView);

        scrollView.post(() -> {
            ValueAnimator animator = ValueAnimator.ofInt(0, scrollView.getChildAt(0).getWidth());
            animator.setDuration(5000); // 5 seconds for slow scrolling
            animator.setRepeatCount(ValueAnimator.INFINITE); // Repeat infinitely
            animator.setRepeatMode(ValueAnimator.RESTART); // Restart from beginning
            animator.addUpdateListener(animation ->
                    scrollView.scrollTo((int) animation.getAnimatedValue(), 0)
            );
            animator.start();
        });







//        ///////////////////////
        bus_seatingtoadress.setText(from);
        bus_seatingfromadress.setText(to);
        bus_seatingvisibledate.setText(date);

        bus_name.setText(travelsname);


        // Define number of rows and columns
        int rows = 8; // Total rows
        int columns = 5; // Total columns
        int p = 0;

        GridLayout seatingLayout = findViewById(R.id.seatingLayout);
        seatingLayout.setRowCount(rows);
        seatingLayout.setColumnCount(columns);

// Create ImageView for driver's seat
        ImageView driverSeatImage = new ImageView(this);
        driverSeatImage.setImageResource(R.drawable.imagedriver);
        GridLayout.LayoutParams driverParams = new GridLayout.LayoutParams();
        driverParams.rowSpec = GridLayout.spec(0, 1);
        driverParams.columnSpec = GridLayout.spec(columns - 1, 1);
        driverParams.width = 40;  // Adjusted size for better scaling
        driverParams.height = 80;
        driverSeatImage.setLayoutParams(driverParams);
        seatingLayout.addView(driverSeatImage);

// Seat price calculation
        int seatPrice = 500;
        AtomicInteger selectedSeats = new AtomicInteger(0);
        TextView passengerCountTextView = findViewById(R.id.passenger_count);
        TextView amountTextView = findViewById(R.id.TotalAmount);
        passengerCountTextView.setText("0 Passenger(s) |");
        amountTextView.setText("₹ 0");

// Adjust seat size dynamically based on screen width
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int seatSize = screenWidth / (columns * 2);  // Adjust based on screen size

// Generate seats dynamically
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j == 2 && i != rows - 1) {
                    View gap = new View(this);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = seatSize / 2;
                    params.height = seatSize / 2;
                    gap.setLayoutParams(params);
                    seatingLayout.addView(gap);
                } else {
                    Button seatButton = new Button(this);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = seatSize;
                    params.height = seatSize;
                    params.setMargins(5, 15, 5, 15);
                    seatButton.setLayoutParams(params);
                    seatButton.setText("D" + p);

                    if (p == 0) {
                        seatButton.setText("C");
                        seatButton.setBackgroundColor(Color.parseColor("#FFDD00")); // Conductor seat
                    } else if (p == 19) {
                        seatButton.setBackgroundResource(R.drawable.women_seat_bg);
                    } else if (p == 2) {
                        seatButton.setBackgroundResource(R.drawable.challenged_or_senior_seat_bg);
                    } else if (p == 13 || p == 14 || p == 26 || p == 33 || p == 6 || p == 12 || p == 24 || p == 21) {
                        seatButton.setBackgroundResource(R.drawable.seat_booked_bg);
                    } else {
                        seatButton.setBackgroundColor(Color.parseColor("#F8FDFF"));
                    }

                    p++;
                    seatButton.setGravity(Gravity.CENTER);
                    seatButton.setTextColor(Color.BLACK);

                    seatButton.setOnClickListener(v -> {
                        if ("C".equals(seatButton.getText().toString())) {
                            Toast.makeText(this, "Conductor's seat cannot be selected!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (v.isSelected()) {
                            v.setSelected(false);
                            seatButton.setBackgroundColor(Color.parseColor("#F8FDFF"));
                            selectedSeats.decrementAndGet();
                        } else {
                            v.setSelected(true);
                            seatButton.setBackgroundColor(Color.parseColor("#78CE9C"));
                            selectedSeats.incrementAndGet();
                        }

                        int totalAmount = selectedSeats.get() * seatPrice;
                        amountTextView.setText("₹ " + totalAmount);
                        passengerCountTextView.setText(selectedSeats.get() + " Passenger(s) |");
                    });

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
            i1.putExtra("amount", TotalAmount.getText().toString()); // Fixed line

            startActivity(i1);

        });
    }

}
