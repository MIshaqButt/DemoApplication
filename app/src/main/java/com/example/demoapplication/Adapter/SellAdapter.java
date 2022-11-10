package com.example.demoapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapplication.Models.BuyModel;
import com.example.demoapplication.Models.SellModel;
import com.example.demoapplication.R;

import java.util.ArrayList;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.MyHolder> {
    private Context context;
    private ArrayList<SellModel> arrayList;

    public SellAdapter(Context context, ArrayList<SellModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public SellAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_layout, parent, false);
        return new SellAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SellAdapter.MyHolder holder, int position) {
        SellModel movie = arrayList.get(position);
        holder.name.setText("Name : " +movie.getName());
        holder.price.setText("Price : " +movie.getPrice());
        holder.quantity.setText("Quantity : " +movie.getQuantity());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView name, price, quantity;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
