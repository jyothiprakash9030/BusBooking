package com.example.vcartbusbooking;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vcartbusbooking.utils.ApiClient;
import com.example.vcartbusbooking.utils.ApiService;
import com.example.vcartbusbooking.utils.City;
import com.example.vcartbusbooking.utils.CityResponse;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourceAndDestination extends AppCompatActivity {
    EditText editTextFrom, editTextTo;
    Button buttonSearchBus;
    String selectedDate;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_source_and_destination);




//        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Create an empty request body
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");

        // Make the API call
//        Call<ResponseBody> call = apiService.postTicketSource(body);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        // Log the response
//                        String responseString = response.body().string();
//                        System.out.println("Response: " + responseString);
//                        Log.d("API Response", "Message: " + responseString);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    System.err.println("Request failed: " + response.code());
//                    Log.e("API Response", "Response unsuccessful: Code " + response.code() + ", Message: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("API Response", "Failure: " + t.getMessage(), t);
//                t.printStackTrace();
//            }
//        });


















        /////////////////////
        ImageView calendarImage = findViewById(R.id.calendarImage);
        TextView datevisible=findViewById(R.id.text_date);

        editTextFrom = findViewById(R.id.editTextFrom);
        editTextTo = findViewById(R.id.editTextTo);
        buttonSearchBus = findViewById(R.id.buttonSearchBus);



// Inside your calendarImage click listener:
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
                        // Create a calendar instance and set the selected date
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

                        // Format date with required format: "dd MMM,EE"
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM,EE", Locale.getDefault());
                        selectedDate = sdf.format(selectedCalendar.getTime());

                        // Display in TextView
                        datevisible.setText(selectedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        TextView textDate = findViewById(R.id.text_date);
        LinearLayout selectTodayDate = findViewById(R.id.container_frame19);
        LinearLayout containerFrame = findViewById(R.id.container_frame); // Another LinearLayout

// Initially set the text as today's date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, EE", Locale.getDefault());
        String todayDate = sdf.format(calendar.getTime());
        String tomorrowDate = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1); // Increment the calendar by 1 day to get tomorrow's date
        tomorrowDate = sdf.format(calendar.getTime());

// Set today's date initially
        textDate.setText(todayDate);

// Boolean flag to keep track of the displayed date (Declare outside the click listeners)
        boolean[] isTomorrowDisplayed = {false};  // Use an array to modify the value inside the listener

// Set today's date when the selectTodayDate LinearLayout is clicked
        selectTodayDate.setOnClickListener(view -> {
            // Always show today's date when selectTodayDate is clicked
            textDate.setText(todayDate);
            isTomorrowDisplayed[0] = false; // Reset flag to indicate today is displayed
        });

// Set tomorrow's date when the containerFrame LinearLayout is clicked
        String finalTomorrowDate = tomorrowDate;
        containerFrame.setOnClickListener(view -> {
            // Only set tomorrow's date if it's not already showing
            if (!isTomorrowDisplayed[0]) {
                textDate.setText(finalTomorrowDate);
                isTomorrowDisplayed[0] = true; // Set flag to indicate tomorrow is displayed
            }
        });



        buttonSearchBus.setOnClickListener(view -> {
            String from = editTextFrom.getText().toString();
            String to = editTextTo.getText().toString();
            String textDate1=textDate.getText().toString();

            if (from.isEmpty() || to.isEmpty() ) {
                Toast.makeText(SourceAndDestination.this, "Please fill all fields and select a date", Toast.LENGTH_SHORT).show();
            } else {


                // Pass data to the next activity
                Intent intent = new Intent(SourceAndDestination.this, BusDetailsActivity.class);
                intent.putExtra("from", from);
                intent.putExtra("to", to);
                intent.putExtra("date", textDate1);
                startActivity(intent);


            }

        });


        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.editTextFrom);
        AutoCompleteTextView autoCompleteTextView1 = findViewById(R.id.editTextTo);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        apiService.GetCities(body).enqueue(new Callback<CityResponse>()
        {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Log full API response
                    Log.d("API Response", "Raw response: " + new Gson().toJson(response.body()));

                    CityResponse apiResponse = response.body();
                    List<City> cityList = apiResponse.getCityData().getCities();

                    if (cityList == null || cityList.isEmpty()) {
                        Log.e("API Error", "City list is empty!");
                        return;
                    }

                    // Create a HashMap to store city names with their corresponding IDs
                    HashMap<String, String> cityMap = new HashMap<>();
                    String[] Cities = new String[cityList.size()];

                    for (int i = 0; i < cityList.size(); i++) {
                        Cities[i] = cityList.get(i).getName();
                        cityMap.put(cityList.get(i).getName(), cityList.get(i).getId()); // Store ID
                        Log.d("API Response City", "Name: " + Cities[i] + ", ID: " + cityList.get(i).getId());
                    }

                    // Create an ArrayAdapter to bind data to AutoCompleteTextView
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SourceAndDestination.this, android.R.layout.simple_dropdown_item_1line, Cities);

                    // Update UI on main thread
                    runOnUiThread(() -> {
                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView1.setAdapter(adapter);
                    });

                    // Set the threshold for suggestions
                    autoCompleteTextView.setThreshold(1);
                    autoCompleteTextView1.setThreshold(1);

                    // Handle item selection
                    autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
                        String selectedCity = (String) parent.getItemAtPosition(position);
                        String cityId = cityMap.get(selectedCity);
                        Log.d("API Response Selected City", "Name: " + selectedCity + ", ID: " + cityId);
                    });

                    autoCompleteTextView1.setOnItemClickListener((parent, view, position, id) -> {
                        String selectedCity = (String) parent.getItemAtPosition(position);
                        String cityId = cityMap.get(selectedCity);
                        Log.d("API Response Selected City", "Name: " + selectedCity + ", ID: " + cityId);
                    });

                } else {
                    Log.e("API Error", "Response unsuccessful: Code " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }

        });




