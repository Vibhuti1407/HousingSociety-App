package com.example.society;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_Payment extends RecyclerView.Adapter<MyAdapter_Payment.MyViewHolder>{
    Context context;
    ArrayList<Payment> list;

    public MyAdapter_Payment(Context context, ArrayList<Payment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.payment, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Payment.MyViewHolder holder, int position) {
        Payment payment = list.get(position);
        holder.flat.setText(payment.getFlat());
        holder.status.setText(payment.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView flat, status;

        public MyViewHolder(@NonNull View view) {
            super(view);
            flat = view.findViewById(R.id.tvflat);
            status = view.findViewById(R.id.tvstatus);
        }
    }
}
