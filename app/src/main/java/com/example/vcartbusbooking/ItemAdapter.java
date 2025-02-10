package com.example.vcartbusbooking;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {


    private Context context;




    private List<Item> itemList;
    private String from, to, date; // Add these variables

    // Modify constructor to accept 'from', 'to', and 'textDate1'
    public ItemAdapter(Context context, List<Item> itemList, String from, String to, String date) {
        this.context = context;
        this.itemList = itemList;
        this.from = from;
        this.to = to;
        this.date = date;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_iteam_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.travelsname.setText(item.getTravelsname());
        holder.pickuptiming.setText(item.getPickuptiming());
        holder.Amount.setText("₹" + item.getPrice());

        // **Set OnClickListener for RecyclerView item**
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Bus_Seating_view.class);

            // Pass Data to Bus_Seating_view
            intent.putExtra("busName", item.getTravelsname());
            intent.putExtra("amount", item.getPrice());
            intent.putExtra("startTime", item.getPickuptiming());
            intent.putExtra("endTime", item.getTotiming());

            // Pass 'from', 'to', and 'date' values
            intent.putExtra("from", from);
            intent.putExtra("to", to);
            intent.putExtra("date", date);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView travelsname, pickuptiming, Amount;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            travelsname = itemView.findViewById(R.id.travelsName);
            pickuptiming = itemView.findViewById(R.id.pickuptiming);
            Amount = itemView.findViewById(R.id.Amount);
        }
    }
}
