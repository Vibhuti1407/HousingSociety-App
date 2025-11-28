package com.example.society;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_Cleaner extends RecyclerView.Adapter<MyAdapter_Cleaner.MyViewHolder>{
    Context context;
    ArrayList<Cleaner> list;

    public MyAdapter_Cleaner(Context context, ArrayList<Cleaner> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter_Cleaner.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cleaner, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Cleaner.MyViewHolder holder, int position) {
        Cleaner cleaner = list.get(position);
        holder.name.setText(cleaner.getName());
        holder.contact.setText(cleaner.getContact());
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
