package com.example.vcartbusbooking;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class help_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_help_page);
        // Define the FAQ items
        String[] faqItems = {
                "What is Vilvakart?",
                "How do I place an order on Vilvakart?",
                "What payment options are available?",
                "How can I track my order?",
                "What is Vilvakart's return policy?",
                "How do I contact Vilvakart customer support?",
                "Are there any shipping charges?",
                "Does Vilvakart offer discounts or promotions?",
                "How do I cancel my order?",
                "How can I create an account on Vilvakart?"
        };

        // Find the ListView
        ListView faqList = findViewById(R.id.faq_list);

        // Remove the divider between rows
        faqList.setDivider(null);
        faqList.setDividerHeight(0);

        // Set the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, faqItems);
        faqList.setAdapter(adapter);

        // Adjust the height of the ListView
        setListViewHeightBasedOnChildren(faqList);
    }

    // Method to adjust ListView height dynamically
    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    }