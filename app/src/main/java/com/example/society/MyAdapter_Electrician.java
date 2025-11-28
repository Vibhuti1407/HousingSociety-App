package com.example.society;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_Electrician extends RecyclerView.Adapter<MyAdapter_Electrician.MyViewHolder>{
    Context context;
    ArrayList<Electrician> list;

    public MyAdapter_Electrician(Context context, ArrayList<Electrician> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter_Electrician.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.electrician, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Electrician.MyViewHolder holder, int position) {
        Electrician electrician = list.get(position);
        holder.name.setText(electrician.getName());
        holder.contact.setText(electrician.getContact());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, contact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvname);
            contact = itemView.findViewById(R.id.tvcontact);
        }
    }
}
