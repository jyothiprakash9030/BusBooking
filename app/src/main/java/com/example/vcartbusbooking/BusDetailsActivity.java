package com.example.vcartbusbooking;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BusDetailsActivity extends AppCompatActivity {
    TextView fromdata, todata, dateview;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        fromdata = findViewById(R.id.from);
        todata = findViewById(R.id.to);
        dateview = findViewById(R.id.datevisible);

        // Get data from the intent
        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String date = getIntent().getStringExtra("date");

        fromdata.setText(from);
        todata.setText(to);
        dateview.setText(date);



            // Populate the RecyclerView
            itemList = new ArrayList<>();
            itemList.add(new Item("Parveen Travels:", 300, "11:00Am", "4:00pm"));
            itemList.add(new Item("Amarnath Travels", 500, "12:00pm", "4:00pm"));
            itemList.add(new Item("Uts Tours And Travels", 300, "11:00pm", "4:00pm"));
            itemList.add(new Item("jp", 300, "11:00Am", "4:00pm"));

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(BusDetailsActivity.this, itemList, from, to, date);

        recyclerView.setAdapter(adapter);




//        } else {
//            // Show a message if no buses are available
//            Toast.makeText(this, "No buses available on this route", Toast.LENGTH_SHORT).show();
//        }
        CheckBox acCheckBox = findViewById(R.id.ac_checkbox);
        CheckBox sleeperCheckBox = findViewById(R.id.sleeper);

        acCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sleeperCheckBox.setChecked(false); // Uncheck sleeperCheckBox
                    itemList = new ArrayList<>();
                    itemList.add(new Item("Parveen Travels:", 300, "11:00Am", "4:00pm"));
                } else {
                    itemList = new ArrayList<>();
                    itemList.add(new Item("Parveen Travels:", 300, "11:00Am", "4:00pm"));
                    itemList.add(new Item("Amarnath Travels", 500, "12:00pm", "4:00pm"));
                    itemList.add(new Item("Uts Tours And Travels", 300, "11:00pm", "4:00pm"));
                    itemList.add(new Item("jp", 300, "11:00Am", "4:00pm"));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(BusDetailsActivity.this));
                adapter = new ItemAdapter(BusDetailsActivity.this, itemList, from, to, date);


                recyclerView.setAdapter(adapter);
            }
        });

        sleeperCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    acCheckBox.setChecked(false); // Uncheck acCheckBox
                    itemList = new ArrayList<>();
                    itemList.add(new Item("Amarnath Travels", 500, "12:00pm", "4:00pm"));
                    itemList.add(new Item("Uts Tours And Travels", 300, "11:00pm", "4:00pm"));
                } else {
                    itemList = new ArrayList<>();
                    itemList.add(new Item("Parveen Travels:", 300, "11:00Am", "4:00pm"));
                    itemList.add(new Item("Amarnath Travels", 500, "12:00pm", "4:00pm"));
                    itemList.add(new Item("Uts Tours And Travels", 300, "11:00pm", "4:00pm"));
                    itemList.add(new Item("jp", 300, "11:00Am", "4:00pm"));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(BusDetailsActivity.this));
                adapter = new ItemAdapter(BusDetailsActivity.this, itemList, from, to, date);

                recyclerView.setAdapter(adapter);
            }
        });

        Button filter = findViewById(R.id.filter);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a ScrollView to wrap the popup content
                ScrollView scrollView = new ScrollView(BusDetailsActivity.this);
                scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                scrollView.setPadding(0, 0, 0, 0);

                // Create a LinearLayout for the popup content
                LinearLayout popupLayout = new LinearLayout(BusDetailsActivity.this);
                popupLayout.setOrientation(LinearLayout.VERTICAL);
                popupLayout.setBackgroundColor(Color.WHITE);
                popupLayout.setPadding(20, 20, 20, 20);

                // Add sections to the popup
                addSectionToPopup(popupLayout, "Seat Type", new String[]{"AC", "NON AC", "SLEEPER", "SEATER"});
                addSectionToPopup(popupLayout, "Special", new String[]{"CHALLENGED/SENIORS", "WOMEN"});
                addSectionToPopup(popupLayout, "Departure Time", new String[]{
                        "Morning (6:00 AM - 12:00 PM)",
                        "Afternoon (12:00 PM - 4:00 PM)",
                        "Evening (4:00 PM - 8:00 PM)",
                        "Night (8:00 PM - 6:00 AM)"
                });
                addSectionToPopup(popupLayout, "Arrival Time", new String[]{
                        "Morning (6:00 AM - 12:00 PM)",
                        "Afternoon (12:00 PM - 4:00 PM)",
                        "Evening (4:00 PM - 8:00 PM)",
                        "Night (8:00 PM - 6:00 AM)"
                });

                // Add section for Boarding and Dropping Points with arrows
                addArrowSection(popupLayout, "Based on Points");

                // Add the LinearLayout to the ScrollView
                scrollView.addView(popupLayout);

                // Create PopupWindow with fixed size (240 width, 532 height)
                PopupWindow popupWindow = new PopupWindow(scrollView,
                        dpToPx(240),  // Convert width from dp to pixels
                        dpToPx(532),  // Convert height from dp to pixels
                        true);

                // Show the popup window below the filter button
                popupWindow.showAsDropDown(filter, 0, 10);
            }

            private void addSectionToPopup(LinearLayout parentLayout, String sectionTitle, String[] options) {
                // Add section title
                TextView title = new TextView(BusDetailsActivity.this);
                title.setText(sectionTitle);
                title.setTextSize(14);
                title.setTextColor(Color.BLACK);
                title.setPadding(0, 0, 0, 10);
                parentLayout.addView(title);

                // Add checkboxes for options
                for (String option : options) {
                    CheckBox checkBox = new CheckBox(BusDetailsActivity.this);
                    checkBox.setText(option);
                    checkBox.setTextSize(12);
                    checkBox.setTextColor(Color.BLACK);
                    parentLayout.addView(checkBox);
                }
            }

            private void addArrowSection(LinearLayout parentLayout, String sectionTitle) {
                // Add section title
                TextView title = new TextView(BusDetailsActivity.this);
                title.setText(sectionTitle);
                title.setTextSize(14);
                title.setTextColor(Color.BLACK);
                title.setPadding(0, 0, 0, 10);
                parentLayout.addView(title);

                // Add rows for Boarding Points and Dropping Points
                addArrowRow(parentLayout, "Boarding Points", R.drawable.baseline_arrow_outward_24);
                addArrowRow(parentLayout, "Dropping Points", R.drawable.down_side_arrow);
            }

            private void addArrowRow(LinearLayout parentLayout, String optionText, int arrowDrawable) {
                LinearLayout row = new LinearLayout(BusDetailsActivity.this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setPadding(5, 10, 5, 10);

                // Add the arrow icon
                ImageView arrowIcon = new ImageView(BusDetailsActivity.this);
                arrowIcon.setImageResource(arrowDrawable);
                arrowIcon.setLayoutParams(new LinearLayout.LayoutParams(90, 100));
                arrowIcon.setPadding(10, 0, 20, 0);
                if ("Boarding Points".equals(optionText)) {
                    arrowIcon.setColorFilter(Color.RED); // Red color for Boarding Points
                }
                // Add the option text
                TextView textView = new TextView(BusDetailsActivity.this);
                textView.setText(optionText);
                textView.setTextSize(14);
                textView.setTextColor(Color.BLACK);

                // Add arrow and text to the row
                row.addView(arrowIcon);
                row.addView(textView);

                // Add the row to the parent layout
                parentLayout.addView(row);
            }

            // Utility function to convert dp to pixels
            private int dpToPx(int dp) {
                float density = getResources().getDisplayMetrics().density;
                return Math.round(dp * density);
            }
        });

        Button sort = findViewById(R.id.sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a LinearLayout for the popup
                LinearLayout popupLayout = new LinearLayout(BusDetailsActivity.this);
                popupLayout.setOrientation(LinearLayout.VERTICAL);
                popupLayout.setBackgroundColor(Color.WHITE);
                popupLayout.setPadding(200, 20, 20, 20);
                popupLayout.setElevation(10);

                // Define colors and labels
                int[] colors = {Color.parseColor("#D1C4E9"), Color.parseColor("#C8E6C9"),
                        Color.parseColor("#FFFFFF"), Color.parseColor("#BDBDBD")};

                String[] labels = {"Low price-First", "High price-First", "Departure Earlier", "Departure Last"};

                // Add rows dynamically
                for (int i = 0; i < labels.length; i++) {
                    LinearLayout row = new LinearLayout(BusDetailsActivity.this);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setGravity(Gravity.CENTER_VERTICAL);
                    row.setPadding(5, 10, 5, 10);

                    // Create colored box
                    View colorBox = new View(BusDetailsActivity.this);
                    LinearLayout.LayoutParams boxParams = new LinearLayout.LayoutParams(40, 40);
                    boxParams.setMargins(10, 0, 20, 0);
                    colorBox.setLayoutParams(boxParams);

                    // Set rounded corner background
                    GradientDrawable shape = new GradientDrawable();
                    shape.setColor(colors[i]);
                    shape.setCornerRadius(5);
                    colorBox.setBackground(shape);

                    // Create text label
                    TextView textView = new TextView(BusDetailsActivity.this);
                    textView.setText(labels[i]);
                    textView.setTextSize(14);
                    textView.setTextColor(Color.BLACK);

                    // Add color box & text to row
                    row.addView(colorBox);
                    row.addView(textView);

                    // Add row to popup layout
                    popupLayout.addView(row);
                }

                // Add the "Apply" button
                Button applyButton = new Button(BusDetailsActivity.this);
                applyButton.setText("Apply");
                applyButton.setTextColor(Color.WHITE);
                applyButton.setTextSize(10);
                applyButton.setBackgroundResource(R.drawable.apply_button_bg);

                // Set button size (66 height, 30 width)
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                        150 , // Convert width to dp (30 x 3 for pixel-to-dp approximation)
                        80  // Convert height to dp
                );
                buttonParams.gravity = Gravity.CENTER_HORIZONTAL;
                buttonParams.setMargins(20, 20, 20, 20); // Add some margin above the button
                applyButton.setLayoutParams(buttonParams);

                // Set click listener for the button
                applyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle "Apply" button click here
                        // For example, dismiss the popup
                        Toast.makeText(BusDetailsActivity.this, "Filters applied", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the button to the popup layout
                popupLayout.addView(applyButton);

                // Create PopupWindow
                PopupWindow popupWindow = new PopupWindow(popupLayout, 700, 500, true);
                popupWindow.showAsDropDown(sort, 0, 20); // Show below button
            }
        });

    }
}
