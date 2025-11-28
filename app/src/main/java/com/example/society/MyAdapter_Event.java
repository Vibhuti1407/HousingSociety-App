package com.example.society;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_Event extends RecyclerView.Adapter<MyAdapter_Event.MyViewHolder>{

    Context context;
    ArrayList<Event> list;

    public MyAdapter_Event(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter_Event.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Event.MyViewHolder holder, int position) {
        Event event = list.get(position);
        holder.date.setText(event.getDate());
        holder.time.setText(event.getTime());
        holder.description.setText(event.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date, time, description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tvdate);
            time = itemView.findViewById(R.id.tvtime);
            description = itemView.findViewById(R.id.tvdescription);
        }
    }
}
