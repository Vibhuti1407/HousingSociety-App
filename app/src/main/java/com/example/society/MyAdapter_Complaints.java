package com.example.society;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_Complaints extends RecyclerView.Adapter<MyAdapter_Complaints.MyViewHolder>{
    Context context;
    ArrayList<Complaints> list;

    public MyAdapter_Complaints(Context context, ArrayList<Complaints> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter_Complaints.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.complaints, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Complaints.MyViewHolder holder, int position) {
        Complaints complaints = list.get(position);
        holder.date.setText(complaints.getDate());
        holder.flatNo.setText(complaints.getFlatNo());
        holder.description.setText(complaints.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date, flatNo, description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tvdate);
            flatNo = itemView.findViewById(R.id.tvtime);
            description = itemView.findViewById(R.id.tvdescription);
        }
    }
}
