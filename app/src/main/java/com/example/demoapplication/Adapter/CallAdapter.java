package com.example.demoapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapplication.Models.CallModel;
import com.example.demoapplication.R;

import java.util.ArrayList;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.MyHolder> {
    private Context context;
    private ArrayList<CallModel> arrayList;

    public CallAdapter(Context context, ArrayList<CallModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CallAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_layout, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CallAdapter.MyHolder holder, int position) {
        CallModel movie = arrayList.get(position);
        holder.name.setText("Name : " +movie.getName());
        holder.number.setText("Number : " +movie.getNumber());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView name, number;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
        }
    }
}
