package com.example.vcartbusbooking;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Disappointing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.disappointing);

        //Radio button Color
//
//
//
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
//                    if(checkedId==R.id.boarding_point){
//
//
//                        navigateToPage(Droping_point.class, "Banglore");
//
//
//                    }
//
//
//                    else if (checkedId==R.id.boarding_point_2)
//                    {
//                        // Redirect to the page for SilkBoard
//                        navigateToPage(Droping_point.class,"Banglore");
//
//                    }
//                    else if (checkedId==R.id.boarding_point_3)
//                    {
//                        // Redirect to the page for SilkBoard
//                        navigateToPage(Droping_point.class, "Banglore");
//
//                    }
//
//                    else {
//                        Toast.makeText(this, "Unknown selection!", Toast.LENGTH_SHORT).show();
//                    }
//
//            }
//        });
//    }
//
//    // Helper method to navigate to another page
//    private void navigateToPage(Class<?> targetActivity, String locationName) {
//        Toast.makeText(this, "Redirecting to " + locationName, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, targetActivity);
//        startActivity(intent);
//
//    }
    }
}