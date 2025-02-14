package com.example.vcartbusbooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Droping_point extends AppCompatActivity {
TextView bus_seatingtoadress,bus_seatingfromadress,bus_seatingvisibledate,bus_name,droping_Timing,pickupt_timing,amounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_droping_point);

//        // Reference to the RadioGroup
//        RadioGroup boardingPointGroup = findViewById(R.id.boarding_point_group);
//
//        // Set an OnCheckedChangeListener to handle clicks on the RadioButtons
//        boardingPointGroup.setOnCheckedChangeListener((group, checkedId) -> {
//            RadioButton selectedRadioButton = findViewById(checkedId);
//
//            // Check which RadioButton was selected and navigate to the corresponding page
//            if (selectedRadioButton != null) {
//
//
//                if(checkedId==R.id.boarding_point){
//
//
//                    navigateToPage(Passenger_details.class, "Banglore");
//
//                }
//
//
//                else if (checkedId==R.id.boarding_point_2)
//                {
//                    // Redirect to the page for SilkBoard
//                    navigateToPage(Passenger_details.class,"Banglore");
//                }
//                else if (checkedId==R.id.boarding_point_3)
//                {
//                    // Redirect to the page for SilkBoard
//                    navigateToPage(Passenger_details.class, "Banglore");
//                }
//
//                else {
//                    Toast.makeText(this, "Unknown selection!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//    }
//    private void navigateToPage(Class<?> targetActivity, String locationName) {
//        Toast.makeText(this, "Redirecting to " + locationName, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, targetActivity);
//        startActivity(intent);
//
//    }
        bus_seatingtoadress=findViewById(R.id.bus_seatingtoadress);
        bus_seatingfromadress=findViewById(R.id.bus_seatingfromadress);
        bus_seatingvisibledate=findViewById(R.id.bus_seatingvisibledate);
        bus_name=findViewById(R.id.bus_name);
        amounts=findViewById(R.id.amount);
//        droping_Timing=findViewById(R.id.droping_Timing);
//        pickupt_timing=findViewById(R.id.pickupt_timing);

        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String date = getIntent().getStringExtra("date");
String amount=getIntent().getStringExtra("amount");
        String travelsname=getIntent().getStringExtra("busName");
//       String startTime=getIntent().getStringExtra("startTime");
//      String endTime=getIntent().getStringExtra("endTime");


        bus_seatingtoadress.setText(from);
        bus_seatingfromadress.setText(to);
        bus_seatingvisibledate.setText(date);
        bus_name.setText(travelsname);
        amounts.setText(amount);
//     droping_Timing.setText(startTime);
//     pickupt_timing.setText(endTime);

     Button proceed_button=findViewById(R.id.proceed_button);

        proceed_button.setOnClickListener(view -> {




        Intent i=new Intent(Droping_point.this,Payment.class);
        startActivity(i);
        });
    }
}