package com.example.vcartbusbooking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class SourceAndDestination extends AppCompatActivity {
    EditText editTextFrom, editTextTo;
    Button buttonSearchBus;
    String selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_source_and_destination);

        ImageView calendarImage = findViewById(R.id.calendarImage);
        TextView datevisible=findViewById(R.id.text_date);
        editTextFrom = findViewById(R.id.editTextFrom);
        editTextTo = findViewById(R.id.editTextTo);
        buttonSearchBus = findViewById(R.id.buttonSearchBus);


        // Set OnClickListener on the calendar image
        calendarImage.setOnClickListener(view -> {
            // Get the current date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Open DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    SourceAndDestination.this,
                    (DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) -> {
                        // Handle selected date here
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        // You can display the selectedDate in a TextView or use it as needed
                        datevisible.setText(selectedDate);
                    },
                    year,
                    month,
                    day
            );
            datePickerDialog.show();
        });


        buttonSearchBus.setOnClickListener(view -> {
            String from = editTextFrom.getText().toString();
            String to = editTextTo.getText().toString();

            if (from.isEmpty() || to.isEmpty() ) {
                Toast.makeText(SourceAndDestination.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Pass data to the next activity
                Intent intent = new Intent(SourceAndDestination.this, BusDetailsActivity.class);
                intent.putExtra("from", from);
                intent.putExtra("to", to);
                intent.putExtra("date", selectedDate);
                startActivity(intent);
            }
        });







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}