//        String[] countries = {"banglore" ,"Chennai", "tirutani", "tirupathi"};
//
//        // Find the AutoCompleteTextView by ID
//        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.editTextFrom);
//
//        // Create an ArrayAdapter to bind data to AutoCompleteTextView
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, countries);
//
//        // Set the adapter to the AutoCompleteTextView
//        autoCompleteTextView.setAdapter(adapter);
//
//        // Optional: Set the number of characters to trigger suggestions
//        autoCompleteTextView.setThreshold(1); // Default is 2
//        String[] countries1 = {"banglore" ,"Chennai", "tirutani", "tirupathi"};


        // Find the AutoCompleteTextView by ID


        // Create an ArrayAdapter to bind data to AutoCompleteTextView


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






































        LinearLayout home=findViewById(R.id.home);
        LinearLayout Booking=findViewById(R.id.Booking);
        LinearLayout help=findViewById(R.id.help);
        LinearLayout account=findViewById(R.id.account);

        Booking.setOnClickListener(view -> {


            ImageView booking_image=findViewById(R.id.Booking_image);
            TextView Booking_textview=findViewById(R.id.Booking_textview);
            // Change background color to red
            booking_image.setImageResource(R.drawable.booking_red_color);
            Booking_textview.setTextColor(Color.RED);

            // Delay for 1000 milliseconds (1 second) before starting the activity
            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(SourceAndDestination.this, Booking_page.class);
                startActivity(i1);

                // Reset background color (optional)
//                Booking.setBackgroundColor(Color.TRANSPARENT);
                booking_image.setImageResource(R.drawable.booking);
                Booking_textview.setTextColor(Color.BLACK);

            }, 200);
        });




        help.setOnClickListener(view -> {


            ImageView help_img=findViewById(R.id.help_img);
            TextView help_text=findViewById(R.id.help_text);
            // Change background color to red
            help_img.setImageResource(R.drawable.help_red_color);
            help_text.setTextColor(Color.RED);

            // Delay for 1000 milliseconds (1 second) before starting the activity
            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(SourceAndDestination.this, help_page.class);
                startActivity(i1);

                // Reset background color (optional)

                help_img.setImageResource(R.drawable.help);
                help_text.setTextColor(Color.BLACK);

            }, 200);
        });




        account.setOnClickListener(view -> {


            ImageView account_img=findViewById(R.id.account_img);
            TextView  account_text =findViewById(R.id.account_text);
            // Change background color to red
            account_img.setImageResource(R.drawable.account_red_color);
            account_text.setTextColor(Color.RED);

            // Delay for 1000 milliseconds (1 second) before starting the activity
            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(SourceAndDestination.this, Account.class);
                startActivity(i1);

                // Reset background color (optional)
//                Booking.setBackgroundColor(Color.TRANSPARENT);
                account_img.setImageResource(R.drawable.account);
                account_text.setTextColor(Color.BLACK);

            }, 200);
        });








    }
















  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void gotosource(View view) {
        // Change the button background
        view.setBackgroundResource(R.drawable.shopping_button_corner_radius_bg);

        // Change the TextView color
        TextView textView = findViewById(R.id.text_shopping);
        textView.setTextColor(getResources().getColor(R.color.white)); // Replace with your color resource

        // Set the background to invisible after 3000 milliseconds
        new android.os.Handler().postDelayed(() -> {
            view.setBackgroundResource(android.R.color.white);
            textView.setTextColor(getResources().getColor(R.color.black));// Set the background to transparent
        }, 1000);

        // Navigate to the next screen
        Intent intent = new Intent(SourceAndDestination.this, Floating_screen_grocery.class);
        startActivity(intent);

    }




}