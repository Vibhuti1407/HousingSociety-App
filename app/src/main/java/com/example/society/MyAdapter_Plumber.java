package com.example.society;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_Plumber extends RecyclerView.Adapter<MyAdapter_Plumber.MyViewHolder>{
    Context context;
    ArrayList<Plumber> list;

    public MyAdapter_Plumber(Context context, ArrayList<Plumber> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter_Plumber.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.plumber, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Plumber.MyViewHolder holder, int position) {
        Plumber plumber = list.get(position);
        holder.name.setText(plumber.getName());
        holder.contact.setText(plumber.getContact());
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
